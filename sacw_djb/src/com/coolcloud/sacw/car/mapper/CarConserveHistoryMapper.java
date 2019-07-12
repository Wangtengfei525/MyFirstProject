package com.coolcloud.sacw.car.mapper;

import java.util.List;

import com.coolcloud.sacw.car.entity.CarConserveHistory;
import com.coolcloud.sacw.common.BaseMapper;

public interface CarConserveHistoryMapper extends BaseMapper<CarConserveHistory, String> {

    /**
     * 查询车辆的最近养护时间
     * 
     * @param propertyId
     *            财物id
     * @return 最新的养护历史记录，每条仅包含conserveContentCode与conserveTime两项内容
     */
    List<CarConserveHistory> selectLatestConserveByPropertyId(String propertyId);

    /**
     * 按养护id删除
     * 
     * @param conserveId
     * @return
     */
    int deleteByConserveId(String conserveId);

    /**
     * 按养护组id删除
     * 
     * @param groupId
     * @return
     */
    int deleteByGroupId(String groupId);

    void updatePropertyId();

    /**
     * 按拆分id查询车辆的最近养护时间
     * 
     * @param splitId
     *            财物拆分id
     * @return 最新的养护历史记录，每条仅包含conserveContentCode与conserveTime两项内容
     */
    List<CarConserveHistory> selectLatestConserveBySplitId(String splitId);

}