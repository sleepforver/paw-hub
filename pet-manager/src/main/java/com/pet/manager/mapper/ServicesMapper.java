package com.pet.manager.mapper;

import java.util.List;
import com.pet.manager.domain.Services;
import com.pet.manager.domain.vo.ServicesVo;

/**
 * 服务管理Mapper接口
 *
 * @author kkk
 * @date 2025-02-17
 */
public interface ServicesMapper
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
     * 删除服务管理
     *
     * @param serviceId 服务管理主键
     * @return 结果
     */
    public int deleteServicesByServiceId(Long serviceId);

    /**
     * 批量删除服务管理
     *
     * @param serviceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteServicesByServiceIds(Long[] serviceIds);

    /**
     * 查询服务管理列表
     *
     * @param services 服务管理
     * @return 服务管理集合
     */
    List<ServicesVo> selectServicesVoList(Services services);

    /**
     * 根据服务类型id查询服务
     *
     * @param serviceTypeId
     * @return
     */
    Services selectServicesByServiceTypeId(Long serviceTypeId);
    ;
}
