package com.coolcloud.sacw.property.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.operation.entity.SearchVo;
import com.coolcloud.sacw.property.entity.NewGetPropertyInfo;
import com.coolcloud.sacw.property.entity.NewProperty;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.entity.PropertyExample;
import com.coolcloud.sacw.property.entity.PropertyVo;

public interface PropertyMapper extends BaseMapper<Property, String> {
	
	
	List<NewGetPropertyInfo> selectPutIn(String case_id);
	

	/**
	 * 根据案件id查询财物信息
	 * @param case_id
	 * @return
	 */
	List<NewProperty> selectPropertyInfoByCaseId(String case_id);
	
	
	
	
	/**
	 * write by yiyu
	 * 入库查询案件所有的财物
	 * 
	 * @param case_id
	 * @return
	 */
	List<Property> selectByCaseId(String case_id);
	

    /**
     * 根据交换记录ID查询相应附件
     * 
     * @param exchangeI
     * @return
     */
    List<Property> selectByExchangeId(String exchangeId);

    /**
     * 按id批量更新财物存储位置
     * 
     * @param ids
     * @param saveId
     * @param saveName
     * @return
     */
    int updateSaveIdBatch(@Param("ids") Collection<String> ids, @Param("saveId") String saveId, @Param("saveName") String saveName);

        
    
    
    //updateSaveIdBatch
    /**
     * 
    * @Title: updateOneToManyStore
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param @param ids  物品的id
    * @param @return    设定文件
    * @return int    返回类型
    * @throws
     */
   int updateOneToManyStore(@Param("ids") Collection<String> ids, @Param("saveId") String saveId, @Param("saveName") String saveName);

   
   
   int updateOneToManyStore1(@Param("ids") String ids,@Param("saveId") String saveId,@Param("saveName") String saveName);

    
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
    int updateStatusByIds(@Param("ids") List<String> ids, @Param("propertyStatus") String propertyStatus, @Param("propertyStatusCode") String propertyStatusCode);

    /**
     * 批量更新财物状态信息同时清除保管单位
     * 
     * @param ids
     *            财物id List
     * @param propertyStatus
     *            财物状态 如 已入库
     * @param propertyStatusCode
     *            财物状态编码 如 9911000000004
     * @return
     */
    int removeStatusByIds(@Param("ids") List<String> ids, @Param("propertyStatus") String propertyStatus, @Param("propertyStatusCode") String propertyStatusCode);

    /**
     * 根据储物柜id集合查询其下所有财务信息
     * 
     * @param ids
     *            柜子id集合
     * @return
     */
    List<Property> selectByStoreIds(@Param("ids") List<String> ids);

    /**
     * 更新财物拆分状态
     * 
     * @param id
     *            财物id
     */
    void updateSplitedById(String id);

    /**
     * 查询储物柜及其下所有子柜中的物品
     * 
     * @param id
     *            库位id
     * @return
     */
    List<Property> selectByKwbmIn(String id);
    /**
     * 查询储物柜及其下所有子柜中的车辆
     * 
     * @param id
     *            库位id
     * @return
     */
    List<Property> selectByKwbmInCar(String id);

    /**
     * 清除财物存储位置
     * 
     * @param id
     * @return
     */
    int clearSaveLocation(String id);

    /**
     * 财物管理页面综合查询
     * 
     * @param example
     * @return
     */
    List<Property> selectByComposite(PropertyExample example);

    /**
     * 查询指定单位的在库车辆
     * 
     * @param unitId
     * @return
     */
    List<Property> selectCarInStockByPermitUnit(String unitId);

    /**
     * 查询所有在库物品
     * 
     * @return
     */
    List<Property> selectInStock();

    /**
     * 按报警记录查询物品信息
     * 
     * @return
     */
    List<Property> selectByWarningId(String warningId);

    /**
     * 查询柜子下的在库物品
     * 
     * @param storeId
     *            库位id
     * @return
     */
    List<Property> selectInStockByStoreId(String storeId);
    /**
     * 清除拆分财物存储位置
     * 
     * @param id
     * @return
     */
	int clearSpliteSaveLocation(String id);

	List<Property>  export(SearchVo search);
	
	//使用基础的功能和使用更加的能力也许是一件不错的事情吧实现更加的能力和你们的意识也许是一件不错的事情吧
	//使用基础的功能和使用
	
	//使用基础的能力和使用
	
	 String   selectCaseNameByProId(String  id);
	

	 
	 
	 
	 
	 void   updatePropertyById(String id);
	 
	 
	 List<Property>  selectPropertyManyCondition(PropertyVo  vo);
	 
	 //查询出未借调的财物的信息
	 List<Property>  findNotSendProperty(PropertyVo  vo);
	 
	 
	 
	 
	   //通过案件的id获取未被借调的财物
	    List<Property>  selectNotSecondPropertyByCaseId(String id);
	 
	    
	    
	 
	  
	 
	 
	 //通过案件的id查询这个案件下面所有的财物
	 List<Property>  selectAllPropertyByCaseId(String id);
	 
	 
	 
	 
	 //通过案件的id获取下面处于借调中的财物
	 List<Property> selectSecondingPropertyByCaseId(String id);
	 
	 
	 
	 //通过案件的id获取未被归还的财物   
	  List<Property> selectNotBackPropertyByCaseId(String id);
	 
	
	  //通过案件的id获取已经被归还的财物
	  List<Property>  selectBackedPropertyByCaseId(String id);
	  

	  //通过案件的id获取未出库的财物 
	 List<Property> selectNotOutPropertyByCaseId(String id);
	 
	 //通过案件的id获取已经出库的财物
	 List<Property> selectOutedPropertyByCaseId(String id);
	  
	
	  
	  
	 
	 
	 

}
