package com.coolcloud.sacw.operation.service;

import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.annex.entity.File1;
import com.coolcloud.sacw.annex.entity.File1Example;
import com.coolcloud.sacw.annex.service.File1Service;
import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.cases.service.CaseService;
import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.common.util.PaginationUtil;
import com.coolcloud.sacw.exchange.entity.Exchange;
import com.coolcloud.sacw.exchange.entity.ExchangeProperty;
import com.coolcloud.sacw.exchange.mapper.ExchangeMapper;
import com.coolcloud.sacw.exchange.mapper.ExchangePropertyMapper;
import com.coolcloud.sacw.exchange.service.ExchangeService;
import com.coolcloud.sacw.operation.entity.SaveLocationHistotry;
import com.coolcloud.sacw.operation.entity.SearchVo;
import com.coolcloud.sacw.operation.entity.Secondment;
import com.coolcloud.sacw.operation.entity.SecondmentDelay;
import com.coolcloud.sacw.operation.entity.SecondmentSearchForm;
import com.coolcloud.sacw.operation.entity.SecondmentVo;
import com.coolcloud.sacw.operation.entity.SecondmentVo1;
import com.coolcloud.sacw.operation.mapper.SecondmentMapper;
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
 * 借调操作服务类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月18日 下午8:57:01
 */
@Service
public class SecondmentService extends BaseService<Secondment, String> {

	 private static Logger logger = LoggerFactory.getLogger(SecondmentService.class);

	    /**
	     * 逾期前提醒天数
	     */
	    private static final int DAYS_BEFORE_OVERDUE = 3;

	    @Value("${app.share.enable}")
	    private boolean shareEnabled;

	    @Autowired
	    private SecondmentMapper secondmentMapper;

	    @Autowired
	    private ExchangeService exchangeService;

	    @Autowired
	    private PropertyService propertyService;

	    @Autowired
	    private ShareService shareService;

	    @Autowired
	    private File1Service file1Service;

	    @Autowired
	    private PhotoService photoService;

	    @Autowired
	    private SecondmentDelayService secondmentDelayService;
	    @Autowired
	    private SaveLocationHistotryService saveLocationHistotryService;

	    @Autowired
	    private   CaseService  caseService;
	    
	    @Autowired
	    private ExchangeMapper exchangeMapper;
	    
	    @Autowired
	   private   ExchangePropertyMapper   exchangePropertyMapper;
	    
	    
	    
	    
	    
	    
	    /**
	     * 执行单个财物借调操作的方法
	     * 
	     * @param exchange
	     * @param files
	     * @return
	     */
	    @Transactional
	    public Integer second(SecondmentVo1 vo) {

	        final String id = vo.getId();   //这个id是财物的id
	        final String tempId = vo.getTempId();   
	        Property  property=propertyService.get(id);//通过财物的id获取到这个财物
	        //发生借调操作的时候财物的状态会发生变化
	        
	        File1Example example = new File1Example();
	        example.setExchangeId(tempId);
	        List<File1> file1s = file1Service.getByExample(example);
	   //     Assert.isTrue(file1s.size() != 0, "未找到对应附件");
	            property.setPropertyStatus("借调中");
	            property.setPropertyStatusCode("9911000000006");
	            property.setRemake("发生了借调操作");  
	            propertyService.updatePropertyById(property.getId()); 
	            propertyService.clearSaveLocation(property.getId());
	          //  propertyService.clearSpliteSaveLocation(property.getId());   
	            propertyService.update(property);
	        return 1;   
	    }
	    
	 /**
	  * 财物批量借调的方法
	  * @param vo
	  * @return
	  */
	    
