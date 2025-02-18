package com.pet.manager.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.PetsMapper;
import com.pet.manager.domain.Pets;
import com.pet.manager.service.IPetsService;

/**
 * 宠物管理Service业务层处理
 * 
 * @author kkk
 * @date 2025-02-17
 */
@Service
public class PetsServiceImpl implements IPetsService 
{
    @Autowired
    private PetsMapper petsMapper;

    /**
     * 查询宠物管理
     * 
     * @param petId 宠物管理主键
     * @return 宠物管理
     */
    @Override
    public Pets selectPetsByPetId(Long petId)
    {
        return petsMapper.selectPetsByPetId(petId);
    }

    /**
     * 查询宠物管理列表
     * 
     * @param pets 宠物管理
     * @return 宠物管理
     */
    @Override
    public List<Pets> selectPetsList(Pets pets)
    {
        return petsMapper.selectPetsList(pets);
    }

    /**
     * 新增宠物管理
     * 
     * @param pets 宠物管理
     * @return 结果
     */
    @Override
    public int insertPets(Pets pets)
    {
        return petsMapper.insertPets(pets);
    }

    /**
     * 修改宠物管理
     * 
     * @param pets 宠物管理
     * @return 结果
     */
    @Override
    public int updatePets(Pets pets)
    {
        return petsMapper.updatePets(pets);
    }

    /**
     * 批量删除宠物管理
     * 
     * @param petIds 需要删除的宠物管理主键
     * @return 结果
     */
    @Override
    public int deletePetsByPetIds(Long[] petIds)
    {
        return petsMapper.deletePetsByPetIds(petIds);
    }

    /**
     * 删除宠物管理信息
     * 
     * @param petId 宠物管理主键
     * @return 结果
     */
    @Override
    public int deletePetsByPetId(Long petId)
    {
        return petsMapper.deletePetsByPetId(petId);
    }
}
