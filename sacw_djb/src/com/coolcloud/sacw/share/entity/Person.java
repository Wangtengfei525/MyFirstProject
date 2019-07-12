package com.coolcloud.sacw.share.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.coolcloud.sacw.share.adapter.DateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

	private String PTAJBH;

	private String PTRYBH;

	private String XM;

	private String GJ_MC;

	private String GJ_BM;

	private String JG_MC;

	private String JG_BM;

	private String RYLX;

	private String RYLB;

	private String ZZJGDM;

	private String ZZJGMC;

	private String ZZJGDZ;

	private String ZM_BM;

	private String ZM_MC;

	private String XB_BM;

	private String XB_MC;

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date CSRQ;

	private String ZJLX_BM;

	private String ZJLX_MC;

	private String ZJHM;

	private String ZSD;

	private String AFD;

	private String LXDH;

	private String BZ;

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date DJRQ;

	private String SFSC = "N";

	public String getPTAJBH() {
		return PTAJBH;
	}

	public void setPTAJBH(String pTAJBH) {
		PTAJBH = pTAJBH;
	}

	public String getPTRYBH() {
		return PTRYBH;
	}

	public void setPTRYBH(String pTRYBH) {
		PTRYBH = pTRYBH;
	}

	public String getXM() {
		return XM;
	}

	public void setXM(String xM) {
		XM = xM;
	}

	public String getGJ_MC() {
		return GJ_MC;
	}

	public void setGJ_MC(String gJ_MC) {
		GJ_MC = gJ_MC;
	}

	public String getGJ_BM() {
		return GJ_BM;
	}

	public void setGJ_BM(String gJ_BM) {
		GJ_BM = gJ_BM;
	}

	public String getJG_MC() {
		return JG_MC;
	}

	public void setJG_MC(String jG_MC) {
		JG_MC = jG_MC;
	}

	public String getJG_BM() {
		return JG_BM;
	}

	public void setJG_BM(String jG_BM) {
		JG_BM = jG_BM;
	}

	public String getRYLX() {
		return RYLX;
	}

	public void setRYLX(String rYLX) {
		RYLX = rYLX;
	}

	public String getRYLB() {
		return RYLB;
	}

	public void setRYLB(String rYLB) {
		RYLB = rYLB;
	}

	public String getZZJGDM() {
		return ZZJGDM;
	}

	public void setZZJGDM(String zZJGDM) {
		ZZJGDM = zZJGDM;
	}

	public String getZZJGMC() {
		return ZZJGMC;
	}

	public void setZZJGMC(String zZJGMC) {
		ZZJGMC = zZJGMC;
	}

	public String getZZJGDZ() {
		return ZZJGDZ;
	}

	public void setZZJGDZ(String zZJGDZ) {
		ZZJGDZ = zZJGDZ;
	}

	public String getZM_BM() {
		return ZM_BM;
	}

	public void setZM_BM(String zM_BM) {
		ZM_BM = zM_BM;
	}

	public String getZM_MC() {
		return ZM_MC;
	}

	public void setZM_MC(String zM_MC) {
		ZM_MC = zM_MC;
	}

	public String getXB_BM() {
		return XB_BM;
	}

	public void setXB_BM(String xB_BM) {
		XB_BM = xB_BM;
	}

	public String getXB_MC() {
		return XB_MC;
	}

	public void setXB_MC(String xB_MC) {
		XB_MC = xB_MC;
	}

	public Date getCSRQ() {
		return CSRQ;
	}

	public void setCSRQ(Date cSRQ) {
		CSRQ = cSRQ;
	}

	public String getZJLX_BM() {
		return ZJLX_BM;
	}

	public void setZJLX_BM(String zJLX_BM) {
		ZJLX_BM = zJLX_BM;
	}

	public String getZJLX_MC() {
		return ZJLX_MC;
	}

	public void setZJLX_MC(String zJLX_MC) {
		ZJLX_MC = zJLX_MC;
	}

	public String getZJHM() {
		return ZJHM;
	}

	public void setZJHM(String zJHM) {
		ZJHM = zJHM;
	}

	public String getZSD() {
		return ZSD;
	}

	public void setZSD(String zSD) {
		ZSD = zSD;
	}

	public String getAFD() {
		return AFD;
	}

	public void setAFD(String aFD) {
		AFD = aFD;
	}

	public String getLXDH() {
		return LXDH;
	}

	public void setLXDH(String lXDH) {
		LXDH = lXDH;
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String bZ) {
		BZ = bZ;
	}

	public Date getDJRQ() {
		return DJRQ;
	}

	public void setDJRQ(Date dJRQ) {
		DJRQ = dJRQ;
	}

	public String getSFSC() {
		return SFSC;
	}

	public void setSFSC(String sFSC) {
		SFSC = sFSC;
	}

}
