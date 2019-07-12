package com.coolcloud.sacw.annex.entity;

import java.sql.Timestamp;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 案件相关的附件实体类
 * @author 王腾飞
 *2018年12月27日上午9:18:58
 *
 */
public class File1  extends BaseEntity{
	private static final long serialVersionUID = 7789884211769302759L;
	private String caseId; // 案件id
	private String exchangeId;// 交换记录id
	private String file_name; //文件名称
	private String file_class;// 文件类型
	private String file_path; // 文件路径
	private String suffix_name; // 后缀名
	private String process_num; // 流程编号
	private String control_num; // 监督编号
	public String create_time;
	public String update_time;
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getExchangeId() {
		return exchangeId;
	}
	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_class() {
		return file_class;
	}
	public void setFile_class(String file_class) {
		this.file_class = file_class;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getSuffix_name() {
		return suffix_name;
	}
	public void setSuffix_name(String suffix_name) {
		this.suffix_name = suffix_name;
	}
	public String getProcess_num() {
		return process_num;
	}
	public void setProcess_num(String process_num) {
		this.process_num = process_num;
	}
	public String getControl_num() {
		return control_num;
	}
	public void setControl_num(String control_num) {
		this.control_num = control_num;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public File1(String caseId, String exchangeId, String file_name, String file_class, String file_path,
			String suffix_name, String process_num, String control_num) {
		super();
		this.caseId = caseId;
		this.exchangeId = exchangeId;
		this.file_name = file_name;
		this.file_class = file_class;
		this.file_path = file_path;
		this.suffix_name = suffix_name;
		this.process_num = process_num;
		this.control_num = control_num;
	}
	public File1() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	
	
}
