package com.coolcloud.sacw.car.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.car.entity.CarConserveContent;
import com.coolcloud.sacw.car.service.CarConserveContentService;
import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.common.util.Assert;
import com.github.pagehelper.PageInfo;

/**
 * 养护内容控制类
 * 
 * @author xyz
 *
 * @date 2018年4月11日 上午10:14:27
 */
@RestController
@RequestMapping("/car-conserve/content")
public class CarConserveContentController {

    @Autowired
    private CarConserveContentService carConserveContentService;

    /**
     * 
     * @return
     */
    @GetMapping("/all")
    public Result all() {
        List<CarConserveContent> list = carConserveContentService.all();
        PageInfo<CarConserveContent> pageInfo = new PageInfo<>(list);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 按财物拆分id查询养护内容设置
     * 
     * @param splitId
     *            财物拆分id
     * @return
     */
    @GetMapping("/list")
    public Result list(String splitId) {
        Assert.isTrue(StringUtils.hasText(splitId), "请指定财物拆分id");
        List<CarConserveContent> list = carConserveContentService.getBySplitId(splitId);
        PageInfo<CarConserveContent> pageInfo = new PageInfo<>(list);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 按财物拆分id删除养护内容设置
     * 
     * @param splitId
     *            财物拆分id
     * @return
     */
    @PostMapping("/delete")
    public Result delete(String splitId) {
        Assert.isTrue(StringUtils.hasText(splitId), "请指定财物id");
        carConserveContentService.deleteBySplitId(splitId);
        return Result.success("养护设置删除成功");
    }

    /**
     * 保存养护内容设置
     * 
     * @param splitId
     *            财物拆分id
     * @param conserveContentCodes
     *            逗号分隔的养护内容代码
     * @return
     */
    @PostMapping("/set")
    public Result set(@RequestParam("splitId") String splitId, @RequestParam("conserveContentCodes") String conserveContentCodes) {
    	System.out.println(splitId+"   "+conserveContentCodes);
        String[] codes = conserveContentCodes.split(",");
        carConserveContentService.set(splitId, codes);
        return Result.success("养护内容设置成功");
    }

}
