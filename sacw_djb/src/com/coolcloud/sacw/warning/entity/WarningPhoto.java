package com.coolcloud.sacw.warning.entity;

import java.util.Date;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 报警照片实体类
 * @author 王腾飞
 *2018年12月28日上午11:04:27
 */
public class WarningPhoto extends BaseEntity {

    private static final long serialVersionUID = -4114941493453100134L;

    /**
     * 报警记录ID
     */
    private String warningId;

    /**
     * 拍摄时间
     *
     */
    private Date takeTime;

    /**
     * 文件路径
     */
    private String filePath;

    public String getWarningId() {
        return warningId;
    }

    public void setWarningId(String warningId) {
        this.warningId = warningId;
    }

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}