package com.pet.manager.mapper;

import java.util.List;
import com.pet.manager.domain.Orders;

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
}
