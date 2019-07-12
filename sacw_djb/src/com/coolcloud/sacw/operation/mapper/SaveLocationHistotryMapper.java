package com.coolcloud.sacw.operation.mapper;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.operation.entity.SaveLocationHistotry;

public interface SaveLocationHistotryMapper extends BaseMapper<SaveLocationHistotry, String> {

	SaveLocationHistotry  findSaveLocationHistoryByProId(String property_id);
	
	
}
