package com.coolcloud.sacw.store.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * 添加储物柜表单
 * 
 * @author xyz
 *
 * @date 2018年5月2日 下午1:49:37
 */
public class StoreAddForm {

    @NotBlank(message = "父id不能为空")
    @Length(min = 1, max = 32, message = "父id长度应在{min}到{max}之间")
    private String parentId;

    @NotBlank(message = "柜子类型不能为空")
    @Length(min = 1, max = 1, message = "柜子类型长度应在{min}到{max}之间")
    private String treeType;

    @NotBlank(message = "控制码不能为空")
    @Length(min = 1, max = 20, message = "控制码长度应在{min}到{max}之间")
    private String storeContro;

    @NotBlank(message = "柜子名称不能为空")
    @Length(min = 1, max = 100, message = "柜子名称长度应在{min}到{max}之间")
    private String storeName;

    @Range(min = 0, max = Integer.MAX_VALUE, message = "排序值应在{min}到{max}之间")
    private Integer sort;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public String getStoreContro() {
        return storeContro;
    }

    public void setStoreContro(String storeContro) {
        this.storeContro = storeContro;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
