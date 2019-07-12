package com.coolcloud.sacw.system.mapper;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.system.entity.Log;

public interface LogMapper extends BaseMapper<Log, String> {

    /**
     * 删除所有日志记录
     * 
     * @return 删除的记录数
     */
    int deleteAll();

}