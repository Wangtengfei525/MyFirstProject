package com.coolcloud.sacw.operation.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author xyz
 *
 */
public class SecondmentDelayAddForm {

    @NotBlank(message = "借调id不能未为空")
    private String secondmentIds;

    /**
     * 延期后归还时间
     *
     */
    @NotNull(message = "延期归还时间不能为空")
    private Date delayedReturnTime;

    /**
     * 延期备注
     *
     */
    @NotBlank(message = "延期备注不能为空")
    @Length(min = 1, max = 255, message = "延期备注应在{min}到{max}个字符之间")
    private String remark;

    public String getSecondmentIds() {
        return secondmentIds;
    }

    public void setSecondmentIds(String secondmentIds) {
        this.secondmentIds = secondmentIds;
    }

    public Date getDelayedReturnTime() {
        return delayedReturnTime;
    }

    public void setDelayedReturnTime(Date delayedReturnTime) {
        this.delayedReturnTime = delayedReturnTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}