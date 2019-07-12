package com.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.coolcloud.sacw.property.mapper.PropertyMapper;

public class TestUpdate {

	@Autowired
	PropertyMapper  pm;
	@Test
	public  void  updateProperty()
	{
		
	
		//pm.updateOneToManyStore1("ad4c729170b743d38d67c04028f","ddd","kkk");
	
		
		pm.selectByPrimaryKey("ad4c729170b743d38d67c04028f");
		
		
		
	}
	
	
}
