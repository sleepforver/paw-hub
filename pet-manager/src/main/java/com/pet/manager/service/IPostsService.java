package com.pet.manager.service;

import java.util.List;
import com.pet.manager.domain.Posts;

/**
 * 动态管理Service接口
 * 
 * @author kkk
 * @date 2025-02-25
 */
public interface IPostsService 
{
    /**
     * 查询动态管理
     * 
     * @param postId 动态管理主键
     * @return 动态管理
     */
    public Posts selectPostsByPostId(Long postId);

    /**
     * 查询动态管理列表
     * 
     * @param posts 动态管理
     * @return 动态管理集合
     */
    public List<Posts> selectPostsList(Posts posts);

    /**
     * 新增动态管理
     * 
     * @param posts 动态管理
     * @return 结果
     */
    public int insertPosts(Posts posts);

    /**
     * 修改动态管理
     * 
     * @param posts 动态管理
     * @return 结果
     */
    public int updatePosts(Posts posts);

    /**
     * 批量删除动态管理
     * 
     * @param postIds 需要删除的动态管理主键集合
     * @return 结果
     */
    public int deletePostsByPostIds(Long[] postIds);

    /**
     * 删除动态管理信息
     * 
     * @param postId 动态管理主键
     * @return 结果
     */
    public int deletePostsByPostId(Long postId);
}
