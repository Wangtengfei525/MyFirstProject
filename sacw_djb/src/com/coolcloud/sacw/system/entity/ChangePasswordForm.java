package com.coolcloud.sacw.system.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 修改密码表单参数
 * 
 * @author xyz
 *
 * @date 2018年4月18日 上午11:05:05
 */
public class ChangePasswordForm {

    @NotBlank(message = "原密码不能为空")
    @Length(min = 1, max = 32, message = "原始密码长度应在{min}到{max}之间")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Length(min = 1, max = 32, message = "新密码长度应在{min}到{max}之间")
    private String newPassword;

    @NotBlank(message = "确认新密码不能为空")
    @Length(min = 1, max = 32, message = "确认新密码长度应在{min}到{max}之间")
    private String rePassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

}
