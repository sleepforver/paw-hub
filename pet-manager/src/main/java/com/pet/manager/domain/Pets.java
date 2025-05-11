package com.pet.manager.domain;

        import java.util.Date;
    import com.fasterxml.jackson.annotation.JsonFormat;
    import com.pet.common.annotation.Excel;
            import com.pet.common.core.domain.BaseEntity;
    import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 宠物管理对象 tb_pets
 *
 * @author kkk
 * @date 2025-02-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pets extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 宠物ID */
                            @Excel(name = "宠物ID")
        private Long petId;

                /** 宠物名称 */
                            @Excel(name = "宠物名称")
        private String petName;

                /** 宠物种类 */
                            @Excel(name = "宠物种类")
        private String species;

                /** 宠物品种 */
                            @Excel(name = "宠物品种")
        private String breed;

                /** 宠物年龄 */
                            @Excel(name = "宠物年龄")
        private Long age;

                /** 宠物性别e/Female/Other */
                            @Excel(name = "宠物性别e/Female/Other")
        private String gender;

                /** 宠物图片URL */
                            @Excel(name = "宠物图片URL")
        private String petImageUrl;

                /** 疫苗接种记录 */
                            @Excel(name = "疫苗接种记录")
        private String vaccinationRecord;

                /** 所属用户ID，外键，关联 tb_users 表 */
                    private Long userId;

                /** 记录创建时间，默认为当前时间 */
                    private Date createdAt;

                /** 记录创建人ID */
                    private Long createdBy;

                /** 记录更新时间，默认为当前时间，更新时自动更新 */
                    private Date updatedAt;

                /** 记录更新人ID */
                    private Long updatedBy;




}
