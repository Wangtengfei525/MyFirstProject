
package com.coolcloud.sacw.annex.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



import com.coolcloud.sacw.annex.entity.File1;
import com.coolcloud.sacw.annex.mapper.File1Mapper;
import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.file.entity.AbstractFile;
import com.coolcloud.sacw.file.service.FileService;

@Service
public class File1Service extends BaseService<File1, String> {
	@Autowired
    private FileService fileService;

    @Autowired
    private File1Mapper file1Mapper;

    /**
     * 上传交换记录附件
     * 
     * @param file
     * @param File1
     */
    @Transactional
    public int upload(MultipartFile[] files, File1 File1) {

        if (files == null || files.length == 0) {
            return 0;
        }

        Assert.isTrue(!StringUtils.isEmpty(File1.getExchangeId()), "未指定临时交换记录id");

        int count = 0;

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            AbstractFile af = fileService.save(file);
            File1.setFile_path(af.getPath());
            File1.setSuffix_name(af.getSuffix());
           /* File1.setExchange_num(File1.getExchange_id());// 设置临时值
            File1.setExchange_batch(File1.getExchange_id());
           */
            if (StringUtils.isEmpty(File1.getFile_name())) {
                File1.setFile_name(af.getName());
            }
            File1.setId(null);
            count += this.save(File1);
        }

        return count;

    }

    /**
     * 上传交换记录附件
     * 
     * @param bytes
     * @param File1
     * @return
     */
    @Transactional
    public int uploadBase64(byte bytes[], File1 File1) {

        Assert.isTrue(!StringUtils.isEmpty(File1.getExchangeId()), "未指定临时交换记录id");

        File1.setId(null);

        AbstractFile af = fileService.saveBytes(bytes);
        File1.setFile_path(af.getPath());
        File1.setSuffix_name(af.getSuffix());
       /* File1.setExchange_num(File1.getExchange_id());// 设置临时值
        File1.setExchange_batch(File1.getExchange_id());
       */
        
        if (StringUtils.isEmpty(File1.getFile_name())) {
            File1.setFile_name(af.getName());
        }
        File1.setId(null);
        return this.save(File1);

    }

    /**
     * 获取与财物相关的由保管中心上传的附件
     * 
     * @param propertyId
     *            财物id
     * @return
     */
    @Transactional(readOnly = true)
    public List<File1> getByPropertyId(String propertyId) {
        return file1Mapper.selectByPropertyId(propertyId);
    }

    /**
     * 按交换记录id获取附件
     * 
     * @param exchangeId
     * @return
     */
    @Transactional(readOnly = true)
    public List<File1> getByExchangeId(String exchangeId) {
        return file1Mapper.selectByExchangeId(exchangeId);
    }
	
	 
}
