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
import com.pet.manager.domain.Policy;
import com.pet.manager.service.IPolicyService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * 策略管理Controller
 *
 * @author kkk
 * @date 2025-02-17
 */
@Api(tags = "策略管理Controller")
@RestController
@RequestMapping("/manager/policy")
public class PolicyController extends BaseController
{
    @Autowired
    private IPolicyService policyService;

/**
 * 查询策略管理列表
 */
@ApiOperation("查询策略管理列表")
@PreAuthorize("@ss.hasPermi('manager:policy:list')")
@GetMapping("/list")
    public TableDataInfo list(Policy policy)
    {
        startPage();
        List<Policy> list = policyService.selectPolicyList(policy);
        return getDataTable(list);
    }

    /**
     * 导出策略管理列表
     */
    @ApiOperation("导出策略管理列表")
    @PreAuthorize("@ss.hasPermi('manager:policy:export')")
    @Log(title = "策略管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Policy policy)
    {
        List<Policy> list = policyService.selectPolicyList(policy);
        ExcelUtil<Policy> util = new ExcelUtil<Policy>(Policy.class);
        util.exportExcel(response, list, "策略管理数据");
    }

    /**
     * 获取策略管理详细信息
     */
    @ApiOperation("获取策略管理详细信息")
    @PreAuthorize("@ss.hasPermi('manager:policy:query')")
    @GetMapping(value = "/{policyId}")
    public R<Policy> getInfo(@PathVariable("policyId") Long policyId)
    {
        return R.ok(policyService.selectPolicyByPolicyId(policyId));
    }

    /**
     * 新增策略管理
     */
    @ApiOperation("新增策略管理")
    @PreAuthorize("@ss.hasPermi('manager:policy:add')")
    @Log(title = "策略管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Policy policy)
    {
        return toAjax(policyService.insertPolicy(policy));
    }

    /**
     * 修改策略管理
     */
    @ApiOperation("修改策略管理")
    @PreAuthorize("@ss.hasPermi('manager:policy:edit')")
    @Log(title = "策略管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Policy policy)
    {
        return toAjax(policyService.updatePolicy(policy));
    }

    /**
     * 删除策略管理
     */
    @ApiOperation("删除策略管理")
    @PreAuthorize("@ss.hasPermi('manager:policy:remove')")
    @Log(title = "策略管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{policyIds}")
    public AjaxResult remove(@PathVariable Long[] policyIds)
    {
        return toAjax(policyService.deletePolicyByPolicyIds(policyIds));
    }
}
