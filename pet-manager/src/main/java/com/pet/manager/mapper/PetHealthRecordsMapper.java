package com.pet.manager.mapper;

import java.util.List;
import com.pet.manager.domain.PetHealthRecords;

/**
 * 宠物健康Mapper接口
 * 
 * @author kkk
 * @date 2025-03-20
 */
public interface PetHealthRecordsMapper 
{
    /**
     * 查询宠物健康
     * 
     * @param recordId 宠物健康主键
     * @return 宠物健康
     */
    public PetHealthRecords selectPetHealthRecordsByRecordId(Long recordId);

    /**
     * 查询宠物健康列表
     * 
     * @param petHealthRecords 宠物健康
     * @return 宠物健康集合
     */
    public List<PetHealthRecords> selectPetHealthRecordsList(PetHealthRecords petHealthRecords);

    /**
     * 新增宠物健康
     * 
     * @param petHealthRecords 宠物健康
     * @return 结果
     */
    public int insertPetHealthRecords(PetHealthRecords petHealthRecords);

    /**
     * 修改宠物健康
     * 
     * @param petHealthRecords 宠物健康
     * @return 结果
     */
    public int updatePetHealthRecords(PetHealthRecords petHealthRecords);

    /**
     * 删除宠物健康
     * 
     * @param recordId 宠物健康主键
     * @return 结果
     */
    public int deletePetHealthRecordsByRecordId(Long recordId);

    /**
     * 批量删除宠物健康
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePetHealthRecordsByRecordIds(Long[] recordIds);
}
