package com.coolcloud.sacw.ftp.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * FTP操作服务类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月20日 上午11:13:24
 */
@Service
public class FtpService {

    @Value("${app.ftp.url}")
    private String url;

    @Value("${app.ftp.port}")
    private Integer port;

    @Value("${app.ftp.username}")
    private String username;

    @Value("${app.ftp.password}")
    private String password;

    private FTPClient getClient() {
        FTPClient client = new FTPClient();
        try {
            client.connect(url, port);
            client.login(username, password);
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
        } catch (IOException e) {
            throw new RuntimeException("连接FTP服务器失败：" + e.getMessage(), e);
        }
        return client;
    }

    /**
     * 上传文件到ftp服务器
     * 
     * @param filename
     *            保存的文件名
     * @param path
     *            文件在本地的绝对路径
     */
    public void upload(String filename, String path) {
        FTPClient client = getClient();
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(path));
            client.storeFile(new String(filename.getBytes("utf-8"), "ISO-8859-1"), bis);
        } catch (Exception e) {
            throw new RuntimeException("上传文件到FTP服务器失败：" + e.getMessage(), e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                client.logout();
                client.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 下载ftp文件到本地路径
     * 
     * @param localPath
     *            本地文件路径
     * @param path
     *            ftp服务器文件名
     */
    public void download(String localPath, String path) {
        FTPClient client = getClient();
        OutputStream out = null;
        InputStream in = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(localPath));
            in = client.retrieveFileStream(path);
            byte[] bytes = new byte[8192];
            int length = 0;
            while ((length = in.read(bytes)) != -1) {
                out.write(bytes, 0, length);
            }
        } catch (Exception e) {
            throw new RuntimeException("从FTP服务器下载文件失败：" + e.getMessage(), e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                client.logout();
                client.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
