package com.pet.manager.domain;

        import java.util.Date;
    import com.fasterxml.jackson.annotation.JsonFormat;
    import com.pet.common.annotation.Excel;
            import com.pet.common.core.domain.BaseEntity;
        import lombok.*;

/**
 * 宠物健康对象 tb_pet_health_records
 *
 * @author kkk
 * @date 2025-03-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetHealthRecords extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 记录ID，主键，自增 */
                            @Excel(name = "记录ID，主键，自增")
        private Long recordId;

                /** 宠物ID，关联宠物表 */
                            @Excel(name = "宠物ID，关联宠物表")
        private Long petId;

                /** 检查日期 */
                            @JsonFormat(pattern = "yyyy-MM-dd")
                @Excel(name = "检查日期", width = 30, dateFormat = "yyyy-MM-dd")
        private Date checkDate;

                /** 上传的图片URL */
                            @Excel(name = "上传的图片URL")
        private String imageUrl;

                /** 症状描述 */
                    private String symptoms;

                /** AI诊断结果 */
                @Excel(name = "AI诊断结果")
                    private String aiDiagnosis;

                /** 选用的图像识别模型 ,目前默认为0 */
                @Excel(name = "选用的图像识别模型")
                @Getter
                 private Long  imageModel;

                /** 选用ai模型 0-zhipu,1-qwen,2-deepseek */
                @Excel(name = "选用ai模型")
                private String aiModel;

                /** 建议措施 */
                @Excel(name = "建议措施")
                    private String recommendations;

                /** 状态(0-健康，1-患病） */
                            @Excel(name = "状态(0-健康，1-患病）")
        private Long status;

                /** 记录创建时间 */
                    private Date createdAt;

                /** 记录创建人ID */
                    private Long createdBy;

                /** 记录更新时间 */
                    private Date updatedAt;

                /** 记录更新人ID */
                    private Long updatedBy;




}
