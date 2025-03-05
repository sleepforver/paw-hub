package com.pet.manager.domain.vo;

import com.pet.manager.domain.Appointments;
import com.pet.manager.domain.Pets;
import com.pet.manager.domain.ServiceTypes;
import com.pet.manager.domain.Users;
import lombok.Data;

@Data
public class AppointmentsVo extends Appointments {

    //宠物信息
    private Pets pets;

    //用户信息
    private Users users;

    //服务类型信息
    private ServiceTypes serviceTypes;
}
