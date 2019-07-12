package com.coolcloud.sacw.share.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 数据共享案件信息类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月23日 上午10:54:00
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Case {

	private String PTAJBH;// 平台案件编码

	private String AJMC;// 案件名称

	private String AJLX_MC;// 案件类型名称

	private String AJLX_BM;// 案件类型编码

	private String AY_MC;// 案由名称

	private String AY_BM;// 案由编码

	private String AJJD;// 案件阶段

	private String AQZY;// 案情摘要

	private String AJLY;// 案件来源

	private String CBDW_LX;// 承办单位类型

	private String CBDW_BM;// 承办单位编码

	private String CBDW_MC;// 承办单位名称

	private String CBBM_BM;// 承办部门编码

	private String CBBM_MC;// 承办部门名称

	private String CBR_XM;// 承办人姓名

	private String CBR_GH;// 承办人工号

	private String BZ;// 备注

	private String SFSC = "N";

	public String getPTAJBH() {
		return PTAJBH;
	}

	public void setPTAJBH(String pTAJBH) {
		PTAJBH = pTAJBH;
	}

	public String getAJMC() {
		return AJMC;
	}

	public void setAJMC(String aJMC) {
		AJMC = aJMC;
	}

	public String getAJLX_MC() {
		return AJLX_MC;
	}

	public void setAJLX_MC(String aJLX_MC) {
		AJLX_MC = aJLX_MC;
	}

	public String getAJLX_BM() {
		return AJLX_BM;
	}

	public void setAJLX_BM(String aJLX_BM) {
		AJLX_BM = aJLX_BM;
	}

	public String getAY_MC() {
		return AY_MC;
	}

	public void setAY_MC(String aY_MC) {
		AY_MC = aY_MC;
	}

	public String getAY_BM() {
		return AY_BM;
	}

	public void setAY_BM(String aY_BM) {
		AY_BM = aY_BM;
	}

	public String getAJJD() {
		return AJJD;
	}

	public void setAJJD(String aJJD) {
		AJJD = aJJD;
	}

	public String getAQZY() {
		return AQZY;
	}

	public void setAQZY(String aQZY) {
		AQZY = aQZY;
	}

	public String getAJLY() {
		return AJLY;
	}

	public void setAJLY(String aJLY) {
		AJLY = aJLY;
	}

	public String getCBDW_LX() {
		return CBDW_LX;
	}

	public void setCBDW_LX(String cBDW_LX) {
		CBDW_LX = cBDW_LX;
	}

	public String getCBDW_BM() {
		return CBDW_BM;
	}

	public void setCBDW_BM(String cBDW_BM) {
		CBDW_BM = cBDW_BM;
	}

	public String getCBDW_MC() {
		return CBDW_MC;
	}

	public void setCBDW_MC(String cBDW_MC) {
		CBDW_MC = cBDW_MC;
	}

	public String getCBBM_BM() {
		return CBBM_BM;
	}

	public void setCBBM_BM(String cBBM_BM) {
		CBBM_BM = cBBM_BM;
	}

	public String getCBBM_MC() {
		return CBBM_MC;
	}

	public void setCBBM_MC(String cBBM_MC) {
		CBBM_MC = cBBM_MC;
	}

	public String getCBR_XM() {
		return CBR_XM;
	}

	public void setCBR_XM(String cBR_XM) {
		CBR_XM = cBR_XM;
	}

	public String getCBR_GH() {
		return CBR_GH;
	}

	public void setCBR_GH(String cBR_GH) {
		CBR_GH = cBR_GH;
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String bZ) {
		BZ = bZ;
	}

	public String getSFSC() {
		return SFSC;
	}

	public void setSFSC(String sFSC) {
		SFSC = sFSC;
	}

}
