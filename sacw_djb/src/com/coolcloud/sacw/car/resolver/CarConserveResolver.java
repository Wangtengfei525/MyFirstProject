package com.coolcloud.sacw.car.resolver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.car.entity.CarConserve;
import com.coolcloud.sacw.car.entity.CarConserveGroup;

/**
 * 养护结果解析
 * 
 * @author xyz
 *
 * @date 2018年4月16日 上午10:42:33
 */
@Component
public class CarConserveResolver {

    /**
     * 内容取起始行号
     */
    private static final int ROW_CONTENT_START = 4;

    /**
     * 养护id所在列号
     */
    private static final int COLUMN_ID = 6;

    /**
     * 备注所在列号
     */
    private static final int COLUMN_REMARK = 5;

    /**
     * 从excel中解析出养护数据
     * 
     * @param workbook
     *            包含养护内容的工作簿
     * @return 养护内容（id,备注）
     */
    public CarConserveGroup resolve(Workbook workbook) {

        CarConserveGroup group = new CarConserveGroup();
        List<CarConserve> conserves = new ArrayList<>();
        CarConserve conserve;

        Row row;
        Sheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        String conserveMan = sheet.getRow(1).getCell(6).getStringCellValue();
        Date conserveTime = sheet.getRow(2).getCell(6).getDateCellValue();

        for (int i = ROW_CONTENT_START; i <= rows; i++) {

            row = sheet.getRow(i);

            String id = row.getCell(COLUMN_ID).getStringCellValue();
            if (StringUtils.isEmpty(id)) {
                continue;
            }

            conserve = new CarConserve();
            conserve.setId(id);
            conserve.setConserveRemark(row.getCell(COLUMN_REMARK).getStringCellValue());
            conserve.setConserveMan(conserveMan);
            conserve.setConserveTime(conserveTime);

            conserves.add(conserve);
        }

        group.setConserveTime(conserveTime);
        group.setConserves(conserves);
        return group;
    }

}
