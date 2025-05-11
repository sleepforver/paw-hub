package com.pet.manager.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PetStatisticsVO implements Serializable {
    // 宠物总数
    private Integer petCount;
    // 宠物猫总数
    private Integer catCount;
    // 宠物狗总数
    private Integer dogCount;
}
