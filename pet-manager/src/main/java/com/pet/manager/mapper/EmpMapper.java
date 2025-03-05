package com.pet.manager.mapper;

import java.util.List;
import com.pet.manager.domain.Emp;

/**
 * 员工列表Mapper接口
 * 
 * @author kkk
 * @date 2025-02-27
 */
public interface EmpMapper 
{
    /**
     * 查询员工列表
     * 
     * @param id 员工列表主键
     * @return 员工列表
     */
    public Emp selectEmpById(Long id);

    /**
     * 查询员工列表列表
     * 
     * @param emp 员工列表
     * @return 员工列表集合
     */
    public List<Emp> selectEmpList(Emp emp);

    /**
     * 新增员工列表
     * 
     * @param emp 员工列表
     * @return 结果
     */
    public int insertEmp(Emp emp);

    /**
     * 修改员工列表
     * 
     * @param emp 员工列表
     * @return 结果
     */
    public int updateEmp(Emp emp);

    /**
     * 删除员工列表
     * 
     * @param id 员工列表主键
     * @return 结果
     */
    public int deleteEmpById(Long id);

    /**
     * 批量删除员工列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEmpByIds(Long[] ids);
}
