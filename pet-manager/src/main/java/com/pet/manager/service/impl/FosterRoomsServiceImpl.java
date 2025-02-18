package com.pet.manager.service.impl;

import java.util.List;
import com.pet.common.utils.DateUtils;
import com.pet.manager.domain.FrTypes;
import com.pet.manager.mapper.FrTypesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.FosterRoomsMapper;
import com.pet.manager.domain.FosterRooms;
import com.pet.manager.service.IFosterRoomsService;

/**
 * 寄养房间信息Service业务层处理
 *
 * @author kkk
 * @date 2025-02-17
 */
@Service
public class FosterRoomsServiceImpl implements IFosterRoomsService
{
    @Autowired
    private FosterRoomsMapper fosterRoomsMapper;

    @Autowired
    private FrTypesMapper frTypesMapper;

    /**
     * 查询寄养房间信息
     *
     * @param fosterRoomId 寄养房间信息主键
     * @return 寄养房间信息
     */
    @Override
    public FosterRooms selectFosterRoomsByFosterRoomId(Long fosterRoomId)
    {
        return fosterRoomsMapper.selectFosterRoomsByFosterRoomId(fosterRoomId);
    }

    /**
     * 查询寄养房间信息列表
     *
     * @param fosterRooms 寄养房间信息
     * @return 寄养房间信息
     */
    @Override
    public List<FosterRooms> selectFosterRoomsList(FosterRooms fosterRooms)
    {
        return fosterRoomsMapper.selectFosterRoomsList(fosterRooms);
    }

    /**
     * 新增寄养房间信息
     *
     * @param fosterRooms 寄养房间信息
     * @return 结果
     */
    @Override
    public int insertFosterRooms(FosterRooms fosterRooms)
    {
        FrTypes frTypes = frTypesMapper.selectFrTypesByRoomNumber(fosterRooms.getRoomNumber());
        fosterRooms.setMaxPetsPerRoom(frTypes.getMaxPetsPerRoom());
        fosterRooms.setCreateTime(DateUtils.getNowDate());
        return fosterRoomsMapper.insertFosterRooms(fosterRooms);
    }

    /**
     * 修改寄养房间信息
     *
     * @param fosterRooms 寄养房间信息
     * @return 结果
     */
    @Override
    public int updateFosterRooms(FosterRooms fosterRooms)
    {
        return fosterRoomsMapper.updateFosterRooms(fosterRooms);
    }

    /**
     * 批量删除寄养房间信息
     *
     * @param fosterRoomIds 需要删除的寄养房间信息主键
     * @return 结果
     */
    @Override
    public int deleteFosterRoomsByFosterRoomIds(Long[] fosterRoomIds)
    {
        return fosterRoomsMapper.deleteFosterRoomsByFosterRoomIds(fosterRoomIds);
    }

    /**
     * 删除寄养房间信息信息
     *
     * @param fosterRoomId 寄养房间信息主键
     * @return 结果
     */
    @Override
    public int deleteFosterRoomsByFosterRoomId(Long fosterRoomId)
    {
        return fosterRoomsMapper.deleteFosterRoomsByFosterRoomId(fosterRoomId);
    }

    /**
     * 批量导入
     * @param fosterRoomsList
     * @return
     */
    @Override
    public int insertSkuBatch(List<FosterRooms> fosterRoomsList) {
        return fosterRoomsMapper.insertFrBatch(fosterRoomsList);
    }
}
