package com.coolcloud.sacw.disposal.entity;

import com.coolcloud.sacw.common.BaseExample;

public class DisposalUnitExample extends BaseExample{
    private String sendUnitCode;
    private String receiveUnitCode;
    private String disposalUnitCode;
    private String comName;
	public String getSendUnitCode() {
		return sendUnitCode;
	}
	public void setSendUnitCode(String sendUnitCode) {
		this.sendUnitCode = sendUnitCode;
	}
	public String getReceiveUnitCode() {
		return receiveUnitCode;
	}
	public void setReceiveUnitCode(String receiveUnitCode) {
		this.receiveUnitCode = receiveUnitCode;
	}
	public String getDisposalUnitCode() {
		return disposalUnitCode;
	}
	public void setDisposalUnitCode(String disposalUnitCode) {
		this.disposalUnitCode = disposalUnitCode;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
}
