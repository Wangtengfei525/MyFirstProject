package com.coolcloud.sacw.property.web;

import java.awt.List;
import java.util.ArrayList;

import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.property.entity.NewGetPropertyInfo;
import com.coolcloud.sacw.property.entity.NewProperty;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.service.NewPropertyService;
import com.github.pagehelper.PageInfo;
import com.sun.org.glassfish.external.probe.provider.annotations.ProbeProvider;

@RestController
@RequestMapping("/newproperty")
public class NewPropertyController {
	
	@Autowired
	public NewPropertyService newPropertyService;
	
	@PostMapping("/newpropertyinsert")
	public String newPropertyInsert(@RequestBody ArrayList<NewProperty> propertylist) {
		return newPropertyService.newPropertyInsert(propertylist);
	}
	
	@GetMapping("/getpropertyinfo")
	public Result getPropertyInfo(@RequestParam String property_id) {
		java.util.List<NewGetPropertyInfo> property=newPropertyService.getPropertyInfo(property_id);
		PageInfo<NewGetPropertyInfo> info = new PageInfo<>(property);
	      return Result.success().total(info.getTotal()).rows(info.getList());
	}
}
