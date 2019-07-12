package com.coolcloud.sacw.car.service;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.car.analyzer.CarConserveAnalyzer;
import com.coolcloud.sacw.car.entity.CarConserve;
import com.coolcloud.sacw.car.entity.CarConserveExample;
import com.coolcloud.sacw.car.entity.CarConserveGroup;
import com.coolcloud.sacw.car.entity.CarConserveHistory;
import com.coolcloud.sacw.car.entity.CarConservePeriod;
import com.coolcloud.sacw.car.entity.NewCarConserve;
import com.coolcloud.sacw.car.mapper.CarConserveMapper;
import com.coolcloud.sacw.car.mapper.NewCarConserveMapper;
import com.coolcloud.sacw.cases.mapper.NewCaseMapper;
import com.coolcloud.sacw.cases.service.NewCaseService;
import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.common.util.UuidUtil;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.entity.PropertySplit;
import com.coolcloud.sacw.property.mapper.NewPropertyMapper;
import com.coolcloud.sacw.property.service.NewPropertyService;
import com.coolcloud.sacw.property.service.PropertyService;
import com.coolcloud.sacw.property.service.PropertySplitService;

@Service
public class CarConserveService extends BaseService<CarConserve, String> {

    @Autowired
    private CarConserveMapper carConserveMapper;

    @Autowired
    private CarConservePeriodService carConservePeriodService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertySplitService propertySplitService;

    @Autowired
    private CarConserveHistoryService carConserveHistoryService;

    @Autowired
    private CarConserveGroupService carConserveGroupService;

    @Autowired
    private CarConserveContentService carConserveContentService;
    
    @Autowired
    public NewCarConserveMapper newCarConserveMapper;
    
    @Autowired
    public NewPropertyMapper newPropertyMapper;

    /**
     * 批量养护默认名称模板
     */
    private static final String GROUP_NAME_PATTERN = "批量养护 yyyy-MM-dd HH:mm:ss";

    /**
     * 默认养护组id，在非批量时使用该值作为养护组id
     */
    private static final String DEFAULT_GROUP_ID = "0";

    /**
     * 覆写默认删除方法，一并删除养护历史
     * 
     * @param id
     * @return
     */
    @Override
    public int delete(String id) {
        carConserveHistoryService.deleteByConserveId(id);
        return super.delete(id);
    }

