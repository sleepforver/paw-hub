package com.pet.manager.service;

import java.util.List;
import com.pet.manager.domain.Breeds;

/**
 * 宠物品种管理Service接口
 * 
 * @author kkk
 * @date 2025-02-17
 */
public interface IBreedsService 
{
    /**
     * 查询宠物品种管理
     * 
     * @param breedId 宠物品种管理主键
     * @return 宠物品种管理
     */
    public Breeds selectBreedsByBreedId(Long breedId);

    /**
     * 查询宠物品种管理列表
     * 
     * @param breeds 宠物品种管理
     * @return 宠物品种管理集合
     */
    public List<Breeds> selectBreedsList(Breeds breeds);

    /**
     * 新增宠物品种管理
     * 
     * @param breeds 宠物品种管理
     * @return 结果
     */
    public int insertBreeds(Breeds breeds);

    /**
     * 修改宠物品种管理
     * 
     * @param breeds 宠物品种管理
     * @return 结果
     */
    public int updateBreeds(Breeds breeds);

    /**
     * 批量删除宠物品种管理
     * 
     * @param breedIds 需要删除的宠物品种管理主键集合
     * @return 结果
     */
    public int deleteBreedsByBreedIds(Long[] breedIds);

    /**
     * 删除宠物品种管理信息
     * 
     * @param breedId 宠物品种管理主键
     * @return 结果
     */
    public int deleteBreedsByBreedId(Long breedId);
}
