package com.pet.manager.service;

import java.util.List;
import com.pet.manager.domain.Orders;

/**
 * 订单管理Service接口
 *
 * @author kkk
 * @date 2025-02-25
 */
public interface IOrdersService
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
     * 批量删除订单管理
     *
     * @param orderIds 需要删除的订单管理主键集合
     * @return 结果
     */
    public int deleteOrdersByOrderIds(Long[] orderIds);

    /**
     * 删除订单管理信息
     *
     * @param orderId 订单管理主键
     * @return 结果
     */
    public int deleteOrdersByOrderId(Long orderId);

    /**
     * 退款
     * @param orders
     * @return
     */
    int refund(Orders orders);

    /**
     * 取消订单
     * @param orders
     * @return
     */
    int cancel(Orders orders);
}
