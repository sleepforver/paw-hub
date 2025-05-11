package com.pet.manager.controller;

import java.io.IOException;
import java.util.List;

import com.pet.manager.domain.vo.PetHealthRecordsVo;
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
import com.pet.manager.domain.PetHealthRecords;
import com.pet.manager.service.IPetHealthRecordsService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * 宠物健康Controller
 *
 * @author kkk
 * @date 2025-03-20
 */
@Api(tags = "宠物健康Controller")
@RestController
@RequestMapping("/manager/records")
public class PetHealthRecordsController extends BaseController
{
    @Autowired
    private IPetHealthRecordsService petHealthRecordsService;

/**
 * 查询宠物健康列表
 */
@ApiOperation("查询宠物健康列表")
@PreAuthorize("@ss.hasPermi('manager:records:list')")
@GetMapping("/list")
    public TableDataInfo list(PetHealthRecords petHealthRecords)
    {
        startPage();
        List<PetHealthRecords> list = petHealthRecordsService.selectPetHealthRecordsList(petHealthRecords);
        return getDataTable(list);
    }

    /**
     * 导出宠物健康列表
     */
    @ApiOperation("导出宠物健康列表")
    @PreAuthorize("@ss.hasPermi('manager:records:export')")
    @Log(title = "宠物健康", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PetHealthRecords petHealthRecords)
    {
        List<PetHealthRecords> list = petHealthRecordsService.selectPetHealthRecordsList(petHealthRecords);
        ExcelUtil<PetHealthRecords> util = new ExcelUtil<PetHealthRecords>(PetHealthRecords.class);
        util.exportExcel(response, list, "宠物健康数据");
    }

    /**
     * 获取宠物健康详细信息
     */
    @ApiOperation("获取宠物健康详细信息")
    @PreAuthorize("@ss.hasPermi('manager:records:query')")
    @GetMapping(value = "/{recordId}")
    public R<PetHealthRecords> getInfo(@PathVariable("recordId") Long recordId)
    {
        return R.ok(petHealthRecordsService.selectPetHealthRecordsByRecordId(recordId));
    }

    /**
     * 新增宠物健康
     */
    @ApiOperation("新增宠物健康")
    @PreAuthorize("@ss.hasPermi('manager:records:add')")
    @Log(title = "宠物健康", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PetHealthRecords petHealthRecords) throws IOException {
        return toAjax(petHealthRecordsService.insertPetHealthRecords(petHealthRecords));
    }

    /**
     * 修改宠物健康
     */
    @ApiOperation("修改宠物健康")
    @PreAuthorize("@ss.hasPermi('manager:records:edit')")
    @Log(title = "宠物健康", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PetHealthRecords petHealthRecords)
    {
        return toAjax(petHealthRecordsService.updatePetHealthRecords(petHealthRecords));
    }

    /**
     * 删除宠物健康
     */
    @ApiOperation("删除宠物健康")
    @PreAuthorize("@ss.hasPermi('manager:records:remove')")
    @Log(title = "宠物健康", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(petHealthRecordsService.deletePetHealthRecordsByRecordIds(recordIds));
    }

    /**
     * 图像分析宠物健康
     */
    @ApiOperation("图像分析宠物健康")
    @PreAuthorize("@ss.hasPermi('manager:records:add')")
    @Log(title = "宠物健康", businessType = BusinessType.INSERT)
    @PostMapping("/analysis")
    public R<PetHealthRecordsVo> analysis(@RequestBody PetHealthRecords petHealthRecords) throws IOException {
        return R.ok(petHealthRecordsService.analysis(petHealthRecords));
    }
}
