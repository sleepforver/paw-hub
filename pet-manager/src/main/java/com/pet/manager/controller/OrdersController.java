package com.pet.manager.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import com.pet.manager.domain.vo.OrderStatisticsVO;
import com.pet.manager.domain.vo.ServicesTop10Vo;
import jakarta.servlet.http.HttpServletResponse;

import com.pet.common.constant.PawHubConstants;
import com.pet.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pet.common.annotation.Log;
import com.pet.common.core.controller.BaseController;
import com.pet.common.core.domain.AjaxResult;
import com.pet.common.enums.BusinessType;
import com.pet.manager.domain.Orders;
import com.pet.manager.service.IOrdersService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * 订单管理Controller
 *
 * @author kkk
 * @date 2025-02-25
 */
@Api(tags = "订单管理Controller")
@RestController
@RequestMapping("/manager/orders")
public class OrdersController extends BaseController
{
    @Autowired
    private IOrdersService ordersService;

/**
 * 查询订单管理列表
 */
@ApiOperation("查询订单管理列表")
@PreAuthorize("@ss.hasPermi('manager:orders:list')")
@GetMapping("/list")
    public TableDataInfo list(Orders orders)
    {
        startPage();
        List<Orders> list = ordersService.selectOrdersList(orders);
        return getDataTable(list);
    }

    /**
     * 导出订单管理列表
     */
    @ApiOperation("导出订单管理列表")
    @PreAuthorize("@ss.hasPermi('manager:orders:export')")
    @Log(title = "订单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Orders orders)
    {
        List<Orders> list = ordersService.selectOrdersList(orders);
        ExcelUtil<Orders> util = new ExcelUtil<Orders>(Orders.class);
        util.exportExcel(response, list, "订单管理数据");
    }

    /**
     * 获取订单管理详细信息
     */
    @ApiOperation("获取订单管理详细信息")
    @PreAuthorize("@ss.hasPermi('manager:orders:query')")
    @GetMapping(value = "/{orderId}")
    public R<Orders> getInfo(@PathVariable("orderId") Long orderId)
    {
        return R.ok(ordersService.selectOrdersByOrderId(orderId));
    }

    /**
     * 新增订单管理
     */
    @ApiOperation("新增订单管理")
    @PreAuthorize("@ss.hasPermi('manager:orders:add')")
    @Log(title = "订单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Orders orders)
    {
        //当前登录用户id
        orders.setUserId(SecurityUtils.getLoginUser().getUserId());
        return toAjax(ordersService.insertOrders(orders));
    }

    /**
     * 修改订单管理
     */
    @ApiOperation("修改订单管理")
    @PreAuthorize("@ss.hasPermi('manager:orders:edit')")
    @Log(title = "订单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Orders orders)
    {
        return toAjax(ordersService.updateOrders(orders));
    }

    /**
     * 删除订单管理
     */
    @ApiOperation("删除订单管理")
    @PreAuthorize("@ss.hasPermi('manager:orders:remove')")
    @Log(title = "订单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(ordersService.deleteOrdersByOrderIds(orderIds));
    }

    /**
     * 退款
     */
    @ApiOperation("退款")
    @PreAuthorize("@ss.hasPermi('manager:orders:edit')")
    @Log(title = "订单管理", businessType = BusinessType.UPDATE)
    @PostMapping("/refund")
    public AjaxResult refund(@RequestBody Orders orders)
    {
        return toAjax(ordersService.refund(orders));
    }

    /**
     * 取消订单
     */
    @ApiOperation("取消订单")
    @PreAuthorize("@ss.hasPermi('manager:orders:edit')")
    @Log(title = "订单管理", businessType = BusinessType.UPDATE)
    @PostMapping("/cancel")
    public AjaxResult cancel(@RequestBody Orders orders)
    {
        return toAjax(ordersService.cancel(orders));
    }

    /**
     * 获取指定时间的订单数量
     */
    @ApiOperation("获取指定时间的订单数量")
    @PreAuthorize("@ss.hasPermi('manager:order:query')")
    @GetMapping("/ordersStatistics")
    public R<OrderStatisticsVO> getOrderStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return R.ok(ordersService.getOrderStatistics(begin, end));
    }


    @ApiOperation("获取今日销量top4的服务")
    @PreAuthorize("@ss.hasPermi('manager:order:query')")
    @GetMapping("/ordersStatistics/top4")
    public R<ServicesTop10Vo> top4() {
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now();
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        return R.ok(ordersService.getTop4(beginTime, endTime));
    }
}
