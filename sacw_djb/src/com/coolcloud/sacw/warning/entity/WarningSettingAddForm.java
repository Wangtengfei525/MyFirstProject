package com.coolcloud.sacw.warning.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * 报警设置添加
 * @author 王腾飞
 *2018年12月28日上午11:05:53
 */
public class WarningSettingAddForm {

    private static final String PARTTERN_IPV4 = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    /**
     * 名称
     *
     */
    @NotBlank(message = "设置名称不能为空")
    @Length(min = 1, max = 255, message = "设置名称长度应在{min}到{max}之间")
    private String name;

    /**
     * 天线ip
     */
    @Pattern(regexp = PARTTERN_IPV4, message = "射频天线IP地址不正确")
    private String rfIp;

    /**
     * 天线端口
     *
     */
    @NotNull(message = "射频天线端口不能为空")
    @Range(min = 1, max = Short.MAX_VALUE, message = "射频天线端口值应在{min}到{max}之间")
    private Integer rfPort;

    /**
     * 摄像头ip
     *
     */
    @Pattern(regexp = PARTTERN_IPV4, message = "摄像头IP地址不正确")
    private String cameraIp;

    /**
     * 摄像头端口
     *
     */
    @NotNull(message = "摄像头端口不能为空")
    @Range(min = 1, max = Short.MAX_VALUE, message = "摄像头端口值应在{min}到{max}之间")
    private Integer cameraPort;

    /**
     * 摄像头用户名
     *
     */
    @NotBlank(message = "摄像头用户名不能为空")
    @Length(min = 1, max = 32, message = "摄像头用户名长度应在{min}到{max}之间")
    private String cameraUsername;

    /**
     * 摄像头密码
     *
     */
    @NotBlank(message = "摄像头密码不能为空")
    @Length(min = 1, max = 32, message = "摄像头密码长度应在{min}到{max}之间")
    private String cameraPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRfIp() {
        return rfIp;
    }

    public void setRfIp(String rfIp) {
        this.rfIp = rfIp;
    }

    public Integer getRfPort() {
        return rfPort;
    }

    public void setRfPort(Integer rfPort) {
        this.rfPort = rfPort;
    }

    public String getCameraIp() {
        return cameraIp;
    }

    public void setCameraIp(String cameraIp) {
        this.cameraIp = cameraIp;
    }

    public Integer getCameraPort() {
        return cameraPort;
    }

    public void setCameraPort(Integer cameraPort) {
        this.cameraPort = cameraPort;
    }

    public String getCameraUsername() {
        return cameraUsername;
    }

    public void setCameraUsername(String cameraUsername) {
        this.cameraUsername = cameraUsername;
    }

    public String getCameraPassword() {
        return cameraPassword;
    }

    public void setCameraPassword(String cameraPassword) {
        this.cameraPassword = cameraPassword;
    }

}