	    @Transactional
	    public Integer batchsecond(SecondmentVo1 vo) {
	        final String propertyIds = vo.getId();   //这个id是选中的所有的财物id组成的一个以逗号为分隔符的字符串
	        final String tempId = vo.getTempId();   //这个是在前台页面随机获取到的一条uuid  
	        final   Date   createTime=vo.getOperationTime();
	        final  Date   updateTime=vo.getExpectedReturnTime();
	        final  String    remark=vo.getRemark();     
	        
	        //发生借调操作的时候财物的状态会发生变化      首先要判断是否有附件进行上传
 	        File1Example example = new File1Example();
 	     //  example.setExchangeId("x"+tempId);
 	       
 	      example.setExchangeId(tempId);
 	       
 	       List<File1> file1s = file1Service.getByExample(example);
 	        Assert.isTrue(file1s.size() != 0, "未找到对应附件");
	        
	        
	        
	        
	        if(!propertyIds.contains(","))
	        {
	        	
	        	/* //发生借调操作的时候财物的状态会发生变化      首先要判断是否有附件进行上传
	 	        File1Example example = new File1Example();
	 	     //  example.setExchangeId("x"+tempId);
	 	       
	 	      example.setExchangeId(tempId);
	 	       
	 	       List<File1> file1s = file1Service.getByExample(example);
	 	        Assert.isTrue(file1s.size() != 0, "未找到对应附件");*/
	        	
	        
	        	
	        	
	        	 Property  property=propertyService.get(propertyIds);//通过财物的id获取到这个财物
	       //当发生借调操作的时候,会在exchange表里面添加一条记录
	        	 Exchange  exchange=new Exchange();
	        	// exchange.setId("x"+tempId);
	        	 
	        	 exchange.setId(tempId);
	        	 
	        	 
	       //通过财物的信息(主键)查询到该财物所属的案件的id
	        	 String  caseID=property.getCaseId();
	        	 exchange.setCaseId(caseID);
	        	 exchange.setProcessCode("1003");
	        	 exchange.setNodeCode("1003001");      	 
	     //通过财物的id查询到该财物所属的案件的名字  即按键名
	        	 Case c=caseService.findCaseByPropertyId(propertyIds);
	        	 String  caseName=c.getCaseName();
	        	exchange.setCaseName(caseName);
	        	String operationTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(createTime);
	        	exchange.setOperationTime(operationTime);
	        	exchange.setRemark(remark);
	        	exchange.setDeleted(0);
	        	exchange.setCreateTime(createTime);
	        	exchange.setUpdateTime(updateTime);
	        	//设置完毕之后，在进行添加记录操作
	        	exchangeMapper.insertSelective(exchange);
	        	
	      //在添加完交换记录后，要在property_exchange表中添加一条记录
	        ExchangeProperty   ep=new   ExchangeProperty();
	      //  ep.setExchangeId("x"+tempId);
	        ep.setExchangeId(tempId);
	        ep.setPropertyId(propertyIds);
	        ep.setPropertyStatus("借调中");
	        ep.setPropertyStatusCode("9911000000006");	 
	        exchangePropertyMapper.insertPropertyExchange(ep);
	   	
	  //在添加完交换记录之后，会在yw_secondement表里面添加对应的借调记录  有多少财物添加多少条记录
	        Secondment   secondment=new Secondment();
	       // secondment.setId("x"+tempId);//设置借调记录的主键
	        
	        secondment.setId(tempId);//设置借调记录的主键
	        secondment.setExchangeId(tempId);
	        secondment.setPropertyId(property.getId());//设置财物的主键
	        secondment.setPropertyName(property.getPropertyName());//设置财物的名字
	        secondment.setCaseId(c.getId());//设置财物的主键
	        secondment.setCaseName(c.getCaseName());//设置财物所对应的案件名字
	        secondment.setSecondmentTime(createTime);//设置借调的时间
	        secondment.setExpectedReturnTime(updateTime);//设置归还的时间   后面如果财物到期未归还   会做逾期处理 
	        secondmentMapper.insertSelective(secondment);//将这条记录加到数据库里面
	        
	        
	        
	        
	        
	        
	        
	        
	        
	 	       /* //发生借调操作的时候财物的状态会发生变化      
	 	        File1Example example = new File1Example();
	 	     //  example.setExchangeId("x"+tempId);
	 	       
	 	      example.setExchangeId(tempId);
	 	       
	 	       List<File1> file1s = file1Service.getByExample(example);
	 	        Assert.isTrue(file1s.size() != 0, "未找到对应附件");*/
	 	     
	 	     
	 	            
	 	            property.setPropertyStatus("借调中");
	 	            property.setPropertyStatusCode("9911000000006");
	 	          //  property.setRemake("发生了借调操作");  
	 	            propertyService.updatePropertyById(property.getId()); 
	 	            propertyService.clearSaveLocation(property.getId());
	 	         //   propertyService.clearSpliteSaveLocation(property.getId());   
	 	            propertyService.update(property);
	 	        return 1;       
	        }
	        else
	        {
	        	

	        	/* //发生借调操作的时候财物的状态会发生变化      首先要判断是否有附件进行上传
	 	        File1Example example = new File1Example();
	 	     //  example.setExchangeId("x"+tempId);
	 	       
	 	      example.setExchangeId(tempId);
	 	       
	 	       List<File1> file1s = file1Service.getByExample(example);
	 	        Assert.isTrue(file1s.size() != 0, "未找到对应附件");*/
	        	
	        	
	        	
	        	
	        	
	        	List<Property> propertys = new ArrayList<Property>();
	        	String  id[]=propertyIds.split(",");
	        	List<String>  listString=new ArrayList<String>();
	        	for(int i=0;i<id.length;i++)
	        	{
	        		

		 	       /* //发生借调操作的时候财物的状态会发生变化      
		 	        File1Example example = new File1Example();
		 	      // example.setExchangeId("x"+tempId);
		 	       
		 	       example.setExchangeId(tempId);
		 	       
		 	       List<File1> file1s = file1Service.getByExample(example);
		 	     Assert.isTrue(file1s.size() != 0, "未找到对应附件");
		 	     
	        		*/
	        		
	        		 Property  property=propertyService.get(id[i]);//通过财物的id获取到这个财物
	        		   property.setPropertyStatus("借调中");
	 	 	            property.setPropertyStatusCode("9911000000006");
	 	 	            propertyService.updatePropertyById(property.getId()); 
	 	 	            propertyService.clearSaveLocation(property.getId());
	 	 	            propertyService.clearSpliteSaveLocation(property.getId());   
	 	 	            propertyService.update(property);	
	 	 	          propertys.add(property);
	 	 	          
	 	 	          
	 	 	        Case c=caseService.findCaseByPropertyId(id[i]);   
	 	 	      //在修改完财物表的财物状态后，会在yw_secondement表里面添加对应的借调记录  有多少财物添加多少条记录
	 		        Secondment   secondment=new Secondment();
	 		        secondment.setId(i+tempId);//设置借调记录的主键
	 		        secondment.setExchangeId(tempId);//设置借调表的交换记录id
	 		        secondment.setPropertyId(property.getId());//设置财物的主键
	 		        secondment.setPropertyName(property.getPropertyName());//设置财物的名字
	 		        secondment.setCaseId(c.getId());//设置财物的主键
	 		        secondment.setCaseName(c.getCaseName());//设置财物所对应的案件名字
	 		        secondment.setSecondmentTime(createTime);//设置借调的时间
	 		        secondment.setExpectedReturnTime(updateTime);//设置归还的时间   后面如果财物到期未归还   会做逾期处理   
	 		        secondmentMapper.insertSelective(secondment);//将这条记录加到数据库里面
	 		        
	 	 	          
	 	 	          
	 	 	          
	 	 	          
	 	 	          
	 	 	          //发生借调操作的时候财物的状态会发生变化	        
	 	 	          /* File1Example example = new File1Example();
	 	 	          example.setExchangeId(tempId);
	 	 	           List<File1> file1s = file1Service.getByExample(example);*/
	 	 	       //     Assert.isTrue(file1s.size() != 0, "未找到对应附件");   		 
	           //如果有多个财物的时候，需要判断是不是同一个案件的财物信息，可以通过每个财物的id查询出对应的案件id 看看案件id是否存在相同的情况  如果是   则把相同案件
	        //的借调记录当成一条处理  即两个即以上相同案件财物的借调记录  在exchange表里面只是增加一条交换记录
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
          	 exchange.setProcessCode("1003");
          	 exchange.setNodeCode("1003001");  
      	 String  caseName=caseService.get(listString.get(a)).getCaseName();   	 
          	exchange.setCaseName(caseName);
         	String operationTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(createTime);
          	exchange.setOperationTime(operationTime);
          	exchange.setRemark(remark);
          	exchange.setDeleted(0);
          	exchange.setCreateTime(createTime);
          	exchange.setUpdateTime(updateTime);
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
	  		        ep.setPropertyStatus("借调中");
	  		        ep.setPropertyStatusCode("9911000000006");	 
	  		        exchangePropertyMapper.insertPropertyExchange(ep);
	  	
	     }
	 
	        	return id.length;
	        }
	   
	    }
	    
	    
	    
	    
	    
	    
	    
	    

	    
	   
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    @Transactional
	    public Integer second1(SecondmentVo vo) {

	        final String id = vo.getId();
	        final String tempId = vo.getTempId();

	   //     Assert.isTrue(StringUtils.hasText(id), "未指定交换记录id");

	        Exchange originExchange = exchangeService.get(id);
	        Assert.notNull(originExchange, "未找到对应交换记录");
	      //  Assert.isTrue("1003001".equals(originExchange.getNodeCode()), "该交换记录并非借调申请记录");
	        //Assert.isTrue("N".equals(originExchange.getReceiveState()), "该借调申请已经处理");
	        List<Property> properties = propertyService.getByExchangeId(id);
	        //通过财物获取案件的名字
	     //   String  caseName=exchangeService.selectCaseNameByProId(properties.get(0).getId());
	        // 查找附件
	        File1Example example = new File1Example();
	        example.setExchangeId(tempId);
	        List<File1> file1s = file1Service.getByExample(example);
	   //     Assert.isTrue(file1s.size() != 0, "未找到对应附件");
	        List<String> ids = new ArrayList<>();
	        // 更新财物状态信息
	        for (Property property : properties) {
	            ids.add(property.getId());
	            SaveLocationHistotry saveLocationHistotry = new SaveLocationHistotry();
	           // property.setKeepUnitCode(originExchange.getSendUnitCode());
	          //  property.setKeepUnitMc(originExchange.getSendUnitName());
	            property.setPropertyStatus("借调中");
	            property.setPropertyStatusCode("9911000000006");
	            property.setRemake("发生了借调操作");  
	            propertyService.updatePropertyById(property.getId()); 
	            propertyService.clearSaveLocation(property.getId());
	            propertyService.clearSpliteSaveLocation(property.getId());
	            saveLocationHistotry.setProperty_status("借调中");
	            saveLocationHistotry.setProperty_id(property.getId());
	            List<SaveLocationHistotry> saveLocationHistotrys = saveLocationHistotryService.getByExample(saveLocationHistotry);
	            if(saveLocationHistotrys.size() != 0) {
	         	   saveLocationHistotryService.update(saveLocationHistotry);
	            } else{
	         	   saveLocationHistotry.setCase_id(property.getCaseId());
	               //saveLocationHistotry.setCase_name(property.getCaseName());   
	         	   //设置案件名字
	         	 //  saveLocationHistotry.setCase_name(caseName);   
	                saveLocationHistotry.setDeleted(0);
	                saveLocationHistotry.setKwbm(property.getKwbm());
	                saveLocationHistotry.setNumber(property.getNumber()==null?0:property.getNumber());
	              //  saveLocationHistotry.setPermit_unit_mc(property.getPermitUnitMc()==null?"":property.getPermitUnitMc());
	                saveLocationHistotry.setProperty_id(property.getId());
	                saveLocationHistotry.setCreateTime(vo.getOperationTime());
	                saveLocationHistotry.setProperty_name(property.getPropertyName());
	               saveLocationHistotry.setCase_name(vo.getCaseName());    
	                saveLocationHistotry.setProperty_split_id("");
	                saveLocationHistotry.setProperty_status("已入库");
	                saveLocationHistotry.setProperty_type(property.getPropertyType()==null?"":property.getPropertyType());
	               // saveLocationHistotry.setRemake_number(property.getRemake_number()==null?0:property.getRemake_number());	   
	               // saveLocationHistotry.setRemake_number(property.getNumber()==null?0:property.getNumber());
	               saveLocationHistotry.setRemake_number(2);
		         	  saveLocationHistotry.setNumber(1); 	    
	                saveLocationHistotryService.save(saveLocationHistotry);
	         	   saveLocationHistotry.setProperty_status("已出库");
	                saveLocationHistotry.setProperty_id(property.getId());
	         	   saveLocationHistotryService.update(saveLocationHistotry);
	            };
	        }
	        propertyService.saveLogs(ids, "财物状态变更为借调中");
        originExchange.setProcessCode("1003");
      originExchange.setNodeCode("1003002");
      exchangeService.update(originExchange); 
	        // 更新借调申请交换记录    
	      /*  Exchange secondExchange = new Exchange();
	        secondExchange.setId(originExchange.getId());*/        
	      //  secondExchange.setReceiveState("Y");     
	      //  exchangeService.update(secondExchange);

	        // 插入借调反馈交换记录
	     /*   Exchange be = exchangeService.createSecondReceipt(originExchange);
	        be.setProperties(properties);
	        be.setOperationTime(vo.getOperationTime());
	        be.setRemark(vo.getRemark());
	*/
	/*        exchangeService.save(be);*/

	        // 借调记录
	       /* for (Property property : be.getProperties()) {
	            Secondment secondment = new Secondment();
	            secondment.setExchangeId(be.getId());
	            secondment.setCaseId(be.getCaseId());
	            secondment.setCaseName(be.getCaseName());
	            secondment.setPropertyId(property.getId());
	            secondment.setPropertyName(property.getPropertyName());
	            secondment.setSecondmentTime(vo.getOperationTime());
	            secondment.setExpectedReturnTime(vo.getExpectedReturnTime());
	            secondment.setSecondmentUnitId(be.getReceiveUnitCode());
	            secondment.setSecondmentUnitName(be.getReceiveUnitName());
	            secondment.setReturned(Integer.valueOf(0));
	            this.save(secondment);
	        }*/

	      /*  // 更新附件信息
	        for (File1 file1 : file1s) {
	            File1 temp = new File1();
	            temp.setExchangeId(be.getId());
	            temp.setId(file1.getId());
	            file1Service.update(temp);
	        }*/
	         
	     /*   if (shareEnabled) {
	            // 发送反馈
	            shareService.feedBack(be.getId());
	        }*/

	     /*   logger.info("财物借调成功，交换记录编号：{}" + be.getExchangeBatch());*/

	        return properties.size();
	    }

	    
	    
	    
	    
	    
	    
	    
	    /**
	     * 退回申请
	     * 
	     * @param exchange
	     */
	    @Transactional
	    public Integer sendBack(Exchange exchange) {
	        String exchangeId = exchange.getId();
	        // 信息完整性检查
	        Assert.isTrue(StringUtils.hasText(exchangeId), "交换记录id不能为空");
	        Exchange originExchange = exchangeService.get(exchangeId);
	        Assert.notNull(originExchange, "未找到对应的交换记录");
	        Assert.isTrue("1003001".equals(originExchange.getNodeCode()), "该交换记录并非借调申请记录");
	     //   Assert.isTrue("N".equals(originExchange.getReceiveState()), "该借调申请已被处理，请勿重复处理");
	        Assert.isTrue(StringUtils.hasText(exchange.getRemark()), "请填写退回备注信息");

	        List<Property> properties = propertyService.getByExchangeId(exchangeId);

	        // 更新财物状态为 已入库
	        List<String> ids = new ArrayList<>();

	        Property temp = new Property();
	        for (Property property : properties) {
	            ids.add(property.getId());
	            temp.setId(property.getId());
	            temp.setPropertyStatus("已入库");
	            temp.setPropertyStatusCode("9911000000004");
	            propertyService.update(temp);
	        }
	        // propertyService.updateStatusByIds(ids, "已入库", "9911000000004");

	        propertyService.saveLogs(ids, "财物状态变更为已入库");

	        // 更新入库申请交换记录
	        Exchange putInExchange = new Exchange();
	        putInExchange.setId(exchangeId);
	      //  putInExchange.setReceiveState("Y");
	        exchangeService.update(putInExchange);

	        // 插入退回交换记录
	        Exchange be = exchangeService.createSecondBack(originExchange);
	       // be.setProperties(properties);
	        be.setRemark(exchange.getRemark());

	        exchangeService.save(be);

	        // 查询、添加照片
	        PhotoExample photoExample = new PhotoExample();
	        photoExample.setExchangeId(exchangeId);
	        photoService.getByExample(photoExample);
	        List<Photo> photos = photoService.getByExample(photoExample);
	        for (Photo photo : photos) {
	            photo.setId(null);
	            photo.setExchangeId(be.getId());
	          //  photo.setExchange_lot(be.getExchangeBatch());
	            photoService.save(photo);
	        }

	        // 查询、添加附件
	        File1Example file1Example = new File1Example();
	        file1Example.setExchangeId(exchangeId);
	        List<File1> file1s = file1Service.getByExample(file1Example);
	        for (File1 file1 : file1s) {
	        	file1.setId(null);
	        //	file1.setExchange_batch(be.getExchangeBatch());
	        	//file1.setExchange_lot(be.getExchangeBatch());
	        	file1.setExchangeId(be.getId());
	        	file1Service.save(file1);
	        }

	        if (shareEnabled) {
	            // 发送退回消息
	            shareService.fallBack(be.getId());
	        }

	        return properties.size();

	    }

