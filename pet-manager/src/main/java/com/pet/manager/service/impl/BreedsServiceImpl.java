package com.pet.manager.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.BreedsMapper;
import com.pet.manager.domain.Breeds;
import com.pet.manager.service.IBreedsService;

/**
 * 宠物品种管理Service业务层处理
 * 
 * @author kkk
 * @date 2025-02-17
 */
@Service
public class BreedsServiceImpl implements IBreedsService 
{
    @Autowired
    private BreedsMapper breedsMapper;

    /**
     * 查询宠物品种管理
     * 
     * @param breedId 宠物品种管理主键
     * @return 宠物品种管理
     */
    @Override
    public Breeds selectBreedsByBreedId(Long breedId)
    {
        return breedsMapper.selectBreedsByBreedId(breedId);
    }

    /**
     * 查询宠物品种管理列表
     * 
     * @param breeds 宠物品种管理
     * @return 宠物品种管理
     */
    @Override
    public List<Breeds> selectBreedsList(Breeds breeds)
    {
        return breedsMapper.selectBreedsList(breeds);
    }

    /**
     * 新增宠物品种管理
     * 
     * @param breeds 宠物品种管理
     * @return 结果
     */
    @Override
    public int insertBreeds(Breeds breeds)
    {
        return breedsMapper.insertBreeds(breeds);
    }

    /**
     * 修改宠物品种管理
     * 
     * @param breeds 宠物品种管理
     * @return 结果
     */
    @Override
    public int updateBreeds(Breeds breeds)
    {
        return breedsMapper.updateBreeds(breeds);
    }

    /**
     * 批量删除宠物品种管理
     * 
     * @param breedIds 需要删除的宠物品种管理主键
     * @return 结果
     */
    @Override
    public int deleteBreedsByBreedIds(Long[] breedIds)
    {
        return breedsMapper.deleteBreedsByBreedIds(breedIds);
    }

    /**
     * 删除宠物品种管理信息
     * 
     * @param breedId 宠物品种管理主键
     * @return 结果
     */
    @Override
    public int deleteBreedsByBreedId(Long breedId)
    {
        return breedsMapper.deleteBreedsByBreedId(breedId);
    }
}
