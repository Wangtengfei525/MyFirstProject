package com.coolcloud.sacw.warning.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.common.exception.OperationFailedException;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.common.util.PaginationUtil;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.service.PropertyService;
import com.coolcloud.sacw.warning.WebService1;
import com.coolcloud.sacw.warning.WebService1Soap;
import com.coolcloud.sacw.warning.entity.Warning;
import com.coolcloud.sacw.warning.entity.WarningExample;
import com.coolcloud.sacw.warning.entity.WarningHandleForm;
import com.coolcloud.sacw.warning.entity.WarningPhoto;
import com.coolcloud.sacw.warning.entity.WarningSearchForm;
import com.coolcloud.sacw.warning.entity.WarningSetting;
import com.coolcloud.sacw.warning.entity.WarningSettingAddForm;
import com.coolcloud.sacw.warning.entity.WarningSettingUpdateForm;
import com.coolcloud.sacw.warning.service.WarningPhotoService;
import com.coolcloud.sacw.warning.service.WarningService;
import com.coolcloud.sacw.warning.service.WarningSettingService;
import com.github.pagehelper.PageInfo;

/**
 * 报警控制类
 * 
 * @author xyz
 *
 * @date 2018年4月17日 上午11:11:17
 */
@RestController
@RequestMapping("/warning")
public class WarningController {

    @Autowired
    private WarningService warningService;

    @Autowired
    private WarningPhotoService warningPhotoService;

    @Autowired
    private WarningSettingService warningSettingService;

    @Autowired
    private PropertyService propertyService;

    @Value("${app.warning.address}")
    private String serviceAddress = "http://192.168.1.167/WebService1.asmx";

    /**
     * 获取未处理异常出库财物数
     * 
     * @return
     */
    @GetMapping("/count")
    public Result count() {
        int count = warningService.getWarningCount();
        return Result.success().add("count", count);
    }

