package com.pet.manager.domain;

        import com.pet.common.annotation.Excel;
            import com.pet.common.core.domain.BaseEntity;
    import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

        import java.math.BigDecimal;

/**
 * 服务类型对象 tb_service_types
 *
 * @author kkk
 * @date 2025-02-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceTypes extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 服务类型ID，主键，自增 */
                            @Excel(name = "服务类型ID，主键，自增")
        private Long serviceTypeId;

                /** 服务类型编码，不可为空 */
                            @Excel(name = "服务类型编码，不可为空")
        private String serviceTypeCode;

                /** 服务名称 */
                            @Excel(name = "服务名称")
        private String serviceName;

                /** 服务类型(0-常规，1-寄养) */
                            @Excel(name = "服务类型(0-常规，1-寄养)")
        private String serviceType;

                /** 服务图片 */
                            @Excel(name = "服务图片")
        private String serviceImage;

            /** 服务价格，不可为空,单位为分 */
                    @Excel(name = "服务价格，不可为空,单位为分")
            private BigDecimal price;



}
