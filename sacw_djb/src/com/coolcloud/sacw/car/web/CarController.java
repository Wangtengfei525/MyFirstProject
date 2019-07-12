package com.coolcloud.sacw.car.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.coolcloud.sacw.car.entity.CarConserve;
import com.coolcloud.sacw.car.entity.CarConserveExample;
import com.coolcloud.sacw.car.entity.CarConserveGroup;
import com.coolcloud.sacw.car.entity.CarConserveGroupExample;
import com.coolcloud.sacw.car.entity.CarConserveGroupSearchForm;
import com.coolcloud.sacw.car.entity.NewCarConserve;
import com.coolcloud.sacw.car.resolver.CarConserveResolver;
import com.coolcloud.sacw.car.service.CarConserveGroupService;
import com.coolcloud.sacw.car.service.CarConserveService;
import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.common.exception.OperationFailedException;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.common.util.PaginationUtil;
import com.coolcloud.sacw.system.entity.Unit;
import com.coolcloud.sacw.system.service.UnitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 车辆养护控制器
 * 
 * @author
 *
 */
@RestController
@RequestMapping("/carConserve")
public class CarController {

    @Autowired
    private CarConserveService carConserveService;

    @Autowired
    private CarConserveGroupService carConserveGroupService;

    @Autowired
    private CarConserveResolver carConserveResolver;
    @Autowired
    private UnitService unitService;

    /**
     * 获取车辆养护记录信息
     * 
     * @param id
     *            养护记录id
     * @return
     */
    @GetMapping("/get")
    public Result get(String id) {
        CarConserve conserve = carConserveService.get(id);
        return Result.success().add("conserve", conserve);
    }

    /**
     * 删除车辆养护记录信息
     * 
     * @param id
     *            养护记录id
     * @return
     */
    @PostMapping("/delete")
    public Result delete(String id) {
        return Result.success(carConserveService.delete(id) + "条记录删除成功");
    }

    /**
     * 车辆养护信息列表查询 page,rows 分页参数
     * 
     * @param conserveExample
     * @return
     */
    @GetMapping("/list")
    public Result list(CarConserveExample conserveExample) {
        Integer page = conserveExample.getPage();
        Integer rows = conserveExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        if (conserveExample.getEndTime() != null) {
            Date endTime = conserveExample.getEndTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endTime);
            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            conserveExample.setEndTime(calendar.getTime());
        }
        List<CarConserve> cars = carConserveService.getByExample(conserveExample);
        PageInfo<CarConserve> pageInfo = new PageInfo<>(cars);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 添加养护记录
     * 
     * @param carConserve
     * @return
     */
    @PostMapping(value = "/add")
    public Result add(@RequestBody NewCarConserve carConserve) {
    	System.out.println(carConserve.getConserveTextName()+"     "+carConserve.getConserveText());
        Integer num = carConserveService.addConserve(carConserve);
        return Result.success(num + "条记录添加成功！");
    }
    

    /**
     * 交还钥匙
     * 
     * @param id
     *            养护记录id
     * @return
     */
    @PostMapping(value = "/return-key")
    public Result returnKey(String id) {
        boolean success = carConserveService.returnKey(id);
        return Result.success(success ? "交还成功" : "交还失败");
    }

