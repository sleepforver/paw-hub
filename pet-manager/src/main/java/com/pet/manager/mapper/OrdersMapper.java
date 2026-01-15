package com.pet.manager.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.pet.manager.domain.Orders;
import com.pet.manager.domain.dto.ServicesTop4Dto;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * 订单管理Mapper接口
 *
 * @author kkk
 * @date 2025-02-25
 */
public interface OrdersMapper
{
    /**
     * 查询订单管理
     *
     * @param orderId 订单管理主键
     * @return 订单管理
     */
    public Orders selectOrdersByOrderId(Long orderId);

    /**
     * 查询订单管理列表
     *
     * @param orders 订单管理
     * @return 订单管理集合
     */
    public List<Orders> selectOrdersList(Orders orders);

    /**
     * 新增订单管理
     *
     * @param orders 订单管理
     * @return 结果
     */
    public int insertOrders(Orders orders);

    /**
     * 修改订单管理
     *
     * @param orders 订单管理
     * @return 结果
     */
    public int updateOrders(Orders orders);

    /**
     * 通过订单编号修改订单管理
     *
     * @param orders 订单管理
     * @return 结果
     */
    public int updateOrdersByOrderNo(Orders orders);
    /**
     * 删除订单管理
     *
     * @param orderId 订单管理主键
     * @return 结果
     */
    public int deleteOrdersByOrderId(Long orderId);

    /**
     * 批量删除订单管理
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrdersByOrderIds(Long[] orderIds);

    /**
     * 统计订单数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

    /**
     * 获取今日的前4个服务
     * @param begin
     * @param end
     * @return
     */
    List<ServicesTop4Dto> getServicesTop4(LocalDateTime begin, LocalDateTime end);

    Orders selectOrdersByOrderNO(String appointmentNo);

    /**
     * 查询过期的订单
     * @param params
     * @return
     */
    List<Orders> selectExpiredOrders(Map<String, Object> params);

    List<Orders> selectTomorrowOrders(Map<String, Object> params);
}
