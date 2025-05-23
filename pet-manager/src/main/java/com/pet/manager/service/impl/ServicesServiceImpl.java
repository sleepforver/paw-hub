package com.pet.manager.service.impl;

import java.util.List;

import com.pet.common.exception.ServiceException;
import com.pet.manager.domain.FosterRooms;
import com.pet.manager.domain.FrTypes;
import com.pet.manager.domain.ServiceTypes;
import com.pet.manager.domain.vo.ServicesVo;
import com.pet.manager.mapper.FosterRoomsMapper;
import com.pet.manager.mapper.FrTypesMapper;
import com.pet.manager.mapper.ServiceTypesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.manager.mapper.ServicesMapper;
import com.pet.manager.domain.Services;
import com.pet.manager.service.IServicesService;

/**
 * 服务管理Service业务层处理
 *
 * @author kkk
 * @date 2025-02-17
 */
@Service
public class ServicesServiceImpl implements IServicesService
{
    @Autowired
    private ServicesMapper servicesMapper;

    @Autowired
    private FosterRoomsMapper fosterRoomsMapper;

    @Autowired
    private ServiceTypesMapper serviceTypesMapper;
    /**
     * 查询服务管理
     *
     * @param serviceId 服务管理主键
     * @return 服务管理
     */
    @Override
    public Services selectServicesByServiceId(Long serviceId)
    {
        return servicesMapper.selectServicesByServiceId(serviceId);
    }

    /**
     * 查询服务管理列表
     *
     * @param services 服务管理
     * @return 服务管理
     */
    @Override
    public List<Services> selectServicesList(Services services)
    {
        return servicesMapper.selectServicesList(services);
    }

    /**
     * 新增服务管理
     *
     * @param services 服务管理
     * @return 结果
     */
    @Override
    public int insertServices(Services services)
    {

        //如何服务类型id不为空
        if (services.getServiceTypeId() != null) {
            //查询服务类型
            ServiceTypes serviceTypes = serviceTypesMapper.selectServiceTypesByServiceTypeId(services.getServiceTypeId());
            services.setServiceType(serviceTypes.getServiceType());//设置服务类型
        }
        return servicesMapper.insertServices(services);
    }

    /**
     * 修改服务管理
     *
     * @param services 服务管理
     * @return 结果
     */
    @Override
    public int updateServices(Services services)
    {
        return servicesMapper.updateServices(services);
    }

    /**
     * 批量删除服务管理
     *
     * @param serviceIds 需要删除的服务管理主键
     * @return 结果
     */
    @Override
    public int deleteServicesByServiceIds(Long[] serviceIds)
    {
        return servicesMapper.deleteServicesByServiceIds(serviceIds);
    }

    /**
     * 删除服务管理信息
     *
     * @param serviceId 服务管理主键
     * @return 结果
     */
    @Override
    public int deleteServicesByServiceId(Long serviceId)
    {
        return servicesMapper.deleteServicesByServiceId(serviceId);
    }

    /**
     * 查询服务管理列表
     *
     * @param services 服务管理
     * @return 服务管理
     */
    @Override
    public List<ServicesVo> selectServicesVoList(Services services) {
        return servicesMapper.selectServicesVoList(services);
    }
}
