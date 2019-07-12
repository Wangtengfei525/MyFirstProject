package com.coolcloud.sacw.statistics.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.statistics.entity.CarStatistics;
import com.coolcloud.sacw.statistics.entity.PropertyStatistics;
import com.coolcloud.sacw.statistics.entity.StatisticsUnit;
import com.coolcloud.sacw.statistics.entity.StatisticsUnitAddVo;
import com.coolcloud.sacw.statistics.entity.StatisticsUnitExample;
import com.coolcloud.sacw.statistics.entity.StatisticsUnitUpdateVo;
import com.coolcloud.sacw.statistics.service.StatisticsService;
import com.coolcloud.sacw.statistics.service.StatisticsUnitService;
import com.github.pagehelper.PageInfo;

/**
 * 统计控制类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月30日 上午12:08:55
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private StatisticsUnitService statisticsUnitService;

	/**
	 * 财物统计
	 */
	@GetMapping("/property")
	public Result property(@RequestParam(name = "code", required = false) String code, //
			@RequestParam(name = "startTime", required = false) Date startTime, //
			@RequestParam(name = "endTime", required = false) Date endTime) {
		List<PropertyStatistics> list = statisticsService.propertyStatistics(code, startTime, endTime);
		return Result.success().total(list.size()).rows(list);
	}

	
	/**
	 * 导出涉案物品统计表
	 */
	@GetMapping("/property/export")
	public ModelAndView propertyExport(@RequestParam(name = "code", required = false) String code, //
			@RequestParam(name = "startTime", required = false) Date startTime, //
			@RequestParam(name = "endTime", required = false) Date endTime) {
		ModelAndView mav = new ModelAndView("propertyWeeklyStatisticsXlsxView");
		List<PropertyStatistics> list = statisticsService.propertyStatistics(code, startTime, endTime);
		mav.addObject("statistics", list);
		mav.addObject("startTime", startTime);
		mav.addObject("endTime", endTime);
		return mav;
	}

	/**
	 * 车辆统计
	 */
	@GetMapping("/car")
	public Result car(@RequestParam(name = "code", required = false) String code, //
			@RequestParam(name = "startTime", required = false) Date startTime, //
			@RequestParam(name = "endTime", required = false) Date endTime) {
		List<CarStatistics> list = statisticsService.carStatistics(code, startTime, endTime);
		return Result.success().total(list.size()).rows(list);
	}

	/**
	 * 导出车辆统计表
	 */
	@GetMapping("/car/export")
	public ModelAndView carExport(@RequestParam(name = "code", required = false) String code, //
			@RequestParam(name = "startTime", required = false) Date startTime, //
			@RequestParam(name = "endTime", required = false) Date endTime) {
		ModelAndView mav = new ModelAndView("carWeeklyStatisticsXlsxView");
		List<CarStatistics> list = statisticsService.carStatistics(code, startTime, endTime);
		mav.addObject("statistics", list);
		mav.addObject("startTime", startTime);
		mav.addObject("endTime", endTime);
		return mav;
	}

	/**
	 * 统计单位查询
	 */
	@GetMapping("/unit/list")
	public Result unitList(StatisticsUnitExample example) {
		List<StatisticsUnit> list = statisticsUnitService.getByExample(example);
		PageInfo<StatisticsUnit> pageInfo = new PageInfo<>(list);
		return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
	}

	/**
	 * 统计单位查询
	 */
	@PostMapping("/unit/delete")
	public Result unitDelete(@RequestParam String id) {
		int num = statisticsUnitService.delete(id);
		return Result.success(num + "个统计单位已经删除");
	}

	/**
	 * 查询统计单位信息
	 */
	@GetMapping("/unit/get")
	public Result unitUpdate(@RequestParam String id) {
		StatisticsUnit unit = statisticsUnitService.get(id);
		return Result.success().add("unit", unit);
	}

	/**
	 * 添加统计单位
	 */
	@PostMapping("/unit/add")
	public Result unitAdd(@Validated StatisticsUnitAddVo vo) {
		StatisticsUnit unit = new StatisticsUnit();
		BeanUtils.copyProperties(vo, unit);
		int num = statisticsUnitService.add(unit);
		return Result.success(num + "个统计单位已添加");
	}

	/**
	 * 更新统计单位
	 */
	@PostMapping("/unit/update")
	public Result unitUpdate(@Validated StatisticsUnitUpdateVo vo) {
		StatisticsUnit unit = new StatisticsUnit();
		BeanUtils.copyProperties(vo, unit);
		int num = statisticsUnitService.update(unit);
		return Result.success(num + "个统计单位已更新");
	}

	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}

}