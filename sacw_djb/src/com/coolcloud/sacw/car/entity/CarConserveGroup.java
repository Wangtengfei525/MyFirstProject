package com.coolcloud.sacw.car.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 车辆养护分组
 * 
 * @author xyz
 *
 * @date 2018年4月11日 下午2:16:03
 */
public class CarConserveGroup extends BaseEntity {

    private static final long serialVersionUID = -6840829695391874831L;

    /**
     * 分组名称
     *
     */
    private String name;

    /**
     * 养护时间
     */
    private Date conserveTime;

    /**
     * 是否养护完成，0未完成1完成
     */
    private Integer completed;
    
    /**
     * 所属区id
     */
    private String   unitId;

    private List<CarConserve> conserves = new ArrayList<>();


	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getConserveTime() {
        return conserveTime;
    }

    public void setConserveTime(Date conserveTime) {
        this.conserveTime = conserveTime;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public List<CarConserve> getConserves() {
        return conserves;
    }

    public void setConserves(List<CarConserve> conserves) {
        this.conserves = conserves;
    }

}