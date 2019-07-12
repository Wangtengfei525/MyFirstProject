package com.coolcloud.sacw.exchange.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;



import com.coolcloud.sacw.annex.entity.File1;
import com.coolcloud.sacw.annex.entity.File1Example;
import com.coolcloud.sacw.annex.mapper.File1Mapper;
import com.coolcloud.sacw.annex.service.File1Service;
import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.cases.entity.NewCase;
import com.coolcloud.sacw.cases.mapper.NewCaseMapper;
import com.coolcloud.sacw.cases.service.CaseService;
import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.common.util.SystemUtil;
import com.coolcloud.sacw.common.util.UuidUtil;
import com.coolcloud.sacw.exchange.entity.Exchange;
import com.coolcloud.sacw.exchange.entity.ExchangeProperty;
import com.coolcloud.sacw.exchange.mapper.ExchangeMapper;
import com.coolcloud.sacw.exchange.mapper.ExchangePropertyMapper;
import com.coolcloud.sacw.operation.entity.NewCaseProperty;
import com.coolcloud.sacw.operation.entity.NewSearchVo;
import com.coolcloud.sacw.operation.entity.SearchVo;
import com.coolcloud.sacw.person.entity.Person;
import com.coolcloud.sacw.person.entity.PersonExample;
import com.coolcloud.sacw.person.service.PersonService;
import com.coolcloud.sacw.photo.entity.Photo;
import com.coolcloud.sacw.photo.entity.PhotoExample;
import com.coolcloud.sacw.photo.service.PhotoService;
import com.coolcloud.sacw.property.entity.NewProperty;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.mapper.PropertyMapper;
import com.coolcloud.sacw.property.mapper.NewPropertyMapper;
import com.coolcloud.sacw.property.mapper.PropertyMapper;
import com.coolcloud.sacw.property.service.PropertyService;

/**
 * 交换记录服务类
 * 
 * @author xyz
 *
 */
@Service
public class ExchangeService extends BaseService<Exchange, String> {

    @Autowired
    private ExchangeMapper exchangeMapper;

    @Autowired
    private ExchangePropertyMapper exchangePropertyMapper;
    
    

    @Autowired
    private CaseService caseService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PersonService personService;

    @Autowired
    private File1Service  file1Service;

    @Autowired
    private PhotoService photoService;
    
    @Autowired
    public NewCaseMapper newCaseMapper;
    
    @Autowired
    public NewPropertyMapper newPropertyMapper;
    
    @Autowired
    public File1Mapper file1Mapper;
    
    @Autowired
    public PropertyMapper propertyMapper;

    /**
     * 覆写默认删除方法，先删除关联的附件、财物信息，再删除自身
     */
    @Override
    @Transactional
    public int delete(String id) {
        if (StringUtils.isEmpty(id)) {
            return 0;
        }

        // 删除附件

        // 删除财物关联信息
        exchangePropertyMapper.deleteByExchangeId(id);

        // 删除自身
        return super.delete(id);
    }

    /**
     * 覆写默认保存方法，保存交换记录时保存与财物的关联信息
     */
    @Override
    public int save(Exchange exchange) {
        Integer num = super.save(exchange);
     /*   if (exchange.getProperties().size() > 0) {
            List<ExchangeProperty> eps = new ArrayList<>();
            for (Property property : exchange.getProperties()) {
                ExchangeProperty ep = new ExchangeProperty(exchange.getId(), property.getId());
                eps.add(ep);
            }
            exchangePropertyMapper.insertBatch(eps);
        }
        return num;*/
        
        return  0;
    }

