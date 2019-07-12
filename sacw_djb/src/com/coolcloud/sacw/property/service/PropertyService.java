package com.coolcloud.sacw.property.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.common.util.SystemUtil;
import com.coolcloud.sacw.common.util.TxmUtil;
import com.coolcloud.sacw.event.PropertyChangeEvent;
import com.coolcloud.sacw.operation.entity.SearchVo;
import com.coolcloud.sacw.property.entity.PrintCode;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.entity.PropertyExample;
import com.coolcloud.sacw.property.entity.PropertyLog;
import com.coolcloud.sacw.property.entity.PropertyVo;
import com.coolcloud.sacw.property.mapper.PropertyMapper;
import com.coolcloud.sacw.store.entity.Store;
import com.coolcloud.sacw.store.service.StoreService;

/**
 * 财物管理服务类
 * 
 * @author xyz
 *
 */
@Service
public class PropertyService extends BaseService<Property, String> {

    @Value("${app.printer-name}")
    private String printerName;

    /**
     * 二维码模板
     */
    private static final String QR_CODE_TEMPLATE = "08510100yyyyMMddHHmmssSSS";

    /**
     * 二维码最大字符长度
     */
    private static final int QR_CODE_MAX_LENGTH = 24;

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 查询条形码中相关信息(案件名称,财物名称,二维码,存储位置,财物数量)
     * 
     * @param id
     *            财物信息id
     * @return
     */
    public void printqrCode(String id) {
        Property property = propertyMapper.selectByPrimaryKey(id);
        Assert.notNull(property, "财物信息不存在");
        String qrcode = property.getQrCode();
        if (qrcode == null || qrcode.length() > QR_CODE_MAX_LENGTH) {
            SimpleDateFormat format = new SimpleDateFormat(QR_CODE_TEMPLATE);
            String code = format.format(new Date()).substring(0, 24);

            property.setQrCode(code);
           // property.setOriginQrCode(qrcode);

            Property temp = new Property();
            temp.setId(id);
            temp.setQrCode(code);
          //  temp.setOriginQrCode(qrcode);
            this.update(temp);

            saveLogs(id, "重新生成二维码：" + code);

       }
        
       PrintCode printCode = new PrintCode(property.getCaseName(), property.getPropertyName(), property.getQrCode(), property.getNumber(), property.getKwmc());
      
       // PrintCode printCode = new PrintCode(property.getClass(), property.getPropertyName(), property.getQrCode(), property.getNumber(), property.getKwmc());
       
        print(printCode);
           // print(null,null,null,null,null);
    }

    /**
     * 打印条形码
     * 
     * @param printCode
     * @return
     */
    public void print(PrintCode printCode) {
        TxmUtil p = new TxmUtil(printerName);
        String bar2 = printCode.getQrCode();
        String bar3Paper = "^FO44,380^BY2.0,3.5,200^BCN,^RFW,H,n,n,E^FD${data}^FS";

        String bar2Paper = "^FO64,360^BY2.5,3.5,200^BCN,,Y,N,N^FD${data}^FS";
        p.setBarcode(bar2, bar2Paper);
        if (bar2.length() == 25) {
            bar2 = bar2.substring(1, bar2.length());
            p.setBarcode(bar2, bar3Paper);
        }
        if (bar2.length() == 26) {
            bar2 = bar2.substring(2, bar2.length());
            p.setBarcode(bar2, bar3Paper);
        }
        if (bar2.length() == 24) {
            p.setBarcode(bar2, bar3Paper);
        }
        if ((printCode.getCaseName().length() > 14) && (printCode.getCaseName().length() <= 29)) {

            p.setText("案件名称:" + printCode.getCaseName().substring(0, 14), 70, 50, 40, 40, 24, 2, 2, 24);
            p.setText(printCode.getCaseName().substring(14, printCode.getCaseName().length()), 280, 100, 40, 40, 24, 2, 2, 24);
            p.setText("财物名称:" + printCode.getPropertyName(), 70, 150, 40, 40, 24, 2, 2, 24);
            p.setText("财物数量:" + printCode.getNumber(), 70, 200, 40, 40, 24, 2, 2, 24);
            if (printCode.getSaveLocationMc().length() >= 18) {
                p.setText("存放位置:" + printCode.getSaveLocationMc().substring(0, 15), 70, 250, 40, 40, 24, 2, 2, 24);
                p.setText(printCode.getSaveLocationMc().substring(15, printCode.getSaveLocationMc().length()), 280, 300, 40, 40, 24, 2, 2, 24);
            } else {
                p.setText("存放位置:" + printCode.getSaveLocationMc(), 70, 250, 40, 40, 24, 2, 2, 24);
            }
        } else if (printCode.getCaseName().length() >= 30) {
            p.setText("案件名称:" + printCode.getCaseName().substring(0, 14), 70, 50, 40, 40, 24, 2, 2, 24);
            p.setText(printCode.getCaseName().substring(14, 29), 280, 100, 40, 40, 24, 2, 2, 24);
            p.setText(printCode.getCaseName().substring(29, printCode.getCaseName().length()), 280, 150, 40, 40, 24, 2, 2, 24);
            p.setText("财物名称:" + printCode.getPropertyName(), 70, 200, 40, 40, 24, 2, 2, 24);
            p.setText("财物数量:" + printCode.getNumber(), 70, 250, 40, 40, 24, 2, 2, 24);
            p.setText("存放位置:" + printCode.getSaveLocationMc(), 70, 300, 40, 40, 24, 2, 2, 24);
        } else {
            p.setText("案件名称:" + printCode.getCaseName(), 70, 50, 40, 40, 24, 2, 2, 24);
            p.setText("财物名称:" + printCode.getPropertyName(), 70, 100, 40, 40, 24, 2, 2, 24);
            p.setText("财物数量:" + printCode.getNumber(), 70, 150, 40, 40, 24, 2, 2, 24);

            if (printCode.getSaveLocationMc().length() > 15) {
                p.setText("存放位置:" + printCode.getSaveLocationMc().substring(0, 14), 70, 200, 40, 40, 24, 2, 2, 24);
                p.setText(printCode.getSaveLocationMc().substring(14, printCode.getSaveLocationMc().length()), 280, 250, 40, 40, 24, 2, 2, 24);
            } else {
                p.setText("存放位置:" + printCode.getSaveLocationMc(), 70, 200, 40, 40, 24, 2, 2, 24);
            }
        }
        String zpl2 = p.getZpl();
        p.print(zpl2);
    }

