package com.pet.manager.domain.vo;

import com.pet.manager.domain.FosterRooms;
import com.pet.manager.domain.ServiceTypes;
import lombok.Data;

@Data
public class FosterRoomsVo extends FosterRooms {

    // 服务类型信息
    private ServiceTypes serviceTypes;
}
