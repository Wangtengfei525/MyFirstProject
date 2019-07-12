package com.coolcloud.sacw.car.mapper;

import java.util.List;

import com.coolcloud.sacw.car.entity.CarConserveContent;
import com.coolcloud.sacw.common.BaseMapper;

/**
 * 
 * @author 王腾飞
 *2018年12月28日上午10:47:48
 */
public interface CarConserveContentMapper extends BaseMapper<CarConserveContent, String> {

    /**
     * 由财物id查询养护内容
     * 
     * @param propertyId
     *            财物id
     * @return
     */
    List<CarConserveContent> selectByPropertyId(String propertyId);

    /**
     * @return
     */
    List<CarConserveContent> selectDistinctProperties();

    int deleteBySplitId(String splitId);

    /**
     * 按拆分id计数
     * 
     * @param splitId
     *            财物拆分id
     * @return
     */
    int countBySplitId(String splitId);

    /**
     * 由财物拆分id查询养护内容
     * 
     * @param splitId
     *            财物拆分id
     * @return
     */
    List<CarConserveContent> selectBySplitId(String splitId);

}