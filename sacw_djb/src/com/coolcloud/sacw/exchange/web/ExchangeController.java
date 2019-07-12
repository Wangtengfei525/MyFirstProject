package com.coolcloud.sacw.exchange.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.exchange.entity.Exchange;
import com.coolcloud.sacw.exchange.entity.ExchangeExample;
import com.coolcloud.sacw.exchange.service.ExchangeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 交换操作控制类
 * 
 * @author xyz
 *
 */
@RequestMapping("/exchange")
@RestController
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    /**
     * 查询交换记录信息 可用的查询参数： <br/>
     * platCaseCode 平台案件编号 <br/>
     * caseName 案件名称 <br/>
     * caseNameLike 案件名称模糊查询 <br/>
     * exchangeOperationName 交换操作名称 <br/>
     * exchangeOperationCode 交换操作编码 <br/>
     * sendTimeStart 发送时间起始值 <br/>
     * sendTimeEnd 发送时间结束值 <br/>
     * nodeCode 节点编号<br/>
     * page,rows 分页信息，不传分页参数不分页<br/>
     * sort,order 排序信息 ,不传排序参数不排序<br/>
     * 
     * 请求示例：http://localhost:8080/sacwonline/exchange/list?sendTimeStart=2017-06-09&sendTimeEnd=2017-08-18&caseNameLike=抢夺案&sort=sendTime&order=desc&page=1&rows=20
     * 
     * @param exchangeExample
     * @return
     */
    @GetMapping("/list")
    public Result list(ExchangeExample exchangeExample) {
        Integer page = exchangeExample.getPage();
        Integer rows = exchangeExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<Exchange> exchanges = exchangeService.getByExample(exchangeExample);
        PageInfo<Exchange> pageInfo = new PageInfo<>(exchanges);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 按财物id查询相关交换记录
     * 
     * @param exchangeExample
     * @return
     */
    @GetMapping("/list/by-property")
    public Result listByProperty(@RequestParam("propertyId") String propertyId) {
        List<Exchange> exchanges = exchangeService.getByPropertyId(propertyId);
        PageInfo<Exchange> pageInfo = new PageInfo<>(exchanges);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 修改交换记录
     * 
     * @param exchange
     * @return
     */
    // @PostMapping("/update")
    public Result update(Exchange exchange) {
        Integer num = exchangeService.update(exchange);
        return Result.success(num + "条记录修改成功！");
    }

    /**
     * 删除交换记录
     * 
     * @param id
     *            交换记录Id
     * @return
     */
    // @PostMapping("/delete")
    public Result delete(String id) {
        Integer num = exchangeService.delete(id);
        return Result.success(num + "条记录删除成功！");

    }

    /**
     * 获取交换记录详情
     * 
     * @param id
     *            交换记录Id
     * @return
     */
    @GetMapping("/info")
    public Object info(String id) {
        Map<String, Object> info = exchangeService.info(id);
        return info;
    }
    
    //这个是借调页面的双击方法  其他页面不做使用
    @GetMapping("/findSecondInfo")
    public Object findSecondInfo(String id,String propertyStatusCode) {
        Map<String, Object> info = exchangeService.findInfoBySecondCondition(id,propertyStatusCode);
        return info;
    }
    
    
    //这个是归还页面点击查看详情的方法  其他页面不做使用
    @GetMapping("/findBackInfo")
    public Object findBackInfo(String id,String propertyStatusCode) {
        Map<String, Object> info = exchangeService.findInfoByBackCondition(id,propertyStatusCode);
  
        return info;
    }
    
    
    
    
    //这个是出库页面用的点击查看详情的方法  其他页面不做使用
    @GetMapping("/findOutInfo")
    public Object findOutInfo(String id,String propertyStatusCode) {
        Map<String, Object> info = exchangeService.findInfoByOutCondition(id,propertyStatusCode);
        return info;
    }
    
    
    
    
    /**
     * 获取交换记录详情
     * 
     * @param id
     *            交换记录Id
     * @return
     */
    @GetMapping("/newinfo")
    public Object newinfo(String id) {
        Map<String, Object> info = exchangeService.getinfo(id);
        return info;
    }

    /**
     * 获取交换记录信息
     * 
     * @param id
     *            交换记录Id
     * @return
     */
    @GetMapping("/get")
    public Result get(String id) {
        Exchange exchange = exchangeService.get(id);
        return Result.success().add("exchange", exchange);
    }
    

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
