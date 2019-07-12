package com.coolcloud.sacw.warning.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.warning.entity.OverdueSet;
import com.coolcloud.sacw.warning.service.OverdueSetService;
import com.github.pagehelper.PageInfo;

/**
 * 报警提醒、逾期设置统计控制器
 * 
 * @author
 *
 */
@RestController
@RequestMapping("overdue")
public class OverdueController {

    @Autowired
    private OverdueSetService overdueSetService;

    /**
     * 返回逾期设置信息
     * 
     * @param overdueSet
     * @return
     */
    @RequestMapping("/setting")
    public Object querySetting(OverdueSet overdueSet) {
        List<OverdueSet> list = overdueSetService.queryByCode(overdueSet);
        return list.get(0);
    }

    @RequestMapping("/add")
    public Result add(OverdueSet overdueSet) {
        Integer num = overdueSetService.save(overdueSet);
        return Result.success(num + "条记录添加成功！");
    }

    /**
     * 更新逾期设置时间周期
     * 
     * @return
     */
    @RequestMapping("/updateSetting")
    public Object update(OverdueSet overdueSet) {
        return overdueSetService.updateSetting(overdueSet);
    }

    /**
     * 通过财物状态编码返回逾期财物信息
     * 
     * @param overdueSet
     * @return
     */
    @RequestMapping("/selectPropertiesByCode")
    public Result propertiesByCode(OverdueSet overdueSet) {
        List<Map<String, Object>> object = overdueSetService.queryPropertiesByCode(overdueSet);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(object);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 导出逾期财物信息excel
     * 
     * @return
     */
    @RequestMapping("export")
    public void export(String code, HttpServletRequest request, HttpServletResponse response) {
        overdueSetService.export(code, request, response);
    }

    /**
     * 逾期统计
     * 
     * @return
     */
    @RequestMapping("statistics")
    public Object statistics() {
        return overdueSetService.statistics();
    }

}
