package com.coolcloud.sacw.operation.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;



import com.coolcloud.sacw.annex.entity.File1;
import com.coolcloud.sacw.annex.entity.File1Example;
import com.coolcloud.sacw.annex.service.File1Service;
import com.coolcloud.sacw.cases.entity.NewCase;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.common.util.UuidUtil;
import com.coolcloud.sacw.exchange.entity.Exchange;
import com.coolcloud.sacw.exchange.entity.PropertyExchange;
import com.coolcloud.sacw.exchange.mapper.ExchangeMapper;
import com.coolcloud.sacw.exchange.service.ExchangeService;
import com.coolcloud.sacw.operation.entity.NewCaseProperty;
import com.coolcloud.sacw.operation.entity.NewSearchVo;
import com.coolcloud.sacw.operation.entity.PutinVo;
import com.coolcloud.sacw.operation.entity.SaveLocationHistotry;
import com.coolcloud.sacw.operation.entity.SearchVo;
import com.coolcloud.sacw.photo.entity.Photo;
import com.coolcloud.sacw.photo.entity.PhotoExample;
import com.coolcloud.sacw.photo.service.PhotoService;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.mapper.PropertyMapper;
import com.coolcloud.sacw.property.service.PropertyService;
import com.coolcloud.sacw.share.service.ShareService;
import com.github.pagehelper.PageHelper;

/**
 * 入库操作服务类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月17日 下午3:05:07
 */
@Service
public class PutInService {
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
	    private SaveLocationHistotryService saveLocationHistotryService;
	    
	    @Autowired
	    public PropertyMapper propertyMapper;
	    
	    @Autowired
	    public ExchangeMapper exchangeMapper;
	    
	    /**
	     * 财物入库
	     * 
	     * @param exchange
	     * @param files
	     */
	    @Transactional
	    public Integer putInStorage(PutinVo putin) {
	        final String exchangeId = putin.getId();
	        final String tempId = putin.getTempId();
	        Assert.notNull(exchangeId, "交换记录id不能为空");
	        Assert.isTrue(StringUtils.hasText(tempId), "未指定附件临时记录id");

	        Exchange originExchange = exchangeService.get(exchangeId);
	        Assert.notNull(originExchange, "未找到对应的交换记录");
	        Assert.isTrue("1002001".equals(originExchange.getNodeCode()), "该交换记录并非入库申请记录");
	     //   Assert.isTrue("N".equals(originExchange.getReceiveState()), "该入库申请已被处理");

	        // 查找附件
	        List<File1> file1s = file1Service.getByExchangeId(tempId);
	        Assert.isTrue(file1s.size() != 0, "请上传附件");

	        List<Property> properties = propertyService.getByExchangeId(exchangeId);

	        // 检查财物是否均已分配库位
	        checkPropertiesState(properties);
	        //插入分配库位分配记录 
	        
	        // 更新财物状态信息
	        List<String> ids = new ArrayList<>();
	        List<SaveLocationHistotry> listSaveLocationHistotry = new ArrayList<>();
	        Date date = new Date();
	        for (Property property : properties) {
	            ids.add(property.getId());
	            SaveLocationHistotry saveLocationHistotry = new SaveLocationHistotry();
	            saveLocationHistotry.setCase_id(property.getCaseId());
	            /*saveLocationHistotry.setCase_name(property.getCaseName());
	            saveLocationHistotry.setCreateTime(date);*/
	            saveLocationHistotry.setDeleted(0);
	            saveLocationHistotry.setId(UuidUtil.get32UUID());
	            saveLocationHistotry.setKwbm(property.getKwbm());
	            saveLocationHistotry.setNumber(property.getNumber()==null?0:property.getNumber());
	            //saveLocationHistotry.setPermit_unit_mc(property.getPermitUnitMc()==null?"":property.getPermitUnitMc());
	            saveLocationHistotry.setProperty_id(property.getId());
	            saveLocationHistotry.setProperty_name(property.getPropertyName());
	            saveLocationHistotry.setProperty_split_id("");
	            saveLocationHistotry.setProperty_status("已入库");
	            saveLocationHistotry.setProperty_type(property.getPropertyType()==null?"":property.getPropertyType());
	          
	            //saveLocationHistotry.setRemake_number(property.getRemake_number()==null?0:property.getRemake_number());
	            saveLocationHistotry.setRemake_number(property.getNumber()==null?0:property.getNumber());
		               
	            
	           // saveLocationHistotry.setUpdateTime(date);
	            listSaveLocationHistotry.add(saveLocationHistotry);
	        }
	        saveLocationHistotryService.saveBatch(listSaveLocationHistotry);
	        propertyService.saveLogs(ids, "财物状态变更为已入库");
	        Integer num = propertyService.updateStatusByIds(ids, "已入库", "9911000000004");

	        // 更新入库申请交换记录
	        Exchange putInExchange = new Exchange();
	        putInExchange.setId(exchangeId);
	     //   putInExchange.setReceiveState("Y");
	        exchangeService.update(putInExchange);

	        // 插入入库反馈交换记录
	        Exchange re = exchangeService.createPutinReceipt(originExchange);
	        if (putin.getOperationTime() != null) {
	   //         re.setOperationTime(putin.getOperationTime());
	        }
	        re.setRemark(putin.getRemark());

	      //  re.setProperties(properties);
	        exchangeService.save(re);

	        // 更新附件信息
	        for (File1 file1 : file1s) {
	            File1 temp = new File1();
	            temp.setExchangeId(re.getId());
	            temp.setId(file1.getId());
	            file1Service.update(temp);
	        }
	        
	        
	        
	        if (shareEnabled) {
	            shareService.feedBack(re.getId());
	        }

	        return num;

	    }

