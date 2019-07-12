package com.coolcloud.sacw.photo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.photo.entity.Photo;

public interface PhotoMapper extends BaseMapper<Photo, String> {

	/**
	 * 按多个财物id查询照片记录
	 * 
	 * @param ids
	 *            财物id List
	 * @return
	 */
	List<Photo> selectByProperties(@Param("ids") List<String> ids);

}
