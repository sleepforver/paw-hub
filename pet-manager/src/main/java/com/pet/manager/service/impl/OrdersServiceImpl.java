package com.pet.manager.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import com.pet.common.constant.PawHubConstants;
import com.pet.common.exception.ServiceException;
import com.pet.common.utils.DateUtils;
import com.pet.common.utils.SnowflakeIdGenerator;
import com.pet.common.utils.uuid.UUID;
import com.pet.manager.domain.Policy;
import com.pet.manager.domain.ServiceTypes;
import com.pet.manager.domain.Services;
import com.pet.manager.mapper.PolicyMapper;
import com.pet.manager.mapper.ServiceTypesMapper;
import com.pet.manager.mapper.ServicesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.OrdersMapper;
import com.pet.manager.domain.Orders;
import com.pet.manager.service.IOrdersService;
import org.springframework.util.DigestUtils;

import static com.fasterxml.jackson.core.io.NumberInput.parseBigDecimal;

/**
 * 订单管理Service业务层处理
 *
 * @author kkk
 * @date 2025-02-25
 */
@Service
public class OrdersServiceImpl implements IOrdersService
{
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private SnowflakeIdGenerator idGenerator; // 注入生成器

    @Autowired
    private ServiceTypesMapper serviceTypesMapper;

    @Autowired
    private ServicesMapper servicesMapper;

    @Autowired
    private PolicyMapper policyMapper;
    /**
     * 查询订单管理
     *
     * @param orderId 订单管理主键
     * @return 订单管理
     */
    @Override
    public Orders selectOrdersByOrderId(Long orderId)
    {
        return ordersMapper.selectOrdersByOrderId(orderId);
    }

    /**
     * 查询订单管理列表
     *
     * @param orders 订单管理
     * @return 订单管理
     */
    @Override
    public List<Orders> selectOrdersList(Orders orders)
    {
        return ordersMapper.selectOrdersList(orders);
    }

    /**
     * 新增订单管理
     *
     * @param orders 订单管理
     * @return 结果
     */
    @Override
    public int insertOrders(Orders orders)
    {
        //检查一致性
        checkCreateOrder(orders);
        //从获取的服务名称，查询服务类型信息
        ServiceTypes serviceTypes = serviceTypesMapper.selectServiceTypesByServiceName(orders.getServiceName());
        //订单id，由雪花算法生成
        orders.setOrderId(Long.valueOf(idGenerator.nextIdStr()));
        // 组合用户ID/服务类型等业务因子
        String datePart = DateUtils.getDate().replaceAll("-", "");
        String userPart = serviceTypes.getServiceTypeCode(); // 取服务类型编号
        String randomPart = String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
        String orderNo = datePart + userPart + randomPart; // 2025022511117162
        orders.setOrderNo(Long.valueOf(orderNo));
        //设置服务类型编号
        orders.setServiceTypeCode(serviceTypes.getServiceTypeCode());
        //从获取的服务名称，查询服务信息
        Services services = servicesMapper.selectServicesByServiceTypeId(serviceTypes.getServiceTypeId());
        //设置服务id
        orders.setServiceId(services.getServiceId());
        //判断订单类型，如果订单类型为寄养，则需要填写相应的房间号和房间类型
        if (Objects.equals(orders.getOrderType(), PawHubConstants.ORDER_TYPE_FOSTER)) {
            //设置房间类型
            orders.setRoomType(orders.getRoomType());
            //设置房间号
            orders.setRoomNumber(orders.getRoomNumber());
        }
        //设置服务金额
        orders.setPrice(serviceTypes.getPrice());
        //查询服务表，查看策略id是否为空
        if (services.getPolicyId() != null) {
            //如果策略id不为空，则查询策略表，查看策略方案
            Policy policy = policyMapper.selectPolicyByPolicyId(services.getPolicyId());
            //如果策略方案不为空，订单金额就为策略方案*订单金额
            if (policy.getDiscount() != null) {
                // 将 policy.getDiscount() 转换为 BigDecimal 类型，再进行乘法运算
                BigDecimal discount = new BigDecimal(policy.getDiscount())
                        .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                orders.setAmount(discount.multiply(serviceTypes.getPrice()));
            }
        }else{
            //如果策略id为空，则订单金额就是订单金额
            orders.setAmount(serviceTypes.getPrice());
        }
        //设置支付状态为未支付
        orders.setPayStatus(PawHubConstants.PAY_STATUS_UNPAID);
        //设置订单状态为未支付
        orders.setOrderStatus(PawHubConstants.ORDER_STATUS_UNPAID);
        return ordersMapper.insertOrders(orders);
    }


    /**
     * 修改订单管理
     *
     * @param orders 订单管理
     * @return 结果
     */
    @Override
    public int updateOrders(Orders orders)
    {
        return ordersMapper.updateOrders(orders);
    }

    /**
     * 批量删除订单管理
     *
     * @param orderIds 需要删除的订单管理主键
     * @return 结果
     */
    @Override
    public int deleteOrdersByOrderIds(Long[] orderIds)
    {
        return ordersMapper.deleteOrdersByOrderIds(orderIds);
    }

    /**
     * 删除订单管理信息
     *
     * @param orderId 订单管理主键
     * @return 结果
     */
    @Override
    public int deleteOrdersByOrderId(Long orderId)
    {
        return ordersMapper.deleteOrdersByOrderId(orderId);
    }

    /**
     * 退款
     * @param orders
     * @return
     */
    @Override
    public int refund(Orders orders) {
        if (orders.getPayStatus() == PawHubConstants.PAY_STATUS_PAID && orders.getPayStatus() != null){
            orders.setOrderStatus(PawHubConstants.ORDER_STATUS_SERVICE_CANCELLED);
            orders.setPayStatus(PawHubConstants.PAY_STATUS_REFUNDED);
        }else {
            throw new  ServiceException("订单状态错误");
        }
        return ordersMapper.updateOrders(orders);
    }

    /**
     * 取消订单
     * @param orders
     * @return
     */
    @Override
    public int cancel(Orders orders) {
        if (orders.getOrderStatus() == PawHubConstants.ORDER_STATUS_UNPAID  && orders.getOrderStatus() != null){
            orders.setOrderStatus(PawHubConstants.ORDER_STATUS_SERVICE_CANCELLED);
        }else  if ( orders.getOrderStatus() == PawHubConstants.ORDER_STATUS_PAID && orders.getOrderStatus() != null){
            refund(orders);
        }else {
            throw new  ServiceException("订单状态错误");
        }
        return ordersMapper.updateOrders(orders);
    }

    private static void checkCreateOrder(Orders orders) {
        //检查订单类型与服务名称是否一致
        //如果订单类型为常规，而服务名称为宠物住宿，则抛出异常
        if (Objects.equals(orders.getOrderType(), PawHubConstants.ORDER_TYPE_NORMAL)) {
            if (Objects.equals(orders.getServiceName(), "宠物住宿")) {
                throw new ServiceException("当前订单类型为常规，不要选择宠物住宿，请修改订单类型或服务名称");
            }
        }
        //如果订单类型为寄养，而服务名称不为宠物住宿，则抛出异常
        if (Objects.equals(orders.getOrderType(), PawHubConstants.ORDER_TYPE_FOSTER)) {
            if (!Objects.equals(orders.getServiceName(), "宠物住宿")) {
                throw new ServiceException("当前订单类型为寄养，应该选择宠物住宿，请修改订单类型或服务名称");
            }
        }
    }
}
