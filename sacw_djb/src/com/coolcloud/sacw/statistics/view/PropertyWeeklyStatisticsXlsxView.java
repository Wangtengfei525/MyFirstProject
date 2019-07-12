package com.coolcloud.sacw.statistics.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.coolcloud.sacw.common.view.BaseXlsxView;
import com.coolcloud.sacw.statistics.entity.PropertyStatistics;

/**
 * 涉案车辆周统计xlsx view
 * 
 * @author xyz
 *
 * @date 2018年4月14日 下午2:46:37
 */
@Component
public class PropertyWeeklyStatisticsXlsxView extends BaseXlsxView {

    private static final String TEMPLATE = "/template/xlsx/涉案物品周统计表.xlsx";

    private static final String TITLE_TEMPLATE_WITH_DATE = "涉案财物统计表（%tF - %tF）";

    private static final String[] CELL_NAMES = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M" };

    private static final String TITLE = "涉案财物统计表";

    private static final int ROW_CONTENT_START = 3;

    public PropertyWeeklyStatisticsXlsxView() {
        setTemplate(TEMPLATE);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"property-statistics.xlsx\"");

        List<PropertyStatistics> list = (List<PropertyStatistics>) model.get("statistics");
        Date startTime = (Date) model.get("startTime");
        Date endTime = (Date) model.get("endTime");

        Sheet sheet = getOrCreateSheet(workbook, 0);
        buildHeader(sheet, startTime, endTime);
        buildContent(sheet, list);
        buildCount(sheet, list.size());
    }

    private void buildCount(Sheet sheet, int size) {
        Row row = getOrCreateRow(sheet, ROW_CONTENT_START + size);
        row.getCell(0).setCellValue("合计");
        int start = ROW_CONTENT_START + 1;
        int end = ROW_CONTENT_START + size;

        String formula;
        for (int i = 1; i < 13; i++) {
            formula = "SUM(" + CELL_NAMES[i] + start + ":" + CELL_NAMES[i] + end + ")";
            row.getCell(i).setCellFormula(formula);
        }

        // 计算公式值
        sheet.getWorkbook().getCreationHelper().createFormulaEvaluator().evaluateAll();

    }

    private void buildHeader(Sheet sheet, Date startTime, Date endTime) {

        Row titleRow = getOrCreateRow(sheet, 0);
        String title;
        if (startTime == null || endTime == null) {
            title = TITLE;
        } else {
            title = String.format(TITLE_TEMPLATE_WITH_DATE, startTime, endTime);
        }
        getOrCreateCell(titleRow, 0).setCellValue(title);

    }

    private void buildContent(Sheet sheet, List<PropertyStatistics> list) {

        Row row, first = sheet.getRow(ROW_CONTENT_START);
        PropertyStatistics statistics;
        int size = list.size();

        short rowHeight = first.getHeight();

        Cell cell;
        List<Cell> cells = new ArrayList<>();
        Iterator<Cell> iterator = first.cellIterator();

        while (iterator.hasNext()) {
            cells.add(iterator.next());
        }

        for (int i = 1; i <= size; i++) {
            row = getOrCreateRow(sheet, ROW_CONTENT_START + i);
            row.setHeight(rowHeight);
            for (int j = 0; j < cells.size(); j++) {
                cell = getOrCreateCell(row, j);
                cell.setCellStyle(cells.get(j).getCellStyle());
            }
        }

        for (int i = 0; i < size; i++) {
            statistics = list.get(i);
            row = getOrCreateRow(sheet, ROW_CONTENT_START + i);

            getOrCreateCell(row, 0).setCellValue(statistics.getUnitName());
         //   getOrCreateCell(row, 1).setCellValue(statistics.getCaseIn());
            getOrCreateCell(row, 2).setCellValue(statistics.getPropertyIn());
          //  getOrCreateCell(row, 3).setCellValue(statistics.getCaseOut());
            getOrCreateCell(row, 4).setCellValue(statistics.getPropertyOut());
          //  getOrCreateCell(row, 5).setCellValue(statistics.getCaseSecondment());
            getOrCreateCell(row, 6).setCellValue(statistics.getPropertySecondment());
        //    getOrCreateCell(row, 7).setCellValue(statistics.getCaseBack());
            getOrCreateCell(row, 8).setCellValue(statistics.getPropertyBack());
            getOrCreateCell(row, 9).setCellValue(statistics.getCaseAll());
            getOrCreateCell(row, 10).setCellValue(statistics.getPropertyAll());
         //   getOrCreateCell(row, 11).setCellValue(statistics.getCaseAllOut());
            getOrCreateCell(row, 12).setCellValue(statistics.getPropertyAllOut());
        }

    }
}
