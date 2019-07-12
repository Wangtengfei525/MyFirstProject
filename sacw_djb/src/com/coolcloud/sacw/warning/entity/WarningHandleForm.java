package com.coolcloud.sacw.warning.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author xyz
 *
 */
public class WarningHandleForm {

    /**
     * 报警记录id
     */
    @NotBlank(message = "请提供报警记录id")
    private String id;

    /**
     * 备注
     */
    @Length(min = 1, max = 255, message = "备注字符长度应在{min}到{max}之间")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
