package com.coolcloud.sacw.property.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.common.util.UuidUtil;
import com.coolcloud.sacw.property.entity.NewGetPropertyInfo;
import com.coolcloud.sacw.property.entity.NewProperty;
import com.coolcloud.sacw.property.mapper.NewPropertyMapper;
import com.coolcloud.sacw.property.mapper.PropertyMapper;

@Service
public class NewPropertyService {
	
	private static final String QR_CODE_TEMPLATE = "08510100yyyyMMddHHmmssSSS";
	
	@Autowired
	public NewPropertyMapper newPropertyMapper;
	
	@Autowired
	public PropertyMapper propertyMapper;
	
	@Transactional
	public String newPropertyInsert(ArrayList<NewProperty> proprertylist) {
		
		for(int i=0;i<proprertylist.size();i++) {
			String id=UuidUtil.get32UUID();
			NewProperty property=proprertylist.get(i);
			SimpleDateFormat format = new SimpleDateFormat(QR_CODE_TEMPLATE);
	        String code = format.format(new Date()).substring(0, 24);
	        newPropertyMapper.newPropertyPutInExchange(newPropertyMapper.selectExchangeId(property.getCase_id()), id);
			newPropertyMapper.newPropertyInsert(id, property.getProperty_name(), property.getProperty_type(), Integer.parseInt(property.getNumber()), "9911000000003", "未入库", code, property.getCase_id(),property.getRemark(),property.getCreate_time());
		}
		return "success";
	}
	
	@Transactional
	public List<NewGetPropertyInfo> getPropertyInfo(String case_id) {
		List<NewGetPropertyInfo> getinfo=propertyMapper.selectPutIn(case_id);
		return getinfo;
	}
	
}
