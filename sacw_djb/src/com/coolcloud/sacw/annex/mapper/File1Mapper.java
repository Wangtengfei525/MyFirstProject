
package com.coolcloud.sacw.annex.mapper;
import java.util.List;


import com.coolcloud.sacw.annex.entity.File1;
import com.coolcloud.sacw.annex.entity.File1Example;
import com.coolcloud.sacw.common.BaseMapper;


public interface File1Mapper    extends BaseMapper<File1, String>{
	List<File1> selectByPropertyId(String propertyId);

    /**
     * 按交换记录id查询
     * 
     * @param exchangeId
     * @return
     */
    List<File1> selectByExchangeId(String exchangeId);
    
    
    List<File1> selectByCaseId(String case_id);
          
    
    
}
