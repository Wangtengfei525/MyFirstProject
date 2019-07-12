package com.coolcloud.sacw.exchange.mapper;

import java.util.List;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.exchange.entity.ExchangeProperty;

/**
 * 交换记录财物关联信息DAO
 * 
 * @author xyz
 *
 */
public interface ExchangePropertyMapper extends BaseMapper<ExchangeProperty, String> {

    /**
     * 按交换记录id批量删除关联信息
     * 
     * @param exchangeIds
     * @return 删除的记录数
     */
    public int deleteByExchangeIdBatch(List<String> exchangeIds);

    /**
     * 按财物id批量删除关联信息
     * 
     * @param propertyIds
     * @return 删除的记录数
     */
    public int deleteByPropertyIdBatch(List<String> propertyIds);

    /**
     * 按交换记录id删除
     * 
     * @param id
     */
    public int deleteByExchangeId(String exchangeId);
    
   
    
    public    void   insertPropertyExchange(ExchangeProperty   exchangeProperty);
    
    

}