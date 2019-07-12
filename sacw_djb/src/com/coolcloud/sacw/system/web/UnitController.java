package com.coolcloud.sacw.system.web;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.system.entity.Unit;
import com.coolcloud.sacw.system.entity.UnitAddForm;
import com.coolcloud.sacw.system.entity.UnitExample;
import com.coolcloud.sacw.system.entity.UnitUpdateForm;
import com.coolcloud.sacw.system.service.UnitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 交换单位控制类 <br/>
 * 交换单位采用预排序遍历树结构与邻接表树结构结合，新增及删除操作请勿直接插入数据库 </br/>
 * 请使用UnitService提供的save及delete方法 <br/>
 * 新增请指定父节点id，否则将插入为根节点 <br/>
 * 若左右值结构混乱，使用UnitsService的rebuild方法重新建立左右值
 * 
 * @author 袁永祥
 *
 * @date 2017年12月21日 下午5:16:03
 */
@RestController
@RequestMapping("/system/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    /**
     * 获取所有交换单位树形结构
     * 
     * @return
     */
    @GetMapping("/tree")
    public Object tree() {
        return unitService.getTree();
    }
    
    /**
     * 获取财物类别树形结构
     * @return
     */
    @GetMapping("/propertytypetree")
    public Object propertyTypeTree() {
    	return unitService.getPropertyTypeTree();
    }

    /**
     * 添加单位
     * 
     * @return
     */
    @PostMapping("/add")
    public Result add(@Validated UnitAddForm form) {
        Unit unit = new Unit();
        BeanUtils.copyProperties(form, unit);
        return Result.success(unitService.add(unit) + "个单位已经添加");
    }

    /**
     * 修改单位
     * 
     * @return
     */
    @PostMapping("/update")
    public Result update(@Validated UnitUpdateForm form) {
        Unit unit = new Unit();
        BeanUtils.copyProperties(form, unit);
        return Result.success(unitService.update(unit) + "个单位已修改");
    }

    /**
     * 查询单位数据
     * 
     * @param unitExample
     * @return
     */
    @RequestMapping("/list")
    public Result list(UnitExample unitExample) {
        Integer page = unitExample.getPage();
        Integer rows = unitExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<Unit> units = unitService.getByExample(unitExample);
        PageInfo<Unit> pageInfo = new PageInfo<>(units);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 删除单位
     * 
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public Result delete(String id) {
        return Result.success(unitService.delete(id) + "个单位删除成功");
    }

    /**
     * 根据parentId结构重建树结构左右值
     *
     * @return
     */
    @RequestMapping("/build")
    public Result build() {
        unitService.rebuild();
        return Result.success("构建成功");
    }
}
