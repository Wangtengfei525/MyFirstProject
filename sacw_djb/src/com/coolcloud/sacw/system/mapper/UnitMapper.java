package com.coolcloud.sacw.system.mapper;

import java.util.List;

import com.coolcloud.sacw.common.PreSortMapper;
import com.coolcloud.sacw.system.entity.Unit;

/**
 * 交换单位Mapper
 * 
 * @author 袁永祥
 *
 * @date 2017年8月21日 上午11:37:18
 */
public interface UnitMapper extends PreSortMapper<Unit, String> {

    /**
     * 是否已存在
     * 
     * @param code
     * @return
     */
    boolean existsWithCode(String code);
    
    List<Unit> selectPropertyType();

}