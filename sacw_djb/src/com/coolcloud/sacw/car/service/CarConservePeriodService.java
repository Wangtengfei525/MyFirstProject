package com.coolcloud.sacw.car.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.car.entity.CarConservePeriod;
import com.coolcloud.sacw.car.mapper.CarConservePeriodMapper;
import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.common.util.UuidUtil;
import com.coolcloud.sacw.system.entity.Category;
import com.coolcloud.sacw.system.service.CategoryService;

/**
 * 
 * @author xyz
 *
 * @date 2018年4月10日 下午4:40:42
 */
@Service
public class CarConservePeriodService extends BaseService<CarConservePeriod, String> {

    private static final String CAR_CONSERVE_TYPE_CODE = "car_conserve_content";

    private static final Integer DEFAULT_ENABLED = Integer.valueOf(1);

    @Autowired
    private CarConservePeriodMapper carConservePeriodMapper;

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有养护周期设置
     * 
     * @param enabledOnly
     *            是否仅查询已启用的设置
     * @return
     */
    @Transactional(readOnly = true)
    public List<CarConservePeriod> getPeriodSettings(boolean enabledOnly) {
        List<CarConservePeriod> periods;
        if (enabledOnly) {
            periods = carConservePeriodMapper.selectEnabledPeriodSettings();
        } else {
            periods = carConservePeriodMapper.selectPeriodSettings();
        }
        return periods;
    }

    /**
     * 按养护内容代码查询养护周期设置
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public List<CarConservePeriod> getPeriodSettings(Collection<String> conserveContentCodes) {
        return carConservePeriodMapper.selectPeriodSettingsByCodes(conserveContentCodes);
    }

    /**
     * 设置养护周期
     * 
     * @param conserveContentCode
     *            养护内容代码
     * @param conservePeriod
     *            养护周期
     */
    @Transactional
    public void set(String conserveContentCode, Integer conservePeriod) {
        Category category = categoryService.getByTypeAndCode(CAR_CONSERVE_TYPE_CODE, conserveContentCode);
        Assert.notNull(category, "未知的养护内容");
        CarConservePeriod period = carConservePeriodMapper.selectByConserveContentCode(conserveContentCode);
        if (period == null) {
            period = new CarConservePeriod();
            period.setConserveContentCode(conserveContentCode);
            period.setConserveContentName(category.getName());
            period.setConservePeriod(conservePeriod);
            period.setEnabled(DEFAULT_ENABLED);
            this.save(period);
        } else {
            period.setConservePeriod(conservePeriod);
            this.update(period);
        }
    }

    /**
     * 批量设置养护内容
     * 
     * @param periods
     */
    @Transactional
    public void set(List<CarConservePeriod> periods) {

        Date today = new Date();
        Set<String> codes = new HashSet<>();
        Category category;
        String code;
        for (CarConservePeriod period : periods) {
            code = period.getConserveContentCode();
            category = categoryService.getByTypeAndCode(CAR_CONSERVE_TYPE_CODE, code);
            Assert.notNull(category, "养护内容代码：" + code + "不存在");

            period.setId(UuidUtil.get32UUID());
         //   period.setCreateTime(today);
          //  period.setUpdateTime(today);
            period.setDeleted(0);
            period.setConserveContentName(category.getName());
            codes.add(period.getConserveContentCode());
        }
        carConservePeriodMapper.deleteByConserveContentCodes(codes);
        carConservePeriodMapper.insertBatch(periods);

    }

    /**
     * 按拆分id获取单独的养护周期设置
     * 
     * @param splitId
     *            财物拆分id
     * @return
     */
    public List<CarConservePeriod> getBySplitId(String splitId) {
        return carConservePeriodMapper.selectBySplitId(splitId);
    }

}
