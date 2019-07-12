package com.coolcloud.sacw.property.web;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.coolcloud.sacw.annex.entity.File1;

import com.coolcloud.sacw.annex.service.File1Service;
import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.cases.service.CaseService;
import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.exchange.entity.Exchange;
import com.coolcloud.sacw.operation.controller.Commons;
import com.coolcloud.sacw.photo.entity.Photo;
import com.coolcloud.sacw.photo.entity.PhotoExample;
import com.coolcloud.sacw.photo.service.PhotoService;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.entity.PropertyExample;
import com.coolcloud.sacw.property.entity.PropertyLog;
import com.coolcloud.sacw.property.service.PropertyLogService;
import com.coolcloud.sacw.property.service.PropertyService;
import com.coolcloud.sacw.store.entity.Store;
import com.coolcloud.sacw.store.mapper.StoreMapper;
import com.coolcloud.sacw.store.service.StoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 财物管理控制类
 * 
 * @author 王孝康
 *
 */
@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private CaseService caseService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private File1Service file1Service;

    @Autowired
    private PropertyLogService propertyLogService;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private StoreService storeService;
    /**
     * 财物列表
     * 
     * @return
     */
    @GetMapping(value = "/list")
    public Result propertyList(PropertyExample propertyExample) {
    	
    	
    	/*if(propertyExample.getComposite()!=null&&propertyExample.getComposite()!="")
    	{
    		 try {
    			 propertyExample.setComposite(new String(propertyExample.getComposite().getBytes("ISO8859-1"), "UTF-8"));
         	} catch (UnsupportedEncodingException e) {
         		e.printStackTrace();
         	}
    	}*/
    	
    	Commons c=new Commons();
    	String  composite=c.SolveIllegalCharacter(propertyExample.getComposite());
    	propertyExample.setComposite(composite);
    	
    	
        Integer page = propertyExample.getPage();
        Integer rows = propertyExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        
        
        List<Property> property;
       // property = propertyService.getByComposite(propertyExample);
        
        final String kwbm = propertyExample.getKwbm();
       // propertyExample
       
        if (org.springframework.util.StringUtils.isEmpty(kwbm)) {
            property = propertyService.getByComposite(propertyExample);
       
        } 
        
        else {
        	String name = storeMapper.queryName(kwbm);
        	name = name.substring(2, 4);
        	if(!"车库".equals(name)) {
        		 property = propertyService.getByKwbmIn(kwbm);
        	}else {
        		property = propertyService.getByKwbmInCar(kwbm);
        	}	
        }
        
        
        PageInfo<Property> pageInfo = new PageInfo<>(property);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    
    
    /**
     * 打印条形码 <br/>
     * 参数： <br/>
     * id：财物id <br/>
     * 
     * 请求示例：http://localhost:8080/sacw_online/property/printCode?id=ssfsf
     * 
     * @param id
     *            财物id
     */
    @PostMapping(value = "/print-code")
    public Result printqrCode(String id) {
        propertyService.printqrCode(id);
        return Result.success("打印成功");
    }

    /**
     * 删除财物
     * 
     * @param property
     * @return
     */
    // @PostMapping("/deleted",)
    public Result deleted(String id) {
        Integer num = propertyService.delete(id);
        return Result.success(num + "个财物已删除");
    }
     //使用
    /**
     * 根据交换记录ID获取相应财物 <br/>
     * 传入参数： <br/>
     * exchangeId：交换记录id <br/>
     * 请求示例：http://localhost:8080/sacw_online/property/list/byexchange?exchangeId=ssfsf
     * 
     * @param exchangeId
     * @return
     */
    @GetMapping("/list/byexchange")
    public Result listByExchangeId(String exchangeId) {
        List<Property> properties = propertyService.getByExchangeId(exchangeId);
        return Result.success().total(properties.size()).rows(properties);
    }

    /**
     * 财物分配入柜 <br/>
     * 
     * 请求参数： <br/>
     * ids 逗号分割的财物id如 ids=123456,654321 <br/>
     * saveId 存储位置id <br/>
     * 
     * 请求示例：http://localhost:8080/sacwonline/property/assign-store?ids=1,2&saveId=12000
     * 
     * @param property
     * @return
     */
    @PostMapping("/assign-store")
    public Result assignStore(String ids, String saveId) {
        Integer num = propertyService.assignStore(ids, saveId);
        return Result.success(num + "个财物已分配入柜！");
    }
    
    @PostMapping("/assign-manystore")
    public Result assignmanyStore(String ids1, String saveIds) {
    	Integer num=null;
             if(saveIds.contains(","))
             {
              num=propertyService.assignManyStore(ids1, saveIds);
             }
             else{
             num = propertyService.assignStore(ids1, saveIds); 
             }
         
          
       
       
           return Result.success(num + "个财物已成功分配入柜！");
          
        

       /* return  Result.success(num+"个财物已成功分配!");
        */
       
       
        
    }
    
    

    /**
     * 修改财物备注数量 <br/>
     * 请求参数： <br/>
     * id : 财物id <br/>
     * number : 备注数量 <br/>
     * 
     * 请求示例：http://localhost:8080/sacw_online/property/update-remark-number?id=0041968251374f7b972d3c561f92be02&number=2
     * <br/>
     * 
     * @param id
     *            财物id
     * @param number
     *            备注数量
     * @return
     */
    @PostMapping(value = "/update-remark-number")
    public Result updateRemarkNumber(@RequestParam("id") String id, @RequestParam("number") Integer number) {

        Assert.isTrue(!StringUtils.isEmpty(id), "未指定财物id");
        Assert.notNull(number, "未指定财物备注数量");

        propertyService.updateRemarkNumber(id, number);
        return Result.success("备注数量修改成功");
        
    }

    /**
     * 获取财物详情（财物，案件，照片等）<br/>
     * 
     * http://localhost:8080/sacw_online/property/info?id=ssfsfs
     * 
     * @param id
     *            财物id
     * @return
     */
    @GetMapping(value = "/info")
    public Result info(String id) {
        Result result = Result.success();
        Property property = propertyService.get(id);
        if (property == null) {
            result.add("caze", null);
        } else {
            Case caze = caseService.get(property.getCaseId());
            result.add("caze", caze);

            PhotoExample photoExample = new PhotoExample();
            photoExample.setPropertyId(property.getId());
            List<Photo> photos = photoService.getByExample(photoExample);
           // property.setPhotos(photos);
            
            
              
            List<File1> file1s= file1Service.getByPropertyId(id);
            result.add("annexs",file1s);
        }
        result.add("property", property);
        return result;
    }

    /**
     * 根据二维码或id获取财物记录（不含照片等）<br/>
     * 
     * http://localhost:8080/sacw_online/property/get?qrCode=ssfsfs <br/>
     * 或 http://localhost:8080/sacw_online/property/get?id=adcb3232d
     * 
     * @param qrCode
     *            财物二维码
     * @param id
     *            财物id
     * @return
     */
    @GetMapping(value = "/get")
    public Result get(@RequestParam(name = "qrCode", required = false) String qrCode, //
            @RequestParam(name = "id", required = false) String id) {
        PropertyExample example = new PropertyExample();
        if (StringUtils.hasText(id)) {
            example.setId(id);
        } else if (StringUtils.hasText(qrCode)) {
            example.setQrCode(qrCode);
        } else {
            return Result.failed().add("property", null);
        }

        Result result = Result.success();
        List<Property> properties = propertyService.getByExample(example);
        System.out.println(properties.size());
        if (properties.size() == 1) {
            result.add("property", properties.get(0));
        } else {
            result.add("property", null);

        }
        return result;
    }

    /**
     * 财物变更记录
     * 
     * @param id
     *            财物id
     * @return
     */
    @GetMapping(value = "/log/list")
    public Result logs(@RequestParam("id") String id) {
        List<PropertyLog> list = propertyLogService.getByPropertyId(id);
        return Result.success().total(list.size()).rows(list);
    }

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    /**
     * 
     */
    
    @GetMapping("/findPropertyById")
    public Result findPropertyById(String id) {
      //  Exchange exchange = exchangeService.get(id);
        
        Property   property=propertyService.get(id);
        
        return Result.success().add("property", property);
    }
    
    
    //根据多个财物的id获取财物的相关信息
    
   /* @GetMapping("/findPropertyByPropertyIds")
    public Result findPropertyByPropertyIds(String propertyIds) {
      //  Exchange exchange = exchangeService.get(id);
    	List<Property> list= new ArrayList<Property>();
    	Property   property=null;
        if(!propertyIds.contains(","))
        {
           property=propertyService.get(propertyIds);
           list.add(property);
        }
        else {
        	String id[]=propertyIds.split(",");
        	for(int i=0;i<id.length;i++)
        	{
        		   property=propertyService.get(id[i]);
        		 list.add(property);
        	} 
        }
        PageInfo<Property> info = new PageInfo<>(list);
   	 return Result.success().total(info.getTotal()).rows(info.getList());
    }*/
   
    
   //通过案件的id获取未被借调的财物
    @GetMapping("/findNotSecondPropertyByCaseId")
    public Result findNotSecondPropertyByCaseId(String id) {
      //  Exchange exchange = exchangeService.get(id);
    	List<Property> list= propertyService.selectNotSendPropertyByCaseId(id);
        PageInfo<Property> info = new PageInfo<>(list);
   	 return Result.success().total(info.getTotal()).rows(info.getList());
    }
    
    
    //通过案件的id获取未归还的财物
     @GetMapping("/findNotBackPropertyByCaseId")
     public Result findNotBackPropertyByCaseId(String id) {
       //  Exchange exchange = exchangeService.get(id);
     	List<Property> list= propertyService.selectNotBackPropertyByCaseId(id);
         PageInfo<Property> info = new PageInfo<>(list);
    	 return Result.success().total(info.getTotal()).rows(info.getList());
     }
     
     
   //通过案件的id获取未出库的财物
     @GetMapping("/findNotOutPropertyByCaseId")
     public Result findNotOutPropertyByCaseId(String id) {
       //  Exchange exchange = exchangeService.get(id);
     	List<Property> list= propertyService.selectNotOutPropertyByCaseId(id);
         PageInfo<Property> info = new PageInfo<>(list);
    	 return Result.success().total(info.getTotal()).rows(info.getList());
     }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
