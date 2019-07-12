package com.coolcloud.sacw.cases.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.cases.entity.CaseExample;
import com.coolcloud.sacw.cases.service.CaseService;
import com.coolcloud.sacw.common.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 案件管理控制类
 * 
 * @author 王孝康
 *
 */
@RestController
@RequestMapping("/caseManage/case")
public class CaseController {
    @Autowired
    private CaseService caseService;

    /**
     * 案件管理列表
     * 
     * @return
     */
    @GetMapping("/list")
    public Result caseList(CaseExample caseExample) {
        Integer page = caseExample.getPage();
        Integer rows = caseExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<Case> cases = caseService.getByExample(caseExample);
        PageInfo<Case> pageInfo = new PageInfo<>(cases);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    
    }
    
    
    
    
    //springboot的成功实践和知识的更新速度应该是一

    /**
     * 更新案件信息
     * 
     * @return
     */
    // @PostMapping("/update")
    public Result update(Case caze) {
        int num = caseService.update(caze);
        return Result.success(num + "个案件更新成功！");
    }

    /**
     * 删除案件
     * 
     * @param cases
     * @return
     */
    // @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        int num = caseService.delete(id);
        return Result.success(num + "个案件删除成功！");
    }

    /**
     * 新增案件
     * 
     * @param binder
     */
    // @PostMapping( "/add" )
    @ResponseBody
    public Result addCase(Case cases) {
        Integer num = caseService.save(cases);
        return Result.success(num + "个案件添加成功！");
    }
    
    
    
    /**
     * 根据案件id获取单个案件信息 <br/>
     * 参数： id ： 案件id（非平台案件编号）
     * 
     * @param id
     *            案件id
     * @return 案件信息
     */
    @GetMapping("/get")
    public Object get(String id) {
        Case caze = caseService.get(id);
        return caze;
    }
    
    
    
    @GetMapping("/findOrganizerName")
    public Result findOrganizerName(String OrganizerCode) {
    	 Result result = Result.success();
        String s=caseService.findOrganizerNameByOrganizerCode(OrganizerCode);
        result.add("name", s);
        return result;
    }
    
    
    
    
    
    
   

     
    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
