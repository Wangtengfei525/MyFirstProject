package com.coolcloud.sacw.property.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.common.util.PaginationUtil;
import com.coolcloud.sacw.property.entity.MotorSearchParam;
import com.coolcloud.sacw.property.entity.NewPropertySplit;
import com.coolcloud.sacw.property.entity.PropertySplit;
import com.coolcloud.sacw.property.entity.PropertySplitAddForm;
import com.coolcloud.sacw.property.entity.PropertySplitExample;
import com.coolcloud.sacw.property.entity.PropertySplitUpdateForm;
import com.coolcloud.sacw.property.service.PropertySplitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 财物拆分服务类
 * 
 * @author 袁永祥
 *
 * @date 2017年12月13日 下午7:07:43
 */
@RestController
@RequestMapping("/property/split")
public class PropertySplitController {

    @Autowired
    private PropertySplitService propertySplitService;

    /**
     * 查询拆分后信息
     * 
     * @param id
     *            id
     * @return
     */
    @GetMapping("/get")
    public Result add(String id) {
        PropertySplit split = propertySplitService.get(id);
        return Result.success().add("split", split);
    }

    /**
     * 查询有单独养护设置的拆分财物信息
     * 
     * @param
     * @return
     */
    @GetMapping("/list/has-own-contents")
    public Result list() {
        //List<PropertySplit> splits = propertySplitService.getSplitHasOwnContents();
        List<PropertySplit> splits = propertySplitService.getSplitHasOwnContents();
        PageInfo<PropertySplit> pageInfo = new PageInfo<>(splits);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 查询财物拆分信息
     * 
     * @param example
     * @return
     */
    @GetMapping("/list")
    public Result list(PropertySplitExample example) {
        Integer page = example.getPage();
        Integer rows = example.getRows();
        PageHelper.startPage(page == null ? 1 : page, rows == null ? 10 : rows);
        List<PropertySplit> splits = propertySplitService.getByExample(example);
        PageInfo<PropertySplit> pageInfo = new PageInfo<>(splits);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 查询拆分后的机动车信息
     * 
     * @return
     */
    @GetMapping("/list/motor")
    public Result listMotor(@Validated MotorSearchParam param) {
        PaginationUtil.startPageIfNeed(param);
        //List<PropertySplit> splits = propertySplitService.getMotorByParam(param);
        List<NewPropertySplit> splits = propertySplitService.newgetMotorByParam(param);
        //PageInfo<PropertySplit> pageInfo = new PageInfo<>(splits);
        PageInfo<NewPropertySplit> pageInfo = new PageInfo<>(splits);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 新增财物拆分
     * 
     * @param form
     *            财物拆分表单对象
     * @return
     */
    @PostMapping("/add")
    public Result add(@Validated PropertySplitAddForm form) {
        PropertySplit split = new PropertySplit();
        BeanUtils.copyProperties(form, split);
        int num = propertySplitService.add(split);
        return Result.success(num + "个拆分记录已添加");
    }

    /**
     * 更新拆分信息
     * 
     * @param propertySplit
     * @return
     */
    @PostMapping("/update")
    public Result Result(@Validated PropertySplitUpdateForm form) {
        PropertySplit split = new PropertySplit();
        BeanUtils.copyProperties(form, split);
        int num = propertySplitService.update(split);
        return Result.success(num + "个拆分记录更新成功");
    }

    /**
     * 删除拆分信息
     * 
     * @param propertySplit
     * @return
     */
    @PostMapping("/delete")
    public Result delete(String id) {
        Integer num = propertySplitService.delete(id);
        return Result.success(num + "个拆分财物删除成功");
    }

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