    /**
     * 根据入库申请交换记录生成入库反馈交换记录
     * 
     * @param exchange
     *            入库申请交换记录
     * @return
     */
    public Exchange createPutinReceipt(Exchange exchange) {
        Assert.isTrue("1002001".equals(exchange.getNodeCode()), "不是入库申请交换记录");
        Exchange re = new Exchange();
        BeanUtils.copyProperties(exchange, re);
        Date date = new Date();

        re.setId(null);
        String JHBH = UuidUtil.get32UUID();
       // re.setExchangeBatch(JHBH);

        re.setNodeCode("1002002");
      //  re.setExchangeOperationCode("9917000000011");
        //re.setExchangeOperationName("入库反馈");
       // re.setDeviceOperationName("入库");
       // re.setDeviceOperationCode("9915000000004");
       // re.setOperationResult("入库成功");

      //  re.setReceiveUnitCode(exchange.getSendUnitCode());
       // re.setReceiveUnitName(exchange.getSendUnitName());
       // re.setReceiveUnitCode(exchange.getSendUnitCode());
   //     re.setReceiveUnitTypeCode(exchange.getSendUnitTypeCode());

    //    re.setOperationTime(date);
      //  re.setSendUnitCode("08510100");
      //  re.setSendUnitName("成都市保管中心");
     //   re.setSendUnitTypeCode("9901000000008");
     //   re.setSendPersonName(SystemUtil.currentUser());
      //  re.setSendTime(date);
        // re.setRemark(exchange.getRemark());

    //    re.setReceiveState("N");
        return re;
    }

    /**
     * 根据借调申请交换记录生成借调反馈交换记录
     * 
     * @param exchange
     *            借调申请交换记录
     * @return
     */
    public Exchange createSecondReceipt(Exchange exchange) {
        Assert.isTrue("1003001".equals(exchange.getNodeCode()), "不是借调申请交换记录");
        Exchange re = new Exchange();
        BeanUtils.copyProperties(exchange, re);
        Date date = new Date();
        re.setId(null);
        String JHBH = UuidUtil.get32UUID();
    //    re.setExchangeBatch(JHBH);

        re.setNodeCode("1003002");
   //     re.setExchangeOperationCode("9917000000009");
   //     re.setExchangeOperationName("借调反馈");
  //      re.setDeviceOperationName("借调");
  //      re.setDeviceOperationCode("9915000000005");
    //    re.setOperationResult("借调成功");

   //     re.setReceiveUnitCode(exchange.getSendUnitCode());
  //      re.setReceiveUnitName(exchange.getSendUnitName());
 //       re.setReceiveUnitCode(exchange.getSendUnitCode());
//        re.setReceiveUnitTypeCode(exchange.getSendUnitTypeCode());

  //      re.setOperationTime(date);
   //     re.setSendUnitCode("08510100");
     //   re.setSendUnitName("成都市保管中心");
      //  re.setSendUnitTypeCode("9901000000008");
     //   re.setSendPersonName(SystemUtil.currentUser());
     //   re.setSendTime(date);
     //   re.setRemark(exchange.getRemark());

    //    re.setReceiveState("N");
        return re;
    }

    /**
     * 根据归还申请交换记录生成归还反馈交换记录
     * 
     * @param exchange
     *            归还申请交换记录
     * @return
     */
    public Exchange createBackReceipt(Exchange exchange) {
        Assert.isTrue("1004001".equals(exchange.getNodeCode()), "不是归还申请交换记录");
        Exchange re = new Exchange();
        BeanUtils.copyProperties(exchange, re);
        Date date = new Date();
        re.setId(null);
        String JHBH = UuidUtil.get32UUID();
 //       re.setExchangeBatch(JHBH);

        re.setNodeCode("1004002");
   //     re.setExchangeOperationCode("9917000000010");
 //       re.setExchangeOperationName("归还反馈");
 //       re.setDeviceOperationCode("9915000000002");
   //     re.setDeviceOperationName("归还");
//        re.setOperationResult("归还成功");

     /*   re.setReceiveUnitCode(exchange.getSendUnitCode());
        re.setReceiveUnitName(exchange.getSendUnitName());
        re.setReceiveUnitCode(exchange.getSendUnitCode());
        re.setReceiveUnitTypeCode(exchange.getSendUnitTypeCode());

        re.setOperationTime(date);
        re.setSendUnitCode("08510100");
        re.setSendUnitName("成都市保管中心");
        re.setSendUnitTypeCode("9901000000008");
        re.setSendPersonName(SystemUtil.currentUser());
        re.setSendTime(date);
        re.setRemark(exchange.getRemark());

        re.setReceiveState("N");*/
        return re;
    }

