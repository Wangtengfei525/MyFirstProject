package com.coolcloud.sacw.operation.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.annex.entity.File1Example;
import com.coolcloud.sacw.annex.entity.File1;

import com.coolcloud.sacw.annex.service.File1Service;
import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.cases.service.CaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.common.util.UuidUtil;
import com.coolcloud.sacw.exchange.entity.Exchange;
import com.coolcloud.sacw.exchange.entity.ExchangeProperty;
import com.coolcloud.sacw.exchange.mapper.ExchangeMapper;
import com.coolcloud.sacw.exchange.mapper.ExchangePropertyMapper;
import com.coolcloud.sacw.exchange.service.ExchangeService;
import com.coolcloud.sacw.operation.entity.BackVo;
import com.coolcloud.sacw.operation.entity.SaveLocationHistotry;
import com.coolcloud.sacw.operation.entity.SearchVo;
import com.coolcloud.sacw.operation.entity.Secondment;
import com.coolcloud.sacw.photo.entity.Photo;
import com.coolcloud.sacw.photo.entity.PhotoExample;
import com.coolcloud.sacw.photo.service.PhotoService;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.entity.PropertyVo;
import com.coolcloud.sacw.property.entity.PropertyVoNew;
import com.coolcloud.sacw.property.service.PropertyService;
import com.coolcloud.sacw.share.service.ShareService;
import com.github.pagehelper.PageHelper;

/**
 * 操作服务类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月17日 下午3:05:07
 */
@Service
public class BackService {

	@Value("${app.share.enable}")
	private boolean shareEnabled;

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private ExchangeService exchangeService;

	@Autowired
	private ShareService shareService;

	@Autowired
	private File1Service file1Service;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private SecondmentService secondmentService;
	@Autowired
	private SaveLocationHistotryService saveLocationHistotryService;
	
	@Autowired
	private CaseService  caseService;
	
	@Autowired
	private ExchangePropertyMapper   exchangePropertyMapper;
	
	@Autowired
	private ExchangeMapper exchangeMapper;

	
	
	
	/**
	 * 进行归还操作的方法
	 * 
	 * @param exchange
	 * @param files
	 */

