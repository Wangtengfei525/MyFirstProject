package com.coolcloud.sacw.common.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.coolcloud.sacw.common.exception.OperationFailedException;

/**
 * @author xyz
 *
 * @date 2018年4月14日 下午2:30:38
 */
public abstract class BaseXlsxView extends BaseXlsView {

    public BaseXlsxView() {
        setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Override
    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
        Workbook workbook;
        String template = getTemplate();

        if (template == null) {
            workbook = new XSSFWorkbook();
        } else {
            try {
                InputStream in = this.getClass().getResourceAsStream(template);
                workbook = new XSSFWorkbook(in);
            } catch (IOException e) {
                throw new OperationFailedException("模板" + template + "加载失败", e);
            }
        }
        return workbook;
    }
}
