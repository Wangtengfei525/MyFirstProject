package com.coolcloud.sacw.common;

import javax.validation.constraints.Min;

/**
 * 基础分页查询表单
 * 
 * @author xyz
 *
 * @date 2018年4月18日 下午2:43:45
 */
public abstract class BasePaginationForm {

    @Min(value = 1, message = "page值不能小于1")
    private Integer page;

    @Min(value = 1, message = "rows值不能小于1")
    private Integer rows;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

}
