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
 * 服务管理对象 tb_services
 *
 * @author kkk
 * @date 2025-02-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Services extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 服务ID，主键，自增 */
                            @Excel(name = "服务ID，主键，自增")
        private Long serviceId;

                /** 服务类型id，外键 */
                            @Excel(name = "服务类型id，外键")
        private Long serviceTypeId;

                            /** 员工id */
                            @Excel(name = "员工id")
        private Long empId;

                /** 服务类型（常规/寄养） */
                            @Excel(name = "服务类型", readConverterExp = "常=规/寄养")
        private String serviceType;

                /** 服务状态 0-启用 1- 禁用 */
                            @Excel(name = "服务状态 0-启用 1- 禁用")
        private String serviceStatus;


                /** 策略id */
                            @Excel(name = "策略id")
        private Long policyId;



                /** 服务描述 */
                            @Excel(name = "服务描述")
        private String description;


                /** 包含遛狗服务 */
                            @Excel(name = "包含遛狗服务")
        private Long includeWalk;

                /** 特殊要求说明 */
                    private String specialRequirements;

                /** 记录创建时间，默认为当前时间 */
                            @JsonFormat(pattern = "yyyy-MM-dd")
                @Excel(name = "记录创建时间，默认为当前时间", width = 30, dateFormat = "yyyy-MM-dd")
        private Date createdAt;

                /** 记录创建人ID */
                    private Long createdBy;

                /** 记录更新时间，默认为当前时间，更新时自动更新 */
                    private Date updatedAt;

                /** 记录更新人ID */
                    private Long updatedBy;




}
