package com.coolcloud.sacw.property.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.coolcloud.sacw.common.view.BaseXlsxView;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.store.entity.Store;

/**
 * 导出储物柜及物品信息到excel
 * 
 * @author xyz
 *
 * @date 2018年4月16日 下午3:55:03
 */
@Component
public class outWithPropertiesXlsxView extends BaseXlsxView {

    private static final String TEMPLATE = "/template/xlsx/财物出库.xlsx";

    private static final int ROW_HEADER = 0;

    private static final int ROW_CONTENT_START = 1;

    public outWithPropertiesXlsxView() {
        setTemplate(TEMPLATE);
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Property> property = (List<Property>) model.get("property");

        Sheet sheet = getOrCreateSheet(workbook, 0);

        // 用于保存树深度信息的对象
        TreeInfo info = new TreeInfo();
        buildHeader(sheet, info.getDeepth());
        buildProperties(sheet, property, ROW_CONTENT_START, info.deepth);
        response.setHeader("Content-Disposition", "attachment; filename=\"outProperty.xlsx\"");
    }

    /**
     * 生成表头
     * 
     * @param sheet
     * @param deepth
     */
    private void buildHeader(Sheet sheet, int deepth) {
        Row row = getOrCreateRow(sheet, ROW_HEADER);
        getOrCreateCell(row, deepth + 0).setCellValue("案件名称");
        getOrCreateCell(row, deepth + 1).setCellValue("财物名称");
        getOrCreateCell(row, deepth + 2).setCellValue("财物数量");
        getOrCreateCell(row, deepth + 3).setCellValue("备注数量");
        getOrCreateCell(row, deepth + 4).setCellValue("财物类型");
        getOrCreateCell(row, deepth + 5).setCellValue("权限单位");
        getOrCreateCell(row, deepth + 6).setCellValue("财物状态");
    }

    /**
     * 递归生成财物信息
     * 
     * @param sheet
     *            工作表
     * @param stores
     *            包含财物信息的储物柜树
     * @param rowstart
     *            起始行号
     * @param colstart
     *            起始列号
     */
    private int buildProperties(Sheet sheet, List<Property> propertys, int rowstart, int colstart) {

        Row row;
        int rowadded = 0;
        for (Property property : propertys) {
            // 填入财物信息
           //     Integer remarkNumber = property.getRemake_number() == null ? 0 : property.getRemake_number();
        	Integer remarkNumber = property.getNumber() == null ? 0 : property.getNumber();
              
                
         //       String permitUnitName = property.getPermitUnitMc() == null ? "" : property.getPermitUnitMc();
         
        	
        	//String permitUnitName = property.getPermitUnitMc() == null ? "" : property.getPermitUnitMc();
            
        	row = getOrCreateRow(sheet, rowstart + rowadded);
            //    getOrCreateCell(row, colstart).setCellValue(property.getCaseName());
                getOrCreateCell(row, colstart + 1).setCellValue(property.getPropertyName());
                getOrCreateCell(row, colstart + 2).setCellValue(property.getNumber());
                getOrCreateCell(row, colstart + 3).setCellValue(remarkNumber);
                getOrCreateCell(row, colstart + 4).setCellValue(property.getPropertyType());
               // getOrCreateCell(row, colstart + 5).setCellValue(permitUnitName);
                getOrCreateCell(row, colstart + 6).setCellValue(property.getPropertyStatus());
                rowadded++;
        }

        return rowadded;

    }


    /**
     * 在递归中传递用于保存树深度
     * 
     * @author xyz
     *
     * @date 2018年4月16日 下午8:53:37
     */
    private class TreeInfo {

        private int deepth = 0;

        public int getDeepth() {
            return deepth;
        }

        public void setDeepth(int deepth) {
            this.deepth = deepth;
        }

    }

}
