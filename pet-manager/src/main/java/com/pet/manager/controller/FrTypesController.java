package com.pet.manager.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

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
import com.pet.manager.domain.FrTypes;
import com.pet.manager.service.IFrTypesService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * 寄养房间类型Controller
 *
 * @author kkk
 * @date 2025-02-17
 */
@Api(tags = "寄养房间类型Controller")
@RestController
@RequestMapping("/manager/types")
public class FrTypesController extends BaseController
{
    @Autowired
    private IFrTypesService frTypesService;

/**
 * 查询寄养房间类型列表
 */
@ApiOperation("查询寄养房间类型列表")
@PreAuthorize("@ss.hasPermi('manager:types:list')")
@GetMapping("/list")
    public TableDataInfo list(FrTypes frTypes)
    {
        startPage();
        List<FrTypes> list = frTypesService.selectFrTypesList(frTypes);
        return getDataTable(list);
    }

    /**
     * 导出寄养房间类型列表
     */
    @ApiOperation("导出寄养房间类型列表")
    @PreAuthorize("@ss.hasPermi('manager:types:export')")
    @Log(title = "寄养房间类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FrTypes frTypes)
    {
        List<FrTypes> list = frTypesService.selectFrTypesList(frTypes);
        ExcelUtil<FrTypes> util = new ExcelUtil<FrTypes>(FrTypes.class);
        util.exportExcel(response, list, "寄养房间类型数据");
    }

    /**
     * 获取寄养房间类型详细信息
     */
    @ApiOperation("获取寄养房间类型详细信息")
    @PreAuthorize("@ss.hasPermi('manager:types:query')")
    @GetMapping(value = "/{roomTypeId}")
    public R<FrTypes> getInfo(@PathVariable("roomTypeId") Long roomTypeId)
    {
        return R.ok(frTypesService.selectFrTypesByRoomTypeId(roomTypeId));
    }

    /**
     * 新增寄养房间类型
     */
    @ApiOperation("新增寄养房间类型")
    @PreAuthorize("@ss.hasPermi('manager:types:add')")
    @Log(title = "寄养房间类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FrTypes frTypes)
    {
        return toAjax(frTypesService.insertFrTypes(frTypes));
    }

    /**
     * 修改寄养房间类型
     */
    @ApiOperation("修改寄养房间类型")
    @PreAuthorize("@ss.hasPermi('manager:types:edit')")
    @Log(title = "寄养房间类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FrTypes frTypes)
    {
        return toAjax(frTypesService.updateFrTypes(frTypes));
    }

    /**
     * 删除寄养房间类型
     */
    @ApiOperation("删除寄养房间类型")
    @PreAuthorize("@ss.hasPermi('manager:types:remove')")
    @Log(title = "寄养房间类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roomTypeIds}")
    public AjaxResult remove(@PathVariable Long[] roomTypeIds)
    {
        return toAjax(frTypesService.deleteFrTypesByRoomTypeIds(roomTypeIds));
    }
}
