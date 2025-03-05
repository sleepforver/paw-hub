package com.pet.manager.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.pet.manager.domain.Posts;
import com.pet.manager.service.IPostsService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * 动态管理Controller
 *
 * @author kkk
 * @date 2025-02-25
 */
@Api(tags = "动态管理Controller")
@RestController
@RequestMapping("/manager/posts")
public class PostsController extends BaseController
{
    @Autowired
    private IPostsService postsService;

/**
 * 查询动态管理列表
 */
@ApiOperation("查询动态管理列表")
@PreAuthorize("@ss.hasPermi('manager:posts:list')")
@GetMapping("/list")
    public TableDataInfo list(Posts posts)
    {
        startPage();
        List<Posts> list = postsService.selectPostsList(posts);
        return getDataTable(list);
    }

    /**
     * 导出动态管理列表
     */
    @ApiOperation("导出动态管理列表")
    @PreAuthorize("@ss.hasPermi('manager:posts:export')")
    @Log(title = "动态管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Posts posts)
    {
        List<Posts> list = postsService.selectPostsList(posts);
        ExcelUtil<Posts> util = new ExcelUtil<Posts>(Posts.class);
        util.exportExcel(response, list, "动态管理数据");
    }

    /**
     * 获取动态管理详细信息
     */
    @ApiOperation("获取动态管理详细信息")
    @PreAuthorize("@ss.hasPermi('manager:posts:query')")
    @GetMapping(value = "/{postId}")
    public R<Posts> getInfo(@PathVariable("postId") Long postId)
    {
        return R.ok(postsService.selectPostsByPostId(postId));
    }

    /**
     * 新增动态管理
     */
    @ApiOperation("新增动态管理")
    @PreAuthorize("@ss.hasPermi('manager:posts:add')")
    @Log(title = "动态管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Posts posts)
    {
        //获取当前登录用户的id
        posts.setUserId(SecurityUtils.getLoginUser().getUserId());
        return toAjax(postsService.insertPosts(posts));
    }

    /**
     * 修改动态管理
     */
    @ApiOperation("修改动态管理")
    @PreAuthorize("@ss.hasPermi('manager:posts:edit')")
    @Log(title = "动态管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Posts posts)
    {
        return toAjax(postsService.updatePosts(posts));
    }

    /**
     * 删除动态管理
     */
    @ApiOperation("删除动态管理")
    @PreAuthorize("@ss.hasPermi('manager:posts:remove')")
    @Log(title = "动态管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable Long[] postIds)
    {
        return toAjax(postsService.deletePostsByPostIds(postIds));
    }
}
