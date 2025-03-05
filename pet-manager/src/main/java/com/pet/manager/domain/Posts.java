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
 * 动态管理对象 tb_posts
 *
 * @author kkk
 * @date 2025-02-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posts extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 动态ID，主键，自增 */
                            @Excel(name = "动态ID，主键，自增")
        private Long postId;

                /** 用户ID，外键，关联 tb_users 表 */
                            @Excel(name = "用户ID，外键，关联 tb_users 表")
        private Long userId;

                /** 动态内容 */
                            @Excel(name = "动态内容")
        private String content;

                /** 动态图片 */
                            @Excel(name = "动态图片")
        private String postImage;

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
