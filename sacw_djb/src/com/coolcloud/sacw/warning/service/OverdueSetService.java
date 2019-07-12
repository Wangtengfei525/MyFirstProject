package com.coolcloud.sacw.warning.service;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.property.service.PropertyService;
import com.coolcloud.sacw.statistics.mapper.StatisticsMapper;
import com.coolcloud.sacw.warning.entity.OverdueSet;
import com.coolcloud.sacw.warning.mapper.OverdueSetMapper;
import com.github.pagehelper.PageHelper;

@Service
public class OverdueSetService extends BaseService<OverdueSet, String> {

    @Resource
    private OverdueSetMapper overdueSetMapper;
    @Resource
    private PropertyService propertyService;
    @Resource
    private StatisticsMapper statisticsMapper;

    /**
     * 通过状态编码查询逾期设置
     * 
     * @param overdueSet
     * @return
     */
    @Transactional(readOnly = true)
    public List<OverdueSet> queryByCode(OverdueSet overdueSet) {

        return this.getByExample(overdueSet);
    }

    /**
     * 更新逾期设置
     * 
     * @param overdueSet
     * @return
     */
    @Transactional
    public Integer updateSetting(OverdueSet overdueSet) {
        if (overdueSet == null) {
            return 0;
        }
        return this.update(overdueSet);
    }

    /**
     * 通过逾期状态编码查询逾期财物信息
     * 
     * @param overdueSet
     * @return
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> queryPropertiesByCode(OverdueSet overdueSet) {
        if (overdueSet == null || StringUtils.isEmpty(overdueSet.getStatusCode())) {
            return null;
        }
        Integer page = overdueSet.getPage();
        Integer rows = overdueSet.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<Map<String, Object>> list = overdueSetMapper.queryPropertiesByCode(overdueSet);
        Date today = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Map<String, Object> map : list) {
                long m = 0;
                String now = sdf.format(today);
                String register_date = map.get("register_date").toString();
                Date old_day = sdf.parse(register_date);
                String ago_day = sdf.format(old_day);
                m = (sdf.parse(now).getTime() - sdf.parse(ago_day).getTime()) / (1000 * 60 * 60 * 24);
                map.put("overdue_days", m);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    @SuppressWarnings({ "deprecation", "resource", "unused" })
    public void export(String code, HttpServletRequest request, HttpServletResponse response) {
        OverdueSet overdueSet = new OverdueSet();
        overdueSet.setStatusCode(code);
        String sheetName = null;
        List<Map<String, Object>> list = overdueSetMapper.queryPropertiesByCode(overdueSet);
        OverdueSet overdueset = new OverdueSet();
        overdueset.setStatusCode(code);
        List<OverdueSet> overduSets = overdueSetMapper.selectByExample(overdueset);
        if (code != null && code.equals("9911000000001")) {
            sheetName = "公安登记";
        } else if (code != null && code.equals("9911000000004")) {
            sheetName = "公安移送";
        } else {
            sheetName = "检察院移送";
        }
        // 创建excel工作表
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName + "逾期财物表");
        sheet.setColumnWidth(1, 30 * 256);
        sheet.setColumnWidth(2, 40 * 256);
        sheet.setColumnWidth(4, 50 * 256);
        /* sheet.setColumnWidth(6, 10 * 10); */
        /* sheet.setColumnWidth(4, 10 * 256); */
        sheet.setColumnWidth(5, 40 * 256);
        // 第四步，创建单元格
        HSSFCellStyle style = wb.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setWrapText(true);// 设置自动换行
        CellRangeAddress region = new CellRangeAddress(0, 0, (short) 0, (short) 6);
        sheet.addMergedRegion(region);
        HSSFRow row0 = sheet.createRow((int) 0);
        HSSFCell cell0 = row0.createCell(0);
        if (code != null && code.equals("9911000000001")) {
            cell0.setCellValue("公安局登记未移交管理中心统计");
        } else if (code != null && code.equals("9911000000004")) {
            cell0.setCellValue("已入库保管中心公安局未移送统计");
        } else {
            cell0.setCellValue("检察院未移送统计");
        }
        cell0.setCellStyle(style);
        // 第三步，在sheet中添加表头第1行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 1);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("案件名称");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("案件编号");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("财物数量");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("处置权单位");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        if (code != null && code.equals("9911000000001")) {
            cell.setCellValue("登记时间");
        } else if (code != null && code.equals("9911000000004")) {
            cell.setCellValue("入库时间");
        } else {
            cell.setCellValue("审查时间");
        }
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("逾期天数");
        cell.setCellStyle(style);
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow((int) i + 2);
            // 第四步，创建单元格，并设置值
            row.createCell(0).setCellValue("" + (i + 1));
            Object case_name = list.get(i).get("case_name");
            row.createCell(1).setCellValue(case_name == null ? "" : case_name.toString());
            Object plat_case_code = list.get(i).get("plat_case_code");
            row.createCell(2).setCellValue(plat_case_code == null ? "" : plat_case_code.toString());
            Object number = list.get(i).get("number");
            row.createCell(3).setCellValue(number == null ? "" : number.toString());
            Object permit_unit_mc = list.get(i).get("permit_unit_mc");
            row.createCell(4).setCellValue(permit_unit_mc == null ? "" : permit_unit_mc.toString());
            System.out.println(list.get(i).get("register_date").toString());
            row.createCell(5).setCellValue(list.get(i).get("register_date").toString());
            // 计算逾期天数
            Integer day = overduSets.get(0).getWarningPeriod();
            Date now_date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long m = 0;
            try {
                String now = sdf.format(now_date);
                String register_date = list.get(i).get("register_date").toString();
                Date old_day = sdf.parse(register_date);
                String ago_day = sdf.format(old_day);
                m = (sdf.parse(now).getTime() - sdf.parse(ago_day).getTime()) / (1000 * 60 * 60 * 24);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            row.createCell(6).setCellValue("" + m);
        }
        // 第六步，将文件存到指定位置
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename=" + "gayq.xls");
            OutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 逾期统计
     * 
     * @return
     */
    public Object statistics() {
        // 获取最上级单位编码和名称
        List<Map<String, Object>> dw = statisticsMapper.queryDW();
        // 按照单位循环统计逾期案件和财物
        for (int i = 0; i < dw.size(); i++) {
            String code = (String) dw.get(i).get("id");
            List<Map<String, Object>> gadjyq = overdueSetMapper.queryGadjyq(code);
            List<Map<String, Object>> gaysyq = overdueSetMapper.queryGaysyq(code);

            dw.get(i).put("GADJAJ", gadjyq.get(0).get("GADJAJ"));
            dw.get(i).put("GADJCW", gadjyq.get(0).get("GADJCW"));
            dw.get(i).put("GAYSAJ", gaysyq.get(0).get("GAYSAJ"));
            dw.get(i).put("GAYSCW", gaysyq.get(0).get("GAYSCW"));
        }
        return dw;
    }
}
