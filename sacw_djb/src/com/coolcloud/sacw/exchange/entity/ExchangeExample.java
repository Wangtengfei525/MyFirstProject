package com.coolcloud.sacw.exchange.entity;

import java.util.Date;
import java.util.List;

import com.coolcloud.sacw.common.BaseExample;

public class ExchangeExample extends BaseExample {

	
	private String caseName;

	/**
	 * 案件名称模糊查询
	 */
	private String caseNameLike;

	

	/**
	 * 节点编号
	 */
	private String nodeCode;
	

	/**
	 * 接收状态标识，取值0，1，2。0：未接收，1：已接收，2：已退回 由于发送反馈后，前置机会返回发送数据
	 * 查询未接收和已退回交换记录时应该过滤接收单位为成都市保管中心的数据
	 */
	

	
}