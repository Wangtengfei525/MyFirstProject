package com.coolcloud.sacw.statistics.mapper;

import java.util.List;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.statistics.entity.StatisticsUnit;

/**
 * 
 * @author xyz
 *
 * @date 2018年4月14日 下午5:56:42
 */
public interface StatisticsUnitMapper extends BaseMapper<StatisticsUnit, String> {

    /**
     * 按类型查询统计单数据
     * 
     * @param type
     * @return
     */
    List<StatisticsUnit> selectByType(String type);

}