package com.pet.manager.service.impl;

import java.util.List;

import com.pet.common.exception.ServiceException;
import com.pet.common.utils.DateUtils;
import com.pet.manager.domain.Role;
import com.pet.manager.domain.vo.EmpStatisticsVO;
import com.pet.manager.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.EmpMapper;
import com.pet.manager.domain.Emp;
import com.pet.manager.service.IEmpService;

/**
 * 员工列表Service业务层处理
 *
 * @author kkk
 * @date 2025-02-27
 */
@Service
public class EmpServiceImpl implements IEmpService
{
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询员工列表
     *
     * @param id 员工列表主键
     * @return 员工列表
     */
    @Override
    public Emp selectEmpById(Long id)
    {
        return empMapper.selectEmpById(id);
    }

    /**
     * 查询员工列表列表
     *
     * @param emp 员工列表
     * @return 员工列表
     */
    @Override
    public List<Emp> selectEmpList(Emp emp)
    {
        return empMapper.selectEmpList(emp);
    }

    /**
     * 新增员工列表
     *
     * @param emp 员工列表
     * @return 结果
     */
    @Override
    public int insertEmp(Emp emp)
    {
        //判断当前员工是否有角色id
        if(emp.getRoleId()!=null){
            //从角色表中获取信息
            Role role = roleMapper.selectRoleByRoleId(emp.getRoleId());
            if(role!=null){
                emp.setRoleCode(role.getRoleCode());
                emp.setRoleName(role.getRoleName());
            }else{
                throw new ServiceException("没有这个角色或角色停用");
            }
        }
        emp.setCreateTime(DateUtils.getNowDate());
        return empMapper.insertEmp(emp);
    }

    /**
     * 修改员工列表
     *
     * @param emp 员工列表
     * @return 结果
     */
    @Override
    public int updateEmp(Emp emp)
    {
        emp.setUpdateTime(DateUtils.getNowDate());
        return empMapper.updateEmp(emp);
    }

    /**
     * 批量删除员工列表
     *
     * @param ids 需要删除的员工列表主键
     * @return 结果
     */
    @Override
    public int deleteEmpByIds(Long[] ids)
    {
        return empMapper.deleteEmpByIds(ids);
    }

    /**
     * 删除员工列表信息
     *
     * @param id 员工列表主键
     * @return 结果
     */
    @Override
    public int deleteEmpById(Long id)
    {
        return empMapper.deleteEmpById(id);
    }

    /**
     * 统计员工数量
     * @return
     */
    @Override
    public EmpStatisticsVO statistics() {
        EmpStatisticsVO empStatisticsVO = new EmpStatisticsVO();
        empStatisticsVO.setTotalCount(empMapper.countEmp());
        return empStatisticsVO;
    }
}
