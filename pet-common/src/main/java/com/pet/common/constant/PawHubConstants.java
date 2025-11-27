package com.pet.common.constant;

public class PawHubConstants {

    /**
     * 预约订单待确认
     */
    public static final Long Appointment_STATUS_WAIT_CONFIRM = 0l;

    /**
     * 预约订单已确认
     */
    public static final Long Appointment_STATUS_CONFIRMED = 1l;

    /**
     * 预约订单进行中
     */
    public static final Long Appointment_STATUS_IN_PROGRESS = 2l;

    /**
     * 预约订单已完成
     */
    public static final Long Appointment_STATUS_COMPLETED = 3l;

    /**
     * 预约订单已取消
     */
    public static final Long Appointment_STATUS_CANCELLED = 4l;

    /**
     * 服务状态 启用
     */
    public static final Long SERVICE_STATUS_ENABLED = 0l;

    /**
     * 服务状态 禁用
     */
    public static final Long SERVICE_STATUS_DISABLED = 1l;

    /**
     * 订单类型 常规订单
     */
    public static final Long ORDER_TYPE_NORMAL = 0l  ;

    /**
     * 订单类型 寄养订单
     */
    public static final Long ORDER_TYPE_FOSTER = 1l;

    /**
     * 支付状态 未支付
     */
    public static final Long PAY_STATUS_UNPAID = 0l;

    /**
     * 支付状态 已支付
     */
    public static final Long PAY_STATUS_PAID = 1l;

    /**
     * 支付状态 退款中
     */
    public static final Long PAY_STATUS_REFUNDING = 2l;

    /**
     * 支付状态 已退款
     */
    public static final Long PAY_STATUS_REFUNDED = 3l;

    /**
     * 订单状态 未支付
     */
    public static final Long ORDER_STATUS_UNPAID = 0l;

    /**
     * 订单状态 已支付
     */
    public static final Long ORDER_STATUS_PAID = 1l;

    /**
     * 订单状态 服务完成
     */
    public static final Long ORDER_STATUS_SERVICE_COMPLETED = 2l;

    /**
     * 订单状态 服务取消
     */
    public static final Long ORDER_STATUS_SERVICE_CANCELLED = 3l;

    /**
     * 健康状态 健康
     */
    public static final Long HEALTH_STATUS_HEALTHY = 0l;

    /**
     * 健康状态 患病
     */
    public static final Long HEALTH_STATUS_ILL = 1l;

    /**
     * 检测状态 检测中
     */
    public static final Long CHECK_STATUS_IN_PROGRESS = 2l;

    /**
     * 检测状态 检测完成
     */
    public static final Long CHECK_STATUS_COMPLETED = 3l;

    /**
     * 宠物类型 狗
     */
    public static final Long PET_TYPE_DOG = 1l;
    /**
     * 宠物类型 猫
     */
    public static final Long PET_TYPE_CAT = 0l;

    /**
     * 敏感词 ：共产党
     */
    public static final String SENSITIVE_WORD_CONSTITUTION = "共产党";
}
