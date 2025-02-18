package com.pet.manager.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.FrTypesMapper;
import com.pet.manager.domain.FrTypes;
import com.pet.manager.service.IFrTypesService;

/**
 * 寄养房间类型Service业务层处理
 * 
 * @author kkk
 * @date 2025-02-17
 */
@Service
public class FrTypesServiceImpl implements IFrTypesService 
{
    @Autowired
    private FrTypesMapper frTypesMapper;

    /**
     * 查询寄养房间类型
     * 
     * @param roomTypeId 寄养房间类型主键
     * @return 寄养房间类型
     */
    @Override
    public FrTypes selectFrTypesByRoomTypeId(Long roomTypeId)
    {
        return frTypesMapper.selectFrTypesByRoomTypeId(roomTypeId);
    }

    /**
     * 查询寄养房间类型列表
     * 
     * @param frTypes 寄养房间类型
     * @return 寄养房间类型
     */
    @Override
    public List<FrTypes> selectFrTypesList(FrTypes frTypes)
    {
        return frTypesMapper.selectFrTypesList(frTypes);
    }

    /**
     * 新增寄养房间类型
     * 
     * @param frTypes 寄养房间类型
     * @return 结果
     */
    @Override
    public int insertFrTypes(FrTypes frTypes)
    {
        return frTypesMapper.insertFrTypes(frTypes);
    }

    /**
     * 修改寄养房间类型
     * 
     * @param frTypes 寄养房间类型
     * @return 结果
     */
    @Override
    public int updateFrTypes(FrTypes frTypes)
    {
        return frTypesMapper.updateFrTypes(frTypes);
    }

    /**
     * 批量删除寄养房间类型
     * 
     * @param roomTypeIds 需要删除的寄养房间类型主键
     * @return 结果
     */
    @Override
    public int deleteFrTypesByRoomTypeIds(Long[] roomTypeIds)
    {
        return frTypesMapper.deleteFrTypesByRoomTypeIds(roomTypeIds);
    }

    /**
     * 删除寄养房间类型信息
     * 
     * @param roomTypeId 寄养房间类型主键
     * @return 结果
     */
    @Override
    public int deleteFrTypesByRoomTypeId(Long roomTypeId)
    {
        return frTypesMapper.deleteFrTypesByRoomTypeId(roomTypeId);
    }
}
