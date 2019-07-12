package com.coolcloud.sacw.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.system.entity.Category;
import com.coolcloud.sacw.system.entity.CategoryExample;
import com.coolcloud.sacw.system.entity.CategoryType;
import com.coolcloud.sacw.system.mapper.CategoryMapper;

/**
 * 分类代码服务类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月24日 上午10:29:52
 */

@Service
public class CategoryService extends BaseService<Category, String> {

    @Autowired
    private CategoryTypeService categoryTypeService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public int save(Category category) {
        String typeId = category.getTypeId();
        if (StringUtils.isEmpty(typeId)) {
            return Integer.valueOf(0);
        }
        CategoryType type = categoryTypeService.get(typeId);
        if (type == null) {
            return Integer.valueOf(0);
        }
        category.setTypeId(typeId);
        category.setTypeCode(type.getCode());
        return super.save(category);
    }

    /**
     * 获取树形结构
     * 
     * @param categoryExample
     * @return
     */
    @Transactional(readOnly = true)
    public List<Category> getTreeByExample(CategoryExample example) {
        List<Category> categories = categoryMapper.selectByExample(example);
        List<Category> tree = new ArrayList<>();
        for (Category category : categories) {
            if (StringUtils.isEmpty(category.getParentId())) {
                tree.add(category);
            }
            for (Category c : categories) {
                String parentId = c.getParentId();
                if (category.getId().equals(parentId)) {
                    category.getChildren().add(c);
                }
            }
        }
        return tree;
    }

    @Transactional(readOnly = true)
    public Category getByTypeAndCode(String type, String code) {
        return categoryMapper.selectByTypeAndCode(type, code);
    }
}
