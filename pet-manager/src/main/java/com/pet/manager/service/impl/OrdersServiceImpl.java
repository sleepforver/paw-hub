package com.pet.manager.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import com.pet.common.constant.PawHubConstants;
import com.pet.common.exception.ServiceException;
import com.pet.common.utils.SnowflakeIdGenerator;
import com.pet.common.utils.StringUtils;
import com.pet.manager.domain.Policy;
import com.pet.manager.domain.ServiceTypes;
import com.pet.manager.domain.Services;
import com.pet.manager.domain.dto.ServicesTop4Dto;
import com.pet.manager.domain.vo.OrderStatisticsVO;
import com.pet.manager.domain.vo.ServicesTop10Vo;
import com.pet.manager.mapper.PolicyMapper;
import com.pet.manager.mapper.ServiceTypesMapper;
import com.pet.manager.mapper.ServicesMapper;
import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.OrdersMapper;
import com.pet.manager.domain.Orders;
import com.pet.manager.service.IOrdersService;

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
        //订单id，由雪花算法生成
        orders.setOrderId(Long.valueOf(idGenerator.nextIdStr()));
        // 组合用户ID/服务类型等业务因子
//        String datePart = DateUtils.getDate().replaceAll("-", "");
//        String userPart = serviceTypes.getServiceTypeCode(); // 取服务类型编号
//        String randomPart = String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
//        String orderNo = datePart + userPart + randomPart; // 2025022511117162
        orders.setOrderNo(orders.getOrderNo());
        //设置服务类型编号
//        orders.setServiceTypeCode(serviceTypes.getServiceTypeCode());
        //设置服务信息
        setServices(orders);
        //设置支付状态为未支付
        orders.setPayStatus(PawHubConstants.PAY_STATUS_UNPAID);
        //设置订单状态为未支付
        orders.setOrderStatus(PawHubConstants.ORDER_STATUS_UNPAID);
        return ordersMapper.insertOrders(orders);
    }

    private void setServices(Orders orders) {
        //从获取的服务名称，查询服务类型信息
        ServiceTypes serviceTypes = serviceTypesMapper.selectServiceTypesByServiceName(orders.getServiceName());
        //从获取的服务名称，查询服务信息
        Services services = servicesMapper.selectServicesByServiceTypeId(serviceTypes.getServiceTypeId());
        //设置服务id
//        orders.setServiceId(services.getServiceId());
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
        setServices(orders);
        return ordersMapper.updateOrders(orders);
    }

    /**
     * 通过订单编号修改订单管理
     *
     * @param orders 订单管理
     * @return 结果
     */
    @Override
    public int updateOrdersByOrderNo(Orders orders)
    {
        setServices(orders);
        return ordersMapper.updateOrdersByOrderNo(orders);
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
     * 支付
     * @param orders
     * @return
     */
    public int pay(Orders orders) {
        //对前端传来的数据进行校验

        //1.检查订单是否存在
        Orders order = ordersMapper.selectOrdersByOrderId(orders.getOrderId());
        if (order == null){
            throw new ServiceException("订单不存在");
        }
        //2.进行订单状态校验
        if (orders.getOrderStatus() != PawHubConstants.ORDER_STATUS_UNPAID && orders.getOrderStatus() != null){
            throw new ServiceException("订单状态错误");
        }
        //3.检查支付金额与前端的支付金额是否一致
        if(order.getAmount().equals(orders.getAmount().setScale(2)))
        //4.设置订单的支付状态，时间
        orders.setPayStatus(PawHubConstants.PAY_STATUS_PAID);
        orders.setUpdateTime(new Date());
        orders.setOrderStatus(PawHubConstants.ORDER_STATUS_PAID);
        return ordersMapper.updateOrders(orders);
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
        }else if ( orders.getOrderStatus() == PawHubConstants.ORDER_STATUS_PAID && orders.getOrderStatus() != null){
            refund(orders);
        }else {
            throw new  ServiceException("订单状态错误");
        }
        return ordersMapper.updateOrders(orders);
    }

    /**
     * 获取指定时间的订单数量
     * @return
     */
    @Override
    public OrderStatisticsVO getOrderStatistics(LocalDate begin, LocalDate end) {
        //当前的集合用于存放从begin到end范围内每天的日期
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);

        while(!begin.equals(end)){
            //日期计算，计算指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        //存放每天的订单总数
        List<Integer> orderCountList = new ArrayList<>();
        //存放每天的有效订单数
        List<Integer> validOrderCountList = new ArrayList<>();
        //遍历dateList集合，查询每天的有效订单数和订单总数
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            //查询每天订单总数  select count(id) from orders where order_time >? and order_time < ？
            Integer orderCount = getOrderCount(beginTime, endTime, null);

            //查询每天有效订单数 select count(id) from orders where order_time > ? and order_time < ? and status =
            Integer validOrderCount = getOrderCount(beginTime, endTime, PawHubConstants.ORDER_STATUS_SERVICE_COMPLETED);

            orderCountList.add(orderCount);
            validOrderCountList.add(validOrderCount);
        }

        //计算时间区间内的订单总数量
        Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).get();

        //计算时间区间内的有效订单总数量
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();

        return OrderStatisticsVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCount(validOrderCount)
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .build();
    }

    /**
     * 获取今日销量top4的服务
     * @param begin
     * @param end
     * @return
     */
    @Override
    public ServicesTop10Vo getTop4(LocalDateTime begin, LocalDateTime end) {
        List<ServicesTop4Dto> servicesTop4DtoList = Optional.ofNullable(ordersMapper.getServicesTop4(begin, end))
                .orElse(Collections.emptyList());

// 过滤掉null元素并提取name，处理空列表情况
        List<String> names = servicesTop4DtoList.stream()
                .filter(Objects::nonNull) // 过滤null的ServicesTop4Dto对象
                .map(ServicesTop4Dto::getName)
                .filter(Objects::nonNull) // 过滤null的name
                .collect(Collectors.toList());

// 过滤掉null元素并提取number，处理空列表情况
        // 假设number是BigDecimal类型
        // 过滤null的ServicesTop4Dto对象
        // 过滤null的number
        List<Decimal> numbers = new ArrayList<>();
        for (ServicesTop4Dto servicesTop4Dto : servicesTop4DtoList) {
            if (servicesTop4Dto != null) {
                Decimal number = servicesTop4Dto.getNumber();
                if (number != null) {
                    numbers.add(number);
                }
            }
        }

// 如果列表为空，返回"0"或特定默认值
        String nameResult = names.isEmpty() ? "0" : StringUtils.join(names, ",");
        String numberResult = numbers.isEmpty() ? "0" : StringUtils.join(numbers, ",");

        return ServicesTop10Vo.builder()
                .nameList(nameResult)
                .numberList(numberResult)
                .build();
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
    private Integer getOrderCount(LocalDateTime begin,LocalDateTime end,Long status){
        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);
        map.put("status",status);

        return ordersMapper.countByMap(map);
    }
}
