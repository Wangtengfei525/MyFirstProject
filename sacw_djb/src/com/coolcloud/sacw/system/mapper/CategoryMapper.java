package com.coolcloud.sacw.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.system.entity.Category;

/**
 * 分类代码mapper
 * 
 * @author 袁永祥
 *
 * @date 2017年8月24日 上午10:21:26
 */
public interface CategoryMapper extends BaseMapper<Category, String> {

    Category selectByTypeAndCode(@Param("type") String type, @Param("code") String code);

}