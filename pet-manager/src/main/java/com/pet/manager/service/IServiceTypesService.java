package com.pet.manager.service;

import java.util.List;
import com.pet.manager.domain.ServiceTypes;

/**
 * 服务类型Service接口
 * 
 * @author kkk
 * @date 2025-02-17
 */
public interface IServiceTypesService 
{
    /**
     * 查询服务类型
     * 
     * @param serviceTypeId 服务类型主键
     * @return 服务类型
     */
    public ServiceTypes selectServiceTypesByServiceTypeId(Long serviceTypeId);

    /**
     * 查询服务类型列表
     * 
     * @param serviceTypes 服务类型
     * @return 服务类型集合
     */
    public List<ServiceTypes> selectServiceTypesList(ServiceTypes serviceTypes);

    /**
     * 新增服务类型
     * 
     * @param serviceTypes 服务类型
     * @return 结果
     */
    public int insertServiceTypes(ServiceTypes serviceTypes);

    /**
     * 修改服务类型
     * 
     * @param serviceTypes 服务类型
     * @return 结果
     */
    public int updateServiceTypes(ServiceTypes serviceTypes);

    /**
     * 批量删除服务类型
     * 
     * @param serviceTypeIds 需要删除的服务类型主键集合
     * @return 结果
     */
    public int deleteServiceTypesByServiceTypeIds(Long[] serviceTypeIds);

    /**
     * 删除服务类型信息
     * 
     * @param serviceTypeId 服务类型主键
     * @return 结果
     */
    public int deleteServiceTypesByServiceTypeId(Long serviceTypeId);
}
