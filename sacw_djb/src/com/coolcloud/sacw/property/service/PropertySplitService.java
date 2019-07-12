package com.coolcloud.sacw.property.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.common.util.UuidUtil;
import com.coolcloud.sacw.operation.entity.SaveLocationHistotry;
import com.coolcloud.sacw.operation.service.SaveLocationHistotryService;
import com.coolcloud.sacw.property.entity.MotorSearchParam;
import com.coolcloud.sacw.property.entity.NewPropertySplit;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.entity.PropertySplit;
import com.coolcloud.sacw.property.mapper.PropertySplitMapper;
import com.coolcloud.sacw.system.entity.Category;
import com.coolcloud.sacw.system.service.CategoryService;

/**
 * 财物拆分服务类
 * 
 * @author 袁永祥
 *
 * @date 2017年12月13日 下午7:06:34
 */
@Service
public class PropertySplitService extends BaseService<PropertySplit, String> {

    private static final String PROPERTY_SPLIT_TYPE_CODE = "9000001";

    @Autowired
    private PropertySplitMapper propertySplitMapper;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SaveLocationHistotryService saveLocationHistotryService;

    /**
     * 添加拆分信息
     * 
     * @param split
     *            财物拆分信息
     * @return 新增的拆分数
     */
    @Transactional
    public int add(PropertySplit split) {
        String propertyId = split.getPropertyId();

        Property property = propertyService.get(propertyId);
        Assert.notNull(property, "财物不存在");

        Category category = categoryService.getByTypeAndCode(PROPERTY_SPLIT_TYPE_CODE, split.getSplitTypeCode());
        Assert.notNull(category, "拆分类型不存在");

        split.setSplitTypeName(category.getName());
        split.setCaseId(property.getCaseId());
        //删除原存储记录
        SaveLocationHistotry saveLocationHistotry = new SaveLocationHistotry();
        saveLocationHistotry.setProperty_id(propertyId);
        List<SaveLocationHistotry> listSaveLocationHistotry = saveLocationHistotryService.getByExample(saveLocationHistotry);
        if(listSaveLocationHistotry.size() != 0) {
        	 saveLocationHistotryService.delete(listSaveLocationHistotry.get(0).getId());
        }
        split.setId(UuidUtil.get32UUID());
        //新增存储记录
        saveLocationHistotry.setCase_id(property.getCaseId());
       // saveLocationHistotry.setCase_name(property.getCaseName());
        saveLocationHistotry.setDeleted(0);
        saveLocationHistotry.setId(UuidUtil.get32UUID());
        saveLocationHistotry.setKwbm(split.getSplitSaveLocationCode());
        saveLocationHistotry.setNumber(split.getSplitVolume()==null?0:split.getSplitVolume().intValue());
        //saveLocationHistotry.setPermit_unit_mc(property.getPermitUnitMc()==null?"":property.getPermitUnitMc());
        saveLocationHistotry.setProperty_id(propertyId);
        saveLocationHistotry.setProperty_name(split.getSplitName());
        saveLocationHistotry.setProperty_split_id(split.getId());
        saveLocationHistotry.setProperty_status("已入库");
        saveLocationHistotry.setProperty_type(property.getPropertyType()==null?"":property.getPropertyType());
       // saveLocationHistotry.setRemake_number(property.getRemake_number()==null?0:property.getRemake_number());
        saveLocationHistotryService.save(saveLocationHistotry);
        // 更新原财物为已拆分
        Property p = new Property();
        p.setId(split.getPropertyId());
       // p.setSplited(Integer.valueOf(1));
        propertyService.update(p);

        // 插入拆分记录
        int i = save(split);
        return i;
    }

    /**
     * 复写删除方法
     */

    @Override
    public int delete(String id) {

        PropertySplit split = this.get(id);
        if (split == null) {
            return new Integer(0);
        }
        // 删除信息
        Integer num = super.delete(id);

        // 更新原财物信息
        propertyService.updateSplitedById(split.getPropertyId());

        return num;
    }

    @Override
    @Transactional
    public int update(PropertySplit split) {

        Category category = categoryService.getByTypeAndCode(PROPERTY_SPLIT_TYPE_CODE, split.getSplitTypeCode());
        Assert.notNull(category, "拆分类型不存在");
        split.setSplitTypeName(category.getName());

        SaveLocationHistotry saveLocationHistotry = new SaveLocationHistotry();
        saveLocationHistotry.setProperty_split_id(split.getId());
        List<SaveLocationHistotry> lists = saveLocationHistotryService.getByExample(saveLocationHistotry);
        if(lists.size() != 0) {
        	saveLocationHistotry.setKwbm(split.getSplitSaveLocationCode());
            saveLocationHistotryService.update(saveLocationHistotry);
        }
        return super.update(split);
    }

    /**
     * 按财物id获取拆分信息
     * 
     * @param propertId
     *            财物id
     * @return
     */
    @Transactional(readOnly = true)
    public List<PropertySplit> getByPropertyId(String propertId) {
        return propertySplitMapper.selectByPropertyId(propertId);
    }

    /**
     * 获取有单独养护内容设置的拆分财物
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public List<PropertySplit> getSplitHasOwnContents() {
        return propertySplitMapper.selectSplitHasOwnContents();
    }

    /**
     * 获取拆分后机动车
     * 
     * @param param
     *            查询参数
     * @return
     */
    @Transactional(readOnly = true)
    public List<PropertySplit> getMotorByParam(MotorSearchParam param) {
        return propertySplitMapper.selectMotorByParam(param);
    }
    
    
    @Transactional(readOnly = true)
    public List<NewPropertySplit> newgetMotorByParam(MotorSearchParam param) {
        return propertySplitMapper.newselectMotorByParam(param);
    }
}
