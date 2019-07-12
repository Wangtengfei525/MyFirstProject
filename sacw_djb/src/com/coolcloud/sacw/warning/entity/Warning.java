package com.coolcloud.sacw.warning.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coolcloud.sacw.common.BaseEntity;
import com.coolcloud.sacw.property.entity.Property;

/**
 * 报警记录实体类
 * @author 王腾飞
 *2018年12月28日上午11:04:03
 */
public class Warning extends BaseEntity {

    private static final long serialVersionUID = -2266156332515098074L;

    /**
     * 报警时间
     *
     */
    private Date warningTime;

    /**
     * 是否已处理
     */
    private Integer handled;

    /**
     * 报警设置id
     */
    private String settingId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 报警设置名称
     */
    private String settingName;

    /**
     * 财物信息
     */
    private List<Property> properties = new ArrayList<>();

    /**
     * 照片信息
     */
    private List<WarningPhoto> photos = new ArrayList<>();

    public Date getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(Date warningTime) {
        this.warningTime = warningTime;
    }

    public Integer getHandled() {
        return handled;
    }

    public void setHandled(Integer handled) {
        this.handled = handled;
    }

    public String getSettingId() {
        return settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<WarningPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<WarningPhoto> photos) {
        this.photos = photos;
    }

}