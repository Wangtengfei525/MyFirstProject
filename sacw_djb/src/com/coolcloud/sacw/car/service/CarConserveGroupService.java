package com.coolcloud.sacw.car.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.car.entity.CarConserve;
import com.coolcloud.sacw.car.entity.CarConserveGroup;
import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;

/**
 * 养护组服务类
 * 
 * @author xyz
 *
 * @date 2018年4月13日 上午11:40:57
 */
@Service
public class CarConserveGroupService extends BaseService<CarConserveGroup, String> {

    @Autowired
    private CarConserveService carConserveService;

    @Autowired
    private CarConserveHistoryService carConserveHistoryService;

    /**
     * 覆写默认删除方法，同步删除养护及历史记录
     */
    @Override
    @Transactional
    public int delete(String id) {
        carConserveHistoryService.deleteByGroupId(id);
        carConserveService.deleteByGoupId(id);
        return super.delete(id);
    }

    /**
     * 完成养护
     * 
     * @param id
     *            养护组id
     * @param conserves
     *            养护内容
     */
    public int complete(CarConserveGroup group) {
        String id = group.getId();
        Assert.isTrue(exists(id), "养护组不存在");

        // 养护组下所有养护记录
        List<String> ids = carConserveService.getIdByGroupId(id);
        String conserveId;
        int count = 0;
        for (CarConserve conserve : group.getConserves()) {
            conserveId = conserve.getId();
            if (!ids.contains(conserveId)) {
                continue;
            }

            count++;
            conserve.setStatus("0");
            conserve.setExchangeKey("1");
            carConserveService.update(conserve);
        }

        // 全部养护完成后设置completed为1
        int completed = carConserveService.countCompletedByGroupId(id);
        if (completed == ids.size()) {
            group.setCompleted(1);
            update(group);
        }

        return count;

    }

}
