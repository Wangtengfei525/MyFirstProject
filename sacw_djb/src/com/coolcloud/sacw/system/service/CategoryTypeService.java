package com.coolcloud.sacw.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.system.entity.CategoryExample;
import com.coolcloud.sacw.system.entity.CategoryType;

/**
 * 分类代码服务类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月24日 上午10:29:52
 */

@Service
public class CategoryTypeService extends BaseService<CategoryType, String> {

    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    public int delete(String id) {
        if (StringUtils.isEmpty(id)) {
            return Integer.valueOf(0);
        }
        CategoryExample example = new CategoryExample();
        example.setTypeId(id);
        if (categoryService.getByExample(example).size() > 0) {
            return 0;
        }
        return super.delete(id);
    }

}
