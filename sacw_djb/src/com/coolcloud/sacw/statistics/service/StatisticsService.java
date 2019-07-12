package com.coolcloud.sacw.statistics.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.statistics.entity.CarStatistics;
import com.coolcloud.sacw.statistics.entity.ExchangeCountParam;
import com.coolcloud.sacw.statistics.entity.PropertyStatistics;
import com.coolcloud.sacw.statistics.entity.StatisticsUnit;
import com.coolcloud.sacw.statistics.mapper.StatisticsMapper;
import com.coolcloud.sacw.system.entity.Unit;
import com.coolcloud.sacw.system.service.UnitService;

/**
 * 统计服务类
 * 
 * @author xyz
 *
 */
@Service
public class StatisticsService {

    /**
     * 统计单位财物类型代码
     */
    private static final String TYPE_CODE_PROPERTY = "statistics_property";

    /**
     * 统计单位车辆类型代码
     */
    private static final String TYPE_CODE_CAR = "statistics_car";

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Autowired
    private StatisticsUnitService statisticsUnitService;

    @Autowired
    private UnitService unitService;

    /**
     * 财物统计
     * 
     * @param unitCode
     *            单位代码
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return 统计结果
     */
    @Transactional(readOnly = true)
    public List<PropertyStatistics> propertyStatistics(String code, Date startTime, Date endTime) {

        List<PropertyStatistics> list = new ArrayList<>();
        ExchangeCountParam param = new ExchangeCountParam();
        PropertyStatistics statistics;
        List<StatisticsUnit> units;
        if (StringUtils.hasText(code)) {
            units = new ArrayList<>();
            Unit unit = unitService.get(code);
            if (unit != null) {
                StatisticsUnit sunit = new StatisticsUnit();
                sunit.setUnitId(unit.getId());
                sunit.setUnitName(unit.getName());
                sunit.setContainsChildren(1);
                units.add(sunit);
            }
        } else {
            units = statisticsUnitService.getByType(TYPE_CODE_PROPERTY);
        }

        
        for (StatisticsUnit unit : units) {
        	
            statistics = new PropertyStatistics();
            statistics.setUnitName(unit.getUnitName());
            param.setContainChildren(unit.getContainsChildren());
            param.setOrganizerCode(unit.getUnitId());
            
            // 总的案件数量
            statistics.setCaseAll(statisticsMapper.countCaseByCondition(param));
          
      
            // 总的财物
            statistics.setPropertyAll(statisticsMapper.countPropertyByCondition(param)); 
            
        
            //财物总的入库的数量     跟查询的时间无关   只跟具体的某个单位有关    主要是从property_exchange表里面查数据   关联到了case表的单位类型编码字段
            param.setPropertyStatusCode("9911000000004");
            //这里跟财物的登记时间无关所以不用设置时间
            statistics.setPropertyAllIn(statisticsMapper.countExchangePropertyByCondition(param));
         
            
         // 财物总的出库的数量     跟查询的时间无关   只跟具体的某个单位有关    主要是从property_exchange表里面查数据   关联到了case表的单位类型编码字段
            param.setPropertyStatusCode("9911000000012");
            statistics.setPropertyAllOut(statisticsMapper.countExchangePropertyByCondition(param));
           
            
            // 财物某一段时间入库，出库，借调，归还的数量     跟查询的时间有关(故下面需要设置时间) 跟具体的某个单位有关    主要是从property_exchange表里面查数据   关联到了case表的单位类型编码字段  
            param.setStartTime(startTime); 
            param.setEndTime(endTime);     
      
            //某一段时间  根据单位查询入库的数量
            param.setPropertyStatusCode("9911000000004");
            statistics.setPropertyIn(statisticsMapper.countExchangePropertyByCondition(param));
         
            //某一段时间  根据单位查询出库的数量 
            param.setPropertyStatusCode("9911000000012");
            statistics.setPropertyOut(statisticsMapper.countExchangePropertyByCondition(param));
            
           //某一段时间  根据单位查询借调的数量  
            param.setPropertyStatusCode("9911000000006");
            statistics.setPropertySecondment(statisticsMapper.countExchangePropertyByCondition(param));
            
            
            //某一段时间  根据单位查询归还的数量  
            param.setPropertyStatusCode("9911000000005");
            statistics.setPropertyBack(statisticsMapper.countExchangePropertyByCondition(param));
            
            
            
            
            
       

            list.add(statistics);
        }

        return list;
    }

