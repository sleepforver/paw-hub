package com.pet.manager.domain;

        import com.pet.common.annotation.Excel;
            import com.pet.common.core.domain.BaseEntity;
    import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 宠物品种管理对象 tb_breeds
 *
 * @author kkk
 * @date 2025-02-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Breeds extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 品种ID，主键，自增 */
                    private Long breedId;

                /** 宠物品种名称，不可为空 */
                            @Excel(name = "宠物品种名称，不可为空")
        private String breedName;

                /** 宠物种类，不可为空 */
                            @Excel(name = "宠物种类，不可为空")
        private String species;

    


}
