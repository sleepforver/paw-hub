package com.pet.manager.mapper;

import java.util.List;
import com.pet.manager.domain.Appointments;
import com.pet.manager.domain.vo.AppointmentsVo;

/**
 * 预约管理Mapper接口
 *
 * @author kkk
 * @date 2025-02-19
 */
public interface AppointmentsMapper
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
     * 删除预约管理
     *
     * @param appointmentId 预约管理主键
     * @return 结果
     */
    public int deleteAppointmentsByAppointmentId(Long appointmentId);

    /**
     * 批量删除预约管理
     *
     * @param appointmentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppointmentsByAppointmentIds(Long[] appointmentIds);

    /**
     * 查询预约管理列表
     *
     * @param appointments 预约管理
     * @return 预约管理集合
     */
    List<AppointmentsVo> selectAppointmentsVoList(Appointments appointments);
}
