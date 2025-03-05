package com.pet.manager.domain.vo;

import com.pet.manager.domain.FosterRooms;
import com.pet.manager.domain.ServiceTypes;
import com.pet.manager.domain.Services;
import lombok.Data;

@Data
public class ServicesVo extends Services {

    // 服务类型
    private ServiceTypes serviceTypes;

    // 房间信息
    private FosterRooms fosterRooms;
}
