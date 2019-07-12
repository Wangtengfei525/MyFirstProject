package com.coolcloud.sacw.warning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.warning.entity.WarningPhoto;
import com.coolcloud.sacw.warning.mapper.WarningPhotoMapper;

/**
 * 报警照片服务类
 * 
 * @author xyz
 *
 * @date 2018年4月17日 上午11:09:26
 */
@Service
public class WarningPhotoService extends BaseService<WarningPhoto, String> {

    @Autowired
    private WarningPhotoMapper warningPhotoMapper;

    /**
     * 由报警记录id查询照片信息
     * 
     * @param warningId
     *            报警记录id
     * @return
     */
    @Transactional(readOnly = true)
    public List<WarningPhoto> getByByWarningId(String warningId) {
        return warningPhotoMapper.selectByByWarningId(warningId);
    }

}