    /**
     * 按照每辆车的最新养护记录显示列表
     * 
     * @param conserveExample
     * @return
     */
    @GetMapping("/queryAll")
    public Object listInfo(CarConserveExample conserveExample) {
        Integer page = conserveExample.getPage();
        Integer rows = conserveExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<CarConserve> cars = carConserveService.infoTable(conserveExample);
        PageInfo<CarConserve> pageInfo = new PageInfo<>(cars);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 导出车辆养护息excel
     * 
     * @return
     */
    @RequestMapping("export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        carConserveService.export(request, response);
    }

    @RequestMapping("/dycar")
    public ModelAndView printReceipt(String qrCode) {
        ModelAndView mav = new ModelAndView("car/caryhxx");
        List<CarConserve> yhxx = null;
        yhxx = carConserveService.queryCaryhxx(qrCode);
        CarConserve queryCar = carConserveService.queryCar(qrCode);
        String carTime = carConserveService.carTime(qrCode);
        if (carTime == null || carTime == "") {
            String carTimes = carConserveService.carTimes(qrCode);
            mav.addObject("carTime", carTimes);
        } else {
            mav.addObject("carTime", carTime);
        }
        mav.addObject("CarMessage", queryCar.getCarMessage());
        mav.addObject("Location", queryCar.getPropertyLocation());
        mav.addObject("yhxx", yhxx);
        mav.addObject("date", new Date());
        return mav;
    }

    /**
     * 查询需要养护的车辆
     * 
     * @param range
     *            最近一次养护距当前天数
     * @return
     */
    @GetMapping(value = "/need-conserve")
    public Result needConserve(@RequestParam(name = "conserveContentCodes", required = false) String conserveContentCodes, @RequestParam(name = "unitId", required = false) String unitId) {
        List<CarConserve> conserves = carConserveService.needConserve(conserveContentCodes, unitId);
        return Result.success().total(conserves.size()).rows(conserves);
    }

    /**
     * 生成新的批量养护任务
     * 
     * @param conserveContentCodes
     * @param unitId
     * @param name
     * @return
     */
    @PostMapping(value = "/group/add")
    public Result addGroup(@RequestParam(name = "conserveContentCodes", required = false) String conserveContentCodes, //
            @RequestParam(name = "unitId", required = false) String unitId, //
            @RequestParam(name = "name", required = false) String name) {
        int num = carConserveService.newGroupConserve(conserveContentCodes, unitId, name);
        return Result.success(num == 0 ? "没有待养护的车辆信息" : num + "条养护信息已生成，请前往批量养护信息中查看");
    }

    /**
     * 查询养护分组
     * 
     * @return
     */
    @GetMapping(value = "/group/list")
    public Result group(CarConserveGroupSearchForm form) {

        PaginationUtil.startPageIfNeed(form);

        CarConserveGroupExample example = new CarConserveGroupExample();
        BeanUtils.copyProperties(form, example);
        List<CarConserveGroup> groups = carConserveGroupService.getByExample(example);
        PageInfo<CarConserveGroup> pageInfo = new PageInfo<>(groups);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 获取单个养护分组信息
     * 
     * @return
     */
    @GetMapping(value = "/group/get")
    public Result groupGet(@RequestParam String id) {
        CarConserveGroup group = carConserveGroupService.get(id);
        return Result.success().add("group", group);
    }

    /**
     * 删除养护分组
     * 
     * @return
     */
    @PostMapping(value = "/group/delete")
    public Result groupDelete(@RequestParam String id) {
        int num = carConserveGroupService.delete(id);
        return Result.success(num + "个养护组已删除");
    }

    /**
     * 导出养护组
     * 
     * @return
     */
    @GetMapping(value = "/group/export")
    public ModelAndView groupExport(@RequestParam String id) {
        ModelAndView mav = new ModelAndView("carConserveGroupXlsView");
        List<CarConserve> conserves;
        CarConserveGroup group = carConserveGroupService.get(id);
        if (group != null) {
            conserves = carConserveService.getByGroupId(id);
        } else {
            conserves = Collections.emptyList();
        }
        //获取标题
        Unit unit = unitService.selectByPrimaryKey(group.getUnitId());
        mav.addObject("conserves", conserves);
        mav.addObject("group", group);
        mav.addObject("unit", unit);
        return mav;
    }

    /**
     * 养护组完成养护
     * 
     * @param id
     *            养护组id
     * @param tempId
     *            附件临时id
     * @param file
     *            养护内容excel文件，该文件应有系统导出，并填写相关内容
     * @return
     */
    @PostMapping(value = "/group/complete")
    public Result groupComplete(@RequestParam("id") String id, //
            @RequestParam("file") MultipartFile file) {

        String filename = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            if (filename.endsWith(".xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (filename.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            throw new OperationFailedException("excel文件格式有误", e);
        }
        Assert.notNull(workbook, "请上传xls或xlsx文件");
        CarConserveGroup group = carConserveResolver.resolve(workbook);
        group.setId(id);

        int num = carConserveGroupService.complete(group);

        return Result.success(num + "条养护已完成");
    }

    /**
     * 根据养护内容生成养护历史记录
     * 
     * @return
     */
    @GetMapping("/generate")
    public Result generated() {
        carConserveService.generateHistory();
        return Result.success("生成成功");
    }

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

}
