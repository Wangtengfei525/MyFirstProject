package com.coolcloud.sacw.zip.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 解压缩服务类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月21日 下午9:04:27
 */
@Service
public class ZipService {

    /**
     * 压缩文件的存放目录
     */
    @Value("${app.zipdir}")
    private String zipdir;

    /**
     * 将文件压缩为zip <br/>
     * 不管原始文件是否在同一个目录，都将其压缩到同一个目录
     * 
     * @param zipFileName
     *            要生成的zip文件名称
     * @param paths
     *            要压缩的文件绝对路径列表
     * 
     * @return 生成的zip文件的绝对路径
     */
    public String zip(String zipFileName, List<String> paths) {
        byte[] bytes = new byte[1024];
        try {
            File dir = new File(zipdir);
            dir.mkdirs();
            File zipFile = new File(dir, zipFileName);
            List<String> entries = new ArrayList<>();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(zipFile));
            ZipOutputStream out = new ZipOutputStream(bos, Charset.forName("GBK"));
            for (String path : paths) {
                File file = new File(path);
                // 2017年12月14日 16点53分 文件不存在则跳过
                if (!file.exists()) {
                    continue;
                }
                if (entries.contains(file.getName())) {
                    continue;// 重复文件
                }
                InputStream in = new FileInputStream(file);
                int length = 0;
                ZipEntry entry = new ZipEntry(file.getName());
                entry.setTime(System.currentTimeMillis());
                out.putNextEntry(entry);
                entries.add(file.getName());
                while ((length = in.read(bytes)) != -1) {
                    out.write(bytes, 0, length);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            return zipFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
