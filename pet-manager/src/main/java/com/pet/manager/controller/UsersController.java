package com.pet.manager.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import com.pet.common.utils.SecurityUtils;
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
import com.pet.manager.domain.Users;
import com.pet.manager.service.IUsersService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * 用户管理Controller
 *
 * @author kkk
 * @date 2025-02-17
 */
@Api(tags = "用户管理Controller")
@RestController
@RequestMapping("/manager/users")
public class UsersController extends BaseController
{
    @Autowired
    private IUsersService usersService;

/**
 * 查询用户管理列表
 */
@ApiOperation("查询用户管理列表")
@PreAuthorize("@ss.hasPermi('manager:users:list')")
@GetMapping("/list")
    public TableDataInfo list(Users users)
    {
        startPage();
        List<Users> list = usersService.selectUsersList(users);
        return getDataTable(list);
    }

    /**
     * 导出用户管理列表
     */
    @ApiOperation("导出用户管理列表")
    @PreAuthorize("@ss.hasPermi('manager:users:export')")
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Users users)
    {
        List<Users> list = usersService.selectUsersList(users);
        ExcelUtil<Users> util = new ExcelUtil<Users>(Users.class);
        util.exportExcel(response, list, "用户管理数据");
    }

    /**
     * 获取用户管理详细信息
     */
    @ApiOperation("获取用户管理详细信息")
    @PreAuthorize("@ss.hasPermi('manager:users:query')")
    @GetMapping(value = "/{userId}")
    public R<Users> getInfo(@PathVariable("userId") Long userId)
    {
        return R.ok(usersService.selectUsersByUserId(userId));
    }

    /**
     * 新增用户管理
     */
    @ApiOperation("新增用户管理")
    @PreAuthorize("@ss.hasPermi('manager:users:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Users users)
    {
        users.setPasswordHash(SecurityUtils.encryptPassword(users.getPasswordHash()));
        return toAjax(usersService.insertUsers(users));
    }

    /**
     * 修改用户管理
     */
    @ApiOperation("修改用户管理")
    @PreAuthorize("@ss.hasPermi('manager:users:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Users users)
    {
        return toAjax(usersService.updateUsers(users));
    }

    /**
     * 删除用户管理
     */
    @ApiOperation("删除用户管理")
    @PreAuthorize("@ss.hasPermi('manager:users:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(usersService.deleteUsersByUserIds(userIds));
    }

    /**
     * 重置密码
     */
    @ApiOperation("重置密码")
    @PreAuthorize("@ss.hasPermi('manager:users:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPassword/{userId}")
    public AjaxResult resetPassword(@PathVariable("userId") Long userId)
    {
        Users users = new Users();
        users.setUserId(userId);
        users.setPasswordHash(SecurityUtils.encryptPassword("123456"));
        return toAjax(usersService.updateUsers(users));
    }
}
