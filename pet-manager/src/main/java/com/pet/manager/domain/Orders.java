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
 * 订单管理对象 tb_orders
 *
 * @author kkk
 * @date 2025-02-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 订单ID */
                            @Excel(name = "订单ID")
        private Long orderId;

                /** 订单编号 */
                            @Excel(name = "订单编号")
        private Long orderNo;

                /** 订单类型 0-常规 1-寄养 */
                            @Excel(name = "订单类型 0-常规 1-寄养")
        private Long orderType;

                /** 用户ID */
                    private Long userId;

                    /** 员工id */
                    private Long empId;

                /** 服务id */
                    private Long serviceId;

                /** 服务编号 */
                            @Excel(name = "服务编号")
        private String serviceTypeCode;

                /** 服务名称 */
                            @Excel(name = "服务名称")
        private String serviceName;

                            @Excel(name = "房间类型")
        private String roomType;

                            @Excel(name = "房间号")
        private Long roomNumber;

                /** 订单金额，单位为分 */
                            @Excel(name = "订单金额，单位为分")
        private BigDecimal amount;

                /** 使用服务金额，单位为分 */
                    private BigDecimal price;

                /** 支付类型0-微信，1-支付宝 */
                    private Long payType;

                /** 支付状态 0-未支付，1-已支付,2-退款中,3-退款完成 */
                    private Long payStatus;

                /** 订单状态 0-未支付，1-已支付 2-服务完成 3-服务取消 */
                            @Excel(name = "订单状态 0-未支付，1-已支付 2-服务完成 3-服务取消")
        private Long orderStatus;

                /** 服务地址 */
                    private String addr;

                    /** 取消原因 */
                    private String cancelInfo;

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
