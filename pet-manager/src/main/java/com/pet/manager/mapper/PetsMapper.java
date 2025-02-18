package com.pet.manager.mapper;

import java.util.List;
import com.pet.manager.domain.Pets;

/**
 * 宠物管理Mapper接口
 * 
 * @author kkk
 * @date 2025-02-17
 */
public interface PetsMapper 
{
    /**
     * 查询宠物管理
     * 
     * @param petId 宠物管理主键
     * @return 宠物管理
     */
    public Pets selectPetsByPetId(Long petId);

    /**
     * 查询宠物管理列表
     * 
     * @param pets 宠物管理
     * @return 宠物管理集合
     */
    public List<Pets> selectPetsList(Pets pets);

    /**
     * 新增宠物管理
     * 
     * @param pets 宠物管理
     * @return 结果
     */
    public int insertPets(Pets pets);

    /**
     * 修改宠物管理
     * 
     * @param pets 宠物管理
     * @return 结果
     */
    public int updatePets(Pets pets);

    /**
     * 删除宠物管理
     * 
     * @param petId 宠物管理主键
     * @return 结果
     */
    public int deletePetsByPetId(Long petId);

    /**
     * 批量删除宠物管理
     * 
     * @param petIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePetsByPetIds(Long[] petIds);
}
