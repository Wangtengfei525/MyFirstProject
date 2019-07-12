package com.coolcloud.sacw.car.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.coolcloud.sacw.car.entity.CarConserve;
import com.coolcloud.sacw.car.entity.CarConserveGroup;
import com.coolcloud.sacw.common.view.BaseXlsView;
import com.coolcloud.sacw.system.entity.Unit;

/**
 * 车辆养护xls视图
 * 
 * @author xyz
 *
 * @date 2018年4月13日 下午1:05:14
 */
@Component
public class CarConserveGroupXlsView extends BaseXlsView {

    private static final String TEMPLATE = "/template/xls/养护登记表.xls";

    // private static final String CONSERVE_DATE_PARTTEN = "养护时间：yyyy年M月d日";
    private static final String CONSERVE_DATE_PARTTEN = "";

    private static final String CREATE_DATE_PARTTEN = "制表时间：yyyy年M月d日";

    private static final int ROW_CREATE_DATE = 0;
    

    /**
     * 日期行号
     */
    private static final int ROW_CONSERVE_DATE = 2;
    

    /**
     * 内容起始行号
     */
    private static final int ROW_CONTENT_START = 4;

    public CarConserveGroupXlsView() {
        setTemplate(TEMPLATE);
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<CarConserve> conserves = (List<CarConserve>) model.get("conserves");
        CarConserveGroup group = (CarConserveGroup) model.get("group");
        Unit unit = (Unit) model.get("unit");

        Sheet sheet = getOrCreateSheet(workbook, 0);
        HSSFFont f  = (HSSFFont) workbook.createFont();      
        f.setFontHeightInPoints((short) 26);//字号      
        f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗      

        HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();     
        style.setFont(f);      
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中 
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        
        createTitle(sheet,unit == null ? "政法委":unit.getName(),style);
        createDate(sheet, group == null ? new Date() : group.getConserveTime());
        createHeader(sheet);
        createContent(sheet, conserves);
        response.setHeader("Content-Disposition", "attachment; filename=\"car-conserve.xls\"");

    }

    /**
     * 内容
     * 
     * @param sheet
     * @param conserves
     */
    private void createContent(Sheet sheet, List<CarConserve> conserves) {
        Row content;
        CarConserve conserve;
        int size = conserves.size();

        // 以内容区域第一行为模板，生成内容区单元格

        Row row, first = sheet.getRow(ROW_CONTENT_START);
        short rowHeight = first.getHeight();

        Cell cell;
        List<Cell> cells = new ArrayList<>();
        Iterator<Cell> iterator = first.cellIterator();

        while (iterator.hasNext()) {
            cells.add(iterator.next());
        }

        for (int i = 1; i < size; i++) {
            row = getOrCreateRow(sheet, ROW_CONTENT_START + i);
            row.setHeight(rowHeight);
            for (int j = 0; j < cells.size(); j++) {
                cell = getOrCreateCell(row, j);
                cell.setCellStyle(cells.get(j).getCellStyle());
            }
        }

        // 填充单元格数据

        for (int i = 0; i < size; i++) {

            conserve = conserves.get(i);
            content = getOrCreateRow(sheet, ROW_CONTENT_START + i);
            getOrCreateCell(content, 0).setCellValue(i + 1);
            getOrCreateCell(content, 1).setCellValue(conserve.getCaseName());
            getOrCreateCell(content, 2).setCellValue(conserve.getPlatCaseCode());
            getOrCreateCell(content, 3).setCellValue(conserve.getPropertyLocation());
            getOrCreateCell(content, 4).setCellValue(conserve.getConserveTextName());
            getOrCreateCell(content, 5).setCellValue(conserve.getConserveRemark());
            getOrCreateCell(content, 6).setCellValue(conserve.getId());
        }
    }

    /**
     * 表头
     * 
     * @param sheet
     */
    private void createHeader(Sheet sheet) {

    }

    /**
     * 日期
     * 
     * @param sheet
     * @param date
     */
    private void createDate(Sheet sheet, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(CONSERVE_DATE_PARTTEN);
        Row row = getOrCreateRow(sheet, ROW_CONSERVE_DATE);
        getOrCreateCell(row, 0).setCellValue(format.format(date));

        format = new SimpleDateFormat(CREATE_DATE_PARTTEN);
        row = getOrCreateRow(sheet, ROW_CREATE_DATE);
        getOrCreateCell(row, 5).setCellValue(format.format(new Date()));
    }

    /**
     * 标题
     * 
     * @param sheet
     */
    private void createTitle(Sheet sheet,String name, HSSFCellStyle style) {
    	Row row = getOrCreateRow(sheet, 1);
    	Cell cell = getOrCreateCell(row, 0);
    	cell.setCellValue(name+"涉案车辆养护登记表");
    	cell.setCellStyle(style);
    }

}
