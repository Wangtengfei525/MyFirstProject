package com.coolcloud.sacw.person.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.person.entity.Person;
import com.coolcloud.sacw.person.entity.PersonExample;
import com.coolcloud.sacw.person.service.PersonService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 人员控制类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月22日 下午8:32:32
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * 获取人员信息 <br/>
     * 
     * 可选参数：<br/>
     * caseId：案件id <br/>
     * personName ：人员姓名 <br/>
     * platCaseCode ：平台案件编号 <br/>
     * platPersonCode ：平台人员编号 <br/>
     * page,rows 分页信息，不传分页参数不分页<br/>
     * sort,order 排序信息 ,不传排序参数不排序<br/>
     * 
     * @param personExample
     * @return
     */
    @GetMapping("/list")
    public Object list(PersonExample personExample) {
        Integer page = personExample.getPage();
        Integer rows = personExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<Person> exchanges = personService.getByExample(personExample);
        PageInfo<Person> pageInfo = new PageInfo<>(exchanges);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

}
