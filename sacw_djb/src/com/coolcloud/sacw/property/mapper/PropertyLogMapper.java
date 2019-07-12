package com.coolcloud.sacw.property.mapper;

import java.util.List;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.property.entity.PropertyLog;

public interface PropertyLogMapper extends BaseMapper<PropertyLog, String> {

    /**
     * 按财物id查询财物变更记录
     * 
     * @param propertyId
     * @return
     */
    List<PropertyLog> selectByPropertyId(String propertyId);

}