package com.coolcloud.sacw.statistics.mapper;

import java.util.List;
import java.util.Map;

import com.coolcloud.sacw.statistics.entity.ExchangeCountParam;

public interface StatisticsMapper {

    /**
     * 查询单位ID和名称
     * 
     * @return
     */

    List<Map<String, Object>> queryDW();

    /***************************** 财物统计相关 **********************************/

    /**
     * 按条件统计交换记录数（不去重）
     * 
     * @param param
     * @return
     */
    int countExchangeByCondition(ExchangeCountParam param);

    /**
     * 按条件统计交换财物数（不去重）
     * 
     * @param param
     * @return
     */
    int countExchangePropertyByCondition(ExchangeCountParam param);

    /**
     * 按条件统计案件数
     * 
     * @param param
     * @return
     */
    int countCaseByCondition(ExchangeCountParam param);

    /**
     * 按条件统计财物数
     * 
     * @param param
     * @return
     */
    int countPropertyByCondition(ExchangeCountParam param);

    /************************* 车辆统计相关 *************************************/

    /**
     * 按条件统计机动车交换数
     * 
     * @param param
     * @return
     */
    int countMotorVehicleExchangeByCondition(ExchangeCountParam param);

    /**
     * 按条件统计非机动车交换数
     * 
     * @param param
     * @return
     */
    int countNonMotorVehicleExchangeByCondition(ExchangeCountParam param);

    /**
     * 按条件统计机动车总数
     * 
     * @param param
     * @return
     */
    int countMotorVehicleByCondition(ExchangeCountParam param);

    /**
     * 按条件统计非机动车总数
     * 
     * @param param
     * @return
     */
    int countNonMotorVehicleByCondition(ExchangeCountParam param);
}
