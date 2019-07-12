package com.coolcloud.sacw.operation.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.cases.entity.NewCase;
import com.coolcloud.sacw.cases.service.CaseService;
import com.coolcloud.sacw.cases.service.NewCaseService;
import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.exchange.entity.Exchange;
import com.coolcloud.sacw.exchange.service.ExchangeService;
import com.coolcloud.sacw.operation.entity.NewCaseProperty;
import com.coolcloud.sacw.operation.entity.NewSearchVo;
import com.coolcloud.sacw.operation.entity.PutinVo;
import com.coolcloud.sacw.operation.entity.SearchVo;
import com.coolcloud.sacw.operation.service.PutInService;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.service.PropertyService;
import com.github.pagehelper.PageInfo;

/**
 * 入库操作控制类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月17日 下午3:03:44
 */
@RestController
@RequestMapping("/put-in")
public class PutInController {

    @Autowired
    private PutInService putInService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private CaseService caseService;
    
    @Autowired
    public NewCaseService newCaseService;

    /**
     * 查询入库交换记录 <br/>
     * 参数 : <br/>
     * status : 0未入库,1已入库,2已退回 <br/>
     * caseName : 案件名称<br/>
     * sendUnitId : 发送单位id<br/>
     * startTime : 开始时间<br/>
     * endTime : 结束时间<br/>
     * 
     * @return
     */
    @GetMapping("/putin-list")
    public Result putinList(SearchVo search) {
    	/*if(search.getComposite()!=null&&search.getComposite()!="")
    	{
    		 try {
    			 search.setComposite(new String(search.getComposite().getBytes("ISO8859-1"), "UTF-8"));
         	} catch (UnsupportedEncodingException e) {
         		e.printStackTrace();
         	}
    	}*/
    	
    	Commons c=new Commons();
    	String  composite=c.SolveIllegalCharacter(search.getComposite());
    	search.setComposite(composite);
    	
         
         
        List<Exchange> list = putInService.getPutinList(search);
        PageInfo<Exchange> info = new PageInfo<>(list);
        return Result.success().total(info.getTotal()).rows(info.getList());
    }
    
    
    /**
     * 
     * write by yiyu
     * 获取财物未入库案件的信息
     * 
     * @param search
     * @return
     */
    @GetMapping("/newputin-list")
    public Result newputinList(NewSearchVo search) {
    	List<NewCaseProperty> list=putInService.getNewPutinList(search);
    	PageInfo<NewCaseProperty> info=new PageInfo<>(list);
    	return Result.success().total(info.getTotal()).rows(info.getList());
    }
    
    /**
     * 获取所有案件信息
     * @param searchVo
     * @return
     */
    @GetMapping("/getallcaselist")
    public Result getAllCaseList(NewSearchVo searchVo) {
    	List<NewCase> list=putInService.getAllCaseList(searchVo);
    	PageInfo<NewCase> info=new PageInfo<>(list);
    	return Result.success().total(info.getTotal()).rows(info.getList());
    }

    /**
     * 财物入库<br/>
     * 参数：<br/>
     * <ul>
     * <li>id：交换记录id</li>
     * <li>tempId：临时交换记录id，用于查找先前已上传附件</li>
     * <li>remark：备注</li>
     * <li>opreationTime：入库时间</li>
     * </ul>
     * 
     * @param exchange
     * @return
     */
   /* @RequestMapping(value = "/put-in-storage", method = RequestMethod.POST)
    public Result putInStorage(PutinVo putin) {
        Integer num = putInService.putInStorage(putin);
        return Result.success(num + "件财物入库成功！");
    }*/
    
    @RequestMapping(value = "/put-in-storage", method = RequestMethod.POST)
    public Result putInStorage(PutinVo putin) {
        Integer num = putInService.newPutIn(putin);
        return Result.success(num + "件财物入库成功！");
    }
    
    

    /**
     * 退回入库申请 <br/>
     * 请求参数：<br/>
     * id：交换记录id <br/>
     * remark：备注<br/>
     * 
     * @param exchange
     * @return
     */
    @RequestMapping("/send-back")
    public Result sendBack(Exchange exchange) {
        putInService.sendBack(exchange);
        return Result.success("入库申请退回成功");
    }

