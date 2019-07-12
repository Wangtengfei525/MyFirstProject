package com.coolcloud.sacw.system.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.common.exception.InvalidArgumentException;
import com.coolcloud.sacw.common.exception.OperationFailedException;
import com.coolcloud.sacw.system.entity.Log;
import com.coolcloud.sacw.system.service.LogService;

/**
 * 全局异常处理
 * 
 * @author 袁永祥
 *
 * @date 2017年12月17日 下午9:48:11
 */
@RestControllerAdvice
public class ExceptionAdvice {

    @Autowired
    private LogService logService;

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {

        if (ex instanceof BindException) {
            return handleBindException((BindException) ex);
        }

        if (ex instanceof InvalidArgumentException) {
            return handleInvalidArgumentException((InvalidArgumentException) ex);
        }

        if (ex instanceof OperationFailedException) {
            return handleOperationFailedException((OperationFailedException) ex);
        }

        if (ex instanceof ServletRequestBindingException) {
            return handleServletRequestBindingException((ServletRequestBindingException) ex);
        }

        log(ex);
        return Result.failed("请求失败：" + ex.getMessage() != null ? ex.getMessage() : ex.getStackTrace()[0].getClassName());
    }

    /**
     * 处理请求绑定异常
     * 
     * @param ex
     * @return
     */
    private Result handleServletRequestBindingException(ServletRequestBindingException ex) {
        return Result.failed("请求失败：" + ex.getMessage());
    }

    /**
     * 处理数据绑定异常
     * 
     * @param ex
     *            绑定异常
     * @return
     */
    private Result handleBindException(BindException ex) {
        return Result.failed("请求失败：" + ex.getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 处理操作失败异常
     * 
     * @param ex
     * @return
     */
    private Result handleOperationFailedException(OperationFailedException ex) {
        return Result.failed("请求失败：" + ex.getMessage());
    }

    /**
     * 处理参数无效异常
     * 
     * @param ex
     * @return
     */
    private Result handleInvalidArgumentException(InvalidArgumentException ex) {
        return Result.failed("请求失败：" + ex.getMessage());
    }

    /**
     * 记录错误日志
     * 
     * @param ex
     */
    private void log(Exception ex) {
        final Logger logger = LoggerFactory.getLogger(ex.getClass());
        if (logger.isErrorEnabled()) {
            logger.error("错误：", ex);
        }
        StringBuilder sb = new StringBuilder("异常信息：")//
                .append(ex.getMessage());

        Log log = new Log();
        log.setType(2);
        log.setLogTime(new Date());
        String content = sb.length() > 1000 ? sb.substring(0, 1000) : sb.toString();
        log.setContent(content);
        logService.save(log);
    }
}