    /**
     * 获取报警记录数据列表<br/>
     * 查询参数
     * <ol>
     * <li>分页参数</li>
     * <li>handled：是否已处理，取值0，1</li>
     * </ol>
     * 
     * @return
     */
    @GetMapping("/list")
    public Result list(@Validated WarningSearchForm form) {

        PaginationUtil.startPageIfNeed(form);
        WarningExample example = new WarningExample();
        BeanUtils.copyProperties(form, example);

        List<Warning> list = warningService.getByExample(example);
        PageInfo<Warning> pageInfo = new PageInfo<>(list);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 获取报警记录数据
     * 
     * @return
     */
    @GetMapping("/get")
    public Result get(@RequestParam String id) {
        Warning warning = warningService.get(id);
        return Result.success().add("warning", warning);
    }

    /**
     * 获取报警记录关联的财物数据
     * 
     * @return
     */
    @GetMapping("/properties")
    public Result properties(@RequestParam String id) {
        List<Property> properties = propertyService.geByWarningId(id);
        return Result.success().total(properties.size()).rows(properties);
    }

    /**
     * 获取报警记录关联的照片数据
     * 
     * @return
     */
    @GetMapping("/photos")
    public Result photos(@RequestParam String id) {
        List<WarningPhoto> photos = warningPhotoService.getByByWarningId(id);
        return Result.success().total(photos.size()).rows(photos);
    }

    /**
     * 处理报警记录
     * 
     * @param id
     *            报警记录id
     * @return
     */
    @PostMapping("/handle")
    public Result handle(@Validated WarningHandleForm form) {

        Warning warning = new Warning();
        warning.setId(form.getId());
        warning.setHandled(1);
        warning.setRemark(form.getRemark());

        warningService.update(warning);

        return Result.success("处理成功");
    }

    /**
     * 处理所有报警记录
     * 
     * @return
     */
    @PostMapping("/handle/all")
    public Result handleAll(String remark) {
        Assert.isTrue(remark == null || remark.length() < 255, "备注应小于255个字符");
        int num = warningService.handleAll(remark);
        return Result.success(num + "个异常处理成功");
    }

    /**
     * 查询报警记录照片
     * 
     * @param id
     *            报警记录id
     * @return
     */
    @PostMapping("/photo/list")
    public Result photoList(@RequestParam String id) {
        List<WarningPhoto> list = warningPhotoService.getByByWarningId(id);
        PageInfo<WarningPhoto> pageInfo = new PageInfo<>(list);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 照片预览
     * 
     * @param id
     *            附件id
     * @return
     */
    @GetMapping("/photo/preview")
    public ResponseEntity<Resource> preview(String id) {
        WarningPhoto photo = warningPhotoService.get(id);
        ResponseEntity<Resource> response;
        String contentType = "application/octet-stream";
        if (photo == null) {
            response = ResponseEntity.ok().build();
        } else {
            String path = photo.getFilePath();
            Resource resource = new FileSystemResource(path);
            if (!resource.exists()) {
                resource = new ClassPathResource("/statics/default.jpg");
                path = resource.getFilename();
            }

            try {
                contentType = Files.probeContentType(Paths.get(path));
            } catch (IOException e) {

            }

            response = ResponseEntity.ok()//
                    .header(HttpHeaders.CONTENT_TYPE, contentType)//
                    .body(resource);
        }
        return response;
    }

    /**
     * 获取报警设置列表数据
     * 
     * @return
     */
    @GetMapping("/setting/list")
    public Result settingList() {
        List<WarningSetting> list = warningSettingService.getAll();
        PageInfo<WarningSetting> pageInfo = new PageInfo<>(list);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 获取单个报警设置数据
     * 
     * @return
     */
    @GetMapping("/setting/get")
    public Result settingGet(@RequestParam String id) {
        WarningSetting setting = warningSettingService.get(id);
        return Result.success().add("setting", setting);
    }

    /**
     * 添加报警设置
     * 
     * @return
     */
    @PostMapping("/setting/add")
    public Result settingAdd(@Validated WarningSettingAddForm form) {
        WarningSetting setting = new WarningSetting();
        BeanUtils.copyProperties(form, setting);
        int num = warningSettingService.save(setting);
        return Result.success(num + "个设置已添加");
    }

    /**
     * 更新报警设置
     * 
     * @return
     */
    @PostMapping("/setting/update")
    public Result settingUpdate(@Validated WarningSettingUpdateForm form) {
        WarningSetting setting = new WarningSetting();
        BeanUtils.copyProperties(form, setting);
        int num = warningSettingService.update(setting);
        return Result.success(num + "个设置已更新");
    }

    /**
     * 删除报警设置
     * 
     * @return
     */
    @PostMapping("/setting/delete")
    public Result settingDelete(@RequestParam String id) {
        int num = warningSettingService.delete(id);
        return Result.success(num + "个设置已删除");
    }

    /**
     * 开启报警扫描
     * 
     * @param id
     *            设置id
     * @return
     */
    @PostMapping("/start")
    public Result start(@RequestParam("id") String id) {
        Assert.notNull(warningSettingService.exists(id), "报警设置不存在：" + id);
        URL url;
        try {
            url = new URL(serviceAddress);
        } catch (MalformedURLException e) {
            throw new OperationFailedException("异常出库service地址不正确", e);
        }
        WebService1 service = new WebService1(url);
        WebService1Soap soap = service.getPort(WebService1Soap.class);
        String msg = soap.startCon(id);
        boolean success = "0".equals(msg);
        Result result = success ? Result.success("开启成功") : Result.failed("开启失败：" + msg);
        if(success) {//改变开启状态
        	WarningSetting war = warningSettingService.get(id);
        	/*war.setState("1");*/
        	warningSettingService.update(war);
        }
        return result;
    }

    /**
     * 停止报警扫描
     * 
     * @param id
     *            设置id
     * @return
     */
    @PostMapping("/stop")
    public Result stop(@RequestParam("id") String id) {
        Assert.notNull(warningSettingService.exists(id), "报警设置不存在：" + id);
        URL url;
        try {
            url = new URL(serviceAddress);
        } catch (MalformedURLException e) {
            throw new OperationFailedException("异常出库service地址不正确", e);
        }
        WebService1 service = new WebService1(url);
        WebService1Soap soap = service.getPort(WebService1Soap.class);
        String msg = soap.closeCon(id);
        boolean success = "0".equals(msg);
        Result result = success ? Result.success("关闭成功") : Result.failed("关闭失败：" + msg);
        if(success) {//改变开启状态
        	WarningSetting war = warningSettingService.get(id);
        /*	war.setState("0");*/
        	warningSettingService.update(war);
        }
        return result;
    }

    /**
     * 停止报警扫描
     * 
     * @return
     */
    @PostMapping("/stop-all")
    public Result stopAll() {
        URL url;
        try {
            url = new URL(serviceAddress);
        } catch (MalformedURLException e) {
            throw new OperationFailedException("异常出库service地址不正确", e);
        }
        WebService1 service = new WebService1(url);
        WebService1Soap soap = service.getPort(WebService1Soap.class);
        String msg = soap.closeAll();
        boolean success = "0".equals(msg);
        Result result = success ? Result.success("关闭成功") : Result.failed("关闭失败：" + msg);
        if(success) {//改变开启状态
        	List<WarningSetting> wars = warningSettingService.getByExample(null);
        	for(WarningSetting war:wars) {
        		/*war.setState("0");*/
        		warningSettingService.update(war);
        	}
        }
        return result;
    }

    /**
     * 报警开启状态
     * 
     * @return
     */
    @GetMapping("/status")
    public Result status() {
        URL url;
        try {
            url = new URL(serviceAddress);
        } catch (MalformedURLException e) {
            throw new OperationFailedException("异常出库service地址不正确", e);
        }
        WebService1 service = new WebService1(url);
        WebService1Soap soap = service.getPort(WebService1Soap.class);
        String msg = soap.getStatus();
        return Result.success(msg);
    }
}
