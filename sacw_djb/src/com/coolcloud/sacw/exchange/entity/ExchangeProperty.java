package com.coolcloud.sacw.exchange.entity;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 涉案财物交换记录
 * @author 王腾飞
 *2018年12月28日上午10:54:37
 */
public class ExchangeProperty extends BaseEntity {

	private static final long serialVersionUID = -229580624226418988L;

	/**
	 * 交换记录ID
	 */
	private String exchangeId;

	/**
	 * 财物ID
	 */
	private String propertyId;

	private String propertyStatus;
	
	private String propertyStatusCode;

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}

	public String getPropertyStatusCode() {
		return propertyStatusCode;
	}

	public void setPropertyStatusCode(String propertyStatusCode) {
		this.propertyStatusCode = propertyStatusCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ExchangeProperty [exchangeId=" + exchangeId + ", propertyId=" + propertyId + ", propertyStatus="
				+ propertyStatus + ", propertyStatusCode=" + propertyStatusCode + "]";
	}
	
	
	
	
	
	
	
}