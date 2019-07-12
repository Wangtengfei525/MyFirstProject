package com.coolcloud.sacw.property.mapper;

import java.util.List;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.property.entity.MotorSearchParam;
import com.coolcloud.sacw.property.entity.NewPropertySplit;
import com.coolcloud.sacw.property.entity.PropertySplit;

/**
 * 财物拆分mapper
 * 
 * @author 袁永祥
 *
 * @date 2017年12月13日 下午7:05:29
 */
public interface PropertySplitMapper extends BaseMapper<PropertySplit, String> {

    /**
     * 按财物id查询拆分信息
     * 
     * @param propertId
     *            财物id
     * @return
     */
    List<PropertySplit> selectByPropertyId(String propertId);

    /**
     * 获取有单独养护内容设置的拆分财物
     * 
     * @return
     */
    List<PropertySplit> selectSplitHasOwnContents();

    /**
     * 查询拆分后机动车
     * 
     * @param param
     *            查询参数
     * @return
     */
    List<PropertySplit> selectMotorByParam(MotorSearchParam param);
    
    
    List<NewPropertySplit> newselectMotorByParam(MotorSearchParam param);

}
