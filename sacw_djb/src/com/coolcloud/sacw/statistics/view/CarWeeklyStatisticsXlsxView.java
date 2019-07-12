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
import com.coolcloud.sacw.statistics.entity.CarStatistics;

/**
 * 涉案车辆周统计xlsx view
 * 
 * @author xyz
 *
 * @date 2018年4月14日 下午2:46:37
 */
@Component
public class CarWeeklyStatisticsXlsxView extends BaseXlsxView {

    private static final String TEMPLATE = "/template/xlsx/涉案车辆周统计表.xlsx";

    private static final String TITLE_TEMPLATE_WITH_DATE = "涉案车辆统计表（%tF - %tF）";

    private static final String[] CELL_NAMES = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };

    private static final String TITLE = "涉案车辆统计表";

    private static final int ROW_CONTENT_START = 3;

    public CarWeeklyStatisticsXlsxView() {
        setTemplate(TEMPLATE);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"car-statistics.xlsx\"");

        List<CarStatistics> list = (List<CarStatistics>) model.get("statistics");
        Date startTime = (Date) model.get("startTime");
        Date endTime = (Date) model.get("endTime");

        Sheet sheet = getOrCreateSheet(workbook, 0);
        buildHeader(sheet, startTime, endTime);
        buildContent(sheet, list);
        buildCount(sheet, list.size());
    }

    /**
     * 标题
     * 
     * @param sheet
     * @param startTime
     * @param endTime
     */
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

    /**
     * 内容区
     * 
     * @param sheet
     * @param list
     */
    private void buildContent(Sheet sheet, List<CarStatistics> list) {

        Row row, first = sheet.getRow(ROW_CONTENT_START);
        CarStatistics statistics;
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
            getOrCreateCell(row, 1).setCellValue(statistics.getMotorVehicleIn());
            getOrCreateCell(row, 2).setCellValue(statistics.getMotorVehicleOut());
            getOrCreateCell(row, 3).setCellValue(statistics.getNonMotorVehicleIn());
            getOrCreateCell(row, 4).setCellValue(statistics.getNonMotorVehicleOut());
            getOrCreateCell(row, 5).setCellValue(statistics.getMotorVehicleAll());
            getOrCreateCell(row, 6).setCellValue(statistics.getNonMotorVehicleAll());
            getOrCreateCell(row, 7).setCellValue(statistics.getMotorVehicleAllOut());
            getOrCreateCell(row, 8).setCellValue(statistics.getNonMotorVehicleAllOut());

        }
    }

    /**
     * 公式行
     * 
     * @param sheet
     * @param size
     */
    private void buildCount(Sheet sheet, int size) {
        Row row = getOrCreateRow(sheet, ROW_CONTENT_START + size);
        row.getCell(0).setCellValue("合计");
        int start = ROW_CONTENT_START + 1;
        int end = ROW_CONTENT_START + size;

        String formula;
        for (int i = 1; i <= 8; i++) {
           /* if (i == 5) {
                continue;
            }*/
            formula = "SUM(" + CELL_NAMES[i] + start + ":" + CELL_NAMES[i] + end + ")";
            row.getCell(i).setCellFormula(formula);
        }

        // 计算公式值
        sheet.getWorkbook().getCreationHelper().createFormulaEvaluator().evaluateAll();

    }

}
