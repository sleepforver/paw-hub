package com.pet.manager.service.impl;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import cn.hutool.core.util.StrUtil;
import com.pet.common.constant.PawHubConstants;
import com.pet.common.exception.ServiceException;
import com.pet.common.utils.DateUtils;
import com.pet.manager.domain.*;
import com.pet.manager.domain.vo.AppointmentsStatisticsVO;
import com.pet.manager.domain.vo.AppointmentsVo;
import com.pet.manager.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.pet.manager.service.IAppointmentsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 预约管理Service业务层处理
 *
 * @author kkk
 * @date 2025-02-19
 */
@Service
public class AppointmentsServiceImpl implements IAppointmentsService
{
    @Autowired
    private AppointmentsMapper appointmentsMapper;

    @Autowired
    private PetsMapper petsMapper;

    @Autowired
    private ServicesMapper servicesMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private FosterRoomsMapper fosterRoomsMapper;
    /**
     * 查询预约管理
     *
     * @param appointmentId 预约管理主键
     * @return 预约管理
     */
    @Override
    public Appointments selectAppointmentsByAppointmentId(Long appointmentId)
    {
        return appointmentsMapper.selectAppointmentsByAppointmentId(appointmentId);
    }

    /**
     * 查询预约管理列表
     *
     * @param appointments 预约管理
     * @return 预约管理
     */
    @Override
    public List<Appointments> selectAppointmentsList(Appointments appointments)
    {
        return appointmentsMapper.selectAppointmentsList(appointments);
    }

    /**
     * 新增预约管理
     *
     * @param appointments 预约管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertAppointments(Appointments appointments)
    {
        //如果房间类型不为空
        if (appointments.getRoomType() != null) {
            //1.查询房间类型信息
            FosterRooms fosterRooms = fosterRoomsMapper.selectFosterRoomsByRoomNumber(appointments.getRoomNumber());
            //2.检查房间是否到达最大容量
            if (fosterRooms.getCurrentPetsCount() >= fosterRooms.getMaxPetsPerRoom()) {
                throw new ServiceException("当前房间已满员，请选择其他房间");
            }
            //3.将当前宠物数量加1
            fosterRooms.setCurrentPetsCount(fosterRooms.getCurrentPetsCount() + 1);
            fosterRoomsMapper.updateFosterRooms(fosterRooms);
        }

        //先获取服务信息，查询当前的服务是否可用
        Services services = servicesMapper.selectServicesByServiceTypeId(appointments.getServiceTypeId());
        if (Objects.equals(services.getServiceStatus(), PawHubConstants.SERVICE_STATUS_DISABLED) || services.getServiceStatus() == null) {
             throw new ServiceException("当前服务不能使用，请另选其他服务");
        }
        //检查是否是否有同类型的预约
        hasAppointments(appointments);

        //根据服务类型id来匹配员工id


        //将一开始的预约订单的状态设置为待确认
        appointments.setStatus(PawHubConstants.Appointment_STATUS_WAIT_CONFIRM);
        //查找Pets表，将对应的userId赋值
        Pets pets = petsMapper.selectPetsByPetId(appointments.getPetId());
        appointments.setUserId(pets.getUserId());
        //生成订单编号（为当前的时间戳)
        appointments.setAppointmentNo(generateAppointmentsNo());
        return appointmentsMapper.insertAppointments(appointments);
    }

    /**
     * 修改预约管理
     *
     * @param appointments 预约管理
     * @return 结果
     */
    @Override
    public int updateAppointments(Appointments appointments)
    {
        appointments.setUpdatedAt(DateUtils.getNowDate());
        return appointmentsMapper.updateAppointments(appointments);
    }

    /**
     * 批量删除预约管理
     *
     * @param appointmentIds 需要删除的预约管理主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentsByAppointmentIds(Long[] appointmentIds)
    {
        return appointmentsMapper.deleteAppointmentsByAppointmentIds(appointmentIds);
    }

    /**
     * 删除预约管理信息
     *
     * @param appointmentId 预约管理主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentsByAppointmentId(Long appointmentId)
    {
        return appointmentsMapper.deleteAppointmentsByAppointmentId(appointmentId);
    }

    /**
     * 查询预约管理列表
     *
     * @param appointments 预约管理
     * @return 预约管理
     */
    @Override
    public List<AppointmentsVo> selectAppointmentsVoList(Appointments appointments) {
        return appointmentsMapper.selectAppointmentsVoList(appointments);
    }

