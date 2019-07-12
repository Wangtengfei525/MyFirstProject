package com.coolcloud.sacw.system.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 单位更新表单
 * 
 * @author xyz
 *
 */
public class UnitUpdateForm {

    @NotBlank(message = "单位id不能为空")
    @Length(min = 1, max = 32, message = "单位id长度应在{min}到{max}之间")
    private String id;

    @Length(min = 1, max = 255, message = "单位名称长度应在{min}到{max}之间")
    private String name;

    @Length(min = 1, max = 255, message = "单位备注长度应在{min}到{max}之间")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
