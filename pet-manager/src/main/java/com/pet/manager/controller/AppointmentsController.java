package com.pet.manager.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.pet.manager.domain.Orders;
import com.pet.manager.domain.dto.AppointmentsDto;
import com.pet.manager.domain.vo.AppointmentsStatisticsVO;
import jakarta.servlet.http.HttpServletResponse;

import com.pet.manager.domain.vo.AppointmentsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pet.common.annotation.Log;
import com.pet.common.core.controller.BaseController;
import com.pet.common.core.domain.AjaxResult;
import com.pet.common.enums.BusinessType;
import com.pet.manager.domain.Appointments;
import com.pet.manager.service.IAppointmentsService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;

/**
 * 预约管理Controller
 *
 * @author kkk
 * @date 2025-02-19
 */
@Api(tags = "预约管理Controller")
@RestController
@RequestMapping("/manager/appointments")
public class AppointmentsController extends BaseController
{
    @Autowired
    private IAppointmentsService appointmentsService;

/**
 * 查询预约管理列表
 */
@ApiOperation("查询预约管理列表")
@PreAuthorize("@ss.hasPermi('manager:appointments:list')")
@GetMapping("/list")
    public TableDataInfo list(Appointments appointments)
    {
        startPage();
        List<AppointmentsVo> list = appointmentsService.selectAppointmentsVoList(appointments);
        return getDataTable(list);
    }

    /**
     * 导出预约管理列表
     */
    @ApiOperation("导出预约管理列表")
    @PreAuthorize("@ss.hasPermi('manager:appointments:export')")
    @Log(title = "预约管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Appointments appointments)
    {
        List<Appointments> list = appointmentsService.selectAppointmentsList(appointments);
        ExcelUtil<Appointments> util = new ExcelUtil<Appointments>(Appointments.class);
        util.exportExcel(response, list, "预约管理数据");
    }

    /**
     * 获取预约管理详细信息
     */
    @ApiOperation("获取预约管理详细信息")
    @PreAuthorize("@ss.hasPermi('manager:appointments:query')")
    @GetMapping(value = "/{appointmentId}")
    public R<Appointments> getInfo(@PathVariable("appointmentId") Long appointmentId)
    {
        return R.ok(appointmentsService.selectAppointmentsByAppointmentId(appointmentId));
    }

    /**
     * 新增预约管理
     */
    @ApiOperation("新增预约管理")
    @PreAuthorize("@ss.hasPermi('manager:appointments:add')")
    @Log(title = "预约管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Appointments appointments)
    {
        return toAjax(appointmentsService.insertAppointments(appointments));
    }

    /**
     * 修改预约管理
     */
    @ApiOperation("修改预约管理")
    @PreAuthorize("@ss.hasPermi('manager:appointments:edit')")
    @Log(title = "预约管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Appointments appointments)
    {
        return toAjax(appointmentsService.updateAppointments(appointments));
    }

    /**
     * 删除预约管理
     */
    @ApiOperation("删除预约管理")
    @PreAuthorize("@ss.hasPermi('manager:appointments:remove')")
    @Log(title = "预约管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{appointmentIds}")
    public AjaxResult remove(@PathVariable Long[] appointmentIds)
    {
        return toAjax(appointmentsService.deleteAppointmentsByAppointmentIds(appointmentIds));
    }

    @ApiOperation("取消预约")
    @PreAuthorize("@ss.hasPermi('manager:appointments:edit')")
    @Log(title = "预约管理", businessType = BusinessType.UPDATE)
    @PutMapping("/cancel")
    public AjaxResult cancel(@RequestBody Appointments appointments)
    {
        return toAjax(appointmentsService.cancelAppointments(appointments));
    }

    @ApiOperation("统计预约数量")
    @PreAuthorize("@ss.hasPermi('manager:appointments:query')")
    @GetMapping("/statistics")
    public R<AppointmentsStatisticsVO> statistics()
    {
        AppointmentsStatisticsVO appointmentsStatisticsVO = appointmentsService.statistics();
        return R.ok(appointmentsStatisticsVO);
    }

    @ApiOperation("获取今日的预约信息")
    @PreAuthorize("@ss.hasPermi('manager:appointments:query')")
    @GetMapping("/appointmentStatistics/today")
    public TableDataInfo getTodayOrderStatistics() {
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now();
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        AppointmentsDto appointmentsDto = new AppointmentsDto();
        appointmentsDto.setBegin(beginTime);
        appointmentsDto.setEnd(endTime);
        List<AppointmentsVo> appointmentsList = appointmentsService.selectAppointmentsVoList(appointmentsDto);
        return getDataTable(appointmentsList);
    }
}