	    /**
	     * 退回申请
	     * 
	     * @param exchange
	     */
	    @Transactional
	    public Integer sendBack(Exchange exchange) {
	        final String exchangeId = exchange.getId();
	        // 信息完整性检查
	        Assert.isTrue(StringUtils.hasText(exchangeId), "交换记录id不能为空");
	        Exchange originExchange = exchangeService.get(exchangeId);
	        Assert.notNull(originExchange, "未找到对应的交换记录");
	        Assert.isTrue("1002001".equals(originExchange.getNodeCode()), "该交换记录并非入库申请记录");
	   //     Assert.isTrue("N".equals(originExchange.getReceiveState()), "该入库申请已被处理");

	        List<Property> properties = propertyService.getByExchangeId(exchangeId);

	        // 更新财物状态为登记
	        List<String> ids = new ArrayList<>();
	        Property temp = new Property();
	        for (Property property : properties) {
	            ids.add(property.getId());
	            temp.setId(property.getId());
	            temp.setPropertyStatus("登记");
	            temp.setPropertyStatusCode("9911000000001");
	            propertyService.update(temp);
	        }
	        // propertyService.updateStatusByIds(ids, "登记", "9911000000001");

	        propertyService.saveLogs(ids, "财物状态变更为登记");

	        // 更新入库申请交换记录
	        Exchange putInExchange = new Exchange();
	        putInExchange.setId(exchangeId);
	     //   putInExchange.setReceiveState("Y");
	        exchangeService.update(putInExchange);

	        // 插入退回交换记录
	        Exchange be = exchangeService.createPutinBack(originExchange);
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
	           // photo.setExchange_lot(be.getExchangeBatch());
	            photoService.save(photo);
	        }

