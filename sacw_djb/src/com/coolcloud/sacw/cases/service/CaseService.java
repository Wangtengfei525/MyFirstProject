package com.coolcloud.sacw.cases.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.cases.mapper.CaseMapper;
import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.property.entity.PropertyVo;
import com.coolcloud.sacw.property.entity.PropertyVoNew;

/**
 * 案件管理service类
 * 
 * @author 王孝康
 *
 */
@Service
public class CaseService extends BaseService<Case, String> {

	@Autowired
	  private CaseMapper    caseMapper;
	
	public    Case   findCaseByPropertyId(String id)
	{
		Case   c=caseMapper.selectCaseByPropertyId(id);
		return  c;
		
	}
	
	//通过未借调的财物状态来查询对应的案件信息
	
	public   List<Case>  findCaseByNotSecondmentProperty(PropertyVo vo)
	{
		List<Case> c=caseMapper.findCaseByNotSecondProperty(vo);
		return  c;
	}
	
	
	//通过财物的状态  案件名称/财务名字  案件的时间   案件的单位  实施多条件查询  
	public   List<Case>  findCaseByPropertyManyCondition(PropertyVo vo)
	{
		List<Case> c=caseMapper.selectCaseByPropertyManyCondition(vo);
		return  c;
	}
	
	
	public  String   findOrganizerNameByOrganizerCode(String OrganizerCode)
	{
		List<Case> listCase=caseMapper.selectCaseByOrganizerCode(OrganizerCode);
		String s;
		if(listCase.size()!=0)
		{
			s=listCase.get(0).getOrganizerName();
		}
		
		else
		{
			s=null;
		}
		return s;
		
	}
	
	
	
	
	
	
	
	
	
}
