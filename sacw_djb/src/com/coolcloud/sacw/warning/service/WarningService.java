package com.coolcloud.sacw.warning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.warning.entity.Warning;
import com.coolcloud.sacw.warning.mapper.WarningMapper;

/**
 * 报警记录服务类
 * 
 * @author xyz
 *
 * @date 2018年4月17日 上午11:10:15
 */
@Service
public class WarningService extends BaseService<Warning, String> {

    @Autowired
    private WarningMapper warningMapper;

    @Transactional(readOnly = true)
    public int getWarningCount() {
        return warningMapper.countByHandled(0);
    }

    /**
     * 处理所有异常出库信息
     * 
     * @param remark
     *            备注
     * 
     * @return
     */
    @Transactional
    public int handleAll(String remark) {
        return warningMapper.updateHandled(1, remark);
    }

}
