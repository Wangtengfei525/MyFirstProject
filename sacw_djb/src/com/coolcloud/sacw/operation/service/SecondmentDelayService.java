package com.coolcloud.sacw.operation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.operation.entity.SecondmentDelay;
import com.coolcloud.sacw.operation.mapper.SecondmentDelayMapper;

/**
 * 借调逾期服务类
 * 
 * @author xyz
 *
 */
@Service
public class SecondmentDelayService extends BaseService<SecondmentDelay, String> {

    @Autowired
    private SecondmentDelayMapper secondmentDelayMapper;
  
    /**
     * 获取借调物品的延期记录
     * 
     * @param secondmentId
     *            借调记录id
     * @return 延期记录
     */
    @Transactional(readOnly = true)
    public List<SecondmentDelay> getBySecondmentId(String secondmentId) {
        return secondmentDelayMapper.selectBySecondmentId(secondmentId);
    }

}
