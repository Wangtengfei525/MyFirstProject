package com.coolcloud.sacw.annex.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.coolcloud.sacw.annex.entity.File1;
import com.coolcloud.sacw.annex.entity.File1Example;
import com.coolcloud.sacw.annex.service.File1Service;
import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.common.util.Assert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;



@RestController
@RequestMapping("/annex")
public class File1Controller {

	private static final String DATA_PREFFIX = "data:image/png;base64,";

    @Autowired
    //private File1Service file1Service;
     private File1Service  file1Service;
    /**
     * 查询案件相关附件 <br/>
     * 可用参数： <br/>
     * caseId：案件id <br/>
     * platCaseCode：平台案件编号 <br/>
     * 
     * 示例：http://localhost:8080/sacw_online/File1/list?caseId=sfsfsfsfsfsfsfs
     * 
     * @return
     */
    @GetMapping(value = "/list")
    public Result File1List(File1Example File1Example) {
        Integer page = File1Example.getPage();
        Integer rows = File1Example.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        
        List<File1> File1s = file1Service.getByExample(File1Example);
        PageInfo<File1> pageInfo = new PageInfo<>(File1s);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
        
    }

    /**
     * 删除附件
     * 
     * @param id
     *            附件id
     * @return
     */
    @PostMapping(value = "/delete")
    public Result delete(String id) {
        Integer num = file1Service.delete(id);
        return Result.success(num + "个附件删除成功！");
    }

    /**
     * 下载案件附件
     * 
     * @return
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> download(String id) {
        File1 File1 = file1Service.get(id);
        ResponseEntity<Resource> response;
        if (File1 == null) {
            response = ResponseEntity.ok().build();
        } else {
            String path = File1.getFile_path();
            String suffix = File1.getSuffix_name();
            if (suffix != null && !suffix.startsWith(".")) {
                suffix = "." + suffix;
            }
            Resource resource = new FileSystemResource(path);
            if (resource.exists()) {
                response = ResponseEntity.ok()//
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"file." + suffix + "\"")//
                        .body(resource);
            } else {
                response = ResponseEntity.ok().build();
            }
        }
        return response;
    }

    /**
     * 附件预览
     * 
     * @param id
     *            附件id
     * @return
     */
    @GetMapping("/preview")
    public ResponseEntity<Resource> preview(String id) {
        File1 File1 = file1Service.get(id);
        ResponseEntity<Resource> response;
        String contentType = "application/octet-stream";
        if (File1 == null) {
            response = ResponseEntity.ok().build();
        } else {
            String path = File1.getFile_path();

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
     * 上传交换记录附件 <br/>
     * 参数： <br/>
     * exchangeId：交换记录id（临时） <br/>
     * name：附件名称 <br/>
     * file ： multipart 文件<br/>
     * 
     * @param file
     * @param File1
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam(name = "file") MultipartFile[] file, File1 File1) {
        int num = file1Service.upload(file, File1);
        return Result.success(num + "个文件上传成功");
    }

    /**
     * 上传交换记录附件 <br/>
     * 参数： <br/>
     * exchangeId：交换记录id（临时） <br/>
     * name：附件名称 <br/>
     * file ： multipart 文件<br/>
     * 
     * @param file
     * @param File1
     */
    @PostMapping("/upload-base64")
    public Result uploadBase64(@RequestParam(name = "data") String data, File1 File1) {
        Assert.isTrue(data.startsWith(DATA_PREFFIX), "数据格式不正确");
        data = data.substring(DATA_PREFFIX.length());
        byte bytes[] = Base64Utils.decodeFromString(data);
        int num = file1Service.uploadBase64(bytes, File1);
        return Result.success(num + "个文件上传成功");
    }

    
    
    //借调 页面上传附件
    @PostMapping("/uploadAnnex")
    public Result uploadAnnex(@RequestParam(name = "file") MultipartFile[] file, File1 File1) {
        int num = file1Service.upload(file, File1);
        return Result.success(num + "个文件上传成功");
    }
    
    
    
    
    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

	
}
