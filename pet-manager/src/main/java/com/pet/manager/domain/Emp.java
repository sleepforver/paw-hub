package com.pet.manager.domain;

        import com.pet.common.annotation.Excel;
            import com.pet.common.core.domain.BaseEntity;
    import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 员工列表对象 tb_emp
 *
 * @author kkk
 * @date 2025-02-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emp extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 主键 */
                    private Long id;

                /** 员工名称 */
                            @Excel(name = "员工名称")
        private String userName;

                /** 角色id */
                    private Long roleId;

                /** 角色编号 */
                    private String roleCode;

                /** 角色名称 */
                            @Excel(name = "角色名称")
        private String roleName;

                /** 联系电话 */
                            @Excel(name = "联系电话")
        private String mobile;

                /** 员工头像 */
                            @Excel(name = "员工头像")
        private String image;

                /** 是否启用 */
                    private Long status;

                    


}