    /**
     * 打印清单<br/>
     * 参数：<br/>
     * <ul>
     * <li>id：交换记录id</li>
     * </ul>
     * 
     * @param id
     *            交换记录id
     * @return
     */
    @RequestMapping("/print-detail")
    public ModelAndView printDetail(String id) {
        ModelAndView mav = new ModelAndView("putin/print_detail");
        Exchange exchange = exchangeService.get(id);
        Case oCase = caseService.get(exchange.getCaseId());
        List<Property> properties = propertyService.getByExchangeId(id);
        mav.addObject("exchange", exchange);
        mav.addObject("case", oCase);
        mav.addObject("date", new Date());
        mav.addObject("properties", properties);
        return mav;
    }

    /**
     * 
     * 单机版
     * 
     * @param idlist 财物id
     * 
     * @return
     */
    @RequestMapping("/newprint-detail")
    public ModelAndView newPrintDetail(String idlist) {
    	//propertyIds是财物的主键组成起来的一个字符串  中间用逗号隔开
        ModelAndView mav = new ModelAndView("putin/print_detail");
        List<Property> properties=new ArrayList<Property>();
        List<Case> oCase=new ArrayList<Case>();
        System.out.println(idlist);
        if(!idlist.contains(","))
        {
        	//说明只有一个财物
        	Property   property=propertyService.get(idlist);
        	Case  case1=caseService.findCaseByPropertyId(idlist);
        	property.setCaseName(case1.getCaseName());
        	properties.add(property);
        	oCase.add(case1);
        	mav.addObject("case", oCase.get(0));
            mav.addObject("date", new Date());
            mav.addObject("properties", properties);
        }
        else {
        	String  id[]=idlist.split(",");
        	for(int i=0;i<id.length;i++)
        	{
        		Property   property=propertyService.get(id[i]);
            	Case c=caseService.findCaseByPropertyId(id[i]);
            	property.setCaseName(c.getCaseName());
            	properties.add(property);
            	oCase.add(c);	
        	}
        	mav.addObject("case", oCase.get(0));
            mav.addObject("date", new Date());
            mav.addObject("properties", properties);	
        }
      /*  Exchange exchange = exchangeService.get(id);
        Case oCase = caseService.get(exchange.getCaseId());
        List<Property> properties = propertyService.getByExchangeId(id);
        mav.addObject("exchange", exchange);
        mav.addObject("case", oCase);
        mav.addObject("date", new Date());
        mav.addObject("properties", properties);*/
        return mav;
    }

    /**
     * 打印回执单<br/>
     * 参数：<br/>
     * <ul>
     * <li>id：交换记录id</li>
     * </ul>
     * 
     * @param id
     *            交换记录id
     * @return
     */
    @RequestMapping("/print-receipt")
    public ModelAndView printReceipt(String idlist) {
    	System.out.println(idlist);
        ModelAndView mav = new ModelAndView("putin/print_receipt");
        List<Property> properties=new ArrayList<Property>();
        List<Case> oCase=new ArrayList<Case>();
        System.out.println(idlist);
        if(!idlist.contains(","))
        {
        	//说明只有一个财物
        	Property   property=propertyService.get(idlist);
        	Case  case1=caseService.findCaseByPropertyId(idlist);
        	property.setCaseName(case1.getCaseName());
        	properties.add(property);
        	oCase.add(case1);
        	mav.addObject("case", oCase.get(0));
            mav.addObject("date", new Date());
            mav.addObject("properties", properties);
        }
        else {
        	String  id[]=idlist.split(",");
        	for(int i=0;i<id.length;i++)
        	{
        		Property   property=propertyService.get(id[i]);
            	Case c=caseService.findCaseByPropertyId(id[i]);
            	property.setCaseName(c.getCaseName());
            	properties.add(property);
            	oCase.add(c);	
        	}
        	mav.addObject("case", oCase.get(0));
            mav.addObject("date", new Date());
            mav.addObject("properties", properties);	
        }
        int total = 0;
        for (Property property : properties) {
            total += property.getNumber();
        }
        mav.addObject("total", total);
        return mav;
    }
    
    
    
   //
    

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    
    
    
    

}
