package com.pet.manager.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.StreamSupport;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.common.constant.PawHubConstants;
import com.pet.common.exception.ServiceException;
import com.pet.manager.controller.AiController;
import com.pet.manager.domain.PetHealthAnalysis;
import com.pet.manager.domain.dto.ChatRequestDto;
import com.pet.manager.domain.dto.PredictionResultDto;
import com.pet.manager.domain.vo.PetHealthRecordsVo;
import com.pet.manager.service.IPetHealthAnalysisService;
import com.pet.manager.service.IRoboflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.PetHealthRecordsMapper;
import com.pet.manager.domain.PetHealthRecords;
import com.pet.manager.service.IPetHealthRecordsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 宠物健康Service业务层处理
 *
 * @author kkk
 * @date 2025-03-20
 */
@Service
public class PetHealthRecordsServiceImpl implements IPetHealthRecordsService
{
    @Autowired
    private PetHealthRecordsMapper petHealthRecordsMapper;

    @Autowired
    private AiController aiController;

    @Autowired
    private IRoboflowService roboflowService;

    @Autowired
    private IPetHealthAnalysisService petHealthAnalysisService;

    /**
     * 查询宠物健康
     *
     * @param recordId 宠物健康主键
     * @return 宠物健康
     */
    @Override
    public PetHealthRecords selectPetHealthRecordsByRecordId(Long recordId)
    {
        return petHealthRecordsMapper.selectPetHealthRecordsByRecordId(recordId);
    }

    /**
     * 查询宠物健康列表
     *
     * @param petHealthRecords 宠物健康
     * @return 宠物健康
     */
    @Override
    public List<PetHealthRecords> selectPetHealthRecordsList(PetHealthRecords petHealthRecords)
    {
        return petHealthRecordsMapper.selectPetHealthRecordsList(petHealthRecords);
    }

    /**
     * 新增宠物健康
     *
     * @param petHealthRecords 宠物健康
     * @return 结果
     */
    @Override
    public int insertPetHealthRecords(PetHealthRecords petHealthRecords) throws IOException {
        return petHealthRecordsMapper.insertPetHealthRecords(petHealthRecords);
    }

    /**
     * 修改宠物健康
     *
     * @param petHealthRecords 宠物健康
     * @return 结果
     */
    @Override
    public int updatePetHealthRecords(PetHealthRecords petHealthRecords)
    {
        return petHealthRecordsMapper.updatePetHealthRecords(petHealthRecords);
    }

    /**
     * 批量删除宠物健康
     *
     * @param recordIds 需要删除的宠物健康主键
     * @return 结果
     */
    @Override
    public int deletePetHealthRecordsByRecordIds(Long[] recordIds)
    {
        return petHealthRecordsMapper.deletePetHealthRecordsByRecordIds(recordIds);
    }

    /**
     * 删除宠物健康信息
     *
     * @param recordId 宠物健康主键
     * @return 结果
     */
    @Override
    public int deletePetHealthRecordsByRecordId(Long recordId)
    {
        return petHealthRecordsMapper.deletePetHealthRecordsByRecordId(recordId);
    }

    @Transactional
    @Override
    public PetHealthRecordsVo analysis(PetHealthRecords petHealthRecords) throws IOException {
        //先做非空判断
            //调用图像识别模型来识别图像
            ChatRequestDto chatRequest = new ChatRequestDto();
            chatRequest.setImage(petHealthRecords.getImageUrl());
            PredictionResultDto predictionResultDto = roboflowService.runWorkflow(petHealthRecords.getImageUrl());
            //获取识别后的结果，如置信区间，疾病名称等，在调用ai模型来识别疾病，来写出建议
            //先获取置信度，如果置信度过低则直接抛异常
            if (predictionResultDto.getConfidence() < 0.6) {
                throw new ServiceException("检测结果偏差过大，请到正规的宠物医院检查");
            }
            //根据分析出的结果拿到置信度最高的疾病信息
            String className = predictionResultDto.getClassName();
            //将拿到的疾病信息封装
            chatRequest.setMessage(className);
            //使用时间戳作为chatId
            chatRequest.setChatId(String.valueOf(System.currentTimeMillis()));
            //然后调用ai模型来分析处理
            String aiAnwer = aiController.chat(petHealthRecords.getAiModel(), chatRequest);
            //封装所拿到的数据
            petHealthRecords.setCheckDate(new Date());
            petHealthRecords.setAiDiagnosis(predictionResultDto.getClassName());
            petHealthRecords.setRecommendations(aiAnwer);
            //如何获取到的predictionResultDto为空并且健康，则为0，反之为1
            if (predictionResultDto != null && !predictionResultDto.getClassName().equals("healthy")) {
                petHealthRecords.setStatus(PawHubConstants.HEALTH_STATUS_HEALTHY);
            } else {
                petHealthRecords.setStatus(PawHubConstants.HEALTH_STATUS_ILL);
            }

            //同时封装Ai分析详情表
            PetHealthAnalysis petHealthAnalysis = new PetHealthAnalysis();
            petHealthAnalysis.setDiseaseName(predictionResultDto.getClassName());
            petHealthAnalysis.setConfidenceScore(BigDecimal.valueOf(predictionResultDto.getConfidence()));
            petHealthAnalysis.setAnalysisDetails(aiAnwer);
            //插入
            petHealthRecordsMapper.insertPetHealthRecords(petHealthRecords);
            petHealthAnalysis.setRecordId(petHealthRecords.getRecordId());
            petHealthAnalysisService.insertPetHealthAnalysis(petHealthAnalysis);
            PetHealthRecordsVo petHealthRecordsVo = new PetHealthRecordsVo();
            BeanUtil.copyProperties(petHealthRecords,  petHealthRecordsVo);
            petHealthRecordsVo.setConfidence(predictionResultDto.getConfidence());
            // 使用毫秒级时间戳
            long timestamp = System.currentTimeMillis();
            // 生成 8 位随机整数
            int randomPart = new Random().nextInt(90000000) + 10000000;
            // 拼接成长整型
            long diagnosisId = timestamp * 100000000L + randomPart;
            //诊断编号为时间戳+uuid的四个随机参数
            petHealthRecordsVo.setClassId(diagnosisId);
            return petHealthRecordsVo;
    }
}
