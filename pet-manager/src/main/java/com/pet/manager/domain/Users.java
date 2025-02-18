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
 * 用户管理对象 tb_users
 *
 * @author kkk
 * @date 2025-02-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 用户ID，主键，自增 */
                            @Excel(name = "用户ID，主键，自增")
        private Long userId;

                /** 用户名，唯一，不可为空 */
                            @Excel(name = "用户名，唯一，不可为空")
        private String username;

                /** 用户密码哈希值，不可为空 */
                    private String passwordHash;

                /** 用户邮箱，唯一，不可为空 */
                    private String email;

                /** 用户手机号 */
                            @Excel(name = "用户手机号")
        private String phoneNumber;

                /** 用户地址 */
                            @Excel(name = "用户地址")
        private String address;

                /** 用户头像图片URL */
                            @Excel(name = "用户头像图片URL")
        private String avatarUrl;

                /** 记录创建时间，默认为当前时间 */
                    private Date createdAt;

                /** 记录创建人ID */
                    private Long createdBy;

                /** 记录更新时间，默认为当前时间，更新时自动更新 */
                    private Date updatedAt;

                /** 记录更新人ID */
                    private Long updatedBy;

    


}
