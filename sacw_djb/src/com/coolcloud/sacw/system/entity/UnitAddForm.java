package com.coolcloud.sacw.system.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 单位添加表单
 * 
 * @author xyz
 *
 */
public class UnitAddForm {

    @NotBlank(message = "单位类型不能为空")
    @Length(min = 1, max = 32, message = "单位类型长度应在{min}到{max}之间")
    private String type;

    @NotBlank(message = "单位代码不能为空")
    @Length(min = 1, max = 32, message = "单位代码长度应在{min}到{max}之间")
    private String code;

    @Length(min = 1, max = 32, message = "父单位代码长度应在{min}到{max}之间")
    private String parentId;

    @NotBlank(message = "单位名称不能为空")
    @Length(min = 1, max = 255, message = "单位名称长度应在{min}到{max}之间")
    private String name;

    @Length(min = 1, max = 255, message = "单位备注长度应在{min}到{max}之间")
    private String remark;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
