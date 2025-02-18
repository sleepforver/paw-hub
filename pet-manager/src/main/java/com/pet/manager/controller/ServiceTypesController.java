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
import com.pet.manager.domain.ServiceTypes;
import com.pet.manager.service.IServiceTypesService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * 服务类型Controller
 *
 * @author kkk
 * @date 2025-02-17
 */
@Api(tags = "服务类型Controller")
@RestController
@RequestMapping("/manager/serviceTypes")
public class ServiceTypesController extends BaseController
{
    @Autowired
    private IServiceTypesService serviceTypesService;

/**
 * 查询服务类型列表
 */
@ApiOperation("查询服务类型列表")
@PreAuthorize("@ss.hasPermi('manager:serviceTypes:list')")
@GetMapping("/list")
    public TableDataInfo list(ServiceTypes serviceTypes)
    {
        startPage();
        List<ServiceTypes> list = serviceTypesService.selectServiceTypesList(serviceTypes);
        return getDataTable(list);
    }

    /**
     * 导出服务类型列表
     */
    @ApiOperation("导出服务类型列表")
    @PreAuthorize("@ss.hasPermi('manager:serviceTypes:export')")
    @Log(title = "服务类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ServiceTypes serviceTypes)
    {
        List<ServiceTypes> list = serviceTypesService.selectServiceTypesList(serviceTypes);
        ExcelUtil<ServiceTypes> util = new ExcelUtil<ServiceTypes>(ServiceTypes.class);
        util.exportExcel(response, list, "服务类型数据");
    }

    /**
     * 获取服务类型详细信息
     */
    @ApiOperation("获取服务类型详细信息")
    @PreAuthorize("@ss.hasPermi('manager:serviceTypes:query')")
    @GetMapping(value = "/{serviceTypeId}")
    public R<ServiceTypes> getInfo(@PathVariable("serviceTypeId") Long serviceTypeId)
    {
        return R.ok(serviceTypesService.selectServiceTypesByServiceTypeId(serviceTypeId));
    }

    /**
     * 新增服务类型
     */
    @ApiOperation("新增服务类型")
    @PreAuthorize("@ss.hasPermi('manager:serviceTypes:add')")
    @Log(title = "服务类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ServiceTypes serviceTypes)
    {
        return toAjax(serviceTypesService.insertServiceTypes(serviceTypes));
    }

    /**
     * 修改服务类型
     */
    @ApiOperation("修改服务类型")
    @PreAuthorize("@ss.hasPermi('manager:serviceTypes:edit')")
    @Log(title = "服务类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ServiceTypes serviceTypes)
    {
        return toAjax(serviceTypesService.updateServiceTypes(serviceTypes));
    }

    /**
     * 删除服务类型
     */
    @ApiOperation("删除服务类型")
    @PreAuthorize("@ss.hasPermi('manager:serviceTypes:remove')")
    @Log(title = "服务类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{serviceTypeIds}")
    public AjaxResult remove(@PathVariable Long[] serviceTypeIds)
    {
        return toAjax(serviceTypesService.deleteServiceTypesByServiceTypeIds(serviceTypeIds));
    }
}
