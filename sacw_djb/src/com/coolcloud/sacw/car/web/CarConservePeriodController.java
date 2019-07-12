package com.coolcloud.sacw.car.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.car.entity.CarConservePeriod;
import com.coolcloud.sacw.car.entity.CarConservePeriodSetVo;
import com.coolcloud.sacw.car.service.CarConservePeriodService;
import com.coolcloud.sacw.common.Result;
import com.github.pagehelper.PageInfo;

/**
 * 车辆养护周期
 * 
 * @author xyz
 *
 * @date 2018年4月10日 下午4:46:06
 */
@RestController
@RequestMapping("/car-conserve/period")
public class CarConservePeriodController {

    @Autowired
    private CarConservePeriodService carConservePeriodService;

    @GetMapping("/all")
    public Result all() {
        List<CarConservePeriod> list = carConservePeriodService.getPeriodSettings(false);
        PageInfo<CarConservePeriod> pageInfo = new PageInfo<>(list);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    @PostMapping("/set")
    public Result set(@Validated @RequestBody CarConservePeriodSetVo[] sets) {
        List<CarConservePeriod> periods = new ArrayList<>();
        CarConservePeriod period;
        for (CarConservePeriodSetVo set : sets) {
            period = new CarConservePeriod();
            BeanUtils.copyProperties(set, period);
            periods.add(period);
        }
        carConservePeriodService.set(periods);
        return Result.success("设置成功");
    }

}
