package com.coolcloud.sacw.operation.mapper;

import java.util.Date;
import java.util.List;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.operation.entity.Secondment;

public interface SecondmentMapper extends BaseMapper<Secondment, String> {

    /**
     * 查询预期未归还的借调信息
     * 
     * @param date
     * @return
     */
    List<Secondment> selectOverdues(Date date);

    /**
     * 更新归还时间
     * 
     * @param secondment
     * @return
     */
    int updateReturnTimeByPropertyId(Secondment secondment);

    /**
     * 查询正常借调的借调信息（未逾期）
     * 
     * @param date
     * @return
     */
    List<Secondment> selectNotOverdues(Date date);

    /**
     * 查询已归还的借调信息
     * 
     * @return
     */
    List<Secondment> selectReturned();

}
