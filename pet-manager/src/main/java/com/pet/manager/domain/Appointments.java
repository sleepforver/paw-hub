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
 * 预约管理对象 tb_appointments
 *
 * @author kkk
 * @date 2025-02-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointments extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 预约ID，主键，自增 */
                            @Excel(name = "预约ID，主键，自增")
        private Long appointmentId;

                            @Excel(name = "预约编号")
        private String appointmentNo;

                /** 宠物ID，外键，关联 tb_pets 表 */
                            @Excel(name = "宠物ID，外键，关联 tb_pets 表")
        private Long petId;

                /** 服务ID，外键，关联 tb_services 表 */
                            @Excel(name = "服务类型id")
        private Long serviceTypeId;

                            /** 员工id */
                            @Excel(name = "员工id")
        private Long empId;

                            @Excel(name = "房间类型")
        private String roomType;

                            @Excel(name = "房间号")
        private Long roomNumber;

                            @Excel(name = "用户id")
                            private Long userId;

                /** 预约日期和时间，不可为空 */
                            @JsonFormat(pattern = "yyyy-MM-dd")
                @Excel(name = "预约日期和时间，不可为空", width = 30, dateFormat = "yyyy-MM-dd")
        private Date appointmentDate;

                /** 预约状态，枚举值：Pending/Confirmed/Cancelled/Completed */
                            @Excel(name = "预约状态，枚举值：Pending/Confirmed/Cancelled/Completed")
        private Long status;

                /** 取消原因 */
                            @Excel(name = "取消原因")
        private String cancelInfo;

                            @Excel(name = "预约地址")
        private String addr;

                /** 记录创建时间，默认为当前时间 */
                    private Date createdAt;

                /** 记录创建人ID */
                    private Long createdBy;

                /** 记录更新时间，默认为当前时间，更新时自动更新 */
                    private Date updatedAt;

                /** 记录更新人ID */
                    private Long updatedBy;




}
