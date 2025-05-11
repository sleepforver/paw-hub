package com.pet.manager.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsStatisticsVO implements Serializable {
    //待处理预约数量
    private Integer pendingCount;
    //已接诊数量
    private Integer confirmedCount;
}
