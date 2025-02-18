package com.pet.manager.mapper;

import java.util.List;
import com.pet.manager.domain.Breeds;

/**
 * 宠物品种管理Mapper接口
 * 
 * @author kkk
 * @date 2025-02-17
 */
public interface BreedsMapper 
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
     * 删除宠物品种管理
     * 
     * @param breedId 宠物品种管理主键
     * @return 结果
     */
    public int deleteBreedsByBreedId(Long breedId);

    /**
     * 批量删除宠物品种管理
     * 
     * @param breedIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBreedsByBreedIds(Long[] breedIds);
}
