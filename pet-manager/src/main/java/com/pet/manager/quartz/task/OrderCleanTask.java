package com.pet.manager.quartz.task;

import cn.hutool.core.date.DateUtil;
import com.pet.common.constant.PawHubConstants;
import com.pet.common.utils.DateUtils;
import com.pet.manager.domain.Appointments;
import com.pet.manager.domain.Orders;
import com.pet.manager.mapper.AppointmentsMapper;
import com.pet.manager.mapper.OrdersMapper;
import com.pet.manager.service.IOrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.pet.framework.datasource.DynamicDataSourceContextHolder.log;

@Component
@Slf4j
public class OrderCleanTask {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private AppointmentsMapper appointmentsMapper;

    /**
     * 每天凌晨2点 执行过期订单和预约清理
     * cron = "秒 分 时 日 月 星期"
     */
    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void cleanExpiredOrders() {
        log.info("开始执行过期订单清理任务...");

        // 1.查询需要清理的订单
        Map<String, Object> params = new HashMap<>();
        params.put("orderStatus", PawHubConstants.ORDER_STATUS_UNPAID); // 只处理未支付订单
        params.put("appointmentStatus", PawHubConstants.Appointment_STATUS_WAIT_CONFIRM); // 只处理待确认预约
        params.put("expiredDate", DateUtil.offsetDay(new Date(), -1)); // 过期日期判断

        //查询过期的订单
        List<Orders> expiredOrders = ordersMapper.selectExpiredOrders(params);
        //查询过期的预约
        List<Appointments> expiredAppointments = appointmentsMapper.selectExpiredAppointments(params);

        int count = 0;
        for (Orders order : expiredOrders) {
            order.setOrderStatus(PawHubConstants.ORDER_STATUS_SERVICE_CANCELLED); // 服务取消
            order.setCancelInfo("系统自动取消，预约已过期");
            order.setUpdateTime(new Date());
            order.setUpdateBy("system-task");

            int rows = ordersMapper.updateOrders(order);
            if (rows > 0) {
                count++;
            }
        }
        for (Appointments appointment : expiredAppointments) {
            appointment.setStatus(PawHubConstants.Appointment_STATUS_CANCELLED); // 服务取消
            appointment.setCancelInfo("系统自动取消，预约已过期");
            appointment.setUpdateTime(new Date());
            appointment.setUpdateBy("system-task");

            int rows = appointmentsMapper.updateAppointments(appointment);
            if (rows > 0) {
                count++;
            }
        }

        log.info("本次清理过期订单和预约 {} 条", count);
    }

    /**
     * 每天晚上 21 點執行
     * 提醒明天的预约
     * cron = "秒 分 时 日 月 星期"
     */
    @Scheduled(cron = "0 0 21 * * ?")
    public void notifyTomorrowAppointments() {
        log.info("开始执行明日预约通知任务...");

        // 查詢明天預約的訂單
        Map<String, Object> params = new HashMap<>();
        Date tomorrowStart = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), 1));
        Date tomorrowEnd = DateUtil.endOfDay(tomorrowStart);

        params.put("startTime", tomorrowStart);
        params.put("endTime", tomorrowEnd);
        params.put("statusList", Arrays.asList(PawHubConstants.ORDER_STATUS_UNPAID,PawHubConstants.ORDER_STATUS_PAID));// 只处理未支付和已支付

        List<Orders> tomorrowOrders = ordersMapper.selectTomorrowOrders(params);

        for (Orders order : tomorrowOrders) {
            try {
                sendAppointmentReminder(order);
            } catch (Exception e) {
                log.error("通知失败，orderId: {}", order.getOrderId(), e);
            }
        }

        log.info("明日预约通知完成，共 {} 笔", tomorrowOrders.size());
    }

    private void sendAppointmentReminder(Orders order) {
        //个人练习使用
        log.info("发送提醒给用户 {}：明天有 {} 服务，时间：{}",
                order.getUserId(), order.getServiceName(),
                DateUtil.formatDateTime(order.getCreateTime()));
    }
}
