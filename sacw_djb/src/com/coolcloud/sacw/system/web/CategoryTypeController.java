package com.coolcloud.sacw.system.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.system.entity.CategoryType;
import com.coolcloud.sacw.system.service.CategoryTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 分类代码控制类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月24日 上午10:30:56
 */
@RestController
@RequestMapping("/system/category-type")
public class CategoryTypeController {

    @Autowired
    private CategoryTypeService categoryTypeService;

    /**
     * 获取分类类别列表
     *
     * @return
     */
    @GetMapping("/list")
    public Result list() {
        PageInfo<CategoryType> pageInfo = new PageInfo<>(categoryTypeService.getAll());
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 获取分类类别
     * 
     * @param id
     *            id
     * @return
     */
    @GetMapping("/get")
    public Result get(String id) {
        CategoryType type = categoryTypeService.get(id);
        return Result.success().add("type", type);
    }

    /**
     * 添加
     * 
     * @param id
     *            id
     * @return
     */
    @PostMapping("/add")
    public Result add(CategoryType type) {
        type.setDeleted(0);
        Integer num = categoryTypeService.save(type);
        return Result.success(num + "个分类添加成功");
    }

    /**
     * 删除
     * 
     * @param id
     *            id
     * @return
     */
    @PostMapping("/delete")
    public Result delete(String id) {
        Integer num = categoryTypeService.delete(id);
        return Result.success(num + "个分类删除成功");
    }

    /**
     * 更新
     * 
     * @param type
     * @return
     */
    @PostMapping("/update")
    public Result update(CategoryType type) {
        Integer num = categoryTypeService.update(type);
        return Result.success(num + "个分类更新成功");
    }

}
