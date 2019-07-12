package com.coolcloud.sacw.cases.entity;

import java.util.Date;

import com.coolcloud.sacw.common.BaseExample;

/**
 * 案件管理列表条件查询实体类
 * @author 王腾飞
 *2018年12月28日上午10:49:12
 */
public class CaseExample extends BaseExample{
       
        private String organizerName;
        private String  caseNamelike; 
        private Date createTimestart;
        private Date createTimeend;
		public String getOrganizerName() {
			return organizerName;
		}
		public void setOrganizerName(String organizerName) {
			this.organizerName = organizerName;
		}
		public String getCaseNamelike() {
			return caseNamelike;
		}
		public void setCaseNamelike(String caseNamelike) {
			this.caseNamelike = caseNamelike;
		}
		public Date getCreateTimestart() {
			return createTimestart;
		}
		public void setCreateTimestart(Date createTimestart) {
			this.createTimestart = createTimestart;
		}
		public Date getCreateTimeend() {
			return createTimeend;
		}
		public void setCreateTimeend(Date createTimeend) {
			this.createTimeend = createTimeend;
		}
		public CaseExample(String organizerName, String caseNamelike, Date createTimestart, Date createTimeend) {
			super();
			this.organizerName = organizerName;
			this.caseNamelike = caseNamelike;
			this.createTimestart = createTimestart;
			this.createTimeend = createTimeend;
		}
		public CaseExample() {
			super();
			// TODO Auto-generated constructor stub
		}
		
        
        

}