	@Transactional
	public Integer back(BackVo vo) {
		 final String id = vo.getId();   //这个id是财物的id
	        final String tempId = vo.getTempId();   
	        Property  property=propertyService.get(id);//通过财物的id获取到这个财物
	        //发生借调操作的时候财物的状态会发生变化
	        
	        File1Example example = new File1Example();
	        example.setExchangeId(tempId);
	        List<File1> file1s = file1Service.getByExample(example);
	   //     Assert.isTrue(file1s.size() != 0, "未找到对应附件");
	            property.setPropertyStatus("已入库");
	            property.setPropertyStatusCode("9911000000004");
	            property.setRemake("back");  
	            propertyService.updatePropertyById(property.getId()); 
	            propertyService.clearSaveLocation(property.getId());
	           // propertyService.clearSpliteSaveLocation(property.getId());   
	            propertyService.update(property);
	   
	        return 1;
	        

	}

	
	@Transactional
	public Integer 	batchBack1(BackVo vo) {
		 final String propertyIds = vo.getId();   //这个id是财物的id
	        final String tempId = vo.getTempId();   
	       if(!propertyIds.contains(","))
	       {
	    	   Property  property=propertyService.get(propertyIds);//通过财物的id获取到这个财物
		        //发生借调操作的时候财物的状态会发生变化
		        
		        /*File1Example example = new File1Example();
		        example.setExchangeId(tempId);
		        List<File1> file1s = file1Service.getByExample(example);*/
		   //     Assert.isTrue(file1s.size() != 0, "未找到对应附件");
		            property.setPropertyStatus("已入库");
		            property.setPropertyStatusCode("9911000000004");
		            property.setRemake("back");  
		            propertyService.updatePropertyById(property.getId()); 
		            propertyService.clearSaveLocation(property.getId());
		            propertyService.clearSpliteSaveLocation(property.getId());   
		            propertyService.update(property);
		   
		        return 1;
	       }
	       else {
	    	   String  id[]=propertyIds.split(",");
	        	for(int i=0;i<id.length;i++)
	        	{
	        		 Property  property=propertyService.get(id[i]);//通过财物的id获取到这个财物
	 	 	        //发生借调操作的时候财物的状态会发生变化
	 	 	        
	 	 	       /* File1Example example = new File1Example();
	 	 	        example.setExchangeId(tempId);
	 	 	        List<File1> file1s = file1Service.getByExample(example);*/
	 	 	   //     Assert.isTrue(file1s.size() != 0, "未找到对应附件");
	        		 property.setPropertyStatus("已入库");
			            property.setPropertyStatusCode("9911000000004");
			            property.setRemake("back");  
			            propertyService.updatePropertyById(property.getId()); 
			            propertyService.clearSaveLocation(property.getId());
			            propertyService.clearSpliteSaveLocation(property.getId());   
			            propertyService.update(property);
	    	 
	        	}	   
	        	 return   id.length;
		    	   
	        
	       }      

	}
	
	
	 @Transactional
	    public Integer batchBack(BackVo vo) {
	        final String propertyIds = vo.getId();   //这个id是选中的所有的财物id组成的一个以逗号为分隔符的字符串
	        final String tempId = vo.getTempId();   //这个是在前台页面随机获取到的一条uuid  
	        final   Date   createTime=vo.getOperationTime();  //获取归还的时间
	      //  final  Date   updateTime=vo.getExpectedReturnTime();
	        final  String    remark=vo.getRemark();     
	        
	      
	 	       File1Example example = new File1Example();
	 	        example.setExchangeId(tempId);
	 	       List<File1> file1s = file1Service.getByExample(example);
	 	        Assert.isTrue(file1s.size() != 0, "未找到对应附件");
	        
	        
	        if(!propertyIds.contains(","))
	        {
	        	 Property  property=propertyService.get(propertyIds);//通过财物的id获取到这个财物
	       //当发生借调操作的时候,会在exchange表里面添加一条记录
	        	 Exchange  exchange=new Exchange();
	        	 exchange.setId(tempId);
	       //通过财物的信息(主键)查询到该财物所属的案件的id
	        	 String  caseID=property.getCaseId();
	        	 exchange.setCaseId(caseID);
	        	 exchange.setProcessCode("1004");
	        	 exchange.setNodeCode("100401");      	 
	     //通过财物的id查询到该财物所属的案件的名字  即按键名
	        	 String  caseName=caseService.findCaseByPropertyId(propertyIds).getCaseName();
	        	exchange.setCaseName(caseName);
	        	String operationTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(createTime);
	        	exchange.setOperationTime(operationTime);
	        	exchange.setRemark(remark);
	        	exchange.setDeleted(0);
	        	exchange.setCreateTime(createTime);
	        	//exchange.setUpdateTime(updateTime);
	        	//设置完毕之后，在进行添加记录操作
	        	exchangeMapper.insertSelective(exchange);
	        	
	      //在添加完交换记录后，要在property_exchange表中添加一条记录
	        ExchangeProperty   ep=new   ExchangeProperty();
	        ep.setExchangeId(tempId);
	        ep.setPropertyId(propertyIds);
	        ep.setPropertyStatus("已归还");
	        ep.setPropertyStatusCode("9911000000005");	 
	        exchangePropertyMapper.insertPropertyExchange(ep);
	   	
	 	        //发生借调操作的时候财物的状态会发生变化      
	 	       // File1Example example = new File1Example();
	 	      //  example.setExchangeId(tempId);
	 	     //  List<File1> file1s = file1Service.getByExample(example);
	 	   //     Assert.isTrue(file1s.size() != 0, "未找到对应附件");
	        
	        //发生归还操作的时候财物的状态会发生变化      
	 	            property.setPropertyStatus("已归还");
	 	            property.setPropertyStatusCode("9911000000005");    
	 	            propertyService.updatePropertyById(property.getId()); 
	 	            propertyService.clearSaveLocation(property.getId());
	 	            propertyService.clearSpliteSaveLocation(property.getId());   
	 	            propertyService.update(property);
	 	        return 1;       
	        }
	        else
	        {
	        	List<Property> propertys = new ArrayList<Property>();
	        	String  id[]=propertyIds.split(",");
	        	List<String>  listString=new ArrayList<String>();
	        	for(int i=0;i<id.length;i++)
	        	{
	        		 Property  property=propertyService.get(id[i]);//通过财物的id获取到这个财物
	        		   property.setPropertyStatus("已归还");
	 	 	            property.setPropertyStatusCode("9911000000005");
	 	 	            propertyService.updatePropertyById(property.getId()); 
	 	 	            propertyService.clearSaveLocation(property.getId());
	 	 	            propertyService.clearSpliteSaveLocation(property.getId());   
	 	 	            propertyService.update(property);	
	 	 	          propertys.add(property);
	 	 	          //发生归还操作的时候财物的状态会发生变化	        
	 	 	          /* File1Example example = new File1Example();
	 	 	          example.setExchangeId(tempId);
	 	 	           List<File1> file1s = file1Service.getByExample(example);*/
	 	 	       //     Assert.isTrue(file1s.size() != 0, "未找到对应附件");   		 
	           //如果有多个财物的时候，需要判断是不是同一个案件的财物信息，可以通过每个财物的id查询出对应的案件id 看看案件id是否存在相同的情况  如果是   则把相同案件
	        //的归还记录当成一条处理  即两个即以上相同案件财物的归还记录  在exchange表里面只是增加一条交换记录
	        //Case  c=caseService.findCaseByPropertyId(id[i]);		 
	       /* List<Property>  listProperty=new   ArrayList<Property>();  
	        listProperty.add(property);
	        //把获取到的案件的id放在一个集合中
	        for(int j=0;j<listProperty.size();j++)
	        {
	        	List<String>  listString=new ArrayList<String>();
	         listString.add(listProperty.get(i).getId());	
	        }*/        
	        listString.add(property.getCaseId());
	         
	      }
	        	
     	//把有重复的案件id合并成一个
     	for  ( int  m  =   0 ; m  <  listString.size()  -   1 ; m ++ )  {       
     		for  ( int  n  =  listString.size()  -   1 ; n  >  m; n -- )  {       
     			if  (listString.get(n).equals(listString.get(m)))  {       
     				listString.remove(n);       
     			}        
     		}        
     	}        
        	
	      //现在的listString是去掉重复案件id过后组成的一个集合  	  即案件id组成的一个集合(其中没有重复的案件id)
	        Map<String,String> map = new HashMap<>();
	     for(int  a=0;a<listString.size();a++)
	     {
	    	 //通过财物的id查询到该财物所属的案件的名字  即按键名
       	/* String  caseName=caseService.findCaseByPropertyId(listString.get(a)).getCaseName();
       	 Property  property=propertyService.get();//通过财物的id获取到这个财物 
*/	    	 
	    	 //当发生借调操作的时候,会在exchange表里面添加一条记录
       	 Exchange  exchange=new Exchange();
       	 exchange.setId(tempId);
      //通过财物的信息(主键)查询到该财物所属的案件的id
       //	 String  caseID=property.getCaseId();
       	 exchange.setCaseId(listString.get(a));
       	 exchange.setProcessCode("1004");
       	 exchange.setNodeCode("1004001");  
   	 String  caseName=caseService.get(listString.get(a)).getCaseName();   	 
       	exchange.setCaseName(caseName);
       	String operationTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(createTime);
       	exchange.setOperationTime(operationTime);
       	exchange.setRemark(remark);
       	exchange.setDeleted(0);
       	exchange.setCreateTime(createTime);
     //  	exchange.setUpdateTime(updateTime);
       	//设置完毕之后，在进行添加记录操作
       	exchangeMapper.insertSelective(exchange);	 
       	
       	//以键值对的形式存储进map集合中
       	map.put(listString.get(a), exchange.getId());
	     } 
	     //存入exchange_property表
	     for(int i=0;i<propertys.size();i++) {
	    		 //  System.out.println(key+"  "+value);
	    		 String exchangeId = map.get(propertys.get(i).getCaseId());//通过map中的key获取value
	  	    	 ExchangeProperty   ep=new   ExchangeProperty();
	  		        ep.setExchangeId(exchangeId);
	  		        ep.setPropertyId(propertys.get(i).getId());
	  		        ep.setPropertyStatus("已归还");
	  		        ep.setPropertyStatusCode("9911000000005");	 
	  		        exchangePropertyMapper.insertPropertyExchange(ep);
	  	
	     }
	 
	        	return id.length;
	        }
	   
	    }
	    
	    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 检查财物是否均为“归还中”状态<br/>
	 * 检查财物是否均已分配库位<br/>
	 * 
	 * @return
	 */
	public boolean checkPropertiesState(List<Property> properties) {
		for (Property property : properties) {
			boolean areYouOk = "9911000000007".equals(property.getPropertyStatusCode())
					&& StringUtils.hasText(property.getKwbm());
			if (!areYouOk) {
				return false;
			}
		}
		return true;
	}

