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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.cases.service.CaseService;
import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.exchange.entity.Exchange;
import com.coolcloud.sacw.exchange.service.ExchangeService;
import com.coolcloud.sacw.operation.entity.BackVo;
import com.coolcloud.sacw.operation.entity.SearchVo;
import com.coolcloud.sacw.operation.service.BackService;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.entity.PropertyVo;
import com.coolcloud.sacw.property.entity.PropertyVoNew;
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
@RequestMapping("/back")
public class BackController {

    @Autowired
    private BackService backService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private CaseService caseService;

    /**
     * 财物归还<br/>
     * 参数：<br/>
     * <ul>
     * <li>id：交换记录id</li>
     * <li>tempId：附件临时交换记录id</li>
     * <li>operationTime：归还时间</li>
     * <li>remark：备注</li>
     * </ul>
     * 
     * 使用form表单提交附件，文件域name为file
     * 
     * 
     * @param back
     * @return
     */
    @PostMapping("/back")
    public Result back(BackVo back) {
        Integer num = backService.back(back);
        return Result.success(num + "件财物归还成功！");
    }
    
    
    @PostMapping("/batchBack")
    public Result batchBack(BackVo back) {
        Integer num = backService.batchBack(back);
        return Result.success(num + "件财物归还成功！");
    }
    
    
    
    
    
    
    
    

    /**
     * 退回归还申请 <br/>
     * 请求参数：<br/>
     * id：交换记录id <br/>
     * remark：备注<br/>
     * 
     * @param exchange
     * @return
     */
   /* @PostMapping("/send-back")
    public Result sendBack(Exchange exchange) {
        backService.sendBack(exchange);
        return Result.success("归还申请退回成功");
    }*/

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
    public ModelAndView printDetail(String propertyIds) {
        ModelAndView mav = new ModelAndView("back/print_detail");
       
        List<Property> properties=new ArrayList<Property>();
        List<Case>  oCase=new ArrayList<Case>();
        if(!propertyIds.contains(","))
        {
        	//说明只有一个财物
        	Property   property=propertyService.get(propertyIds);
        	Case  case1=caseService.findCaseByPropertyId(propertyIds);
        	property.setCaseName(case1.getCaseName());
        	properties.add(property);
        	oCase.add(case1);
        	mav.addObject("case", oCase.get(0));
            mav.addObject("date", new Date());
            mav.addObject("properties", properties);
        }
        else {
        	String  id[]=propertyIds.split(",");
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
    public ModelAndView printReceipt(String propertyIds) {
		ModelAndView mav = new ModelAndView("back/print_receipt");

        List<Property> properties=new ArrayList<Property>();
        List<Case>  oCase=new ArrayList<Case>();
        if(!propertyIds.contains(","))
        {
        	//说明只有一个财物
        	Property   property=propertyService.get(propertyIds);
        	Case  case1=caseService.findCaseByPropertyId(propertyIds);
        	property.setCaseName(case1.getCaseName());
        	properties.add(property);
        	oCase.add(case1);
        	mav.addObject("case", oCase.get(0));
            mav.addObject("date", new Date());
            mav.addObject("properties", properties);
        }
        else {
        	String  id[]=propertyIds.split(",");
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
    
    
    
    
    
    
    
    

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

   /* @GetMapping("/back-list")
    public Result putinList(SearchVo search) {
        List<Exchange> list = backService.getBackList(search);
        PageInfo<Exchange> pageInfo = new PageInfo<>(list);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }*/
    
    @GetMapping("/back-list")
    public Result putinList(PropertyVo vo) {
    	
    	/*if(vo.getComposite()!=null&&vo.getComposite()!="")
    	{
    		 try {
    			 vo.setComposite(new String(vo.getComposite().getBytes("ISO8859-1"), "UTF-8"));
         	} catch (UnsupportedEncodingException e) {
         		e.printStackTrace();
         	}
    	}*/
    	Commons c=new Commons();
    	String  composite=c.SolveIllegalCharacter(vo.getComposite());
    	vo.setComposite(composite);
    	
    	
    	
    	
    	
        List<Case> list = backService.getBackList(vo);
        
        
       /* for(int i=0;i<list.size();i++)
        {
        	String organizerCode=caseService.findCaseByPropertyId(list.get(i).getId()).getOrganizerCode();
        	
        	list.get(i).setOrganizerCode(organizerCode);	
        }*/
        
        PageInfo<Case> pageInfo = new PageInfo<>(list);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }
    
    
    
    

}
