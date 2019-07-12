package com.coolcloud.sacw.operation.entity;

import com.coolcloud.sacw.common.BasePaginationForm;

public class SecondmentSearchForm extends BasePaginationForm {

    /**
     * 借调状态，0已逾期/即将逾期，1未逾期，2已归还
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
