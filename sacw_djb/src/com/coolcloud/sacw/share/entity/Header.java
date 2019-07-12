package com.coolcloud.sacw.share.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.coolcloud.sacw.share.adapter.DateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Header {

	private String JHBH;

	private String LCBH;

	private String JDBH;

	private String PTAJBH;

	private String AJMC;

	private String FSDW_BM;

	private String FSDW_MC;

	private String FSRXM;

	private String FSRDH;

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date FSSJ;

	private String JSDW_BM;

	private String JSDW_MC;

	private String LJ_ZIP;

	private String LX;

	private String FSDW_LXBM;

	private String JSDW_LXBM;

	private String JHPC;

	private String FSBZ;
	
	private String SFSC = "N";

	public String getJHBH() {
		return JHBH;
	}

	public void setJHBH(String jHBH) {
		JHBH = jHBH;
	}

	public String getLCBH() {
		return LCBH;
	}

	public void setLCBH(String lCBH) {
		LCBH = lCBH;
	}

	public String getJDBH() {
		return JDBH;
	}

	public void setJDBH(String jDBH) {
		JDBH = jDBH;
	}

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

	public String getFSDW_BM() {
		return FSDW_BM;
	}

	public void setFSDW_BM(String fSDW_BM) {
		FSDW_BM = fSDW_BM;
	}

	public String getFSDW_MC() {
		return FSDW_MC;
	}

	public void setFSDW_MC(String fSDW_MC) {
		FSDW_MC = fSDW_MC;
	}

	public String getFSRXM() {
		return FSRXM;
	}

	public void setFSRXM(String fSRXM) {
		FSRXM = fSRXM;
	}

	public String getFSRDH() {
		return FSRDH;
	}

	public void setFSRDH(String fSRDH) {
		FSRDH = fSRDH;
	}

	public Date getFSSJ() {
		return FSSJ;
	}

	public void setFSSJ(Date fSSJ) {
		FSSJ = fSSJ;
	}

	public String getJSDW_BM() {
		return JSDW_BM;
	}

	public void setJSDW_BM(String jSDW_BM) {
		JSDW_BM = jSDW_BM;
	}

	public String getJSDW_MC() {
		return JSDW_MC;
	}

	public void setJSDW_MC(String jSDW_MC) {
		JSDW_MC = jSDW_MC;
	}

	public String getLJ_ZIP() {
		return LJ_ZIP;
	}

	public void setLJ_ZIP(String lJ_ZIP) {
		LJ_ZIP = lJ_ZIP;
	}

	public String getLX() {
		return LX;
	}

	public void setLX(String lX) {
		LX = lX;
	}

	public String getFSDW_LXBM() {
		return FSDW_LXBM;
	}

	public void setFSDW_LXBM(String fSDW_LXBM) {
		FSDW_LXBM = fSDW_LXBM;
	}

	public String getJSDW_LXBM() {
		return JSDW_LXBM;
	}

	public void setJSDW_LXBM(String jSDW_LXBM) {
		JSDW_LXBM = jSDW_LXBM;
	}

	public String getJHPC() {
		return JHPC;
	}

	public void setJHPC(String jHPC) {
		JHPC = jHPC;
	}

	public String getFSBZ() {
		return FSBZ;
	}

	public void setFSBZ(String fSBZ) {
		FSBZ = fSBZ;
	}

	public String getSFSC() {
		return SFSC;
	}

	public void setSFSC(String sFSC) {
		SFSC = sFSC;
	}

}
