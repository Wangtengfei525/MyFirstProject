package com.coolcloud.sacw.photo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.photo.entity.Photo;
import com.coolcloud.sacw.photo.mapper.PhotoMapper;

/**
 * 照片service类
 * 
 * @author 王孝康
 *
 */
@Service
public class PhotoService extends BaseService<Photo, String> {

	@Autowired
	private PhotoMapper photoMapper;

	/**
	 * 批量获取照片数据
	 * 
	 * @param ids
	 *            财物id List
	 * @return 照片信息
	 */
	public List<Photo> getByProperties(List<String> ids) {
		return photoMapper.selectByProperties(ids);
	}

}
