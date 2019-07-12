package com.coolcloud.sacw.store.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.coolcloud.sacw.common.PreSortEntity;
import com.coolcloud.sacw.property.entity.Property;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 储物柜实体类
 * 
 * @author 袁永祥
 *
 * @date 2017年12月21日 下午10:01:41
 */
public class Store extends PreSortEntity {

    private static final long serialVersionUID = 442781086790882741L;

    /**
     * 储物柜名称
     */
    @NotEmpty(message = "储物柜名称不能为空")
    private String storeName;
    /**
     * 储物柜父ID
     */
    private String parentId;
    /**
     * 仓库码
     */
    private String storeCode;
    /**
     * 储物柜全名
     */
    private String storeUnitName;
    /**
     * 智能储物柜
     */
    private String storeContro;
    /**
     * 是否父节点
     */
    private String treeType;
    /**
     * 是否删除
     */
    private Integer deleted;

    @Range(min = 1, max = Integer.MAX_VALUE, message = "排序列值应在{min}到{max}之间")
    private Integer sort;

    @JsonIgnore
    private List<Property> properties = new ArrayList<>();

    /**
     * 当前储物柜及其下所有储物柜中物品数量，用于储物柜管理中数量统计 <br/>
     * 不持久化到数据库
     */
    private Integer propertiesAmount;

    /**
     * 下级储物柜，用于生成树结构
     */
    private List<Store> children = new ArrayList<>();

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreUnitName() {
        return storeUnitName;
    }

    public void setStoreUnitName(String storeUnitName) {
        this.storeUnitName = storeUnitName;
    }

    public String getStoreContro() {
        return storeContro;
    }

    public void setStoreContro(String storeContro) {
        this.storeContro = storeContro;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public List<Store> getChildren() {
        return children;
    }

    public void setChildren(List<Store> children) {
        this.children = children;
    }

    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public void addChild(Store store) {
        this.children.add(store);
    }

    public Integer getPropertiesAmount() {
        return propertiesAmount;
    }

    public void setPropertiesAmount(Integer propertiesAmount) {
        this.propertiesAmount = propertiesAmount;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

}
