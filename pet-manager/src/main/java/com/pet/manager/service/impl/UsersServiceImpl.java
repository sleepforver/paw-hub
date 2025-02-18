package com.pet.manager.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.UsersMapper;
import com.pet.manager.domain.Users;
import com.pet.manager.service.IUsersService;

/**
 * 用户管理Service业务层处理
 * 
 * @author kkk
 * @date 2025-02-17
 */
@Service
public class UsersServiceImpl implements IUsersService 
{
    @Autowired
    private UsersMapper usersMapper;

    /**
     * 查询用户管理
     * 
     * @param userId 用户管理主键
     * @return 用户管理
     */
    @Override
    public Users selectUsersByUserId(Long userId)
    {
        return usersMapper.selectUsersByUserId(userId);
    }

    /**
     * 查询用户管理列表
     * 
     * @param users 用户管理
     * @return 用户管理
     */
    @Override
    public List<Users> selectUsersList(Users users)
    {
        return usersMapper.selectUsersList(users);
    }

    /**
     * 新增用户管理
     * 
     * @param users 用户管理
     * @return 结果
     */
    @Override
    public int insertUsers(Users users)
    {
        return usersMapper.insertUsers(users);
    }

    /**
     * 修改用户管理
     * 
     * @param users 用户管理
     * @return 结果
     */
    @Override
    public int updateUsers(Users users)
    {
        return usersMapper.updateUsers(users);
    }

    /**
     * 批量删除用户管理
     * 
     * @param userIds 需要删除的用户管理主键
     * @return 结果
     */
    @Override
    public int deleteUsersByUserIds(Long[] userIds)
    {
        return usersMapper.deleteUsersByUserIds(userIds);
    }

    /**
     * 删除用户管理信息
     * 
     * @param userId 用户管理主键
     * @return 结果
     */
    @Override
    public int deleteUsersByUserId(Long userId)
    {
        return usersMapper.deleteUsersByUserId(userId);
    }
}
