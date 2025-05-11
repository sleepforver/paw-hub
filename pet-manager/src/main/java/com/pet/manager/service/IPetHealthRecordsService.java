package com.pet.manager.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pet.manager.domain.PetHealthRecords;
import com.pet.manager.domain.vo.PetHealthRecordsVo;

/**
 * 宠物健康Service接口
 *
 * @author kkk
 * @date 2025-03-20
 */
public interface IPetHealthRecordsService
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
    public int insertPetHealthRecords(PetHealthRecords petHealthRecords) throws IOException;

    /**
     * 修改宠物健康
     *
     * @param petHealthRecords 宠物健康
     * @return 结果
     */
    public int updatePetHealthRecords(PetHealthRecords petHealthRecords);

    /**
     * 批量删除宠物健康
     *
     * @param recordIds 需要删除的宠物健康主键集合
     * @return 结果
     */
    public int deletePetHealthRecordsByRecordIds(Long[] recordIds);

    /**
     * 删除宠物健康信息
     *
     * @param recordId 宠物健康主键
     * @return 结果
     */
    public int deletePetHealthRecordsByRecordId(Long recordId);

    /**
     * 宠物健康分析
     * @param petHealthRecords
     * @return
     */
    PetHealthRecordsVo analysis(PetHealthRecords petHealthRecords) throws IOException;
}