	@Transactional(readOnly = true)
	public List<Exchange> getBackList1(SearchVo search) {
		Integer status = search.getStatus() == null ? 0 : search.getStatus();
		String unitId = search.getSendUnitId();
		Integer page = search.getPage() == null ? 1 : search.getPage();
		Integer rows = search.getRows() == null ? 10 : search.getRows();
		switch (status) {
		case 0:
			search.setNodeCode("1003002");
			break;
		case 1:
			search.setNodeCode("1002002");
			search.setRemark("归还");
			break;
		}
		PageHelper.startPage(page, rows);
		return exchangeService.getListByCondition(search);

	}
	
	
	/*@Transactional(readOnly = true)
	public List<Property> getBackList(PropertyVo vo) {
		Integer status = vo.getStatus() == null ? 0 : vo.getStatus();
		//String unitId = vo.getSendUnitId();
		Integer page = vo.getPage() == null ? 1 : vo.getPage();
		Integer rows = vo.getRows() == null ? 10 : vo.getRows();
		switch (status) {
		case 0:
			//search.setNodeCode("1003002");
		//	vo.setPropertyStatusCode("9911000000007");//这是未归还的状态码
			vo.setPropertyStatusCode("9911000000006");//这里未归还当成借调中来处理
			
			break;
		case 1:
			vo.setPropertyStatusCode("9911000000005");
		
			break;
		}
		PageHelper.startPage(page, rows);
		//return exchangeService.getListByCondition(search);
          return   propertyService.selectPropertyByManyConditions(vo);
	}*/
	
	
	
	@Transactional(readOnly = true)
	public List<Case> getBackList(PropertyVo vo) {
		Integer status = vo.getStatus() == null ? 0 : vo.getStatus();
		//String unitId = vo.getSendUnitId();
		Integer page = vo.getPage() == null ? 1 : vo.getPage();
		Integer rows = vo.getRows() == null ? 10 : vo.getRows();
		switch (status) {
		case 0:
			//search.setNodeCode("1003002");
		//	vo.setPropertyStatusCode("9911000000007");//这是未归还的状态码
			vo.setPropertyStatusCode("9911000000006");//这里未归还当成借调中来处理
			
			break;
		case 1:
			vo.setPropertyStatusCode("9911000000005");
		
			break;
		}
		PageHelper.startPage(page, rows);
		//return exchangeService.getListByCondition(search);
          return   caseService.findCaseByPropertyManyCondition(vo);
	}
	
	
	
	
	
	
	
	
	
	
}
