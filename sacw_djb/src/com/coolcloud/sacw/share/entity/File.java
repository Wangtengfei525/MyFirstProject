package com.coolcloud.sacw.share.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.coolcloud.sacw.share.adapter.DateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class File {

	private String PTAJBH;

	private String PTWJBH;

	private String XH;

	private String WSWH;

	private String WJLX;

	private String WSMB_BM;

	private String WSMB_MC;

	private String WJLJ;

	private String HZM;

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date ZZSJ;

	private String BZ;

	private String WJBH;

	private String WJMC;

	private String JHBH;

	private String JHPC;

	private String SFSC = "N";

	public String getPTAJBH() {
		return PTAJBH;
	}

	public void setPTAJBH(String pTAJBH) {
		PTAJBH = pTAJBH;
	}

	public String getPTWJBH() {
		return PTWJBH;
	}

	public void setPTWJBH(String pTWJBH) {
		PTWJBH = pTWJBH;
	}

	public String getXH() {
		return XH;
	}

	public void setXH(String xH) {
		XH = xH;
	}

	public String getWSWH() {
		return WSWH;
	}

	public void setWSWH(String wSWH) {
		WSWH = wSWH;
	}

	public String getWJLX() {
		return WJLX;
	}

	public void setWJLX(String wJLX) {
		WJLX = wJLX;
	}

	public String getWSMB_BM() {
		return WSMB_BM;
	}

	public void setWSMB_BM(String wSMB_BM) {
		WSMB_BM = wSMB_BM;
	}

	public String getWSMB_MC() {
		return WSMB_MC;
	}

	public void setWSMB_MC(String wSMB_MC) {
		WSMB_MC = wSMB_MC;
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

	public Date getZZSJ() {
		return ZZSJ;
	}

	public void setZZSJ(Date zZSJ) {
		ZZSJ = zZSJ;
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String bZ) {
		BZ = bZ;
	}

	public String getWJBH() {
		return WJBH;
	}

	public void setWJBH(String wJBH) {
		WJBH = wJBH;
	}

	public String getWJMC() {
		return WJMC;
	}

	public void setWJMC(String wJMC) {
		WJMC = wJMC;
	}

	public String getJHBH() {
		return JHBH;
	}

	public void setJHBH(String jHBH) {
		JHBH = jHBH;
	}

	public String getJHPC() {
		return JHPC;
	}

	public void setJHPC(String jHPC) {
		JHPC = jHPC;
	}

	public String getSFSC() {
		return SFSC;
	}

	public void setSFSC(String sFSC) {
		SFSC = sFSC;
	}

}