	    /**
	     * 检查财物是否均为借调申请状态
	     * 
	     * @param properties
	     * @return
	     */
	    public boolean checkPropertiesState(List<Property> properties) {
	        for (Property property : properties) {
	            if (!"9911000000005".equals(property.getPropertyStatusCode())) {
	                return false;
	            }
	        }
	        return true;
	    }

	   /* @Transactional(readOnly = true)
	    public List<Exchange> getSecondmentList1(SearchVo search) {
	        Integer status = search.getStatus() == null ? 0 : search.getStatus();
	      //  String unitId = search.getSendUnitId();
	        Integer page = search.getPage() == null ? 1 : search.getPage();
	        Integer rows = search.getRows() == null ? 10 : search.getRows();

	        switch (status) {
	        case 0:
	            search.setReceiveState("N");
	            search.setNodeCode("1003001");
	            search.setSendUnitId(unitId);
	            search.setReceiveUnitId(null);
	            break;
	            
	        case 0:
	            //search.setReceiveState("N");
	            search.setNodeCode("1002002");
	            search.setSendUnitId(unitId);
	            search.setReceiveUnitId(null);
	            break;  
	               
	        case 1:
	           // search.setReceiveState(null);
	            search.setNodeCode("1003002");
	            search.setSendUnitId(null);
	            search.setReceiveUnitId(unitId);
	            break;
	        case 2:
	            search.setReceiveState(null);
	            search.setNodeCode("1003003");
	            search.setSendUnitId(null);
	            search.setReceiveUnitId(unitId);
	            break;
	            
	        }
	        PageHelper.startPage(page, rows);
	        String    composite=java.net.URLDecoder.decode(search.getComposite(), "utf-8");
	        search.setComposite(composite);
	        return exchangeService.getListByCondition(search);
	    }
	    
	    
	    
	    @Transactional(readOnly = true)
	    public List<Property> getSecondmentList1(PropertyVo vo) {
	        Integer status = vo.getStatus() == null ? 0 : vo.getStatus();
	      //  String unitId = search.getSendUnitId();
	        Integer page = vo.getPage() == null ? 1 :  vo.getPage();
	        Integer rows =  vo.getRows() == null ? 10 :  vo.getRows();

	        switch (status) {
	       
	        case 0:
	            vo.setPropertyStatusCode("9911000000004");  //这里的未借调查询出来的是已入库的财物
	          // vo.setPropertyStatusCode(null);
	            break;  
	               
	        case 1:
	        	
	        	 vo.setPropertyStatusCode("9911000000006");
	        	 
	            break;
	    
	        }
	        PageHelper.startPage(page, rows);
	        String    composite=java.net.URLDecoder.decode(search.getComposite(), "utf-8");
	        search.setComposite(composite);
	        //return exchangeService.getListByCondition(search);
	        return  propertyService.selectPropertyByManyConditions(vo);
	    }
	    
	    */
	    
	    
	   /* @Transactional(readOnly = true)
	    public List<Property> getSecondmentList(PropertyVo vo) {
	        Integer status = vo.getStatus() == null ? 0 : vo.getStatus();
	      //  String unitId = search.getSendUnitId();
	        Integer page = vo.getPage() == null ? 1 :  vo.getPage();
	        Integer rows =  vo.getRows() == null ? 10 :  vo.getRows();
         List<Property>  property=null;
	        
	        switch (status) {
	        case 0:
	          //  vo.setPropertyStatusCode("9911000000004");  //这里的未借调查询出来的是已入库的财物
	            PageHelper.startPage(page, rows);
	            property=propertyService.selectNotSendPropertyByManyConditions(vo);
	            break;  
	               
	        case 1:
	        	 vo.setPropertyStatusCode("9911000000006");
	        	   PageHelper.startPage(page, rows);
	        	   property=propertyService.selectPropertyByManyConditions(vo);
		            break;  
	        }
	        PageHelper.startPage(page, rows);
	        String    composite=java.net.URLDecoder.decode(search.getComposite(), "utf-8");
	        search.setComposite(composite);
	        //return exchangeService.getListByCondition(search);
	        return  propertyService.selectPropertyByManyConditions(vo);
	        return  property;
	    }*/
	    
