package com.pet.manager.mapper;

import java.util.List;
import com.pet.manager.domain.FosterRooms;
import com.pet.manager.domain.vo.FosterRoomsVo;

/**
 * 寄养房间信息Mapper接口
 *
 * @author kkk
 * @date 2025-02-17
 */
public interface FosterRoomsMapper
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
     * 删除寄养房间信息
     *
     * @param fosterRoomId 寄养房间信息主键
     * @return 结果
     */
    public int deleteFosterRoomsByFosterRoomId(Long fosterRoomId);

    /**
     * 批量删除寄养房间信息
     *
     * @param fosterRoomIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFosterRoomsByFosterRoomIds(Long[] fosterRoomIds);

    /**
     * 批量插入寄养房间信息
     * @param fosterRoomsList
     * @return
     */
    int insertFrBatch(List<FosterRooms> fosterRoomsList);


    /**
     * 根据房间编号查询房间信息
     * @param roomNumber
     * @return
     */
    FosterRooms selectFosterRoomsByRoomNumber(Long roomNumber);

    /**
     * 根据房间编号增加当前宠物数量
     * @param roomNumber
     * @return
     */
    int incrementCurrentPetsCount(Long roomNumber);
}
