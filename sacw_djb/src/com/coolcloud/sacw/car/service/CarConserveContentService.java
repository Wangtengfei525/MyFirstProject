package com.coolcloud.sacw.car.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.car.entity.CarConserveContent;
import com.coolcloud.sacw.car.mapper.CarConserveContentMapper;
import com.coolcloud.sacw.car.mapper.NewCarConserveMapper;
import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.property.entity.PropertySplit;
import com.coolcloud.sacw.property.service.PropertySplitService;
import com.coolcloud.sacw.system.entity.Category;
import com.coolcloud.sacw.system.service.CategoryService;

/**
 * @author xyz
 *
 * @date 2018年4月11日 上午9:56:35
 */
@Service
public class CarConserveContentService extends BaseService<CarConserveContent, String> {

    private static final String CAR_CONSERVE_TYPE_CODE = "car_conserve_content";

    @Autowired
    private CarConserveContentMapper carConserveContentMapper;

    @Autowired
    private PropertySplitService propertySplitService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    public NewCarConserveMapper newCarConserveMappper;

    @Transactional(readOnly = true)
    public List<CarConserveContent> all() {
        return carConserveContentMapper.selectDistinctProperties();
    }

    @Transactional
    public int deleteBySplitId(String splitId) {
        return carConserveContentMapper.deleteBySplitId(splitId);
    }

    /**
     * 设置养护内容
     * 
     * @param propertyId
     * @param codes
     */
    @Transactional
    public void set(String splitId, String[] codes) {
        //PropertySplit split = propertySplitService.get(splitId);
        //Assert.notNull(split, "拆分财物不存在");

    	/*
        Set<String> sets = new HashSet<>(Arrays.asList(codes));
        Category category;
        CarConserveContent content;
        carConserveContentMapper.deleteBySplitId(splitId);
        for (String code : sets) {
            category = categoryService.getByTypeAndCode(CAR_CONSERVE_TYPE_CODE, code);
            Assert.notNull(category, "养护内容" + code + "不存在");

            content = new CarConserveContent();
            content.setSplitId(splitId);
            content.setConserveContentCode(category.getCode());

            this.save(content);
        }
        */
    	for(int i=0;i<codes.length;i++) {
    		newCarConserveMappper.newInsertCarConserveContent(splitId, codes[i]);
    	}

    }

    /**
     * 按财物拆分id计数
     * 
     * @param splitId
     *            财物拆分id
     * @return
     */
    @Transactional(readOnly = true)
    public int countBySplitId(String splitId) {
        return carConserveContentMapper.countBySplitId(splitId);
    }

    /**
     * 按财物拆分id查询养护内容
     * 
     * @param splitId
     * @return
     */
    @Transactional(readOnly = true)
    public List<CarConserveContent> getBySplitId(String splitId) {
        return carConserveContentMapper.selectBySplitId(splitId);
    }

}
