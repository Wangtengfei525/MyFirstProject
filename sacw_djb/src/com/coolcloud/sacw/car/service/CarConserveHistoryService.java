package com.coolcloud.sacw.car.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.car.entity.CarConserveHistory;
import com.coolcloud.sacw.car.mapper.CarConserveHistoryMapper;
import com.coolcloud.sacw.common.BaseService;

/**
 * 车辆养护历史
 * 
 * @author xyz
 *
 * @date 2018年4月12日 下午4:13:13
 */
@Service
public class CarConserveHistoryService extends BaseService<CarConserveHistory, String> {

    @Autowired
    private CarConserveHistoryMapper carConserveHistoryMapper;

    /**
     * 获取车辆最新养护历史
     * 
     * @param propertyId
     *            财物id
     * @return 最新养护历史
     */
    
    //应用范围   一次执行多次查询来统计某些信息  这事为了保证数据的整体的一致性，要使用只读事务
    @Transactional(readOnly = true)//设置只读事物     注意是一次执行多次查询来统计某些信息，这时为了保证数据整体的一致性，要用只读事务
    public List<CarConserveHistory> getLatestConserveByPropertyId(String propertyId) {
        return carConserveHistoryMapper.selectLatestConserveByPropertyId(propertyId);
    }

    /**
     * 按养护id删除
     * 
     * @param conserveId
     *            养护记录id
     * @return
     */
    
    //使用如下注解配置，抛出异常之后，事务会自动回滚，所做的操所不会影响数据库。
    @Transactional
    public int deleteByConserveId(String conserveId) {
        return carConserveHistoryMapper.deleteByConserveId(conserveId);
    }

    /**
     * 按养护组删除
     * 
     * @param conserveId
     *            养护记录id
     * @return
     */
    @Transactional
    public int deleteByGroupId(String groupId) {
        return carConserveHistoryMapper.deleteByGroupId(groupId);
    }

    @Transactional
    public void updatePropertyId() {
        carConserveHistoryMapper.updatePropertyId();

    }

    /**
     * 获取车辆最新养护记录
     * 
     * @param splitId
     *            财物拆分id
     * @return
     */
    @Transactional(readOnly = true)
    public List<CarConserveHistory> getLatestConserveBySplitId(String splitId) {
        return carConserveHistoryMapper.selectLatestConserveBySplitId(splitId);
    }
}
