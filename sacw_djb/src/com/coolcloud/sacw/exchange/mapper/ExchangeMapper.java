package com.coolcloud.sacw.exchange.mapper;

import java.util.List;

import com.coolcloud.sacw.cases.entity.NewCase;
import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.exchange.entity.Exchange;
import com.coolcloud.sacw.exchange.entity.PropertyExchange;
import com.coolcloud.sacw.operation.entity.NewCaseProperty;
import com.coolcloud.sacw.operation.entity.NewSearchVo;
import com.coolcloud.sacw.operation.entity.SearchVo;

public interface ExchangeMapper extends BaseMapper<Exchange, String> {

    List<Exchange> selectListByCondition(SearchVo search);

    /**
     * 按财物id查询交换记录
     * 
     * @param propertyId
     *            财物id
     * @return 财物相关的交换记录
     * 
     * 
     * 
     * 
     * 
     */
    
    
  void insertToExchange(Exchange exchange);
    
   void insertToPropertyExchange(PropertyExchange propertyExchange);
    
    
    
    
    List<Exchange> selectByPropertyId(String propertyId);

    
    String   selectCaseNameByProId(String  id);
    
    

    
    List<NewCaseProperty> selectNewPutIn(NewSearchVo search);
    
    List<Exchange> selectExchangeByCaseId(String case_id);
    
    List<NewCase> selectAllCaseList(NewSearchVo newSearchVo);
    
    
    
    //通过案件名字查询其下面所有的交换记录详情
    List<Exchange>   findExchangeByCaseId(String id);
    
    
    
    //通过案件的名字查询未借调的交换记录
    List<Exchange>   findNotSecondExchangeByCaseId(String id);
    
    
  //通过案件的名字查询已经借调的交换记录
    
    List<Exchange> findSecondExchangeByCaseId(String id);
    
    
    
    //通过案件的名字查询未归还的交换记录
    List<Exchange> findNotBackExchangeByCaseId(String id);
    
    //通过案件的名字查询已归还的交换记录
    
    List<Exchange>  findBackedExchangeByCaseId(String id);
    
    
    
   //通过案件的名字查询未出库的交换记录  也就是查询出来的是 已入库和已归还的交换记录
    
    List<Exchange>  findNotOutExchangeByCaseId(String id);
    
    
  //通过案件的名字查询已经出库的交换记录  
    
    List<Exchange>  findOutedExchangeByCaseId(String id);
    
    
    
    
    

}