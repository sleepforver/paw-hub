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
import com.pet.manager.domain.Breeds;
import com.pet.manager.service.IBreedsService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * 宠物品种管理Controller
 *
 * @author kkk
 * @date 2025-02-17
 */
@Api(tags = "宠物品种管理Controller")
@RestController
@RequestMapping("/manager/breeds")
public class BreedsController extends BaseController
{
    @Autowired
    private IBreedsService breedsService;

/**
 * 查询宠物品种管理列表
 */
@ApiOperation("查询宠物品种管理列表")
@PreAuthorize("@ss.hasPermi('manager:breeds:list')")
@GetMapping("/list")
    public TableDataInfo list(Breeds breeds)
    {
        startPage();
        List<Breeds> list = breedsService.selectBreedsList(breeds);
        return getDataTable(list);
    }

    /**
     * 导出宠物品种管理列表
     */
    @ApiOperation("导出宠物品种管理列表")
    @PreAuthorize("@ss.hasPermi('manager:breeds:export')")
    @Log(title = "宠物品种管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Breeds breeds)
    {
        List<Breeds> list = breedsService.selectBreedsList(breeds);
        ExcelUtil<Breeds> util = new ExcelUtil<Breeds>(Breeds.class);
        util.exportExcel(response, list, "宠物品种管理数据");
    }

    /**
     * 获取宠物品种管理详细信息
     */
    @ApiOperation("获取宠物品种管理详细信息")
    @PreAuthorize("@ss.hasPermi('manager:breeds:query')")
    @GetMapping(value = "/{breedId}")
    public R<Breeds> getInfo(@PathVariable("breedId") Long breedId)
    {
        return R.ok(breedsService.selectBreedsByBreedId(breedId));
    }

    /**
     * 新增宠物品种管理
     */
    @ApiOperation("新增宠物品种管理")
    @PreAuthorize("@ss.hasPermi('manager:breeds:add')")
    @Log(title = "宠物品种管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Breeds breeds)
    {
        return toAjax(breedsService.insertBreeds(breeds));
    }

    /**
     * 修改宠物品种管理
     */
    @ApiOperation("修改宠物品种管理")
    @PreAuthorize("@ss.hasPermi('manager:breeds:edit')")
    @Log(title = "宠物品种管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Breeds breeds)
    {
        return toAjax(breedsService.updateBreeds(breeds));
    }

    /**
     * 删除宠物品种管理
     */
    @ApiOperation("删除宠物品种管理")
    @PreAuthorize("@ss.hasPermi('manager:breeds:remove')")
    @Log(title = "宠物品种管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{breedIds}")
    public AjaxResult remove(@PathVariable Long[] breedIds)
    {
        return toAjax(breedsService.deleteBreedsByBreedIds(breedIds));
    }
}
