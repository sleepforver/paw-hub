package com.pet.manager.service;

import java.util.List;
import com.pet.manager.domain.FosterRooms;

/**
 * 寄养房间信息Service接口
 *
 * @author kkk
 * @date 2025-02-17
 */
public interface IFosterRoomsService
{
    /**
     * 查询寄养房间信息
     *
     * @param fosterRoomId 寄养房间信息主键
     * @return 寄养房间信息
     */
    public FosterRooms selectFosterRoomsByFosterRoomId(Long fosterRoomId);

    /**
     * 查询寄养房间信息列表
     *
     * @param fosterRooms 寄养房间信息
     * @return 寄养房间信息集合
     */
    public List<FosterRooms> selectFosterRoomsList(FosterRooms fosterRooms);

    /**
     * 新增寄养房间信息
     *
     * @param fosterRooms 寄养房间信息
     * @return 结果
     */
    public int insertFosterRooms(FosterRooms fosterRooms);

    /**
     * 修改寄养房间信息
     *
     * @param fosterRooms 寄养房间信息
     * @return 结果
     */
    public int updateFosterRooms(FosterRooms fosterRooms);

    /**
     * 批量删除寄养房间信息
     *
     * @param fosterRoomIds 需要删除的寄养房间信息主键集合
     * @return 结果
     */
    public int deleteFosterRoomsByFosterRoomIds(Long[] fosterRoomIds);

    /**
     * 删除寄养房间信息信息
     *
     * @param fosterRoomId 寄养房间信息主键
     * @return 结果
     */
    public int deleteFosterRoomsByFosterRoomId(Long fosterRoomId);

    /**
     * 批量导入
     * @param fosterRoomsList
     */
    int insertSkuBatch(List<FosterRooms> fosterRoomsList);
}
