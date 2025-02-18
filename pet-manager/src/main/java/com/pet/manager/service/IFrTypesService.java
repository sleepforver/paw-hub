package com.pet.manager.service;

import java.util.List;
import com.pet.manager.domain.FrTypes;

/**
 * 寄养房间类型Service接口
 * 
 * @author kkk
 * @date 2025-02-17
 */
public interface IFrTypesService 
{
    /**
     * 查询寄养房间类型
     * 
     * @param roomTypeId 寄养房间类型主键
     * @return 寄养房间类型
     */
    public FrTypes selectFrTypesByRoomTypeId(Long roomTypeId);

    /**
     * 查询寄养房间类型列表
     * 
     * @param frTypes 寄养房间类型
     * @return 寄养房间类型集合
     */
    public List<FrTypes> selectFrTypesList(FrTypes frTypes);

    /**
     * 新增寄养房间类型
     * 
     * @param frTypes 寄养房间类型
     * @return 结果
     */
    public int insertFrTypes(FrTypes frTypes);

    /**
     * 修改寄养房间类型
     * 
     * @param frTypes 寄养房间类型
     * @return 结果
     */
    public int updateFrTypes(FrTypes frTypes);

    /**
     * 批量删除寄养房间类型
     * 
     * @param roomTypeIds 需要删除的寄养房间类型主键集合
     * @return 结果
     */
    public int deleteFrTypesByRoomTypeIds(Long[] roomTypeIds);

    /**
     * 删除寄养房间类型信息
     * 
     * @param roomTypeId 寄养房间类型主键
     * @return 结果
     */
    public int deleteFrTypesByRoomTypeId(Long roomTypeId);
}
