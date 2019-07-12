package com.coolcloud.sacw.exchange.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coolcloud.sacw.common.BaseEntity;
import com.coolcloud.sacw.property.entity.Property;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 交换记录
 * @author 王腾飞
 *2018年12月28日上午10:53:58
 */
public class Exchange extends BaseEntity {

	private static final long serialVersionUID = 4122480072081484181L;

	private  String  caseId;//案件id
	private String processCode;//流程编号
	private String  nodeCode;//节点编号
	private   String caseName;//案件名称
	private  String   operationTime;//操作时间
	private  String  remark;//备注
	private String nodeName;
	private String id;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Exchange(String caseId, String processCode, String nodeCode, String caseName, String operationTime,
			String remark) {
		super();
		this.caseId = caseId;
		this.processCode = processCode;
		this.nodeCode = nodeCode;
		this.caseName = caseName;
		this.operationTime = operationTime;
		this.remark = remark;
	}
	public Exchange() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	
	
	
	
	
}