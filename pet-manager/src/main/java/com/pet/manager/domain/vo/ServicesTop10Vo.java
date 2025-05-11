package com.pet.manager.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicesTop10Vo implements Serializable {
    //服务名称列表，以逗号分隔
    private String nameList;
    //销量列表，以逗号分隔，例如：260,215,200
    private String numberList;

}
