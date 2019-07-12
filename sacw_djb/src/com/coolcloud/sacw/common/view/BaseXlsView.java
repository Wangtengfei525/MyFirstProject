package com.coolcloud.sacw.common.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.coolcloud.sacw.common.exception.OperationFailedException;

/**
 * xls view
 * 
 * @author xyz
 *
 * @date 2018年4月14日 下午2:29:15
 */
public abstract class BaseXlsView extends AbstractXlsView {

    /**
     * classpath下的模板路径
     */
    private String template;

    protected String getTemplate() {
        return template;
    }

    protected void setTemplate(String template) {
        this.template = template;
    }

    @Override
    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
        Workbook workbook;
        if (template == null) {
            workbook = new HSSFWorkbook();
        } else {
            try {
                InputStream in = this.getClass().getResourceAsStream(template);
                workbook = new HSSFWorkbook(in);
            } catch (IOException e) {
                throw new OperationFailedException("模板" + template + "加载失败", e);
            }
        }
        return workbook;
    }

    /**
     * 获取工作表中的行，没有则创建
     * 
     * @param sheet
     *            工作表
     * @param index
     *            行号
     * @return
     */
    protected final Row getOrCreateRow(Sheet sheet, int index) {
        Row row = sheet.getRow(index);
        return row == null ? sheet.createRow(index) : row;
    }

    /**
     * 获取行中的列，没有则创建
     * 
     * @param row
     *            行
     * @param index
     *            列号
     * @return
     */
    protected final Cell getOrCreateCell(Row row, int index) {
        Cell cell = row.getCell(index);
        return cell == null ? row.createCell(index) : cell;
    }

    /**
     * 获取工作簿中的工作表，没有则创建
     * 
     * @param workbook
     *            工作簿
     * @param index
     *            序号
     * @return
     */
    protected final Sheet getOrCreateSheet(Workbook workbook, int index) {
        Sheet sheet = workbook.getSheetAt(index);
        return sheet == null ? workbook.createSheet() : sheet;
    }

}