	    @Transactional(readOnly = true)
	    public List<Case> getSecondmentList(PropertyVo vo) {
	        Integer status = vo.getStatus() == null ? 0 : vo.getStatus();
	      //  String unitId = search.getSendUnitId();
	        Integer page = vo.getPage() == null ? 1 :  vo.getPage();
	        Integer rows =  vo.getRows() == null ? 10 :  vo.getRows();
         List<Case>  c=null;
	        
	        switch (status) {
	        case 0:
	          //  vo.setPropertyStatusCode("9911000000004");  //这里的未借调查询出来的是已入库的财物
	            PageHelper.startPage(page, rows);
	          //  property=propertyService.selectNotSendPropertyByManyConditions(vo);
	            c=caseService.findCaseByNotSecondmentProperty(vo);
	            break;  
	            
	        case 1:
	        	 vo.setPropertyStatusCode("9911000000006");
	        	   PageHelper.startPage(page, rows);
	        	  // property=propertyService.selectPropertyByManyConditions(vo);
	        	   c=caseService.findCaseByPropertyManyCondition(vo);
		            break;  
	        }
	        /*PageHelper.startPage(page, rows);
	        String    composite=java.net.URLDecoder.decode(search.getComposite(), "utf-8");
	        search.setComposite(composite);
	        //return exchangeService.getListByCondition(search);
	        return  propertyService.selectPropertyByManyConditions(vo);*/
	        return  c;
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    

	    /**
	     * 查询逾期借调信息
	     * 
	     * @return
	     */
	    @Transactional(readOnly = true)
	    public List<Secondment> getOverdues(SecondmentSearchForm form) {

	        LocalDate today = new LocalDate();
	        LocalDate ago = today.plusDays(DAYS_BEFORE_OVERDUE);
	        List<Secondment> list;
	        Integer status = form.getStatus() == null ? Integer.valueOf(0) : form.getStatus();

	        switch (status.intValue()) {
	        case 0:
	            // 查询已逾期、即将逾期记录
	            list = secondmentMapper.selectOverdues(ago.toDate());
	            PaginationUtil.startPageIfNeed(form);
	            break;
	        case 1:
	            // 查询未逾期记录
	            PaginationUtil.startPageIfNeed(form);
	            list = secondmentMapper.selectNotOverdues(ago.toDate());
	            break;
	        case 2:
	            // 查询已归还记录
	            PaginationUtil.startPageIfNeed(form);
	            list = secondmentMapper.selectReturned();
	            break;

	        default:
	            // 未知
	            list = new ArrayList<>();
	            break;
	        }

	        for (Secondment secondment : list) {
	            LocalDate date = new LocalDate(secondment.getExpectedReturnTime());
	            secondment.setOverdueDays(Days.daysBetween(date, today).getDays());
	        }
	        return list;
	    }

	    /**
	     * 更新归还时间
	     * 
	     * @return
	     */
	    public int updateReturnTimeByPropertyId(Secondment secondment) {
	       // secondment.setUpdateTime(new Date());
	        secondment.setReturned(Integer.valueOf(1));
	        return secondmentMapper.updateReturnTimeByPropertyId(secondment);
	    }

	    /**
	     * 借调延期
	     * 
	     * @param ids
	     *            借调记录id集合
	     * @param delay
	     *            延期信息
	     * @return
	     */
	    @Transactional
	    public int delay(Collection<String> ids, SecondmentDelay delay) {

	        for (String id : ids) {
	            Secondment secondment = secondmentMapper.selectByPrimaryKey(id);
	            Assert.notNull(secondment, "借调信息不存在");
	            Assert.isTrue(secondment.getReturned() == 0, "物品已归还");

	            delay.setId(null);
	            delay.setSecondmentId(id);
	            delay.setExpectedReturnTime(secondment.getExpectedReturnTime());
	            delay.setDelayTime(new Date());
	            secondmentDelayService.save(delay);

	            secondment.setExpectedReturnTime(delay.getDelayedReturnTime());
	            update(secondment);
	        }

	        return ids.size();
	    }
	    
	 
	    
	    
	    
	    
	    

}
