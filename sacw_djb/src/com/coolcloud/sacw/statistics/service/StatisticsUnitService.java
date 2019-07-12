package com.coolcloud.sacw.statistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.statistics.entity.StatisticsUnit;
import com.coolcloud.sacw.statistics.mapper.StatisticsUnitMapper;
import com.coolcloud.sacw.system.entity.Unit;
import com.coolcloud.sacw.system.service.UnitService;

/**
 * 
 * @author xyz
 *
 * @date 2018年4月14日 下午6:00:01
 */
@Service
public class StatisticsUnitService extends BaseService<StatisticsUnit, String> {

    @Autowired
    private UnitService unitService;

    @Autowired
    private StatisticsUnitMapper statisticsUnitMapper;

    /**
     * 添加统计单位
     * 
     * @param unit
     * @return
     */
    public int add(StatisticsUnit statisticsUnit) {
        String unitId = statisticsUnit.getUnitId();
        String unitName = statisticsUnit.getUnitName();
        Unit unit = unitService.get(unitId);
        Assert.notNull(unit, "单位：" + unitId + "不存在");
        if (StringUtils.isEmpty(unitName)) {
            statisticsUnit.setUnitName(unit.getName());
        }
        return save(statisticsUnit);
    }

    /**
     * 按统计类型获取单位设置
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public List<StatisticsUnit> getByType(String type) {
        return statisticsUnitMapper.selectByType(type);
    }

}
