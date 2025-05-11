package com.pet.manager.domain;

        import java.math.BigDecimal;
    import java.util.Date;
    import com.fasterxml.jackson.annotation.JsonFormat;
    import com.pet.common.annotation.Excel;
            import com.pet.common.core.domain.BaseEntity;
    import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * AI分析详情对象 tb_pet_health_ai_analysis
 *
 * @author kkk
 * @date 2025-03-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetHealthAnalysis extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 分析ID，主键，自增 */
                    private Long analysisId;

                /** 关联健康记录ID */
                            @Excel(name = "关联健康记录ID")
        private Long recordId;

                /** 疾病名称 */
                            @Excel(name = "疾病名称")
        private String diseaseName;

                /** AI置信度得分 */
                            @Excel(name = "AI置信度得分")
        private BigDecimal confidenceScore;

                /** 检测到的特征 */
                            @Excel(name = "检测到的特征")
        private String detectedFeatures;

                /** 详细分析结果 */
                            @Excel(name = "详细分析结果")
        private String analysisDetails;

                /** 记录创建时间 */
                            @JsonFormat(pattern = "yyyy-MM-dd")
                @Excel(name = "记录创建时间", width = 30, dateFormat = "yyyy-MM-dd")
        private Date createdAt;

                /** 记录更新时间 */
                            @JsonFormat(pattern = "yyyy-MM-dd")
                @Excel(name = "记录更新时间", width = 30, dateFormat = "yyyy-MM-dd")
        private Date updatedAt;

    


}
