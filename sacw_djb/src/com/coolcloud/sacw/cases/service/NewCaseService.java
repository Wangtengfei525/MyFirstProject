package com.coolcloud.sacw.cases.service;

import java.io.Console;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.cases.entity.NewCase;
import com.coolcloud.sacw.cases.mapper.NewCaseMapper;
import com.coolcloud.sacw.common.util.UuidUtil;
import com.coolcloud.sacw.operation.entity.NewSearchVo;
import com.coolcloud.sacw.operation.entity.SearchVo;
import com.coolcloud.sacw.system.entity.Category;
import com.coolcloud.sacw.system.entity.Unit;

@Service
public class NewCaseService {
	
	@Autowired
	public NewCaseMapper newcasemapper;
	
	@Transactional
	public String newinsert(NewCase newcase) {
		String newcaseid=UuidUtil.get32UUID();
		Unit unit=newcasemapper.selectunit(newcase.getOrganizerName());
		Category organizer_type_code=newcasemapper.selectOranizerType(unit.getType());
		newcasemapper.newcaseinsert(newcaseid, newcase.getCaseName(), organizer_type_code.getName(), unit.getCode(), newcase.getOrganizerName(), newcase.getUndertakerName(), unit.getType(), newcase.getBz(),newcase.getCreate_time());
		newcasemapper.newPutInExchangeInsert(UuidUtil.get32UUID(), newcaseid, newcase.getCaseName());
		return newcaseid;
	}
	
	@Transactional
	public NewCase getCase(String id) {
		NewCase newCase=newcasemapper.selectCaseInfoById(id);
		return newCase;
	}
	
	@Transactional
	public String putFileCaseId(String id,String case_id) {
		newcasemapper.putFileCaseId(case_id, id);
		return "success";
	}
	
	@Transactional
	public List<NewCase> getAllCaseInfo(NewSearchVo newSearchVo){
		List<NewCase> caselist=null;
		return caselist;
	}
	
}
