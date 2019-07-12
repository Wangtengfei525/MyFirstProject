package com.coolcloud.sacw.share.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.coolcloud.sacw.share.adapter.DateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Photo {

	private String PTAJBH;

	private String PTCWBH;

	private String CWMC;

	private String ZPBH;

	private String ZPMS;

	private String ZPPSR;

	private String ZPPSDW_BM;

	private String ZPPSDW_MC;

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date ZPPSSJ;

	private String WJMC;

	private String WJLJ;

	private String HZM;

	private String BZ;

	private String WJLX_BM;

	private String WJLX_MC;

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

	public String getCWMC() {
		return CWMC;
	}

	public void setCWMC(String cWMC) {
		CWMC = cWMC;
	}

	public String getZPBH() {
		return ZPBH;
	}

	public void setZPBH(String zPBH) {
		ZPBH = zPBH;
	}

	public String getZPMS() {
		return ZPMS;
	}

	public void setZPMS(String zPMS) {
		ZPMS = zPMS;
	}

	public String getZPPSR() {
		return ZPPSR;
	}

	public void setZPPSR(String zPPSR) {
		ZPPSR = zPPSR;
	}

	public String getZPPSDW_BM() {
		return ZPPSDW_BM;
	}

	public void setZPPSDW_BM(String zPPSDW_BM) {
		ZPPSDW_BM = zPPSDW_BM;
	}

	public String getZPPSDW_MC() {
		return ZPPSDW_MC;
	}

	public void setZPPSDW_MC(String zPPSDW_MC) {
		ZPPSDW_MC = zPPSDW_MC;
	}

	public Date getZPPSSJ() {
		return ZPPSSJ;
	}

	public void setZPPSSJ(Date zPPSSJ) {
		ZPPSSJ = zPPSSJ;
	}

	public String getWJMC() {
		return WJMC;
	}

	public void setWJMC(String wJMC) {
		WJMC = wJMC;
	}

	public String getWJLJ() {
		return WJLJ;
	}

	public void setWJLJ(String wJLJ) {
		WJLJ = wJLJ;
	}

	public String getHZM() {
		return HZM;
	}

	public void setHZM(String hZM) {
		HZM = hZM;
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String bZ) {
		BZ = bZ;
	}

	public String getWJLX_BM() {
		return WJLX_BM;
	}

	public void setWJLX_BM(String wJLX_BM) {
		WJLX_BM = wJLX_BM;
	}

	public String getWJLX_MC() {
		return WJLX_MC;
	}

	public void setWJLX_MC(String wJLX_MC) {
		WJLX_MC = wJLX_MC;
	}

	public String getSFSC() {
		return SFSC;
	}

	public void setSFSC(String sFSC) {
		SFSC = sFSC;
	}

}
