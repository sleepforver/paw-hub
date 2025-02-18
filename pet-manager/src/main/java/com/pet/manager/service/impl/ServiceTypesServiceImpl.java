package com.pet.manager.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.ServiceTypesMapper;
import com.pet.manager.domain.ServiceTypes;
import com.pet.manager.service.IServiceTypesService;

/**
 * 服务类型Service业务层处理
 * 
 * @author kkk
 * @date 2025-02-17
 */
@Service
public class ServiceTypesServiceImpl implements IServiceTypesService 
{
    @Autowired
    private ServiceTypesMapper serviceTypesMapper;

    /**
     * 查询服务类型
     * 
     * @param serviceTypeId 服务类型主键
     * @return 服务类型
     */
    @Override
    public ServiceTypes selectServiceTypesByServiceTypeId(Long serviceTypeId)
    {
        return serviceTypesMapper.selectServiceTypesByServiceTypeId(serviceTypeId);
    }

    /**
     * 查询服务类型列表
     * 
     * @param serviceTypes 服务类型
     * @return 服务类型
     */
    @Override
    public List<ServiceTypes> selectServiceTypesList(ServiceTypes serviceTypes)
    {
        return serviceTypesMapper.selectServiceTypesList(serviceTypes);
    }

    /**
     * 新增服务类型
     * 
     * @param serviceTypes 服务类型
     * @return 结果
     */
    @Override
    public int insertServiceTypes(ServiceTypes serviceTypes)
    {
        return serviceTypesMapper.insertServiceTypes(serviceTypes);
    }

    /**
     * 修改服务类型
     * 
     * @param serviceTypes 服务类型
     * @return 结果
     */
    @Override
    public int updateServiceTypes(ServiceTypes serviceTypes)
    {
        return serviceTypesMapper.updateServiceTypes(serviceTypes);
    }

    /**
     * 批量删除服务类型
     * 
     * @param serviceTypeIds 需要删除的服务类型主键
     * @return 结果
     */
    @Override
    public int deleteServiceTypesByServiceTypeIds(Long[] serviceTypeIds)
    {
        return serviceTypesMapper.deleteServiceTypesByServiceTypeIds(serviceTypeIds);
    }

    /**
     * 删除服务类型信息
     * 
     * @param serviceTypeId 服务类型主键
     * @return 结果
     */
    @Override
    public int deleteServiceTypesByServiceTypeId(Long serviceTypeId)
    {
        return serviceTypesMapper.deleteServiceTypesByServiceTypeId(serviceTypeId);
    }
}
