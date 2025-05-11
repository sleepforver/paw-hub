package com.pet.manager.controller;

import java.util.List;

import com.pet.manager.domain.vo.EmpStatisticsVO;
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
import com.pet.manager.domain.Emp;
import com.pet.manager.service.IEmpService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * 员工列表Controller
 *
 * @author kkk
 * @date 2025-02-27
 */
@Api(tags = "员工列表Controller")
@RestController
@RequestMapping("/manager/emp")
public class EmpController extends BaseController
{
    @Autowired
    private IEmpService empService;

/**
 * 查询员工列表列表
 */
@ApiOperation("查询员工列表列表")
@PreAuthorize("@ss.hasPermi('manager:emp:list')")
@GetMapping("/list")
    public TableDataInfo list(Emp emp)
    {
        startPage();
        List<Emp> list = empService.selectEmpList(emp);
        return getDataTable(list);
    }

    /**
     * 导出员工列表列表
     */
    @ApiOperation("导出员工列表列表")
    @PreAuthorize("@ss.hasPermi('manager:emp:export')")
    @Log(title = "员工列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Emp emp)
    {
        List<Emp> list = empService.selectEmpList(emp);
        ExcelUtil<Emp> util = new ExcelUtil<Emp>(Emp.class);
        util.exportExcel(response, list, "员工列表数据");
    }

    /**
     * 获取员工列表详细信息
     */
    @ApiOperation("获取员工列表详细信息")
    @PreAuthorize("@ss.hasPermi('manager:emp:query')")
    @GetMapping(value = "/{id}")
    public R<Emp> getInfo(@PathVariable("id") Long id)
    {
        return R.ok(empService.selectEmpById(id));
    }

    /**
     * 新增员工列表
     */
    @ApiOperation("新增员工列表")
    @PreAuthorize("@ss.hasPermi('manager:emp:add')")
    @Log(title = "员工列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Emp emp)
    {
        return toAjax(empService.insertEmp(emp));
    }

    /**
     * 修改员工列表
     */
    @ApiOperation("修改员工列表")
    @PreAuthorize("@ss.hasPermi('manager:emp:edit')")
    @Log(title = "员工列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Emp emp)
    {
        return toAjax(empService.updateEmp(emp));
    }

    /**
     * 删除员工列表
     */
    @ApiOperation("删除员工列表")
    @PreAuthorize("@ss.hasPermi('manager:emp:remove')")
    @Log(title = "员工列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(empService.deleteEmpByIds(ids));
    }

    /**
     * 统计员工数量
     */
    @ApiOperation("统计员工数量")
    @PreAuthorize("@ss.hasPermi('manager:emp:query')")
    @GetMapping("/statistics")
    public R<EmpStatisticsVO> statistics()
    {
        EmpStatisticsVO empStatisticsVO = empService.statistics();
        return R.ok(empStatisticsVO);
    }
}
