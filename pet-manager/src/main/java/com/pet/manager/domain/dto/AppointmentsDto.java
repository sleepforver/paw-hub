package com.pet.manager.domain.dto;

import com.pet.manager.domain.Appointments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentsDto extends Appointments {
    //今日时间
    private LocalDateTime begin;
    private LocalDateTime end;
}
