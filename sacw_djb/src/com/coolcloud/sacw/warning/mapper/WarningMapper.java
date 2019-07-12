package com.coolcloud.sacw.warning.mapper;

import org.apache.ibatis.annotations.Param;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.warning.entity.Warning;

public interface WarningMapper extends BaseMapper<Warning, String> {

    /**
     * 按是否处理统计总数
     * 
     * @param handled
     *            是否处理，0，1
     * @return 符合条件总数
     */
    int countByHandled(Integer handled);

    /**
     * 更新所有handled值
     * 
     * @param handled
     * @return 更新的记录数
     */
    int updateHandled(@Param("handled") Integer handled, @Param("remark") String remark);

}