    /**
     * 返回所有车辆最近一条养护记录的车辆养护记录列表数据
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public List<CarConserve> infoTable(CarConserveExample example) {
        return carConserveMapper.queryConserveInfo(example);
    }

    @SuppressWarnings({ "deprecation", "resource" })
    public void export(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = carConserveMapper.carexp();
        // 创建excel工作表
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("车辆养护信息表");
        sheet.setColumnWidth(0, 30 * 256);
        sheet.setColumnWidth(2, 40 * 256);
        sheet.setColumnWidth(3, 50 * 256);
        sheet.setColumnWidth(4, 50 * 256);
        sheet.setColumnWidth(5, 40 * 256);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格
        HSSFCellStyle style = wb.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setWrapText(true);// 设置自动换行
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("二维码");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("案件名称");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("养护时间");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("养护内容");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("养护人");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("养护批号");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("车辆信息");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("财物名称");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("财物位置");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("养护备注");
        cell.setCellStyle(style);
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow((int) i + 1);
            // 第四步，创建单元格，并设置值
            String code = (String) list.get(i).get("qr_code");
            if (StringUtils.hasText(code)) {
                row.createCell(0).setCellValue(list.get(i).get("qr_code").toString());
            } else {
                row.createCell(0).setCellValue("");
            }
            String case_mc = (String) list.get(i).get("case_mc");
            if (StringUtils.hasText(case_mc)) {
                row.createCell(1).setCellValue(list.get(i).get("case_mc").toString());
            } else {
                row.createCell(1).setCellValue("");
            }
            Object object = list.get(i).get("conserve_time");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String conserve_time = simpleDateFormat.format(object);
            if (StringUtils.hasText(conserve_time)) {
                row.createCell(2).setCellValue(list.get(i).get("conserve_time").toString());
            } else {
                row.createCell(2).setCellValue("");
            }
            String conserve_text_mc = (String) list.get(i).get("conserve_text_mc");
            if (StringUtils.hasText(conserve_text_mc)) {
                row.createCell(3).setCellValue(list.get(i).get("conserve_text_mc").toString());
            } else {
                row.createCell(3).setCellValue("");
            }
            String conserve_man = (String) list.get(i).get("conserve_man");
            if (StringUtils.hasText(conserve_man)) {
                row.createCell(4).setCellValue(list.get(i).get("conserve_man").toString());
            } else {
                row.createCell(4).setCellValue("");
            }
            String conserve_number = (String) list.get(i).get("conserve_number");
            if (StringUtils.hasText(conserve_number)) {
                row.createCell(5).setCellValue(list.get(i).get("conserve_number").toString());
            } else {
                row.createCell(5).setCellValue("");
            }
            String car_message = (String) list.get(i).get("car_message");
            if (StringUtils.hasText(car_message)) {
                row.createCell(6).setCellValue(list.get(i).get("car_message").toString());
            } else {
                row.createCell(6).setCellValue("");
            }
            String property_mc = (String) list.get(i).get("property_mc");
            if (StringUtils.hasText(property_mc)) {
                row.createCell(7).setCellValue(list.get(i).get("property_mc").toString());
            } else {
                row.createCell(7).setCellValue("");
            }
            String path = (String) list.get(i).get("property_location");
            if (StringUtils.hasText(path)) {
                row.createCell(8).setCellValue(list.get(i).get("property_location").toString());
            } else {
                row.createCell(8).setCellValue("");
            }
            String remark = (String) list.get(i).get("conserve_remark");
            if (StringUtils.hasText(remark)) {
                row.createCell(9).setCellValue(list.get(i).get("conserve_remark").toString());
            } else {
                row.createCell(9).setCellValue("");
            }

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

    public List<CarConserve> queryCaryhxx(String qrCode) {
        List<CarConserve> queryCaryhxx = carConserveMapper.queryCaryhxx(qrCode);
        return queryCaryhxx;

    }

    public CarConserve queryCar(String qrCode) {
        CarConserve queryCar = carConserveMapper.queryCar(qrCode);
        return queryCar;
    }

    public String carTime(String qrCode) {
        String carTime = carConserveMapper.carTime(qrCode);
        return carTime;
    }

    public String carTimes(String qrCode) {
        String carTime = carConserveMapper.carTimes(qrCode);
        return carTime;
    }

    public Integer updateCar(String qrCode, String code) {
        Integer updateCar = carConserveMapper.updateCar(qrCode, code);
        return updateCar;
    }

    /**
     * 添加养护记录
     * 
     * @param carConserve
     * @return
     */
    @Transactional
    public int addConserve(NewCarConserve carConserve) {
        int num = 0;
        String qrCode = carConserve.getQrCode();
        Property property = propertyService.getByQrCode(qrCode);

        Assert.notNull(property, "财物不存在");
        String groupId = carConserve.getGroupId();
        if (StringUtils.hasText(groupId)) {
            CarConserveGroup group = carConserveGroupService.get(groupId);
            Assert.notNull(group, "分组不存在");
            Assert.isTrue(group.getCompleted() != 1, "养护组已完成养护，不可再添加内容");

        } else {
            carConserve.setGroupId(DEFAULT_GROUP_ID);
        }
        
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date conserveTime = null;
		try {
			conserveTime = sdf.parse(carConserve.getConserveTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format2 = new SimpleDateFormat("HHmmss");
        String conserveNumber = qrCode + format1.format(conserveTime) + format2.format(new Date());

      //  carConserve.setCaseName(property.getCaseName());
        if ("0".equals(carConserve.getStatus())) {
            carConserve.setExchangeKey("1");
        } else {
            carConserve.setStatus("4");
            carConserve.setExchangeKey("0");
        }

        String propertyId = property.getId();
     //   carConserve.setCarMessage(property.getGoodsSpecial());
        carConserve.setPropertyName(property.getPropertyName());
       /* carConserve.setPropertyLocation(property.getKwmc());*/
        carConserve.setConserveNumber(conserveNumber);
        carConserve.setPropertyId(propertyId);
        
        num += newCarConserveMapper.newInsertCarConserve(UuidUtil.get32UUID(), propertyId, qrCode, carConserve.getCaseName(), conserveTime,
        		carConserve.getConserveText(), carConserve.getConserveMan(), carConserve.getStatus(), carConserve.getConserveRemark(), Double.parseDouble(carConserve.getPredictTime()),
        		carConserve.getConserveTextName(), groupId, carConserve.getExchangeKey(), carConserve.getConserveNumber(), property.getRemake(), property.getPropertyName());

        /*
        List<PropertySplit> splits = propertySplitService.getByPropertyId(propertyId);
        for (PropertySplit split : splits) {
            carConserve.setId(null);
            carConserve.setSplitId(split.getId());
            carConserve.setPropertyLocation(split.getSplitSaveLocationName());
            num += this.save(carConserve);
        }

        // 插入养护历史
        String[] codes = carConserve.getConserveText().split(",");
        CarConserveHistory history = new CarConserveHistory();
        for (String code : codes) {

            history.setPropertyId(property.getId());
            history.setConserveId(carConserve.getId());
            history.setConserveContentCode(code);
            history.setConserveTime(conserveTime);

            for (PropertySplit split : splits) {
                history.setId(null);
                history.setSplitId(split.getId());
                carConserveHistoryService.save(history);
            }

        }
        */

        return num;
    }

    /**
     * 交还钥匙
     * 
     * @param id
     * @return 是否交还成功
     */
    @Transactional
    public boolean returnKey(String id) {
        CarConserve carConserve = new CarConserve();
        carConserve.setId(id);
        carConserve.setExchangeKey("1");
        carConserve.setStatus("0");
        return this.update(carConserve) > 0;
    }

    /**
     * 查询需要养护的车辆信息
     * 
     * @param conserveContentCodes
     * @param unitId
     * @return
     */
    public List<CarConserve> needConserve(String conserveContentCodes, String unitId) {

        // 该对象在整个分析中复用，请勿更改该列表中的值
        List<CarConservePeriod> periods;

        if (StringUtils.hasText(conserveContentCodes)) {
            periods = carConservePeriodService.getPeriodSettings(StringUtils.commaDelimitedListToSet(conserveContentCodes));
        } else {
            periods = carConservePeriodService.getPeriodSettings(true);
        }
        
        System.out.println(periods.get(0).getConserveContentName());

        List<Property> propertylist=newPropertyMapper.selectByUnitCode(unitId);
        
        System.out.println(propertylist.get(0).getPropertyName());
        
        //List<Property> properties = propertyService.getCarInStockByPermitUnit(unitId);
        List<CarConserve> conserves = new ArrayList<>();

        CarConserveAnalyzer analyzer = new CarConserveAnalyzer();

        Date date = new Date();
        String status = "4";
        String key = "0";
        Double predict = 0.5;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String suffix = format.format(date);

        for (Property property : propertylist) {

            String splitId = null;

            // 获取最近养护数据
            List<CarConserveHistory> histories = carConserveHistoryService.getLatestConserveBySplitId(splitId);

            int n = carConserveContentService.countBySplitId(splitId);
            if (n > 0) {
                // 有单独的养护内容设置
                List<CarConservePeriod> separatePeriods = carConservePeriodService.getBySplitId(splitId);
                analyzer.setPeriods(separatePeriods);
            } else {
                analyzer.setPeriods(periods);
            }

            // 分析需要养护的内容
            List<CarConservePeriod> contents = analyzer.analyze(histories);

        //    if (contents.size() > 0) {

                String codes = "";
                String text = "";

                for (CarConservePeriod content : contents) {
                    codes = codes + content.getConserveContentCode() + ",";
                    text = text + content.getConserveContentName() + "，";
               }
                codes = codes.substring(0, codes.length() - 1);
                text = text.substring(0, text.length() - 1);

                CarConserve c = getConserveFromProperty(property);
                c.setConserveTime(date);
                c.setConserveTextName(text);
                c.setConserveText(codes);
                c.setStatus(status);
                c.setExchangeKey(key);
                c.setConserveNumber(property.getQrCode() + suffix);
                c.setPredictTime(predict);
                conserves.add(c);
            }
      //  }
        return conserves;
    }

    /**
     * 由财物信息创建新的养护记录信息
     * 
     * @param property
     *            财物信息
     * @return
     */
    private CarConserve getConserveFromProperty(Property property) {
        CarConserve c = new CarConserve();
        c.setPropertyId(property.getId());
        //c.setSplitId(property.getSplitId());
        c.setQrCode(property.getQrCode());
       // c.setCaseName(property.getCaseName());
        //c.setCarMessage(property.getGoodsSpecial());
        c.setPropertyName(property.getPropertyName());
        //c.setPropertyLocation(property.getSplitSaveLocationName());
        //c.setPermitUnitName(property.getPermitUnitMc());
        //c.setCarMessage(property.getGoodsSpecial());
        return c;
    }

    /**
     * 新增批量养护
     * 
     * @param conserveContentCodes
     *            逗号分隔的养护内容代码
     * @param unitId
     *            权限单位id
     * @param name
     *            养护组名称
     * @return 新增养护数
     */
    public int newGroupConserve(String conserveContentCodes, String unitId, String name) {
        // 查询需要养护的记录
        List<CarConserve> conserves = needConserve(conserveContentCodes, unitId);
        // 保存养护信息，单独拿出来隔离查询与保存事务
        saveGroupsConserve(conserves, name,unitId);
        return conserves.size();
    }

    /**
     * 保存批量养护信息
     * 
     * @param conserves
     */
    @Transactional
    private void saveGroupsConserve(List<CarConserve> conserves, String name,String unitId) {

        if (conserves.size() > 0) {

            Date conserveTime = new Date();
            String groupId;
            String conserveId;
            String[] codes;

            CarConserveGroup group = new CarConserveGroup();
            CarConserveHistory history;
            List<CarConserveHistory> histories = new ArrayList<>();

            // 未提供养护名称时自动生成
            if (StringUtils.isEmpty(name)) {
                SimpleDateFormat format = new SimpleDateFormat(GROUP_NAME_PATTERN);
                name = format.format(conserveTime);
            }

            group.setConserveTime(conserveTime);
            group.setName(name);
            group.setCompleted(0);
            group.setUnitId(unitId);
            carConserveGroupService.save(group);
            groupId = group.getId();

            for (CarConserve conserve : conserves) {

                // 填充养护记录数据
                conserveId = UuidUtil.get32UUID();
                conserve.setId(conserveId);
                conserve.setGroupId(groupId);
                
                conserve.setCaseName(newPropertyMapper.selectCaseNameByPropertyId(conserve.getPropertyId()));

                // 生成养护历史记录
                codes = conserve.getConserveText().split(",");
                for (String code : codes) {
                    history = new CarConserveHistory();
                    history.setId(UuidUtil.get32UUID());
                    history.setPropertyId(conserve.getPropertyId());
                    history.setConserveId(conserveId);
                    history.setSplitId(conserve.getSplitId());
                    history.setConserveContentCode(code);
                    history.setConserveTime(conserve.getConserveTime());
                  //  history.setCreateTime(conserveTime);
                    //history.setUpdateTime(conserveTime);
                    histories.add(history);
                }
            }
            // 批量保存养护记录
            this.saveBatch(conserves);
            // 批量保存历史记录
            carConserveHistoryService.saveBatch(histories);

        }

    }

    /**
     * 按养护组id删除
     * 
     * @param id
     */
    @Transactional
    public int deleteByGoupId(String groupId) {
        return carConserveMapper.deleteByGroupId(groupId);
    }

    /**
     * 按养护组查询
     * 
     * @param id
     * @return
     */
    public List<CarConserve> getByGroupId(String groupId) {
        return carConserveMapper.selectByGroupId(groupId);
    }

    /**
     * 按养护组查询养护id
     * 
     * @param groupId
     * @return
     */
    public List<String> getIdByGroupId(String groupId) {
        return carConserveMapper.selectIdByGroupId(groupId);
    }

    /**
     * 按养护组统计完成养护的总数
     * 
     * @param groupId
     *            养护组id
     * @return
     */
    public int countCompletedByGroupId(String groupId) {
        return carConserveMapper.countCompletedByGroupId(groupId);
    }

    /**
     * 根据养护记录生成养护历史
     */
    @Transactional
    public void generateHistory() {
        List<CarConserve> conserves = getAll();

        Set<String> contents;
        CarConserveHistory history;
        Date date = new Date();
        List<CarConserveHistory> histories = new ArrayList<>();

        for (CarConserve conserve : conserves) {
            String text = conserve.getConserveText();
            if (StringUtils.hasText(text)) {
                contents = StringUtils.commaDelimitedListToSet(text);
                for (String content : contents) {

                    String propertId = conserve.getPropertyId();
                    List<PropertySplit> splits = propertySplitService.getByPropertyId(propertId);

                    for (PropertySplit split : splits) {
                        history = new CarConserveHistory();
                        history.setId(UuidUtil.get32UUID());
                        history.setPropertyId(conserve.getPropertyId());
                        history.setSplitId(split.getId());
                        history.setConserveId(conserve.getId());
                      //  history.setCreateTime(date);
                       // history.setUpdateTime(date);
                        history.setConserveContentCode(content);
                        history.setConserveTime(conserve.getConserveTime());
                        history.setDeleted(0);

                        histories.add(history);
                        if (histories.size() >= 1000) {
                            carConserveHistoryService.saveBatch(histories);
                            histories.clear();
                        }
                    }

                }
            }
        }

        if (histories.size() > 0) {
            carConserveHistoryService.saveBatch(histories);
        }

    }
}
