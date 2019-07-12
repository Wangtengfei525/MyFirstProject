package com.coolcloud.sacw.warning.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报警信息详情
 * 
 * @author xyz
 *
 * @date 2018年4月17日 下午5:07:13
 */
public class WarningInfo {

    private String caseName;

    private String propertyName;

    private String qrCode;

    private Date warningTime;

    private Integer handled;

    private List<WarningPhoto> photos = new ArrayList<>();

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

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

    public List<WarningPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<WarningPhoto> photos) {
        this.photos = photos;
    }

}