    /**
     * 根据出库申请交换记录生成出库反馈交换记录
     * 
     * @param exchange
     *            出库申请交换记录
     * @return
     */
    public Exchange createOutReceipt(Exchange exchange) {
        Assert.isTrue("1011001".equals(exchange.getNodeCode()), "不是出库申请交换记录");
        Exchange re = new Exchange();
        BeanUtils.copyProperties(exchange, re);
        Date date = new Date();
        re.setId(null);
        String JHBH = UuidUtil.get32UUID();
       /* re.setExchangeBatch(JHBH);

        re.setNodeCode("1011002");
        re.setExchangeOperationCode("9917000000015");
        re.setExchangeOperationName("出库反馈");
        re.setDeviceOperationCode("9915000000009");
        re.setDeviceOperationName("出库");
        re.setOperationResult("出库成功");*/

       /* re.setReceiveUnitCode(exchange.getSendUnitCode());
        re.setReceiveUnitName(exchange.getSendUnitName());
        re.setReceiveUnitCode(exchange.getSendUnitCode());
        re.setReceiveUnitTypeCode(exchange.getSendUnitTypeCode());

        re.setOperationTime(date);
        re.setSendUnitCode("08510100");
        re.setSendUnitName("成都市保管中心");
        re.setSendUnitTypeCode("9901000000008");
        re.setSendPersonName(SystemUtil.currentUser());
        re.setSendTime(date);
        re.setRemark(exchange.getRemark());

        re.setReceiveState("N");*/
        return re;
    }

    /**
     * 根据入库申请交换记录生成入库退回交换记录
     * 
     * @param exchange
     *            入库申请交换记录
     * @return
     */
    public Exchange createPutinBack(Exchange exchange) {
        Assert.isTrue("1002001".equals(exchange.getNodeCode()), "不是入库申请交换记录");
        Exchange re = new Exchange();
        BeanUtils.copyProperties(exchange, re);
        Date date = new Date();
        re.setId(null);
        String JHBH = UuidUtil.get32UUID();
     /*   re.setExchangeBatch(JHBH);

        re.setNodeCode("1002003");
        re.setExchangeOperationCode("9917000000007");
        re.setExchangeOperationName("入库退回");
        re.setDeviceOperationName("发送");
        re.setDeviceOperationCode("9914000000002");
        re.setOperationResult("入库失败");

        re.setReceiveUnitCode(exchange.getSendUnitCode());
        re.setReceiveUnitName(exchange.getSendUnitName());
        re.setReceiveUnitTypeCode(exchange.getSendUnitTypeCode());

        re.setOperationTime(date);
        re.setSendUnitCode("08510100");
        re.setSendUnitName("成都市保管中心");
        re.setSendUnitTypeCode("9901000000008");
        re.setSendPersonName(SystemUtil.currentUser());
        re.setSendTime(date);*/
        re.setRemark(exchange.getRemark());

     //   re.setReceiveState("N");
        return re;
    }

    /**
     * 根据借调申请交换记录生成借调退回交换记录
     * 
     * @param exchange
     *            借调申请交换记录
     * @return
     */
    public Exchange createSecondBack(Exchange exchange) {
        Assert.isTrue("1003001".equals(exchange.getNodeCode()), "不是借调申请交换记录");
        Exchange re = new Exchange();
        BeanUtils.copyProperties(exchange, re);
        Date date = new Date();
        re.setId(null);
        String JHBH = UuidUtil.get32UUID();
       /* re.setExchangeBatch(JHBH);

        re.setNodeCode("1003003");
        re.setExchangeOperationCode("不知道代码是多少");
        re.setExchangeOperationName("借调退回");
        re.setDeviceOperationName("发送");
        re.setDeviceOperationCode("9914000000002");
        re.setOperationResult("借调失败");

        re.setReceiveUnitCode(exchange.getSendUnitCode());
        re.setReceiveUnitName(exchange.getSendUnitName());
        re.setReceiveUnitCode(exchange.getSendUnitCode());
        re.setReceiveUnitTypeCode(exchange.getSendUnitTypeCode());

        re.setOperationTime(date);
        re.setSendUnitCode("08510100");
        re.setSendUnitName("成都市保管中心");
        re.setSendUnitTypeCode("9901000000008");
        re.setSendPersonName(SystemUtil.currentUser());
        re.setSendTime(date);
        re.setRemark(exchange.getRemark());*/

      //  re.setReceiveState("N");
        return re;
    }

