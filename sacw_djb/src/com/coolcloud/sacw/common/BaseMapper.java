package com.coolcloud.sacw.common;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 基本映射接口
 * 
 * @author xyz
 *
 * @param <T>
 * @param <P>
 */
public interface BaseMapper<T extends BaseEntity, P extends Serializable> {

    /**
     * 选择性插入
     * 
     * @param entity
     * @return
     */
    int insertSelective(T entity);

    /**
     * 批量插入
     * 
     * @param entities
     * @return
     */
    int insertBatch(List<T> entities);

    /**
     * 按主键删除
     * 
     * @param id
     * @return
     */
    int deleteByPrimaryKey(P id);

    /**
     * 按主键批量删除
     * 
     * @param ids
     * @return
     */
    int deleteByPrimaryKeyBatch(@Param("ids") List<P> ids);

    /**
     * 按主键选择性更新
     * 
     * @param entity
     * @return
     */
    int updateByPrimaryKeySelective(T entity);

    /**
     * 按主键查询
     * 
     * @param id
     * @return
     */
    T selectByPrimaryKey(P id);

    /**
     * 按条件查询
     * 
     * @param example
     * @return
     */
    List<T> selectByExample(Object example);
}
