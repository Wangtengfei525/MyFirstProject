package com.coolcloud.sacw.warning.entity;

import org.hibernate.validator.constraints.Range;

import com.coolcloud.sacw.common.BasePaginationForm;

/**
 * 报警记录查询表单实体类
 * @author 王腾飞
 *2018年12月28日上午11:04:51
 */
public class WarningSearchForm extends BasePaginationForm {

    @Range(min = -1, max = 1, message = "handled值只能为-1、0或1")
    private Integer handled;

    public Integer getHandled() {
        return handled;
    }

    public void setHandled(Integer handled) {
        this.handled = handled;
    }

}
