package com.coolcloud.sacw.share.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.coolcloud.sacw.share.adapter.DateAdapter;

/**
 * 财物状态回执信息
 * 
 * @author 袁永祥
 *
 * @date 2017年8月28日 下午4:43:56
 */
@XmlRootElement(name = "good")
@XmlAccessorType(XmlAccessType.FIELD)
public class Receipt {

	public String PTAJBH;

	public String PTCWBH;

	public String CZJG_BM;

	public String CZJG_MC;

	public String CZJG_SM;

	public String KWBM;

	public String KWMC;

	public String BGDW_BM = "";

	public String BGDW_MC = "";

	public String CWDJZT_BM;

	public String CWDJZT_MC;

	public String CZR;

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date CZSJ;

	public String BZ;

	private String SFSC = "N";

	public String getPTAJBH() {
		return PTAJBH;
	}

	public void setPTAJBH(String pTAJBH) {
		PTAJBH = pTAJBH;
	}

	public String getPTCWBH() {
		return PTCWBH;
	}

	public void setPTCWBH(String pTCWBH) {
		PTCWBH = pTCWBH;
	}

	public String getCZJG_BM() {
		return CZJG_BM;
	}

	public void setCZJG_BM(String cZJG_BM) {
		CZJG_BM = cZJG_BM;
	}

	public String getCZJG_MC() {
		return CZJG_MC;
	}

	public void setCZJG_MC(String cZJG_MC) {
		CZJG_MC = cZJG_MC;
	}

	public String getCZJG_SM() {
		return CZJG_SM;
	}

	public void setCZJG_SM(String cZJG_SM) {
		CZJG_SM = cZJG_SM;
	}

	public String getKWBM() {
		return KWBM;
	}

	public void setKWBM(String kWBM) {
		KWBM = kWBM;
	}

	public String getKWMC() {
		return KWMC;
	}

	public void setKWMC(String kWMC) {
		KWMC = kWMC;
	}

	public String getBGDW_BM() {
		return BGDW_BM;
	}

	public void setBGDW_BM(String bGDW_BM) {
		BGDW_BM = bGDW_BM;
	}

	public String getBGDW_MC() {
		return BGDW_MC;
	}

	public void setBGDW_MC(String bGDW_MC) {
		BGDW_MC = bGDW_MC;
	}

	public String getCWDJZT_BM() {
		return CWDJZT_BM;
	}

	public void setCWDJZT_BM(String cWDJZT_BM) {
		CWDJZT_BM = cWDJZT_BM;
	}

	public String getCWDJZT_MC() {
		return CWDJZT_MC;
	}

	public void setCWDJZT_MC(String cWDJZT_MC) {
		CWDJZT_MC = cWDJZT_MC;
	}

	public String getCZR() {
		return CZR;
	}

	public void setCZR(String cZR) {
		CZR = cZR;
	}

	public Date getCZSJ() {
		return CZSJ;
	}

	public void setCZSJ(Date cZSJ) {
		CZSJ = cZSJ;
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
