package com.coolcloud.sacw.property.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.security.access.method.P;

import com.coolcloud.sacw.property.entity.NewGetPropertyInfo;
import com.coolcloud.sacw.property.entity.NewProperty;
import com.coolcloud.sacw.property.entity.Property;

@Mapper
public interface NewPropertyMapper {
	
	//新增财物信息
	@Insert("insert into yw_property(id,property_name,property_type,number,property_status_code,property_status,qr_code,case_id,remake,create_time) "+
	"values(#{id},#{property_name},#{property_type},#{number},#{property_status_code},#{property_status},#{qr_code},#{case_id},#{remake},#{create_time})")
	public void newPropertyInsert(@Param("id") String id,@Param("property_name") String property_name,@Param("property_type") String property_type,
			@Param("number") int number,@Param("property_status_code") String property_status_code,@Param("property_status") String property_statuts,
			@Param("qr_code") String qr_code,@Param("case_id") String case_id,@Param("remake") String remake,@Param("create_time") String create_time);
	
	//根据财物id查询财物信息
	@Select("select * from yw_property where case_id=#{id}")
	public NewGetPropertyInfo selectgetPropertyInfo(@Param("id") String property_id);
	
	//新增property_exchange记录
	@Insert("insert into property_exchange(exchange_id,property_id,property_status,property_status_code)"+
	" values(#{exchange_id},#{property_id},\"未入库\",\"9911000000003\")")
	public void newPropertyPutInExchange(@Param("exchange_id") String exchange_id,@Param("property_id") String property_id);
	
	//根据案件id查询exchange_id从exchange表中
	@Select("select id from exchange where case_id=#{case_id}")
	public String selectExchangeId(@Param("case_id") String case_id);
	
	//根据单位编码查询所在单位的在库车辆
	@Select("select * from yw_property where property_type=\"车辆\" and property_status=\"已入库\" and case_id in (select id from `case` where organizer_code=#{code})")
	public List<Property> selectByUnitCode(@Param("code") String code);
	
	//根据财物id查询案件名称
	@Select("select case_name as caseName from `case`,yw_property where yw_property.case_id=`case`.id and yw_property.id=#{property_id}")
	public String selectCaseNameByPropertyId(@Param("property_id") String property_id);
	
	
	//根据案件id查询所属的财物信息
	@Select("select id,case_id,property_name,qr_code,property_type,number,property_status,remake,kwmc,create_time from yw_property where case_id=#{case_id}")
	public List<NewProperty> selectPropertyInfoByCaseId(@Param("case_id") String case_id);
	
	
	NewGetPropertyInfo selectPutInProperty(String case_id);
}
