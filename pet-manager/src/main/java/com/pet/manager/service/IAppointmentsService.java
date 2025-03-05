package com.pet.manager.service;

import java.util.List;
import com.pet.manager.domain.Appointments;
import com.pet.manager.domain.vo.AppointmentsVo;

/**
 * 预约管理Service接口
 *
 * @author kkk
 * @date 2025-02-19
 */
public interface IAppointmentsService
{
    /**
     * 查询预约管理
     *
     * @param appointmentId 预约管理主键
     * @return 预约管理
     */
    public Appointments selectAppointmentsByAppointmentId(Long appointmentId);

    /**
     * 查询预约管理列表
     *
     * @param appointments 预约管理
     * @return 预约管理集合
     */
    public List<Appointments> selectAppointmentsList(Appointments appointments);

    /**
     * 新增预约管理
     *
     * @param appointments 预约管理
     * @return 结果
     */
    public int insertAppointments(Appointments appointments);

    /**
     * 修改预约管理
     *
     * @param appointments 预约管理
     * @return 结果
     */
    public int updateAppointments(Appointments appointments);

    /**
     * 批量删除预约管理
     *
     * @param appointmentIds 需要删除的预约管理主键集合
     * @return 结果
     */
    public int deleteAppointmentsByAppointmentIds(Long[] appointmentIds);

    /**
     * 删除预约管理信息
     *
     * @param appointmentId 预约管理主键
     * @return 结果
     */
    public int deleteAppointmentsByAppointmentId(Long appointmentId);

    /**
     * 查询预约管理列表
     *
     * @param appointments 预约管理
     * @return 预约管理集合
     */
    List<AppointmentsVo> selectAppointmentsVoList(Appointments appointments);

    /**
     * 取消预约
     * @param appointments
     * @return
     */
    int cancelAppointments(Appointments appointments);
}
