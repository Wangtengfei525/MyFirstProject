package com.coolcloud.sacw.operation.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
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
import com.coolcloud.sacw.operation.entity.OutVo;
import com.coolcloud.sacw.operation.entity.SearchVo;
import com.coolcloud.sacw.operation.service.OutService;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.entity.PropertyVo;
import com.coolcloud.sacw.property.entity.PropertyVoNew;
import com.coolcloud.sacw.property.service.PropertyService;
import com.coolcloud.sacw.store.entity.Store;
import com.github.pagehelper.PageInfo;

/**
 * 出库操作控制类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月20日 下午12:01:18
 */
@RestController
@RequestMapping("/out")
public class OutController {

    @Autowired
    private OutService outService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private CaseService caseService;

    /**
     * 财物出库<br/>
     * 参数：<br/>
     * <ul>
     * <li>id：交换记录id</li>
     * <li>tempId：附件临时交换记录id</li>
     * <li>remark：备注</li>
     * </ul>
     * 
     * @param exchange
     * @return
     */
    @PostMapping("/out")
    public Result out(@Validated OutVo out) {
        Integer num = outService.out(out);
        return Result.success(num + "件财物出库成功！");
    }

   
    
    @PostMapping("/batchOut")
    public Result  batchOut(@Validated OutVo out) {
       
        Integer  num=outService.batchOut(out);
        return Result.success(num + "件财物出库成功！");
    }
    
    
    
    /**
     * 退回出库申请 <br/>
     * 请求参数：<br/>
     * id：交换记录id <br/>
     * remark：备注<br/>
     * 
     * @param exchange
     * @return
     */
   /* @PostMapping("/send-back")
    public Result sendBack(Exchange exchange) {
        outService.sendBack(exchange);
        return Result.success("出库申请退回成功");
    }
*/
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
        ModelAndView mav = new ModelAndView("out/print_detail");
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
     * 打印出库审批表<br/>
     * 参数：<br/>
     * <ul>
     * <li>id：交换记录id</li>
     * </ul>
     * 
     * @param id
     *            交换记录id
     * @return
     */
    @RequestMapping("/print-approve")
    public ModelAndView printReceipt(String propertyIds) {
        ModelAndView mav = new ModelAndView("out/print_approve");
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
            if (property.getNumber() != null) {
                total += property.getNumber();
            } else {
                total += property.getNumber();
            }
        }
        mav.addObject("total", total);
        return mav;
    }

    @GetMapping("/out-list")
    public Result outList(PropertyVo vo) {
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
    	
         
        List<Case> list = outService.getOutList(vo);
        
        /*for(int i=0;i<list.size();i++)
        {
        	String organizerCode=caseService.findCaseByPropertyId(list.get(i).getId()).getOrganizerCode();
        	
        	list.get(i).setOrganizerCode(organizerCode);	
        }
        */
        
        
        PageInfo<Case> info = new PageInfo<>(list);
        return Result.success().total(info.getTotal()).rows(info.getList());
    }
    
    
    @GetMapping("/export-list")
    public ModelAndView exportList(SearchVo search) {
        ModelAndView mav = new ModelAndView("outWithPropertiesXlsxView");

        List<Property> property = outService.export(search);
        
      /*  for (Property p : property) {
        	
			String  caseName=caseService.findCaseByPropertyId(p.getId()).getCaseName();
			String   organizerName=caseService.findCaseByPropertyId(p.getId()).getOrganizerName();
			p.setCaseId(caseName);
			p.setOrganizerName(organizerName);
        		
        	
		}
*/
        mav.addObject("property", property);
        return mav;
    }

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

}