	        // 查询、添加附件
	        File1Example file1Example = new File1Example();
	        file1Example.setExchangeId(exchangeId);
	        List<File1> file1sss = file1Service.getByExample(file1Example);
	        for (File1 file1 : file1sss) {
	            file1.setId(null);
	           // file1.setExchange_batch(be.getExchangeBatch());
	           // file1.setExchange_lot(be.getExchangeBatch());
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
	     * 检查财物是否均入库和拆分
	     * 
	     * @return
	     */
	    private boolean checkPropertiesState(List<Property> properties) {
	        for (Property property : properties) {
	            // 无库位id认为没有分配库位
	            if (StringUtils.isEmpty(property.getKwbm())) {
	                throw new RuntimeException("物品：【" + property.getPropertyName() + "】尚未分配库位");
	            }
	        }
	        return true;
	    }

	    /**
	     * 获取入库记录列表
	     * 
	     * @return
	     */
	    @Transactional(readOnly = true)
	    public List<Exchange> getPutinList(SearchVo search) {
	        Integer status = search.getStatus() == null ? 0 : search.getStatus();
	      //  String unitId = search.getSendUnitId();
	        Integer page = search.getPage() == null ? 1 : search.getPage();
	        Integer rows = search.getRows() == null ? 10 : search.getRows();

	        switch (status) {
	       /* case 0:
	            search.setReceiveState("N");
	            search.setNodeCode("1002001");
	            search.setSendUnitId(unitId);
	            search.setReceiveUnitId(null);
	            break;*/
	        case 1:
	            search.setNodeCode("1002002");
	          //  search.setSendUnitId(null);
	          //  search.setReceiveUnitId(unitId);
	            break;
	       /* case 2:
	            search.setNodeCode("1002003");
	            search.setSendUnitId(null);
	            search.setReceiveUnitId(unitId);
	            break;*/
	            
	        }
	        PageHelper.startPage(page, rows);
	        return exchangeService.getListByCondition(search);
	    }
	    
	    /**
	     * write by yiyu
	     * 单机版获取未入库信息
	     * 
	     * @param search
	     * @return
	     */
	    @Transactional(readOnly = true)
	    public List<NewCaseProperty> getNewPutinList(NewSearchVo search) {
	     //   Integer status = search.getStatus() == null ? 0 : search.getStatus();
	      //  String unitId = search.getSendUnitId();
	        Integer page = search.getPage() == null ? 1 : search.getPage();
	        Integer rows = search.getRows() == null ? 10 : search.getRows();

	      /*  switch (status) {
	        case 0:
	          //  search.setReceiveState("N");
	          //  search.setNodeCode("1002001");
	        	
	        	//search.setPropertyStatusCode("");
	          //  search.setSendUnitId(unitId);
	           // search.setReceiveUnitId(null);
	        	search.setPropertyStatusCode("c");
	            break;
	        
	        
	        case 1:
	            search.setNodeCode("1002002");
	          //  search.setSendUnitId(null);
	          //  search.setReceiveUnitId(unitId);
	            break;
	        case 2:
	            search.setNodeCode("1002003");
	            search.setSendUnitId(null);
	            search.setReceiveUnitId(unitId);
	            break;
	            
	        }*/
	        PageHelper.startPage(page, rows);
	        return exchangeService.getNewListByCondition(search);
	    }
	    
	    @Transactional
	    public List<NewCase> getAllCaseList(NewSearchVo newSearchVo){
	    	Integer page = newSearchVo.getPage() == null ? 1 : newSearchVo.getPage();
	        Integer rows = newSearchVo.getRows() == null ? 10 : newSearchVo.getRows();
	        PageHelper.startPage(page, rows);
	        return exchangeService.getAllCaseList(newSearchVo);
	        
	        
	        
	    }
	    
	    
	    
	    /**
	     * write by yiyu
	     * 新财物入库
	     * 
	     * @param putinVo
	     * @return
	     */
	    @Transactional
	    public Integer newPutIn(PutinVo putinVo){
	    	final String caseId=putinVo.getId();
	    	final String tempId=putinVo.getTempId();
	    	/*
	    	Exchange originExchange=exchangeService.get(caseId);
	    	Assert.notNull(originExchange, "未找到对应的交换记录");
	        Assert.isTrue("1002001".equals(originExchange.getNodeCode()), "该交换记录并非入库申请记录");
	        */
	    	
	    	List<Property> propertylist=propertyMapper.selectByCaseId(caseId);
	    	System.out.println(propertylist.size());
	    	List<Property> putinpropertylist=new ArrayList<Property>();
	    	List<String> propertyids=new ArrayList<String>();
	    	for(int i=0;i<propertylist.size();i++){
	    		if (propertylist.get(i).getPropertyStatusCode().equals("9911000000003")) {
					putinpropertylist.add(propertylist.get(i));
				}
	    	}
	    	for(int j=0;j<putinpropertylist.size();j++){
	    		System.out.println(putinpropertylist.get(j).getId());
	    		propertyids.add(putinpropertylist.get(j).getId());
	    	}
	    	int num=propertyMapper.updateStatusByIds(propertyids,"已入库","9911000000004");
	    	
	    	Exchange putinexchange=new Exchange();
	    	putinexchange.setId(tempId);
	    	putinexchange.setCaseId(caseId);
	    	putinexchange.setProcessCode("1002");
	    	putinexchange.setNodeCode("1002002");
	    	System.out.println(putinVo.getOperationTime().toString());
	    	putinexchange.setOperationTime(putinVo.getOperationTime().toString());
	    	exchangeMapper.insertToExchange(putinexchange);
	    	
	    	for(int q=0;q<propertyids.size();q++){
	    		PropertyExchange propertyExchange=new PropertyExchange();
	    		propertyExchange.setId(UuidUtil.get32UUID());
	    		propertyExchange.setProperty_id(propertyids.get(q));
	    		propertyExchange.setProperty_status("已入库");
	    		propertyExchange.setProperty_status_code("9911000000004");
	    		exchangeMapper.insertToPropertyExchange(propertyExchange);
	    	}
	    	
	    	return num;
	    }
	    
	    
	    
}
