package com.coolcloud.sacw.photo.entity;

import java.sql.Timestamp;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 照片实体类
 * @author 王腾飞
 *2018年12月28日上午11:01:11
 */
public class Photo extends BaseEntity {

	private static final long serialVersionUID = -1750288182299081076L;

	private String exchangeId;

	private String exchange_num; // 交换编号

	private String platCaseCode; // 平台案件编号

	private String property_num; // 财物编号

	private String property_name; // 财物名称

	private String photo_num; // 照片编号

	private String photo_desc; // 照片描述

	private String photo_node; // 照片节点

	private String photographer; // 照片拍摄者

	private String unit_code; // 拍摄单位编码

	private String unit_name; // 拍摄单位名称

	private Timestamp filming_time; // 拍摄时间

	private String file_name; // 文件名称

	private String file_path; // 文件路径

	private String file_class_code; // 文件类型编码

	private String file_class_name; // 文件类型名称

	private String suffix_name; // 后缀名

	private String exchange_lot; // 交换批次

	private String process_num; // 流程编号

	private String node_num; // 节点编号

	private Timestamp create_date; // 创建时间

	private Timestamp last_update_date; // 最后修改时间

	private String property_id;

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getProperty_id() {
		return property_id;
	}

	public void setProperty_id(String property_id) {
		this.property_id = property_id;
	}

	public String getExchange_num() {
		return exchange_num;
	}

	public void setExchange_num(String exchange_num) {
		this.exchange_num = exchange_num;
	}

	public String getPlatCaseCode() {
		return platCaseCode;
	}

	public void setPlatCaseCode(String platCaseCode) {
		this.platCaseCode = platCaseCode;
	}

	public String getProperty_num() {
		return property_num;
	}

	public void setProperty_num(String property_num) {
		this.property_num = property_num;
	}

	public String getProperty_name() {
		return property_name;
	}

	public void setProperty_name(String property_name) {
		this.property_name = property_name;
	}

	public String getPhoto_num() {
		return photo_num;
	}

	public void setPhoto_num(String photo_num) {
		this.photo_num = photo_num;
	}

	public String getPhoto_desc() {
		return photo_desc;
	}

	public void setPhoto_desc(String photo_desc) {
		this.photo_desc = photo_desc;
	}

	public String getPhoto_node() {
		return photo_node;
	}

	public void setPhoto_node(String photo_node) {
		this.photo_node = photo_node;
	}

	public String getPhotographer() {
		return photographer;
	}

	public void setPhotographer(String photographer) {
		this.photographer = photographer;
	}

	public String getUnit_code() {
		return unit_code;
	}

	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public Timestamp getFilming_time() {
		return filming_time;
	}

	public void setFilming_time(Timestamp filming_time) {
		this.filming_time = filming_time;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_class_code() {
		return file_class_code;
	}

	public void setFile_class_code(String file_class_code) {
		this.file_class_code = file_class_code;
	}

	public String getFile_class_name() {
		return file_class_name;
	}

	public void setFile_class_name(String file_class_name) {
		this.file_class_name = file_class_name;
	}

	public String getSuffix_name() {
		return suffix_name;
	}

	public void setSuffix_name(String suffix_name) {
		this.suffix_name = suffix_name;
	}

	public String getExchange_lot() {
		return exchange_lot;
	}

	public void setExchange_lot(String exchange_lot) {
		this.exchange_lot = exchange_lot;
	}

	public String getProcess_num() {
		return process_num;
	}

	public void setProcess_num(String process_num) {
		this.process_num = process_num;
	}

	public String getNode_num() {
		return node_num;
	}

	public void setNode_num(String node_num) {
		this.node_num = node_num;
	}

	public Timestamp getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	public Timestamp getLast_update_date() {
		return last_update_date;
	}

	public void setLast_update_date(Timestamp last_update_date) {
		this.last_update_date = last_update_date;
	}

}
