package com.pet.manager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hpsf.Decimal;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesTop4Dto implements Serializable {
    //服务名称
    private String name;
    //销量
    private Decimal number;
}