    /**
     * 车辆统计
     * 
     * @param unitCode
     *            单位代码
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return 统计结果
     */
    @Transactional(readOnly = true)
    public List<CarStatistics> carStatistics(String unitCode, Date startTime, Date endTime) {

        List<CarStatistics> list = new ArrayList<>();
        ExchangeCountParam param = new ExchangeCountParam();
        CarStatistics statistics;
        List<StatisticsUnit> units;

        if (StringUtils.hasText(unitCode)) {
            units = new ArrayList<>();
            Unit unit = unitService.get(unitCode);
            if (unit != null) {
                StatisticsUnit sunit = new StatisticsUnit();
                sunit.setUnitId(unit.getId());
                sunit.setUnitName(unit.getName());
                sunit.setContainsChildren(1);
                units.add(sunit);
            }
        } else {
            units = statisticsUnitService.getByType(TYPE_CODE_CAR);
        }

        for (StatisticsUnit unit : units) {
        	if("02510100".equals(unit.getUnitId())) {
        		unit.setContainsChildren(1);
        	}
            statistics = new CarStatistics();
            statistics.setUnitName(unit.getUnitName());

            param.setContainChildren(unit.getContainsChildren());
            param.setOrganizerCode(unit.getUnitId());

            /***** 日期范围设空开始统计总数 *****/
         //   param.setOperationTimeStart(null);
           // param.setOperationTimeEnd(null);
            // 机动车总数
            int carMount = statisticsMapper.countMotorVehicleByCondition(param);
            statistics.setMotorVehicleAll(carMount);
            // 非机动车总数
           int noCarMount = statisticsMapper.countNonMotorVehicleByCondition(param);
            statistics.setNonMotorVehicleAll(noCarMount);

          //  param.setReceiveUnitCode(unit.getUnitId());
            param.setNodeCode("1002002");
            // 机动车入库总数
            statistics.setMotorVehicleAllIn(statisticsMapper.countMotorVehicleExchangeByCondition(param));
            // 非机动车入库总数
            statistics.setNonMotorVehicleAllIn(statisticsMapper.countNonMotorVehicleExchangeByCondition(param));

            param.setNodeCode("1011002");
            // 机动车出库总数
            statistics.setMotorVehicleAllOut(statisticsMapper.countMotorVehicleExchangeByCondition(param));
            // 非机动车出库总数
            statistics.setNonMotorVehicleAllOut(statisticsMapper.countNonMotorVehicleExchangeByCondition(param));

            /***** 设置日期范围开始统计范围内数量 *****/
          //  param.setOperationTimeStart(startTime);
          //  param.setOperationTimeEnd(endTime);

            param.setNodeCode("1002002");
            // 机动车入库
            statistics.setMotorVehicleIn(statisticsMapper.countMotorVehicleExchangeByCondition(param));
            // 非机动车入库
            statistics.setNonMotorVehicleIn(statisticsMapper.countNonMotorVehicleExchangeByCondition(param));

            param.setNodeCode("1011002");
            // 机动车出库
            statistics.setMotorVehicleOut(statisticsMapper.countMotorVehicleExchangeByCondition(param));
            // 非机动车出库
            statistics.setNonMotorVehicleOut(statisticsMapper.countNonMotorVehicleExchangeByCondition(param));

            list.add(statistics);
        }

        return list;
    }

}