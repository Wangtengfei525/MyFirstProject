package com.coolcloud.sacw.car.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 新增车辆养护表单
 * 
 * @author
 *
 */
public class CarConserveAddForm {

    /**
     * 二维码
     */
    @NotBlank(message = "二维码不能为空")
    private String qrCode;

    /**
     * 养护时间
     */
    @NotNull(message = "养护日期不可为空")
    private Date conserveTime;

    /**
     * 养护人
     */
    @NotBlank(message = "养护人不可为空")
    @Length(min = 1, max = 255, message = "养护人长度应在{min}到{max}之间")
    private String conserveMan;

    /**
     * 养护备注
     */
    @Length(min = 1, max = 255, message = "养护备注长度应在{min}到{max}之间")
    private String conserveRemark;

    /**
     * 养护内容
     */
    @NotBlank(message = "养护内容不能为空")
    @Length(min = 1, max = 255, message = "养护内容长度应在{min}到{max}之间")
    private String conserveText;

    /**
     * 分组id
     */
    
    
    private String groupId;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Date getConserveTime() {
        return conserveTime;
    }

    public void setConserveTime(Date conserveTime) {
        this.conserveTime = conserveTime;
    }

    public String getConserveMan() {
        return conserveMan;
    }

    public void setConserveMan(String conserveMan) {
        this.conserveMan = conserveMan;
    }

    public String getConserveRemark() {
        return conserveRemark;
    }

    public void setConserveRemark(String conserveRemark) {
        this.conserveRemark = conserveRemark;
    }

    public String getConserveText() {
        return conserveText;
    }

    public void setConserveText(String conserveText) {
        this.conserveText = conserveText;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    
    

}
