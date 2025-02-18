package com.pet.manager.service;

import java.util.List;
import com.pet.manager.domain.Services;

/**
 * 服务管理Service接口
 * 
 * @author kkk
 * @date 2025-02-17
 */
public interface IServicesService 
{
    /**
     * 查询服务管理
     * 
     * @param serviceId 服务管理主键
     * @return 服务管理
     */
    public Services selectServicesByServiceId(Long serviceId);

    /**
     * 查询服务管理列表
     * 
     * @param services 服务管理
     * @return 服务管理集合
     */
    public List<Services> selectServicesList(Services services);

    /**
     * 新增服务管理
     * 
     * @param services 服务管理
     * @return 结果
     */
    public int insertServices(Services services);

    /**
     * 修改服务管理
     * 
     * @param services 服务管理
     * @return 结果
     */
    public int updateServices(Services services);

    /**
     * 批量删除服务管理
     * 
     * @param serviceIds 需要删除的服务管理主键集合
     * @return 结果
     */
    public int deleteServicesByServiceIds(Long[] serviceIds);

    /**
     * 删除服务管理信息
     * 
     * @param serviceId 服务管理主键
     * @return 结果
     */
    public int deleteServicesByServiceId(Long serviceId);
}
