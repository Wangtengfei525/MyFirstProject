package com.coolcloud.sacw.warning.entity;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 报警设置实体类
 * @author 王腾飞
 *2018年12月28日上午11:05:18
 */
public class WarningSetting extends BaseEntity {

    private static final long serialVersionUID = 7340729375985056508L;

    /**
     * 名称
     *
     */
    private String name;

    /**
     * 天线ip
     */
    private String rfIp;

    /**
     * 天线端口
     *
     */
    private Integer rfPort;

    /**
     * 摄像头ip
     *
     */
    private String cameraIp;

    /**
     * 摄像头端口
     *
     */
    private Integer cameraPort;

    /**
     * 摄像头用户名
     *
     */
    private String cameraUsername;

    /**
     * 摄像头密码
     *
     */
    private String cameraPassword;
    /**
     * 开启状态
     */
  // private String state;

    /*public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}*/

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