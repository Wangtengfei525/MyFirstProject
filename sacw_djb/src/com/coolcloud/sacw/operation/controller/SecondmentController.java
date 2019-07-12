package com.coolcloud.sacw.operation.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.xml.bind.Binder;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.cases.service.CaseService;
import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.exchange.entity.Exchange;
import com.coolcloud.sacw.exchange.service.ExchangeService;
import com.coolcloud.sacw.operation.entity.SearchVo;
import com.coolcloud.sacw.operation.entity.Secondment;
import com.coolcloud.sacw.operation.entity.SecondmentDelay;
import com.coolcloud.sacw.operation.entity.SecondmentDelayAddForm;
import com.coolcloud.sacw.operation.entity.SecondmentSearchForm;
import com.coolcloud.sacw.operation.entity.SecondmentVo;
import com.coolcloud.sacw.operation.entity.SecondmentVo1;
import com.coolcloud.sacw.operation.service.SecondmentDelayService;
import com.coolcloud.sacw.operation.service.SecondmentService;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.entity.PropertyVo;
import com.coolcloud.sacw.property.entity.PropertyVoNew;
import com.coolcloud.sacw.property.service.PropertyService;
import com.github.pagehelper.PageInfo;

/**
 * 借调操作控制类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月18日 下午7:58:24
 */
@RestController
@RequestMapping("/secondment")
public class SecondmentController {

    @Autowired
    private SecondmentService secondmentService;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private CaseService caseService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private SecondmentDelayService secondmentDelayService;
    
    
    

    /**
     * 财物借调<br/>
     * 请求参数:<br/>
     * id：借调申请交换记录id<br/>
     * tempId：附件临时交换记录id<br/>
     * operationTime：借调时间<br/>
     * expectedReturnTime：预计归还时间id<br/>
     * remark：备注<br/>
     * 
     * post方式 ，multipart/form-data 格式<br/>
     * 
     * @return
     */
   /* @PostMapping("/second")
    public Result second(@Validated SecondmentVo vo) {
        Integer num = secondmentService.second(vo);
        return Result.success(num + "件财物借调成功！");
    }
*/
    /*@PostMapping("/second")
    public Result second(@Validated SecondmentVo1 vo) {
        Integer num = secondmentService.second(vo);
        return Result.success(num + "件财物借调成功！");
    }
    */
    
    @PostMapping("/batchSecond")
    public Result batchSecond(@Validated SecondmentVo1 vo) {
        Integer num = secondmentService.batchsecond(vo);
        return Result.success(num + "件财物借调成功！");
    }
    
    
    
    
    
    
    
    
    
    /**
     * 退回借调申请 <br/>
     * 请求参数：<br/>
     * id：交换记录id<br/>
     * remark：备注<br/>
     * 
     * @param exchange
     * @return
     */
   /* @RequestMapping(value = "/send-back")
    public Result sendBack(Exchange exchange) {
        secondmentService.sendBack(exchange);
        return Result.success("借调申请退回成功");
    }
*/
    /**
     * 打印清单<br/>
     * 参数：<br/>
     * id：交换记录id <br/>
     * 
     * @param id
     *            交换记录id
     * @return
     */
   /* @RequestMapping("/print-detail")
    public ModelAndView printDetail(String id) {
        ModelAndView mav = new ModelAndView("secondment/print_detail");
        Exchange exchange = exchangeService.get(id);
        Case oCase = caseService.get(exchange.getCaseId());
        List<Property> properties = propertyService.getByExchangeId(id);
        mav.addObject("exchange", exchange);
        mav.addObject("case", oCase);
        mav.addObject("date", new Date());
        mav.addObject("properties", properties);
        return mav;
    }*/
    
    @RequestMapping("/print-detail")
    public ModelAndView printDetail(String propertyIds) {
    	//propertyIds是财物的主键组成起来的一个字符串  中间用逗号隔开
        ModelAndView mav = new ModelAndView("secondment/print_detail");
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
    public ModelAndView printReceipt(String propertyIds) {
        ModelAndView mav = new ModelAndView("secondment/print_receipt");
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

    /**
     * 借调信息查询
     * 
     * @param search
     * @return
     * @throws UnsupportedEncodingException 
     */
   /* @GetMapping("/secondment-list")
    public Result putinList(SearchVo search) {
   // String  s=new  String(search.getComposite());
    
        List<Exchange> list = secondmentService.getSecondmentList(search);
       
        
        PageInfo<Exchange> info = new PageInfo<>(list);
        return Result.success().total(info.getTotal()).rows(info.getList());
    }
    */
    /*@GetMapping("/secondment-list")
    public Result putinList(PropertyVo vo) throws UnsupportedEncodingException {
  
    	Commons c=new Commons();
    	String  composite=c.SolveIllegalCharacter(vo.getComposite());
    	vo.setComposite(composite);
    	
        List<Property> list = secondmentService.getSecondmentList(vo);
        
        for(int i=0;i<list.size();i++)
        {
        	String organizerCode=caseService.findCaseByPropertyId(list.get(i).getId()).getOrganizerCode();
        	
        	list.get(i).setOrganizerCode(organizerCode);	
        }
        
        PageInfo<Property> info = new PageInfo<>(list);
        return Result.success().total(info.getTotal()).rows(info.getList());
    }*/
    
    
    @GetMapping("/secondment-list")
    public Result putinList(PropertyVo vo)  {
  
    	Commons c=new Commons();
    	String  composite=c.SolveIllegalCharacter(vo.getComposite());
    	//System.out.println(vo.getStartTime());
    	//System.out.println(vo.getEndTime());
    	vo.setComposite(composite);
    //	System.out.println(vo.getStartTime());
        List<Case> list = secondmentService.getSecondmentList(vo);
        
       /* for(int i=0;i<list.size();i++)
        {
        	String organizerCode=caseService.findCaseByPropertyId(list.get(i).getId()).getOrganizerCode();
        	
        	list.get(i).setOrganizerCode(organizerCode);	
        }*/
        
        PageInfo<Case> info = new PageInfo<>(list);
        return Result.success().total(info.getTotal()).rows(info.getList());
    }
    
    
    
    
    
    
    
    

    /**
     * 逾期查询
     * 
     * @return
     */
    @GetMapping("/overdues")
    public Result overdueList(@Validated SecondmentSearchForm form) {
        List<Secondment> list = secondmentService.getOverdues(form);
        PageInfo<Secondment> info = new PageInfo<>(list);
        return Result.success().total(info.getTotal()).rows(info.getList());

    }

    /**
     * 查询借调延期记录
     * 
     * @return
     */
    @GetMapping("/delay/list")
    public Result delayList(@RequestParam("secondmentId") String secondmentId) {
        List<SecondmentDelay> list = secondmentDelayService.getBySecondmentId(secondmentId);
        PageInfo<SecondmentDelay> info = new PageInfo<>(list);
        return Result.success().total(info.getTotal()).rows(info.getList());

    }

    /**
     * 添加借调延期记录
     * 
     * @return
     */
    @PostMapping("/delay/add")
    public Result delayAdd(@Validated SecondmentDelayAddForm form) {

        String ids = form.getSecondmentIds();
        Set<String> idSet = StringUtils.commaDelimitedListToSet(ids);

        SecondmentDelay delay = new SecondmentDelay();
        BeanUtils.copyProperties(form, delay);

        int num = secondmentService.delay(idSet, delay);

        return Result.success(num + "个借调物品延期成功");

    }

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    
    
}
