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
import com.pet.manager.domain.Pets;
import com.pet.manager.service.IPetsService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * 宠物管理Controller
 *
 * @author kkk
 * @date 2025-02-17
 */
@Api(tags = "宠物管理Controller")
@RestController
@RequestMapping("/manager/pets")
public class PetsController extends BaseController
{
    @Autowired
    private IPetsService petsService;

/**
 * 查询宠物管理列表
 */
@ApiOperation("查询宠物管理列表")
@PreAuthorize("@ss.hasPermi('manager:pets:list')")
@GetMapping("/list")
    public TableDataInfo list(Pets pets)
    {
        startPage();
        List<Pets> list = petsService.selectPetsList(pets);
        return getDataTable(list);
    }

    /**
     * 导出宠物管理列表
     */
    @ApiOperation("导出宠物管理列表")
    @PreAuthorize("@ss.hasPermi('manager:pets:export')")
    @Log(title = "宠物管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Pets pets)
    {
        List<Pets> list = petsService.selectPetsList(pets);
        ExcelUtil<Pets> util = new ExcelUtil<Pets>(Pets.class);
        util.exportExcel(response, list, "宠物管理数据");
    }

    /**
     * 获取宠物管理详细信息
     */
    @ApiOperation("获取宠物管理详细信息")
    @PreAuthorize("@ss.hasPermi('manager:pets:query')")
    @GetMapping(value = "/{petId}")
    public R<Pets> getInfo(@PathVariable("petId") Long petId)
    {
        return R.ok(petsService.selectPetsByPetId(petId));
    }

    /**
     * 新增宠物管理
     */
    @ApiOperation("新增宠物管理")
    @PreAuthorize("@ss.hasPermi('manager:pets:add')")
    @Log(title = "宠物管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Pets pets)
    {
        return toAjax(petsService.insertPets(pets));
    }

    /**
     * 修改宠物管理
     */
    @ApiOperation("修改宠物管理")
    @PreAuthorize("@ss.hasPermi('manager:pets:edit')")
    @Log(title = "宠物管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Pets pets)
    {
        return toAjax(petsService.updatePets(pets));
    }

    /**
     * 删除宠物管理
     */
    @ApiOperation("删除宠物管理")
    @PreAuthorize("@ss.hasPermi('manager:pets:remove')")
    @Log(title = "宠物管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{petIds}")
    public AjaxResult remove(@PathVariable Long[] petIds)
    {
        return toAjax(petsService.deletePetsByPetIds(petIds));
    }
}
