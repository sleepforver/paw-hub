package com.pet.manager.domain.vo;

import com.pet.manager.domain.PetHealthRecords;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetHealthRecordsVo extends PetHealthRecords {
    //置信度
    private Double confidence;
    //疾病id
    private Long classId;
    //疾病名称
    private String className;
}