    /**
     * 取消预约
     * @param appointments
     * @return
     */
    @Override
    @Transactional
    public int cancelAppointments(Appointments appointments) {

        //先判断该预约是否可用取下
        //1.先获取预约信息
        Appointments appointmentsInfo = appointmentsMapper.selectAppointmentsByAppointmentId(appointments.getAppointmentId());
        //如果预约为已取消，则抛出异常
        if (Objects.equals(appointmentsInfo.getStatus(), PawHubConstants.Appointment_STATUS_CANCELLED)) {
            throw new ServiceException("当前预约已被取消，请勿重复操作");
        }
        //如果预约为已完成，则抛出异常
        if (Objects.equals(appointmentsInfo.getStatus(), PawHubConstants.Appointment_STATUS_COMPLETED)) {
            throw new ServiceException("当前预约已完成，请勿重复操作");
        }
        //如果预约为进行中，则抛出异常
        if (Objects.equals(appointmentsInfo.getStatus(), PawHubConstants.Appointment_STATUS_IN_PROGRESS)) {
            throw new ServiceException("当前预约正在进行中，请勿重复操作");
        }
        //如果取消预约，则需要将当前宠物数量减1
        if (appointments.getRoomType() != null) {
            //1.查询房间类型信息
            FosterRooms fosterRooms = fosterRoomsMapper.selectFosterRoomsByRoomNumber(appointments.getRoomNumber());
            //2.将当前宠物数量减1
            fosterRooms.setCurrentPetsCount(fosterRooms.getCurrentPetsCount() - 1);
            fosterRoomsMapper.updateFosterRooms(fosterRooms);
        }
        //2.设置字段
        appointments.setStatus(PawHubConstants.Appointment_STATUS_CANCELLED);
        appointments.setUpdatedAt(DateUtils.getNowDate());
        return appointmentsMapper.updateAppointments(appointments);
    }

    @Override
    public AppointmentsStatisticsVO statistics() {
        //根据状态，拿到待处理预约和已完成预约的数量
        AppointmentsStatisticsVO appointmentsStatisticsVO = new AppointmentsStatisticsVO();
        Integer  pendingCount = appointmentsMapper.countStatus(PawHubConstants.Appointment_STATUS_WAIT_CONFIRM);
        Integer confirmedCount = appointmentsMapper.countStatus(PawHubConstants.Appointment_STATUS_COMPLETED);

        appointmentsStatisticsVO.setPendingCount(pendingCount);
        appointmentsStatisticsVO.setConfirmedCount(confirmedCount);
        return appointmentsStatisticsVO;
    }

    //检查是否是否有同类型的预约
    private void hasAppointments(Appointments appointments) {
        //创建Appointments对象，并将宠物编号，服务类型id，以及预约状态为进行时封装
        Appointments appointmentsParam = new Appointments();
        appointmentsParam.setPetId(appointments.getPetId());
        appointmentsParam.setServiceTypeId(appointments.getServiceTypeId());
        appointmentsParam.setStatus(PawHubConstants.Appointment_STATUS_IN_PROGRESS);
        //调用appointmentsMapper的selectAppointmentsList(appointmentsParam)方法
        List<Appointments> appointmentsList = appointmentsMapper.selectAppointmentsList(appointmentsParam);
        if (appointmentsList != null && appointmentsList.size() > 0){
            throw new ServiceException("当前宠物已有预约，请不要重复创建");
        }
    }

    // 生成并获取当天工单编号（唯一标识）
    public String generateAppointmentsNo() {
        //获取当前日期以格式化"yyyyMMdd"
        String date = DateUtils.getDate().replaceAll("-","");
        //根据日期生成redis的key
        String key = "dkd.task.code:" + date;
        //判断key是否存在
        if(!redisTemplate.hasKey(key)){
            //如果key不存在，设置初始值为1，并指定过期时间为1天
            redisTemplate.opsForValue().set(key,"1", Duration.ofDays(1));
            //返回工单编号（日期+0001）
            return date + "0001";
        }
        // 如果存在，获取当前值并检查是否为整数
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        try {
            Long increment = Long.parseLong(currentValue) + 1;
            redisTemplate.opsForValue().set(key, increment.toString());
            return date + StrUtil.padPre(increment.toString(), 4, '0');
        } catch (NumberFormatException e) {
            throw new ServiceException("Redis键的值不是整数: " + currentValue);
        }
    }

}
