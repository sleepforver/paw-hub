package com.pet.manager.service.impl;

import java.util.List;

import com.pet.manager.controller.AiController;
import com.pet.manager.mapper.PetHealthRecordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.PetHealthAnalysisMapper;
import com.pet.manager.domain.PetHealthAnalysis;
import com.pet.manager.service.IPetHealthAnalysisService;

/**
 * AI分析详情Service业务层处理
 *
 * @author kkk
 * @date 2025-03-20
 */
@Service
public class PetHealthAnalysisServiceImpl implements IPetHealthAnalysisService
{
    @Autowired
    private PetHealthAnalysisMapper petHealthAnalysisMapper;

    @Autowired
    private PetHealthRecordsMapper petHealthRecordsMapper;

    /**
     * 查询AI分析详情
     *
     * @param analysisId AI分析详情主键
     * @return AI分析详情
     */
    @Override
    public PetHealthAnalysis selectPetHealthAnalysisByAnalysisId(Long analysisId)
    {
        return petHealthAnalysisMapper.selectPetHealthAnalysisByAnalysisId(analysisId);
    }

    /**
     * 查询AI分析详情列表
     *
     * @param petHealthAnalysis AI分析详情
     * @return AI分析详情
     */
    @Override
    public List<PetHealthAnalysis> selectPetHealthAnalysisList(PetHealthAnalysis petHealthAnalysis)
    {
        return petHealthAnalysisMapper.selectPetHealthAnalysisList(petHealthAnalysis);
    }

    /**
     * 新增AI分析详情
     *
     * @param petHealthAnalysis AI分析详情
     * @return 结果
     */
    @Override
    public int insertPetHealthAnalysis(PetHealthAnalysis petHealthAnalysis)
    {
        return petHealthAnalysisMapper.insertPetHealthAnalysis(petHealthAnalysis);
    }

    /**
     * 修改AI分析详情
     *
     * @param petHealthAnalysis AI分析详情
     * @return 结果
     */
    @Override
    public int updatePetHealthAnalysis(PetHealthAnalysis petHealthAnalysis)
    {
        return petHealthAnalysisMapper.updatePetHealthAnalysis(petHealthAnalysis);
    }

    /**
     * 批量删除AI分析详情
     *
     * @param analysisIds 需要删除的AI分析详情主键
     * @return 结果
     */
    @Override
    public int deletePetHealthAnalysisByAnalysisIds(Long[] analysisIds)
    {
        return petHealthAnalysisMapper.deletePetHealthAnalysisByAnalysisIds(analysisIds);
    }

    /**
     * 删除AI分析详情信息
     *
     * @param analysisId AI分析详情主键
     * @return 结果
     */
    @Override
    public int deletePetHealthAnalysisByAnalysisId(Long analysisId)
    {
        return petHealthAnalysisMapper.deletePetHealthAnalysisByAnalysisId(analysisId);
    }
}
