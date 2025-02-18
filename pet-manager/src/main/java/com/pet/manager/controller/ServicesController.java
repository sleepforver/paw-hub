package com.pet.manager.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.pet.manager.domain.Services;
import com.pet.manager.service.IServicesService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * 服务管理Controller
 *
 * @author kkk
 * @date 2025-02-17
 */
@Api(tags = "服务管理Controller")
@RestController
@RequestMapping("/manager/services")
public class ServicesController extends BaseController
{
    @Autowired
    private IServicesService servicesService;

/**
 * 查询服务管理列表
 */
@ApiOperation("查询服务管理列表")
@PreAuthorize("@ss.hasPermi('manager:services:list')")
@GetMapping("/list")
    public TableDataInfo list(Services services)
    {
        startPage();
        List<Services> list = servicesService.selectServicesList(services);
        return getDataTable(list);
    }

    /**
     * 导出服务管理列表
     */
    @ApiOperation("导出服务管理列表")
    @PreAuthorize("@ss.hasPermi('manager:services:export')")
    @Log(title = "服务管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Services services)
    {
        List<Services> list = servicesService.selectServicesList(services);
        ExcelUtil<Services> util = new ExcelUtil<Services>(Services.class);
        util.exportExcel(response, list, "服务管理数据");
    }

    /**
     * 获取服务管理详细信息
     */
    @ApiOperation("获取服务管理详细信息")
    @PreAuthorize("@ss.hasPermi('manager:services:query')")
    @GetMapping(value = "/{serviceId}")
    public R<Services> getInfo(@PathVariable("serviceId") Long serviceId)
    {
        return R.ok(servicesService.selectServicesByServiceId(serviceId));
    }

    /**
     * 新增服务管理
     */
    @ApiOperation("新增服务管理")
    @PreAuthorize("@ss.hasPermi('manager:services:add')")
    @Log(title = "服务管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Services services)
    {
        return toAjax(servicesService.insertServices(services));
    }

    /**
     * 修改服务管理
     */
    @ApiOperation("修改服务管理")
    @PreAuthorize("@ss.hasPermi('manager:services:edit')")
    @Log(title = "服务管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Services services)
    {
        return toAjax(servicesService.updateServices(services));
    }

    /**
     * 删除服务管理
     */
    @ApiOperation("删除服务管理")
    @PreAuthorize("@ss.hasPermi('manager:services:remove')")
    @Log(title = "服务管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{serviceIds}")
    public AjaxResult remove(@PathVariable Long[] serviceIds)
    {
        return toAjax(servicesService.deleteServicesByServiceIds(serviceIds));
    }
}