    /**
     * 根据交换记录ID获取相应财物
     * 
     * @param
     * @return
     */
    @Transactional(readOnly = true)
    public List<Property> getByExchangeId(String exchangeId) {
        if (StringUtils.isEmpty(exchangeId)) {
            return new ArrayList<>();
        }
        return propertyMapper.selectByExchangeId(exchangeId);
    }

    @Override
    @Transactional
    public int delete(String id) {
        return 0;
    }

    /**
     * 分配入柜 <br/>
     * 
     * @param ids
     *            逗号分割的财物id
     * @param saveId
     *            存储位置id
     */
    @Transactional
    public Integer assignStore(String ids, String saveId) {
        Assert.isTrue(!StringUtils.isEmpty(ids), "未指定要分配入柜的财物");
        Assert.isTrue(!StringUtils.isEmpty(saveId), "未指定柜子");
        Set<String> list = StringUtils.commaDelimitedListToSet(ids);

        Store store = storeService.get(saveId);
        Assert.notNull(store, "柜子不存在");

        // 更新存储位置
        int num = propertyMapper.updateSaveIdBatch(list, saveId, store.getStoreUnitName());

        saveLogs(ids, "库位更为：" + store.getStoreUnitName());

        return num;

    }
    
  /*  @Transactional*/
    public Integer assignManyStore(String ids1, String saveIds) {
    	  Assert.isTrue(!StringUtils.isEmpty(ids1), "未指定要分配入柜的财物");
    	String a[]=saveIds.split(",");
    	
    	
    	
    	
    	String saveName1="";
    	
    	
    	Set<String> list = StringUtils.commaDelimitedListToSet(ids1);
         Integer  num=0;
         for(int i=0;i<a.length;i++)
         {
        	 Assert.isTrue(!StringUtils.isEmpty(a[i]), "未指定柜子");	
    		 Store store = storeService.get(a[i]);
    	     Assert.notNull(store, "柜子不存在");
         }
         
         Store store=null;
         String  storeUnitName="";
    	for(int i=0;i<a.length;i++)
    	{
    		/*Assert.isTrue(!StringUtils.isEmpty(a[i]), "未指定柜子");	*/
    		  store = storeService.get(a[i]);
    	     /*Assert.notNull(store, "柜子不存在");*/
    	     saveName1=saveName1+store.getStoreUnitName()+",";
    	     
    	     //获取多个柜子的存储位置
    	     storeUnitName=storeUnitName+store.getStoreUnitName()+",";
    	     
    	    
    	      /*saveLogs(ids1, "库位更为：" + store.getStoreUnitName());
 		      */
    	      num++;
    	      
    	     /*store.setStoreUnitName(storeUnitName);
   		      saveLogs(ids1, "库位更为:"+store.getStoreUnitName());
    	 */
    	      
    	     
    	      
    	} 
    	String storeUnitName1=storeUnitName.substring(0, storeUnitName.length()-1).trim();
    	store.setStoreUnitName(storeUnitName1);
		      saveLogs(ids1, "库位更为:"+store.getStoreUnitName());
	 
    	//当加到最后一个的时候，也有一个逗号，需要去掉  
    	String saveName=saveName1.substring(0, saveName1.length()-1).trim();
    //	propertyMapper.updateOneToManyStore(list, ","+saveIds, saveName);
    	
    	Property  property=propertyMapper.selectByPrimaryKey(ids1);
    	if(property.getKwbm()!=null)
    	{
    	
    	propertyMapper.updateOneToManyStore1(ids1, ","+saveIds, ","+saveName);
    	}
    	else
    	{
    		
    		propertyMapper.updateOneToManyStore1(ids1, saveIds, saveName);
    	}
    	
    	 
        return num;
    }
    
    
    
    
    
    
    
    
    

