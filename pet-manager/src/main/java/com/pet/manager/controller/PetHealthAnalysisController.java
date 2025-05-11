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
import com.pet.manager.domain.PetHealthAnalysis;
import com.pet.manager.service.IPetHealthAnalysisService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * AI分析详情Controller
 *
 * @author kkk
 * @date 2025-03-20
 */
@Api(tags = "AI分析详情Controller")
@RestController
@RequestMapping("/manager/analysis")
public class PetHealthAnalysisController extends BaseController
{
    @Autowired
    private IPetHealthAnalysisService petHealthAnalysisService;

/**
 * 查询AI分析详情列表
 */
@ApiOperation("查询AI分析详情列表")
@PreAuthorize("@ss.hasPermi('manager:analysis:list')")
@GetMapping("/list")
    public TableDataInfo list(PetHealthAnalysis petHealthAnalysis)
    {
        startPage();
        List<PetHealthAnalysis> list = petHealthAnalysisService.selectPetHealthAnalysisList(petHealthAnalysis);
        return getDataTable(list);
    }

    /**
     * 导出AI分析详情列表
     */
    @ApiOperation("导出AI分析详情列表")
    @PreAuthorize("@ss.hasPermi('manager:analysis:export')")
    @Log(title = "AI分析详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PetHealthAnalysis petHealthAnalysis)
    {
        List<PetHealthAnalysis> list = petHealthAnalysisService.selectPetHealthAnalysisList(petHealthAnalysis);
        ExcelUtil<PetHealthAnalysis> util = new ExcelUtil<PetHealthAnalysis>(PetHealthAnalysis.class);
        util.exportExcel(response, list, "AI分析详情数据");
    }

    /**
     * 获取AI分析详情详细信息
     */
    @ApiOperation("获取AI分析详情详细信息")
    @PreAuthorize("@ss.hasPermi('manager:analysis:query')")
    @GetMapping(value = "/{analysisId}")
    public R<PetHealthAnalysis> getInfo(@PathVariable("analysisId") Long analysisId)
    {
        return R.ok(petHealthAnalysisService.selectPetHealthAnalysisByAnalysisId(analysisId));
    }

    /**
     * 新增AI分析详情
     */
    @ApiOperation("新增AI分析详情")
    @PreAuthorize("@ss.hasPermi('manager:analysis:add')")
    @Log(title = "AI分析详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PetHealthAnalysis petHealthAnalysis)
    {
        return toAjax(petHealthAnalysisService.insertPetHealthAnalysis(petHealthAnalysis));
    }

    /**
     * 修改AI分析详情
     */
    @ApiOperation("修改AI分析详情")
    @PreAuthorize("@ss.hasPermi('manager:analysis:edit')")
    @Log(title = "AI分析详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PetHealthAnalysis petHealthAnalysis)
    {
        return toAjax(petHealthAnalysisService.updatePetHealthAnalysis(petHealthAnalysis));
    }

    /**
     * 删除AI分析详情
     */
    @ApiOperation("删除AI分析详情")
    @PreAuthorize("@ss.hasPermi('manager:analysis:remove')")
    @Log(title = "AI分析详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{analysisIds}")
    public AjaxResult remove(@PathVariable Long[] analysisIds)
    {
        return toAjax(petHealthAnalysisService.deletePetHealthAnalysisByAnalysisIds(analysisIds));
    }
}
