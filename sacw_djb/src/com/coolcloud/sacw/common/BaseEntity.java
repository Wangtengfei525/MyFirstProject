package com.coolcloud.sacw.common;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 基本实体
 * 
 * @author xyz
 *
 */
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -6222687539974075427L;

    /**
     * 记录ID
     */
    private String id;

    /**
     * 是否删除
     */
    @JsonIgnore
    private Integer deleted;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

 
     
    @JsonIgnore
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
