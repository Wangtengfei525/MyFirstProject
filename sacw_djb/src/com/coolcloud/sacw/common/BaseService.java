package com.coolcloud.sacw.common;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.common.util.UuidUtil;

/**
 * 基本服务类
 * 
 * @author xyz
 *
 * @param <T>
 * @param <P>
 */
public abstract class BaseService<T extends BaseEntity, P extends Serializable> {

    @Autowired
    private BaseMapper<T, P> baseMapper;

    /**
     * 选择性保存实体到数据库（仅保存非null值），有id值为更新操作，无id为插入操作
     * 
     * @param entity
     * @return
     */
    @Transactional
    public int save(T entity) {
        Date date = new Date();
        /*entity.setCreateTime(date);
        entity.setUpdateTime(date);*/
        entity.setDeleted(0);
        if (StringUtils.isEmpty(entity.getId())) {
            entity.setId(UuidUtil.get32UUID());
        }
        return baseMapper.insertSelective(entity);
    }

    @Transactional
    public int saveBatch(List<T> entities) {
        if (entities == null || entities.size() == 0) {
            return 0;
        }
        return baseMapper.insertBatch(entities);
    }

    @Transactional
    public int delete(P id) {
        if (id == null) {
            return 0;
        }
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public int deleteBatch(List<P> ids) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }
        return baseMapper.deleteByPrimaryKeyBatch(ids);
    }

    @Transactional
    public int update(T entity) {
        if (StringUtils.isEmpty(entity.getId())) {
            return 0;
        }
       // entity.setUpdateTime(new Date());
        return baseMapper.updateByPrimaryKeySelective(entity);
    }
    
    /*@Transactional
    public int updateExchange(String id) {
       
       // entity.setUpdateTime(new Date());
        return baseMapper.updateByPrimaryKeySelective1(id);
    }
    */
    
    

    /**
     * 查询全部
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return this.getByExample(null);
    }

    /**
     * 根据example查询
     * 
     * @param example
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> getByExample(Object example) {
        // 若传空example则查询全部
        if (example == null) {
            example = new HashMap<>();
        }
        return baseMapper.selectByExample(example);
    }

    /**
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public T get(P id) {
        if (id == null) {
            return null;
        }
        return baseMapper.selectByPrimaryKey(id);
    }

    /**
     * 检查记录是否存在
     * 
     * @param id
     *            实体id
     * @return
     */
    @Transactional(readOnly = true)
    public boolean exists(P id) {
        return this.get(id) != null;
    }

}
