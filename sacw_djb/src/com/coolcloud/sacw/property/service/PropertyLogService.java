package com.coolcloud.sacw.property.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.property.entity.PropertyLog;
import com.coolcloud.sacw.property.mapper.PropertyLogMapper;

/**
 * 财物变更记录服务类
 * 
 * @author xyz
 *
 */
@Service
public class PropertyLogService extends BaseService<PropertyLog, String> {

    @Autowired
    private PropertyLogMapper propertyLogMapper;

    /**
     * 按财物id查询财物变更记录
     * 
     * @param propertyId
     *            财物id
     * @return
     */
    @Transactional(readOnly = true)
    public List<PropertyLog> getByPropertyId(String propertyId) {
        return propertyLogMapper.selectByPropertyId(propertyId);
    }

}
