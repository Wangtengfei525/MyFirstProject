package com.coolcloud.sacw.file.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.coolcloud.sacw.common.util.UuidUtil;
import com.coolcloud.sacw.file.entity.AbstractFile;

/**
 * 文件操作服务
 * 
 * @author 袁永祥
 *
 * @date 2017年8月19日 上午11:32:02
 */
@Service
public class FileService {

    @Value("${app.upload-path}")
    private String path;

    /**
     * 保存文件到本地硬盘
     * 
     * @param multipartFile
     * @return 文件在本地的绝对路径
     */
    public AbstractFile save(MultipartFile multipartFile) {
        final String ofn = multipartFile.getOriginalFilename();
        final String suffix = ofn.substring(ofn.lastIndexOf(".") + 1, ofn.length());
        final SimpleDateFormat format = new SimpleDateFormat(File.separator + "yyyy" + File.separator + "MM" + File.separator + "dd");
        File folder = new File(path, format.format(new Date()));
        File file = new File(folder, UuidUtil.get32UUID() + "." + suffix);
        file.getParentFile().mkdirs();
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
        AbstractFile af = new AbstractFile();
        af.setName(ofn);
        af.setSuffix(suffix);
        af.setPath(file.getAbsolutePath());
        return af;
    }

    /**
     * 保存文件到本地硬盘
     * 
     * @param multipartFile
     * @return 文件在本地的绝对路径
     */
    public AbstractFile saveBytes(byte[] bytes) {
        String suffix = "png";
        final SimpleDateFormat format = new SimpleDateFormat(File.separator + "yyyy" + File.separator + "MM" + File.separator + "dd");
        File folder = new File(path, format.format(new Date()));
        File file = new File(folder, UuidUtil.get32UUID() + "." + suffix);
        file.getParentFile().mkdirs();
        try {
            FileCopyUtils.copy(bytes, file);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
        AbstractFile af = new AbstractFile();
        af.setName("image.png");
        af.setSuffix(suffix);
        af.setPath(file.getAbsolutePath());
        return af;
    }
}
