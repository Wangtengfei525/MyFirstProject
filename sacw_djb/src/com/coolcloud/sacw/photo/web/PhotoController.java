package com.coolcloud.sacw.photo.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.photo.entity.Photo;
import com.coolcloud.sacw.photo.entity.PhotoExample;
import com.coolcloud.sacw.photo.service.PhotoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 照片控制类
 * 
 * @author 王孝康
 *
 */
@Controller
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    /**
     * 查询所有照片
     * 
     * @param photoExample
     * @return
     */
    @GetMapping(value = "/list")
    public Result PhotoList(PhotoExample photoExample) {
        Integer page = 1;
        Integer rows = 20;
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<Photo> photo = photoService.getByExample(photoExample);
        PageInfo<Photo> pageInfo = new PageInfo<>(photo);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 下载附件照片
     * 
     * @param id
     *            照片id
     * @return
     */
    @GetMapping(value = "/download")
    public ResponseEntity<Resource> download(String id) {
        Photo photo = photoService.get(id);
        if (photo == null) {
            return ResponseEntity.ok().build();
        }
        String path = photo.getFile_path();
        String name = photo.getFile_name();
        String suffix = photo.getSuffix_name();
        Resource resource = new FileSystemResource(path);

        if (resource.exists()) {
            return ResponseEntity.ok()//
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + name + suffix)//
                    .body(resource);
        } else {
            return ResponseEntity.ok().build();
        }

    }

    /**
     * 照片预览
     * 
     * @param id
     *            照片id
     * @return
     */
    @GetMapping("/preview")
    public ResponseEntity<Resource> preview(String id) {
        Photo photo = photoService.get(id);
        ResponseEntity<Resource> response;
        String contentType = "application/octet-stream";
        if (photo == null) {
            response = ResponseEntity.ok().build();
        } else {
            String path = photo.getFile_path();
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

}