    /**
     * 根据归还申请交换记录生成归还反馈交换记录
     * 
     * @param exchange
     *            归还申请交换记录
     * @return
     */
    public Exchange createBackBack(Exchange exchange) {
        Assert.isTrue("1004001".equals(exchange.getNodeCode()), "不是归还申请交换记录");
        Exchange re = new Exchange();
        BeanUtils.copyProperties(exchange, re);
        Date date = new Date();
        re.setId(null);
        String JHBH = UuidUtil.get32UUID();
       /* re.setExchangeBatch(JHBH);

        re.setNodeCode("1004003");
        re.setExchangeOperationCode("不知道代码是多少");
        re.setExchangeOperationName("归还退回");
        re.setDeviceOperationCode("9914000000002");
        re.setDeviceOperationName("发送");
        re.setOperationResult("归还失败");

        re.setReceiveUnitCode(exchange.getSendUnitCode());
        re.setReceiveUnitName(exchange.getSendUnitName());
        re.setReceiveUnitCode(exchange.getSendUnitCode());
        re.setReceiveUnitTypeCode(exchange.getSendUnitTypeCode());

        re.setOperationTime(date);
        re.setSendUnitCode("08510100");
        re.setSendUnitName("成都市保管中心");
        re.setSendUnitTypeCode("9901000000008");
        re.setSendPersonName(SystemUtil.currentUser());
        re.setSendTime(date);
        re.setRemark(exchange.getRemark());

        re.setReceiveState("N");*/
        return re;
    }

    /**
     * 根据出库申请交换记录生成出库反馈交换记录
     * 
     * @param exchange
     *            出库申请交换记录
     * @return
     */
    public Exchange createOutBack(Exchange exchange) {
        Assert.isTrue("1011001".equals(exchange.getNodeCode()), "不是出库申请交换记录");
        Exchange re = new Exchange();
        BeanUtils.copyProperties(exchange, re);
        Date date = new Date();
        re.setId(null);
        String JHBH = UuidUtil.get32UUID();
      /*  re.setExchangeBatch(JHBH);

        re.setNodeCode("1011003");
        re.setExchangeOperationName("出库退回");
        re.setDeviceOperationCode("9914000000002");
        re.setDeviceOperationName("发送");
        re.setOperationResult("出库失败");

        re.setReceiveUnitCode(exchange.getSendUnitCode());
        re.setReceiveUnitName(exchange.getSendUnitName());
        re.setReceiveUnitCode(exchange.getSendUnitCode());
        re.setReceiveUnitTypeCode(exchange.getSendUnitTypeCode());

        re.setOperationTime(date);
        re.setSendUnitCode("08510100");
        re.setSendUnitName("成都市保管中心");
        re.setSendUnitTypeCode("9901000000008");
        re.setSendPersonName(SystemUtil.currentUser());
        re.setSendTime(date);*/
        re.setRemark(exchange.getRemark());

      //  re.setReceiveState("N");
        return re;
    }