    /**
     * 批量更新财物状态信息
     * 
     * @param ids
     *            财物id List
     * @param propertyStatus
     *            财物状态 如 已入库
     * @param propertyStatusCode
     *            财物状态编码 如 9911000000004
     * @return
     */
    @Transactional
    public Integer updateStatusByIds(List<String> ids, String propertyStatus, String propertyStatusCode) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }

        return propertyMapper.updateStatusByIds(ids, propertyStatus, propertyStatusCode);
    }

    /**
     * 批量更新财物状态信息同时删除保管单位
     * 
     * @param ids
     *            财物id List
     * @param propertyStatus
     *            财物状态 如 已入库
     * @param propertyStatusCode
     *            财物状态编码 如 9911000000004
     * @return
     */
    @Transactional
    public Integer removeStatusByIds(List<String> ids, String propertyStatus, String propertyStatusCode) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }

        return propertyMapper.removeStatusByIds(ids, propertyStatus, propertyStatusCode);
    }

    @Transactional(readOnly = true)
    public List<Property> selectByStoreIds(List<String> ids) {
        return propertyMapper.selectByStoreIds(ids);
    }

    /**
     * 修改备注数量
     * 
     * @param id
     *            财物id
     * @param number
     *            备注数量
     */
    @Transactional
    public Integer updateRemarkNumber(String id, Integer number) {

        Property property = propertyMapper.selectByPrimaryKey(id);
        Assert.notNull(property, "财物不存在");

        property = new Property();
        property.setDeleted(null);
        property.setId(id);
      //  property.setRemake_number(number);
property.setNumber(number);
        return propertyMapper.updateByPrimaryKeySelective(property);
    }

    /**
     * 更新财物拆分状态
     * 
     * @param propertyId
     */
    @Transactional
    public void updateSplitedById(String propertyId) {
        propertyMapper.updateSplitedById(propertyId);
    }

    @Transactional(readOnly = true)
    public Property getByQrCode(String qrCode) {
        PropertyExample example = new PropertyExample();
        example.setQrCode(qrCode);
        List<Property> properties = propertyMapper.selectByExample(example);
        if (properties.size() == 1) {
            return properties.get(0);
        } else {
            return null;
        }
    }

    /**
     * 获取储物柜及其下所有子柜中的物品
     * 
     * @param kwbm
     *            储物柜id
     * @return
     */
    @Transactional(readOnly = true)
    public List<Property> getByKwbmIn(String storeId) {
        return propertyMapper.selectByKwbmIn(storeId);
    }
    
    /**
     * 获取储物柜及其下所有子柜中的车辆
     * 
     * @param kwbm
     *            储物柜id
     * @return
     */
    @Transactional(readOnly = true)
    public List<Property> getByKwbmInCar(String storeId) {
        return propertyMapper.selectByKwbmInCar(storeId);
    }

    /**
     * 清除存储位置
     * 
     * @param id
     */
    @Transactional
    public int clearSaveLocation(String id) {
        return propertyMapper.clearSaveLocation(id);
    }
    /**
     * 清除拆分存储位置
     * 
     * @param id
     */
    @Transactional
    public int clearSpliteSaveLocation(String id) {
        return propertyMapper.clearSpliteSaveLocation(id);
    }

    /**
     * 财物管理页面综合查询
     * 
     * @param example
     * @return
     */
    @Transactional(readOnly = true)
    public List<Property> getByComposite(PropertyExample example) {
        return propertyMapper.selectByComposite(example);
    }

    /**
     * 获取指定单位的在库车辆
     * 
     * @param example
     * @return
     */
    @Transactional(readOnly = true)
    public List<Property> getCarInStockByPermitUnit(String unitId) {
        return propertyMapper.selectCarInStockByPermitUnit(unitId);
    }

    public List<Property> selectInStock() {
        return propertyMapper.selectInStock();
    }

    @Transactional(readOnly = true)
    public List<Property> geByWarningId(String id) {
        return propertyMapper.selectByWarningId(id);
    }

    /**
     * 按库位查询在库物品
     * 
     * @param storeId
     * @return
     */
    @Transactional(readOnly = true)
    public List<Property> selectInStockByStoreId(String storeId) {
        return propertyMapper.selectInStockByStoreId(storeId);
    }

    /**
     * 记录财物变更信息
     * 
     * @param id
     *            财物id
     * @param content
     *            变更内容
     */
    public void saveLogs(String id, String content) {
        List<String> ids = new ArrayList<>();
        ids.add(id);
        saveLogs(ids, content);
    }

    /**
     * 批量记录财物变更信息
     * 
     * @param ids
     *            财物id集合
     * @param content
     *            变更内容
     */
    public void saveLogs(Collection<String> ids, String content) {
        List<PropertyLog> logs = new ArrayList<>();
        Date now = new Date();
        String username = SystemUtil.currentUser();
        PropertyLog log;
        for (String id : ids) {
            log = new PropertyLog();
            log.setPropertyId(id);
            log.setOperationContent(content);
            log.setOperationTime(now);
            log.setOperationUser(username);
            logs.add(log);
        }
        PropertyChangeEvent event = new PropertyChangeEvent(logs);
        applicationEventPublisher.publishEvent(event);
    }

	public List<Property> export(SearchVo search) {
		List<Property> propertys = propertyMapper.export(search);
		return propertys;
	}
	
	//更新财物 的信息
	public    void  updatePropertyById(String id)
	{
		
		propertyMapper.updatePropertyById(id);
		
		
	}
	
	//在借调页面多条件查询出财物的信息
	
	 @Transactional(readOnly = true)
	    public List<Property> selectPropertyByManyConditions(PropertyVo  vo) {
	        return propertyMapper.selectPropertyManyCondition(vo);
	    }
	

	 //
	 
	 //查询出未借调的财物
	/* @Transactional(readOnly = true)
	 public List<Property> selectNotSendPropertyByManyConditions(PropertyVo  vo) {
	      //  return propertyMapper.selectPropertyManyCondition(vo);
		 
		 return  propertyMapper.findNotSendProperty(vo);
	    }*/
	 
	 @Transactional(readOnly = true)
	 public List<Property> selectNotSendPropertyByManyConditions(PropertyVo  vo) {
	      //  return propertyMapper.selectPropertyManyCondition(vo);
		 
		 return  propertyMapper.findNotSendProperty(vo);
	    }
	 
	 
	 
	 
	 
	 //通过案件的id查询出该案件下面未被借调的财物
	 @Transactional(readOnly = true)
	 public List<Property> selectNotSendPropertyByCaseId(String  id) {
	      //  return propertyMapper.selectPropertyManyCondition(vo);
		 
		 return  propertyMapper.selectNotSecondPropertyByCaseId(id);
	    }
	 
	 //通过案件的id查询未归还的财物
	 @Transactional(readOnly = true)
	 public List<Property> selectNotBackPropertyByCaseId(String  id) {
	      //  return propertyMapper.selectPropertyManyCondition(vo);
		 
		 return  propertyMapper.selectNotBackPropertyByCaseId(id);
	    } 
	 
	 //通过案件的id查询未出库的财物
	 @Transactional(readOnly = true)
	 public List<Property> selectNotOutPropertyByCaseId(String  id) {
	      //  return propertyMapper.selectPropertyManyCondition(vo);
		 
		 return  propertyMapper.selectNotOutPropertyByCaseId(id);
	    } 
	
	 
	 
	 @Transactional(readOnly = true)
	 public List<Property> selectAllPropertyByCaseId(String  id) {
	      //  return propertyMapper.selectPropertyManyCondition(vo);
		 
		 return  propertyMapper.selectAllPropertyByCaseId(id);
		 
	    } 
	 
	
	 //通过案件的id查询处于借调中的财物
	 @Transactional(readOnly = true)
	 public List<Property> selectSecondingPropertyByCaseId(String  id) {
	      //  return propertyMapper.selectPropertyManyCondition(vo);
		 
		 return  propertyMapper.selectSecondingPropertyByCaseId(id);
		 
	    } 
	 
	 
	 
	 
	 
	

}
