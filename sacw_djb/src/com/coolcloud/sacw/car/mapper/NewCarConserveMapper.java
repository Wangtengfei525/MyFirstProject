package com.coolcloud.sacw.car.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface NewCarConserveMapper {
	
	
	//养护记录插入
	@Insert("insert into yw_car_conserve(id,property_id,qr_code,case_mc,conserve_time,conserve_text,conserve_man,conserve_remark,exchange_key,status"+
	",conserve_number,car_message,property_mc,predict_time,conserve_text_mc) "+
	"values(#{id},#{property_id},#{qr_code},#{case_mc},#{conserve_time},#{conserve_text},#{conserve_man},#{conserve_remark},#{exchange_key},#{status}"+
	",#{conserve_number},#{car_message},#{property_mc},#{predict_time},#{conserve_text_mc})")
	public int newInsertCarConserve(@Param("id") String id,@Param("property_id") String property_id,@Param("qr_code") String qr_code,
			@Param("case_mc") String case_name,@Param("conserve_time") Date conserve_time,@Param("conserve_text") String conserve_text,
			@Param("conserve_man") String conserve_man,@Param("status") String status,@Param("conserve_remark") String conserve_remark,
			@Param("predict_time") Double predict_time,@Param("conserve_text_mc") String conserveTextName,@Param("groupId") String groupId,
			@Param("exchange_key") String exchange_key,@Param("conserve_number") String conserve_number,@Param("car_message") String car_message,
			@Param("property_mc") String property_name);
	
	
	//关联插入
	@Insert("insert into yw_car_conserve_content(property_id,conserve_content_code) values(#{property_id},#{code})")
	public void newInsertCarConserveContent(@Param("property_id") String property_id,@Param("code") String code);
}
