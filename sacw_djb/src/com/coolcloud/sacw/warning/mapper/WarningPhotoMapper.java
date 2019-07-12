package com.coolcloud.sacw.warning.mapper;

import java.util.List;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.warning.entity.WarningPhoto;

public interface WarningPhotoMapper extends BaseMapper<WarningPhoto, String> {

    /**
     * 按报警记录id查询照片信息
     * 
     * @param warningId
     *            报警记录id
     * @return
     */
    List<WarningPhoto> selectByByWarningId(String warningId);

}