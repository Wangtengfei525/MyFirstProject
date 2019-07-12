package com.coolcloud.sacw.cases.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.junit.runners.Parameterized.Parameters;

import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.cases.entity.NewCase;
import com.coolcloud.sacw.operation.entity.NewSearchVo;
import com.coolcloud.sacw.system.entity.Category;
import com.coolcloud.sacw.system.entity.Unit;

@Mapper
public interface NewCaseMapper {
	
	//新增案件信息
	@Insert("insert into `case`(id,case_name,organizer_type,organizer_code,organizer_name,undertaker_name,organizer_type_code,remark,create_time) "+
	"values(#{id},#{caseName},#{organizer_type},#{organizer_code},#{organizer_name},#{undertaker_name},#{organizer_type_code},#{remark},#{create_time})")
	public void newcaseinsert(@Param("id") String id,@Param("caseName") String caseName,@Param("organizer_type") String organizer_type,
			@Param("organizer_code") String organizer_code,@Param("organizer_name") String organizer_name,
			@Param("undertaker_name") String undertaker_name,@Param("organizer_type_code") String organizer_type_code,@Param("remark") String bz,@Param("create_time") String create_time);
	
	//查询案件类型编码与案件单位编码
	@Select("select type,code from sys_unit where name=#{name}")
	public Unit selectunit(@Param("name") String name);
	
	//查询案件类型
	@Select("select name from sys_category where code=#{code}")
	public Category selectOranizerType(@Param("code") String code);
	
	//根据财物id查询案件信息
	@Select("select `case`.case_name,`case`.organizer_name,`case`.undertaker_name from `case`,yw_property "+
	"where yw_property.case_id=`case`.id and yw_property.id=#{id}")
	public NewCase selectgetcase(@Param("id") String id);
	
	//绑定案件附件
	@Update("update file set case_id=#{case_id} where exchange_id=#{id}")
	public void putFileCaseId(@Param("case_id") String case_id,@Param("id") String id);
	
	//入库exchange表新增记录
	@Insert("insert into exchange(id,case_id,process_code,node_code,case_name,deleted)"+
	" values(#{id},#{case_id},\"1002\",\"1002002\",#{case_name},0)")
	public void newPutInExchangeInsert(@Param("id") String id,@Param("case_id") String case_id,@Param("case_name") String case_name);
	
	//根据案件id查询案件信息
	@Select("select `case`.case_name,`case`.organizer_name,`case`.create_time from `case` where id=#{id}")
	public NewCase selectCaseInfoById(@Param("id") String id);
	
	
	public List<NewCase> getAllCaseInfo(NewSearchVo newSearchVo);
	
}
