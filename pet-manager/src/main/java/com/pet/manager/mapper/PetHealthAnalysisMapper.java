package com.pet.manager.mapper;

import java.util.List;
import com.pet.manager.domain.PetHealthAnalysis;

/**
 * AI分析详情Mapper接口
 * 
 * @author kkk
 * @date 2025-03-20
 */
public interface PetHealthAnalysisMapper 
{
    /**
     * 查询AI分析详情
     * 
     * @param analysisId AI分析详情主键
     * @return AI分析详情
     */
    public PetHealthAnalysis selectPetHealthAnalysisByAnalysisId(Long analysisId);

    /**
     * 查询AI分析详情列表
     * 
     * @param petHealthAnalysis AI分析详情
     * @return AI分析详情集合
     */
    public List<PetHealthAnalysis> selectPetHealthAnalysisList(PetHealthAnalysis petHealthAnalysis);

    /**
     * 新增AI分析详情
     * 
     * @param petHealthAnalysis AI分析详情
     * @return 结果
     */
    public int insertPetHealthAnalysis(PetHealthAnalysis petHealthAnalysis);

    /**
     * 修改AI分析详情
     * 
     * @param petHealthAnalysis AI分析详情
     * @return 结果
     */
    public int updatePetHealthAnalysis(PetHealthAnalysis petHealthAnalysis);

    /**
     * 删除AI分析详情
     * 
     * @param analysisId AI分析详情主键
     * @return 结果
     */
    public int deletePetHealthAnalysisByAnalysisId(Long analysisId);

    /**
     * 批量删除AI分析详情
     * 
     * @param analysisIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePetHealthAnalysisByAnalysisIds(Long[] analysisIds);
}