    /**
     * 获取交换记录详情数据
     * 
     * 案件id
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getinfo(String id) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(id)) {
            return map;
        }
        /*
        Exchange exchange = this.get(id);
        if (exchange == null) {
            return map;
        }
        Case caze = null;
        if (StringUtils.hasText(exchange.getCaseId())) {
            caze = caseService.get(exchange.getCaseId());
        }
        List<Property> properties = propertyService.getByExchangeId(id);
        for (Property property : properties) {
            PhotoExample photoExample = new PhotoExample();
            photoExample.setPropertyId(property.getId());
            List<Photo> photos = photoService.getByExample(photoExample);
            property.setPhotos(photos);
        }
        
        PersonExample pe = new PersonExample();
        pe.setCaseId(caze.getId());
        List<Person> persons = personService.getByExample(pe);
        File1Example ae = new File1Example();
        ae.setExchangeId(id);
        List<File1> annexs = file1Service.getByExample(ae);

        map.put("exchange", exchange);
        map.put("caze", caze);
        map.put("properties", properties);
        map.put("persons", persons);
        map.put("annexs", annexs);
        */
        System.out.println(id);
        List<Exchange> exchange=exchangeMapper.selectExchangeByCaseId(id);
        if(exchange.get(0).getProcessCode().equals("1002")){
        	exchange.get(0).setNodeName("已入库");
        }
        else if(exchange.get(0).getProcessCode().equals("1003")){
        	exchange.get(0).setNodeName("借调");
        }
        else if(exchange.get(0).getProcessCode().equals("1011")){
        	exchange.get(0).setNodeName("出库");
        }
        else if(exchange.get(0).getProcessCode().equals("1004")){
        	exchange.get(0).setNodeName("归还");
        }
        
        
        
        
        
        
        NewCase caseinfo=newCaseMapper.selectCaseInfoById(id);
//        List<NewProperty> propertylist=newPropertyMapper.selectPropertyInfoByCaseId(id);
        List<NewProperty> propertylist=propertyMapper.selectPropertyInfoByCaseId(id);
        List<File1> filelist=file1Mapper.selectByCaseId(id);
        
        
        
        
        
        map.put("exchange", exchange.get(0));
        map.put("caze", caseinfo);
        map.put("properties", propertylist);
        map.put("annexs", filelist);
        

