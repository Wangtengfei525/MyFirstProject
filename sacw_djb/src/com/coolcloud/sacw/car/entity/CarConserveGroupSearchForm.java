package com.coolcloud.sacw.car.entity;

import java.util.Date;

import com.coolcloud.sacw.common.BasePaginationForm;

/**
 * 车辆养护分组查询表单
 * 
 * @author xyz
 *
 * @date 2018年4月11日 下午2:16:03
 */
public class CarConserveGroupSearchForm extends BasePaginationForm {

    /**
     * 开始时间
     */
    private Date conserveTimeStart;

    /**
     * 结束时间
     */
    private Date conserveTimeEnd;

    /**
     * 是否养护完成，0未完成1完成
     */
    private Integer completed;

    public Date getConserveTimeStart() {
        return conserveTimeStart;
    }

    public void setConserveTimeStart(Date conserveTimeStart) {
        this.conserveTimeStart = conserveTimeStart;
    }

    public Date getConserveTimeEnd() {
        return conserveTimeEnd;
    }

    public void setConserveTimeEnd(Date conserveTimeEnd) {
        this.conserveTimeEnd = conserveTimeEnd;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

}