package com.coolcloud.sacw.operation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.operation.entity.SaveLocationHistotry;
import com.coolcloud.sacw.operation.mapper.SaveLocationHistotryMapper;
/**
 * 
 * @author 王腾飞
 *2019年1月12日下午3:52:42
 */

@Service
public class SaveLocationHistotryService extends BaseService<SaveLocationHistotry, String>{
	
	@Autowired
	    private     SaveLocationHistotryMapper   slhm;
	
	
	 @Transactional(readOnly = true)
	public   SaveLocationHistotry   findSaveLocationHistoryByProId(String id)
	{
		SaveLocationHistotry   saveLocationHistory=slhm.findSaveLocationHistoryByProId(id);
		return saveLocationHistory;
	}
	
	
}
