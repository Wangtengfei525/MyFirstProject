package com.coolcloud.sacw.store.view;

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
public class StoreWithPorpertiesXlsxView extends BaseXlsxView {

    private static final String TEMPLATE = "/template/xlsx/储物柜.xlsx";

    private static final int ROW_HEADER = 0;

    private static final int ROW_CONTENT_START = 1;

    public StoreWithPorpertiesXlsxView() {
        setTemplate(TEMPLATE);
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Store> stores = (List<Store>) model.get("stores");

        Sheet sheet = getOrCreateSheet(workbook, 0);

        // 用于保存树深度信息的对象
        TreeInfo info = new TreeInfo();
        buildStores(sheet, stores, ROW_CONTENT_START, 0, info);
        buildHeader(sheet, info.getDeepth());
        buildProperties(sheet, stores, ROW_CONTENT_START, info.deepth);
        response.setHeader("Content-Disposition", "attachment; filename=\"store.xlsx\"");
    }

    /**
     * 生成表头
     * 
     * @param sheet
     * @param deepth
     */
    private void buildHeader(Sheet sheet, int deepth) {
        Row row = getOrCreateRow(sheet, ROW_HEADER);
        for (int i = 0; i < deepth; i++) {
            getOrCreateCell(row, i).setCellValue((i + 1) + "级目录");
        }
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
    private int buildProperties(Sheet sheet, List<Store> stores, int rowstart, int colstart) {

        Row row;
        int rowadded = 0;

        for (Store store : stores) {

            // 填入财物信息
            for (Property property : store.getProperties()) {
/*
                Integer remarkNumber = property.getRemake_number() == null ? 0 : property.getRemake_number();
                String permitUnitName = property.getPermitUnitMc() == null ? "" : property.getPermitUnitMc();
*/

              //  Integer remarkNumber = property.getRemake_number() == null ? 0 : property.getRemake_number();
                
              //  String permitUnitName = property.getPermitUnitMc() == null ? "" : property.getPermitUnitMc();

            	
                row = getOrCreateRow(sheet, rowstart + rowadded);
               // getOrCreateCell(row, colstart).setCellValue(property.getCaseName());
                getOrCreateCell(row, colstart + 1).setCellValue(property.getPropertyName());
                getOrCreateCell(row, colstart + 2).setCellValue(property.getNumber());
              //  getOrCreateCell(row, colstart + 3).setCellValue(remarkNumber);
                getOrCreateCell(row, colstart + 4).setCellValue(property.getPropertyType());
               // getOrCreateCell(row, colstart + 5).setCellValue(permitUnitName);
                getOrCreateCell(row, colstart + 6).setCellValue(property.getPropertyStatus());

                rowadded++;
            }

            // 没有财物时也有空行
            if (store.getProperties().size() == 0) {
                rowadded++;
            }

            // 递归生成下级
            rowadded += buildProperties(sheet, store.getChildren(), rowstart + rowadded, colstart);
        }

        return rowadded;

    }

    /**
     * 递归生成储物柜信息
     * 
     * @param sheet
     *            工作表
     * @param stores
     *            储物柜树
     * @param startrownumber
     *            开始行号
     * @param indent
     *            缩进列数
     * @param info
     *            树深度信息
     * @return 新增加的行数
     */
    private int buildStores(Sheet sheet, List<Store> stores, int startrownumber, int indent, TreeInfo info) {

        // 总共新增行数
        int rowadded = 0;

        // 单次循环中占用行数
        int singleLoopAdded;

        int size = stores.size();
        Store store;
        Row row;
        String name;

        // 设置深度信息
        if (size > 0) {
            info.setDeepth(Math.max(info.getDeepth(), indent + 1));
        }

        for (int i = 0; i < size; i++) {
            singleLoopAdded = 0;
            store = stores.get(i);
            name = store.getStoreUnitName();

            // 柜子信息占用一行
            singleLoopAdded++;

            // 财物数量大于1时额外占用 财物数量-1 行，0或1时不额外占用
            singleLoopAdded += store.getProperties().size() > 1 ? store.getProperties().size() - 1 : 0;

            // 递归填充下级内容，并返回新增的行数
            singleLoopAdded += buildStores(sheet, store.getChildren(), startrownumber + rowadded + singleLoopAdded, indent + 1, info);

            // 填充本级柜子名称
            for (int j = 0; j < singleLoopAdded; j++) {
                row = getOrCreateRow(sheet, startrownumber + rowadded + j);
                getOrCreateCell(row, indent).setCellValue(name);
            }

            rowadded += singleLoopAdded;

        }

        // 返回新增加的行数
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