        return map;
    }
    
    
    
    /**
     * 通过案件的id获取交换记录详情
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, Object> info(String id) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(id)) {
            return map;
        }
        Exchange exchange = exchangeMapper.findExchangeByCaseId(id).get(0);
        if (exchange == null) {
            return map;
        }
        Case caze =  caze = caseService.get(id);
        //通过案件的id获取案件的相关信息
       /* if (StringUtils.hasText(exchange.getCaseId())) {
            caze = caseService.get(id);
        }*/
        
       
        List<Property> properties = propertyService.selectAllPropertyByCaseId(id);
       /* for (Property property : properties) {
           PhotoExample photoExample = new PhotoExample();
           photoExample.setPropertyId(property.getId());
            List<Photo> photos = photoService.getByExample(photoExample);
            property.setPhotos(photos);
        }       */
     //   PersonExample pe = new PersonExample();
       // pe.setCaseId(caze.getId());
       // List<Person> persons = personService.getByExample(pe);
      //  File1Example ae = new File1Example();
      //  ae.setExchangeId();
    //    List<File1> annexs = file1Service.getByExample(ae);
        map.put("caze", caze);
        map.put("properties", properties);
        map.put("exchange", exchange);
       
        
       // map.put("persons", persons);
      //  map.put("annexs", annexs);
        return map;
    }
    
    
    
    
    
    
    
    
    
    
    //这个方法是借调页面特用的，其他页面不做使用
    @Transactional(readOnly = true)
    public Map<String, Object> findInfoBySecondCondition(String id,String propertyStatusCode) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(id)) {
            return map;
        }
        Exchange exchange = null;
       /* if (exchange == null) {
            return map;
        }*/
        Case  caze = caseService.get(id);
        //通过案件的id获取案件的相关信息
       /* if (StringUtils.hasText(exchange.getCaseId())) {
            caze = caseService.get(id);
        }*/
        
       
   //     List<Property> properties = propertyService.selectAllPropertyByCaseId(id);
        List<Property> properties=null;
        if("yrkygh".equals(propertyStatusCode))
        {
        	//此时查询的是 未借调的财物    即已经 入库的 和 已经归还的财物
        	properties=propertyService.selectNotSendPropertyByCaseId(id);
        	List<Exchange>  ListExchange=exchangeMapper.findNotSecondExchangeByCaseId(id);
        	if(ListExchange.size()!=0)
        	{
        		exchange=ListExchange.get(0);
            	
        	}
        	else
        	{
        		exchange=null;
        	}
        	
        	
        }
        if("9911000000006".equals(propertyStatusCode))
        {
        	//此时查询的已借调的财物
        	properties=propertyService.selectSecondingPropertyByCaseId(id);
        	//此时查询的是借调产生的交换记录
        List<Exchange> ListExchange=exchangeMapper.findSecondExchangeByCaseId(id);
        	if(ListExchange.size()!=0)
        	{
        	exchange=ListExchange.get(0);	
        	}
        	else
        	{
        		exchange=null;
        	}
        }
        if (exchange == null) {
            return map;
        }
    
      /* for (Property property : properties) {
           PhotoExample photoExample = new PhotoExample();
           photoExample.setPropertyId(property.getId());
            List<Photo> photos = photoService.getByExample(photoExample);
            property.setPhotos(photos);
        }   */
     //   PersonExample pe = new PersonExample();
       // pe.setCaseId(caze.getId());
       // List<Person> persons = personService.getByExample(pe);
       File1Example ae = new File1Example();
        ae.setExchangeId(exchange.getId());
       List<File1> annexs = file1Service.getByExample(ae);
       
       
        map.put("caze", caze);
        map.put("properties", properties);
        map.put("exchange", exchange);
       
        
       // map.put("persons", persons);
       map.put("annexs", annexs);
        return map;
    }
    
    
    //这个方法是归还页面特用的，其他页面不做使用
    @Transactional(readOnly = true)
    public Map<String, Object> findInfoByBackCondition(String id,String propertyStatusCode) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(id)) {
            return map;
        }
        Exchange exchange = new Exchange();
       
        Case caze =  caze = caseService.get(id);
        //通过案件的id获取案件的相关信息
       /* if (StringUtils.hasText(exchange.getCaseId())) {
            caze = caseService.get(id);
        }*/
        
       
   //     List<Property> properties = propertyService.selectAllPropertyByCaseId(id);
        List<Property> properties=null;
        if("9911000000006".equals(propertyStatusCode))
        {
        	//此时查询的是 未归还的物品 ，查询出来就是已经借调的财物
        	properties=propertyService.selectNotBackPropertyByCaseId(id);
        	
        	List<Exchange> listexchange=exchangeMapper.findNotSecondExchangeByCaseId(id);
        	if(listexchange.size()!=0)
        	{
        		exchange=listexchange.get(0);
        	}
        	else
        	{
        		exchange=null;
        	}
        	
        //	exchange=exchangeMapper.findNotSecondExchangeByCaseId(id).get(0);
        	
        }
        if("9911000000005".equals(propertyStatusCode))
        {
        	//此时查询的已归还的财物
        	properties=propertyMapper.selectBackedPropertyByCaseId(id);
        	//此时查询的是归还产生的交换记录
        	List<Exchange> listexchange=exchangeMapper.findBackedExchangeByCaseId(id);
        	if(listexchange.size()!=0)
        	{
        		exchange=listexchange.get(0);
        	}
            
        	else {
        		exchange=null;
        	}
        	
        }
        
        if (exchange == null) {
            return map;
        }
       /* for (Property property : properties) {
           PhotoExample photoExample = new PhotoExample();
           photoExample.setPropertyId(property.getId());
            List<Photo> photos = photoService.getByExample(photoExample);
            property.setPhotos(photos);
        }       */
     //   PersonExample pe = new PersonExample();
       // pe.setCaseId(caze.getId());
       // List<Person> persons = personService.getByExample(pe);
      //  File1Example ae = new File1Example();
      //  ae.setExchangeId();
    //    List<File1> annexs = file1Service.getByExample(ae);
        
        File1Example ae = new File1Example();
        ae.setExchangeId(exchange.getId());
       List<File1> annexs = file1Service.getByExample(ae);
       
        map.put("caze", caze);
        map.put("properties", properties);
        map.put("exchange", exchange);   
       // map.put("persons", persons);
       map.put("annexs", annexs);
        return map;
    }
    
    
    
    
    
    
    
    //这个方法是页面特用的，其他页面不做使用
    @Transactional(readOnly = true)
    public Map<String, Object> findInfoByOutCondition(String id,String propertyStatusCode) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(id)) {
            return map;
        }
        Exchange exchange = new Exchange();
        
        Case caze =  caze = caseService.get(id);
        //通过案件的id获取案件的相关信息
       /* if (StringUtils.hasText(exchange.getCaseId())) {
            caze = caseService.get(id);
        }*/
        
       
   //     List<Property> properties = propertyService.selectAllPropertyByCaseId(id);
        List<Property> properties=null;
        if("yrkygh".equals(propertyStatusCode))
        {
        	//此时查询的是 未出库的财物  也就是已入库加上已归还的财物
        	properties=propertyService.selectNotOutPropertyByCaseId(id);
        	List<Exchange>  listexchange=exchangeMapper.findNotOutExchangeByCaseId(id);
        	
        	if(listexchange.size()!=0)
        	{
        		exchange=listexchange.get(0);
        	}
            
        	else {
        		exchange=null;
        	}
        	
        	
        }
        if("9911000000012".equals(propertyStatusCode))
        {
        	//此时查询的是该案件下已出库的财物
        	properties=propertyMapper.selectOutedPropertyByCaseId(id);
        	//此时查询的是归还产生的交换记录
       List<Exchange> 	listexchange =exchangeMapper.findOutedExchangeByCaseId(id);	     
            if(listexchange.size()!=0)
        	{
        		exchange=listexchange.get(0);
        	}   
        	else {
        		exchange=null;
        	}        
        }
           
        if (exchange == null) {
            return map;
        }
       /* for (Property property : properties) {
           PhotoExample photoExample = new PhotoExample();
           photoExample.setPropertyId(property.getId());
            List<Photo> photos = photoService.getByExample(photoExample);
            property.setPhotos(photos);
        }       */
     //   PersonExample pe = new PersonExample();
       // pe.setCaseId(caze.getId());
       // List<Person> persons = personService.getByExample(pe);
      //  File1Example ae = new File1Example();
      //  ae.setExchangeId();
    //    List<File1> annexs = file1Service.getByExample(ae);
        
        File1Example ae = new File1Example();
        ae.setExchangeId(exchange.getId());
       List<File1> annexs = file1Service.getByExample(ae);
       
        map.put("caze", caze);
        map.put("properties", properties);
        map.put("exchange", exchange);   
       // map.put("persons", persons);
        map.put("annexs", annexs);
        return map;
    }
    
    
    
    
    
    
    
    
    
    
    

    /**
     * 根据条件获取交换记录数据（用户前台页面展示的查询）
     * 
     * @param search
     * @return
     */
    @Transactional(readOnly = true)
    public List<Exchange> getListByCondition(SearchVo search) {
        return exchangeMapper.selectListByCondition(search);
    }
    
    /**
     * write by yiyu
     * 单机版获取未入库信息
     * 
     * @param search
     * @return
     */
    @Transactional(readOnly = true)
    public List<NewCaseProperty> getNewListByCondition(NewSearchVo search) {
        return exchangeMapper.selectNewPutIn(search);
    }
    
    /**
     * 获取所有案件信息
     * @param newSearchVo
     * @return
     */
    @Transactional
    public List<NewCase> getAllCaseList(NewSearchVo newSearchVo){
    	return exchangeMapper.selectAllCaseList(newSearchVo);
    }

    /**
     * 
     * @param propertyId
     * @return
     */
    @Transactional(readOnly = true)
    public List<Exchange> getByPropertyId(String propertyId) {
        return exchangeMapper.selectByPropertyId(propertyId);
    }

    @Transactional(readOnly = true)
    public   String selectCaseNameByProId(String id)
    {
    	
    	String caseName=exchangeMapper.selectCaseNameByProId(id);
    	return  caseName;
    }
    
    
    
    
    
    
    
}
