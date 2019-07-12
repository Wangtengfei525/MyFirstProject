package com.coolcloud.sacw.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.store.entity.Library;

public interface LibraryMapper extends BaseMapper<Library, String>{

	/**
	 * 删除盘库数据表数据
	 */
	void del();

	/**
	 * 根据柜子ID查询异常财物信息数据
	 * @param chests
	 * @return
	 */
	List<Object> selectEx(@Param("stores") List<String> chests);
	

}
