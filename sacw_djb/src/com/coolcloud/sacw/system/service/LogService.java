package com.coolcloud.sacw.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.system.entity.Log;
import com.coolcloud.sacw.system.mapper.LogMapper;

/**
 * 日志服务类
 * 
 * @author xyz
 *
 */

@Service
public class LogService extends BaseService<Log, String> {

    @Autowired
    private LogMapper logMapper;

    /**
     * 删除所有日志
     * 
     * @return
     */
    @Transactional
    public int deleteAll() {
        return logMapper.deleteAll();
    }

}
