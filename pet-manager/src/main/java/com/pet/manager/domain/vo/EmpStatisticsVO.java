package com.pet.manager.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmpStatisticsVO implements Serializable {
    // 员工总数
    private Integer totalCount;
}
