package com.coolcloud.sacw.system.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.system.entity.Category;
import com.coolcloud.sacw.system.entity.CategoryExample;
import com.coolcloud.sacw.system.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 分类代码控制类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月24日 上午10:30:56
 */
@RestController
@RequestMapping("/system/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类代码列表 <br/>
     * 可选参数：<br/>
     * typeId : 类型id <br/>
     * typeCode : 类型代码 <br/>
     * 
     * 示例：http://localhost:8080/sacw_online/system/category/list?typeCode=AJLB
     * 
     * @param categoryExample
     * @return
     */
    @RequestMapping(value = "/list")
    public Result list(CategoryExample categoryExample) {
        Integer page = categoryExample.getPage();
        Integer rows = categoryExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<Category> dicts = categoryService.getByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(dicts);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 分类代码列表 <br/>
     * 可选参数：<br/>
     * typeId : 类型id <br/>
     * typeCode : 类型代码 <br/>
     * 不支持分页 <br/>
     * 
     * 示例：http://localhost:8080/sacw_online/system/category/list?typeCode=AJLB
     * 
     * @param categoryExample
     * @return
     */
    @RequestMapping(value = "/tree")
    public Object tree(CategoryExample categoryExample) {
        // List<Category> tree = categoryService.getTreeByExample(categoryExample);
        // PageInfo<Category> pageInfo = new PageInfo<>(tree);
        // return ResultUtils.listResult(pageInfo.getTotal(), pageInfo.getList());
        return categoryService.getTreeByExample(categoryExample);
    }

    /**
     * 获取字典数据
     * 
     * @param id
     *            字典数据id
     * @return
     */
    @GetMapping(value = "/get")
    public Result get(String id) {
        Category category = categoryService.get(id);
        return Result.success().add("category", category);
    }

    /**
     * 添加字典数据
     * 
     * @param id
     *            字典数据id
     * @return
     */
    @PostMapping(value = "/add")
    public Result add(Category category) {
        Integer num = categoryService.save(category);
        return Result.success(num + "个字典添加成功");
    }

    /**
     * 更新字典数据
     * 
     * @param category
     * @return
     */
    @PostMapping(value = "/update")
    public Result update(Category category) {
        // 不允许更新类型
        category.setTypeId(null);
        category.setTypeCode(null);
        Integer num = categoryService.update(category);
        return Result.success(num + "个字典更新成功");
    }

    /**
     * 删除字典项
     * 
     * @param id
     * @return
     */
    @PostMapping(value = "/delete")
    public Result delete(String id) {
        Integer num = categoryService.delete(id);
        return Result.success(num + "个字典删除成功");
    }

}
