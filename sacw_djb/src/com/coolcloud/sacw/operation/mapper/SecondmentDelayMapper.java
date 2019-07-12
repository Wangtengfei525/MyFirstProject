package com.coolcloud.sacw.operation.mapper;

import java.util.List;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.operation.entity.SecondmentDelay;

/**
 * 借调延期记录mapper
 * 
 * @author xyz
 *
 */
public interface SecondmentDelayMapper extends BaseMapper<SecondmentDelay, String> {

    /**
     * 按借调id查询延期记录
     * 
     * @param secondmentId
     *            借调id
     * @return 对应延期记录
     */
    List<SecondmentDelay> selectBySecondmentId(String secondmentId);
}