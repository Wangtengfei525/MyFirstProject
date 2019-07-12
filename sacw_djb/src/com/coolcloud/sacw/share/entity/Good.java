package com.coolcloud.sacw.share.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.coolcloud.sacw.share.adapter.DateAdapter;

/**
 * 
 * @author 袁永祥
 *
 * @date 2017年8月23日 下午12:37:38
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Good {

	private String PTAJBH;

	private String PTCWBH;

	private String FPTCWBH;

	private String CWMC;

	private String EWM;

	private String LY;

	private String CWLX_BM;

	private String CWLX_MC;

	private Integer SL;

	private String JL_DW;

	private Double JZ;

	private String JZ_DW;

	private String BZ_DM;

	private String BZ_MC;

	private String TZ;

	private String CYRLX_BM;

	private String CYRLX_MC;

	private String CYRBH;

	private String CYRXM;

	private String CWCS_MC;

	private String CWCS_BM;

	private String CSZXR;

	private String DJR;

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date DJRQ;

	private String SFSC = "N";

	private String BZ;

	private String BGDW_BM = "";

	private String BGDW_MC = "";

	private String KWBM;

	private String KWMC;

	private String CWDJZT_BM;

	private String CWDJZT_MC;

	private String CZR;

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date CZSJ;

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

	public String getFPTCWBH() {
		return FPTCWBH;
	}

	public void setFPTCWBH(String fPTCWBH) {
		FPTCWBH = fPTCWBH;
	}

	public String getCWMC() {
		return CWMC;
	}

	public void setCWMC(String cWMC) {
		CWMC = cWMC;
	}

	public String getEWM() {
		return EWM;
	}

	public void setEWM(String eWM) {
		EWM = eWM;
	}

	public String getLY() {
		return LY;
	}

	public void setLY(String lY) {
		LY = lY;
	}

	public String getCWLX_BM() {
		return CWLX_BM;
	}

	public void setCWLX_BM(String cWLX_BM) {
		CWLX_BM = cWLX_BM;
	}

	public String getCWLX_MC() {
		return CWLX_MC;
	}

	public void setCWLX_MC(String cWLX_MC) {
		CWLX_MC = cWLX_MC;
	}

	public Integer getSL() {
		return SL;
	}

	public void setSL(Integer sL) {
		SL = sL;
	}

	public String getJL_DW() {
		return JL_DW;
	}

	public void setJL_DW(String jL_DW) {
		JL_DW = jL_DW;
	}

	public Double getJZ() {
		return JZ;
	}

	public void setJZ(Double jZ) {
		JZ = jZ;
	}

	public String getJZ_DW() {
		return JZ_DW;
	}

	public void setJZ_DW(String jZ_DW) {
		JZ_DW = jZ_DW;
	}

	public String getBZ_DM() {
		return BZ_DM;
	}

	public void setBZ_DM(String bZ_DM) {
		BZ_DM = bZ_DM;
	}

	public String getBZ_MC() {
		return BZ_MC;
	}

	public void setBZ_MC(String bZ_MC) {
		BZ_MC = bZ_MC;
	}

	public String getTZ() {
		return TZ;
	}

	public void setTZ(String tZ) {
		TZ = tZ;
	}

	public String getCYRLX_BM() {
		return CYRLX_BM;
	}

	public void setCYRLX_BM(String cYRLX_BM) {
		CYRLX_BM = cYRLX_BM;
	}

	public String getCYRLX_MC() {
		return CYRLX_MC;
	}

	public void setCYRLX_MC(String cYRLX_MC) {
		CYRLX_MC = cYRLX_MC;
	}

	public String getCYRBH() {
		return CYRBH;
	}

	public void setCYRBH(String cYRBH) {
		CYRBH = cYRBH;
	}

	public String getCYRXM() {
		return CYRXM;
	}

	public void setCYRXM(String cYRXM) {
		CYRXM = cYRXM;
	}

	public String getCWCS_MC() {
		return CWCS_MC;
	}

	public void setCWCS_MC(String cWCS_MC) {
		CWCS_MC = cWCS_MC;
	}

	public String getCWCS_BM() {
		return CWCS_BM;
	}

	public void setCWCS_BM(String cWCS_BM) {
		CWCS_BM = cWCS_BM;
	}

	public String getCSZXR() {
		return CSZXR;
	}

	public void setCSZXR(String cSZXR) {
		CSZXR = cSZXR;
	}

	public String getDJR() {
		return DJR;
	}

	public void setDJR(String dJR) {
		DJR = dJR;
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

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String bZ) {
		BZ = bZ;
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

}
