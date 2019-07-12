package com.coolcloud.sacw.property.entity;

import java.sql.Date;
import java.sql.Timestamp;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 涉案财物照片实体类
 * @author 王腾飞
 *2018年12月28日上午10:59:00
 */
public class PropertyPhoto extends BaseEntity{
	private static final long serialVersionUID = 429240272579160760L;
	
	  private String  exchangeId;//交换记录id
	  private String propertyId;//财物id
	  private String photoDesc;//图片描述
	  private String suffixName;//图片后缀名
	  private String photoName;//图片名称
	  private String processNum;//流程编号
	  private String nodeNum;//节点编号
	  private  String  photoPath;//图片路径
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
	public String getPhotoDesc() {
		return photoDesc;
	}
	public void setPhotoDesc(String photoDesc) {
		this.photoDesc = photoDesc;
	}
	public String getSuffixName() {
		return suffixName;
	}
	public void setSuffixName(String suffixName) {
		this.suffixName = suffixName;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getProcessNum() {
		return processNum;
	}
	public void setProcessNum(String processNum) {
		this.processNum = processNum;
	}
	public String getNodeNum() {
		return nodeNum;
	}
	public void setNodeNum(String nodeNum) {
		this.nodeNum = nodeNum;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public PropertyPhoto(String exchangeId, String propertyId, String photoDesc, String suffixName, String photoName,
			String processNum, String nodeNum, String photoPath) {
		super();
		this.exchangeId = exchangeId;
		this.propertyId = propertyId;
		this.photoDesc = photoDesc;
		this.suffixName = suffixName;
		this.photoName = photoName;
		this.processNum = processNum;
		this.nodeNum = nodeNum;
		this.photoPath = photoPath;
	}
	public PropertyPhoto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
