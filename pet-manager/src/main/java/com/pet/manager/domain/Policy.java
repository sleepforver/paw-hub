package com.pet.manager.domain;

        import com.pet.common.annotation.Excel;
            import com.pet.common.core.domain.BaseEntity;
    import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 策略管理对象 tb_policy
 *
 * @author kkk
 * @date 2025-02-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 策略id */
                            @Excel(name = "策略id")
        private Long policyId;

                /** 策略名称 */
                            @Excel(name = "策略名称")
        private String policyName;

                /** 策略方案，如：80代表8折 */
                            @Excel(name = "策略方案，如：80代表8折")
        private Long discount;

                    


}
