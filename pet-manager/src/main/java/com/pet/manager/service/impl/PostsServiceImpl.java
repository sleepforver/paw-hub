package com.pet.manager.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.PostsMapper;
import com.pet.manager.domain.Posts;
import com.pet.manager.service.IPostsService;

/**
 * 动态管理Service业务层处理
 * 
 * @author kkk
 * @date 2025-02-25
 */
@Service
public class PostsServiceImpl implements IPostsService 
{
    @Autowired
    private PostsMapper postsMapper;

    /**
     * 查询动态管理
     * 
     * @param postId 动态管理主键
     * @return 动态管理
     */
    @Override
    public Posts selectPostsByPostId(Long postId)
    {
        return postsMapper.selectPostsByPostId(postId);
    }

    /**
     * 查询动态管理列表
     * 
     * @param posts 动态管理
     * @return 动态管理
     */
    @Override
    public List<Posts> selectPostsList(Posts posts)
    {
        return postsMapper.selectPostsList(posts);
    }

    /**
     * 新增动态管理
     * 
     * @param posts 动态管理
     * @return 结果
     */
    @Override
    public int insertPosts(Posts posts)
    {
        return postsMapper.insertPosts(posts);
    }

    /**
     * 修改动态管理
     * 
     * @param posts 动态管理
     * @return 结果
     */
    @Override
    public int updatePosts(Posts posts)
    {
        return postsMapper.updatePosts(posts);
    }

    /**
     * 批量删除动态管理
     * 
     * @param postIds 需要删除的动态管理主键
     * @return 结果
     */
    @Override
    public int deletePostsByPostIds(Long[] postIds)
    {
        return postsMapper.deletePostsByPostIds(postIds);
    }

    /**
     * 删除动态管理信息
     * 
     * @param postId 动态管理主键
     * @return 结果
     */
    @Override
    public int deletePostsByPostId(Long postId)
    {
        return postsMapper.deletePostsByPostId(postId);
    }
}
