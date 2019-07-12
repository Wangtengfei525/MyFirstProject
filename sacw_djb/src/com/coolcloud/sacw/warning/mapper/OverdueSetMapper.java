package com.coolcloud.sacw.warning.mapper;

import java.util.List;
import java.util.Map;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.warning.entity.OverdueSet;

public interface OverdueSetMapper extends BaseMapper<OverdueSet, String>{

	/**
	 * 通过财物状态编码查询逾期财物
	 * @param overdueSet 包含逾期财物状态编码的逾期设置实体类
	 * @return
	 */
	List<Map<String, Object>> queryPropertiesByCode(OverdueSet overdueSet);

	/**
	 * 按部门统计公安登记逾期案件和财物数量
	 * @param code
	 * @return
	 */
	List<Map<String, Object>> queryGadjyq(String code);
	
	/**
	 * 按部门统计公安移送逾期案件和财物数量
	 * @param code
	 * @return
	 */
	List<Map<String, Object>> queryGaysyq(String code);

}
