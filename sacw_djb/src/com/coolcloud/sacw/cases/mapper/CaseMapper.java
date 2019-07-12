package com.coolcloud.sacw.cases.mapper;

import java.util.List;

import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.property.entity.PropertyVo;
import com.coolcloud.sacw.property.entity.PropertyVoNew;

public interface CaseMapper extends BaseMapper<Case, String>{

	//通过财物的主键查询对应的案件
	    Case  selectCaseByPropertyId(String  id);
	
	    
	//查询总的案件数量
	    
	    
	
	    
	    
	    //查询未借调的财物所属的案件有哪些 
	    List<Case>  findCaseByNotSecondProperty(PropertyVo vo);
	    
	  
	  //通过财物的状态  案件名称/财务名字  案件的时间   案件的单位  实施多条件查询  
	    List<Case>   selectCaseByPropertyManyCondition(PropertyVo  vo);
	
	   
	   
	  //通过organizerCode 查询案件
	    
	    List<Case>   selectCaseByOrganizerCode(String OrganizerCode);
	    
	    
	
	
}
