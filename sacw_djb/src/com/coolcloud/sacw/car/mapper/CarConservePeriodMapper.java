package com.coolcloud.sacw.car.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coolcloud.sacw.car.entity.CarConservePeriod;
import com.coolcloud.sacw.common.BaseMapper;

/**
 * 
 * @author xyz
 *
 * @date 2018年4月10日 下午4:32:13
 */
public interface CarConservePeriodMapper extends BaseMapper<CarConservePeriod, String> {

    List<CarConservePeriod> selectPeriodSettings();

    List<CarConservePeriod> selectEnabledPeriodSettings();

    CarConservePeriod selectByConserveContentCode(String conserveContentCode);

    List<CarConservePeriod> selectPeriodSettingsByCodes(@Param("conserveContentCodes") Collection<String> conserveContentCodes);

    /**
     * 按养护内容代码删除
     * 
     * @param codes
     * @return
     */
    int deleteByConserveContentCodes(@Param("codes") Collection<String> codes);

    /**
     * 按拆分id获取单独的养护周期设置
     * 
     * @param splitId
     *            财物拆分id
     * @return
     */
    List<CarConservePeriod> selectBySplitId(String splitId);

}