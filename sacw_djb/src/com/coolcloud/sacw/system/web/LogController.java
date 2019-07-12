package com.coolcloud.sacw.system.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.system.entity.Log;
import com.coolcloud.sacw.system.entity.LogExample;
import com.coolcloud.sacw.system.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 日志控制类
 * 
 * @author xyz
 *
 */

@RestController
@RequestMapping("/system/log")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 查询日志
     * 
     * @param logExample
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Result list(LogExample logExample) {
        Integer page = logExample.getPage();
        Integer rows = logExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<Log> logs = logService.getByExample(logExample);
        PageInfo<Log> pageInfo = new PageInfo<>(logs);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 删除日志
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(String id) {
        Integer num = logService.delete(id);
        return Result.success(num + "条日志已删除！");
    }

    /**
     * 删除所有日志
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/all")
    @ResponseBody
    public Result deleteAll() {
        int num = logService.deleteAll();
        return Result.success(num + "条日志已删除！");
    }

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
