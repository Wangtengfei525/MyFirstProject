package com.coolcloud.sacw.car.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.coolcloud.sacw.car.entity.CarConserve;
import com.coolcloud.sacw.car.entity.CarConserveExample;
import com.coolcloud.sacw.common.BaseMapper;

public interface CarConserveMapper extends BaseMapper<CarConserve, String> {

    /**
     * 查询车辆养护信息列表(每个车辆最近的一条养护信息)
     * 
     * @param example
     * @return
     */
    List<CarConserve> queryConserveInfo(CarConserveExample example);

    List<Map<String, Object>> carexp();

    List<CarConserve> queryCaryhxx(String qrCode);

    CarConserve queryCar(String qrCode);

    String carTime(String qrCode);

    String carTimes(String qrCode);

    Integer updateCar(@Param("qrCode") String qrCode, @Param("code") String code);

    /**
     * 按分组id查询养护信息
     * 
     * @param groupId
     * @return
     */
    List<CarConserve> selectByGroupId(String groupId);

    /**
     * 按养护组id删除
     * 
     * @param groupId
     * @return
     */
    int deleteByGroupId(String groupId);

    /**
     * 按养护组查询养护id
     * 
     * @param groupId
     * @return
     */
    List<String> selectIdByGroupId(String groupId);

    /**
     * 按养护组查询完成养护的养护记录数
     * 
     * @param groupId
     * @return
     */
    int countCompletedByGroupId(String groupId);
}
