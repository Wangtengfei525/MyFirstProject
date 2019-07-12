package com.coolcloud.sacw.share.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;


import com.coolcloud.sacw.annex.entity.File1;
import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.exchange.entity.Exchange;
import com.coolcloud.sacw.person.entity.Person;
import com.coolcloud.sacw.photo.entity.Photo;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.share.entity.File;
import com.coolcloud.sacw.share.entity.Good;
import com.coolcloud.sacw.share.entity.Header;
import com.coolcloud.sacw.share.entity.Receipt;
import com.coolcloud.sacw.share.entity.Share;

/**
 * 根据已有的交换记录、案件、财物、照片、嫌疑人、附件等信息生成共享平台的数据结构
 * 
 * @author 袁永祥
 *
 * @date 2017年8月23日 下午6:01:25
 */
public class ShareBuilder {

    private Share share;

    private ShareBuilder() {
        share = new Share();
    }

    /**
     * 创建ShareBuilder实例
     * 
     * @return
     */
    public static ShareBuilder instance() {
        return new ShareBuilder();
    }

    /**
     * 通过exchange设置header
     * 
     * @param exchange
     * @return
     */
    private ShareBuilder setHeader(Exchange exchange) {
        Header header = new Header();
      //  header.setJHBH(exchange.getExchangeCode());
        header.setLCBH(exchange.getProcessCode());
        header.setJDBH(exchange.getNodeCode());
    //    header.setPTAJBH(exchange.getPlatCaseCode());
        header.setAJMC(exchange.getCaseName());

       // header.setFSDW_BM(exchange.getSendUnitCode());
      //  header.setFSDW_MC(exchange.getSendUnitName());
       // header.setFSRXM(exchange.getSendPersonName());
     //   header.setFSRDH(exchange.getSendPersonPhone());
      //  header.setFSSJ(exchange.getSendTime());

    //    header.setJSDW_BM(exchange.getReceiveUnitCode());
      //  header.setJSDW_MC(exchange.getReceiveUnitName());
    //    header.setLJ_ZIP(exchange.getZipPath());
     //   header.setLX(exchange.getOperationPersonType());

     //   header.setFSDW_LXBM(exchange.getSendUnitTypeCode());
     //   header.setJSDW_LXBM(exchange.getReceiveUnitTypeCode());
        header.setFSBZ(exchange.getRemark());

   //     header.setJHPC(exchange.getExchangeBatch());
        share.setHeader(header);

        return this;
    }

    /**
     * 通过com.coolcloud.sacw.caseManage.entity.Case设置com.coolcloud.sacw.share.entity.Case
     * 
     * @param caze
     * @return
     */
    private ShareBuilder setCase(Case caze) {
        com.coolcloud.sacw.share.entity.Case shareCase = new com.coolcloud.sacw.share.entity.Case();

       // shareCase.setPTAJBH(caze.getPlatCaseCode());
        shareCase.setAJMC(caze.getCaseName());

        shareCase.setAJLX_MC(caze.getCaseTypeName());
        shareCase.setAJLX_BM(caze.getCaseTypeCode());

       /* shareCase.setAY_BM(caze.getCaseCauseCode());
        shareCase.setAY_MC(caze.getCaseCauseName());
*/
      /*  shareCase.setAJJD(caze.getCaseStage());
        shareCase.setAQZY(caze.getCaseBriefing());
*/
       /* shareCase.setAJLY(caze.getCaseSource());
        shareCase.setAQZY(caze.getCaseBriefing());
*/
        shareCase.setCBDW_LX(caze.getOrganizerType());
        shareCase.setCBDW_BM(caze.getOrganizerCode());
        shareCase.setCBDW_MC(caze.getOrganizerName());
/*
        shareCase.setCBBM_BM(caze.getDepartmentCode());
        shareCase.setCBBM_MC(caze.getDepartmentName());
*/
        shareCase.setCBR_XM(caze.getUndertakerName());
       // shareCase.setCBR_GH(caze.getUndertakerId());

        share.setCaze(shareCase);
        return this;
    }

    /**
     * 通过文件设置附件信息
     * 
     * @param af
     * @return
     */
    private ShareBuilder addFile(File1 file11) {
        File file = new File();
        java.io.File ioFile = new java.io.File(file11.getFile_path());
       // file.setPTAJBH(file11.getPlatCaseCode());
     //   file.setPTWJBH(file11.getNumber());
      //  file.setWSWH(file11.getText_num());
        file.setWJLX(file11.getFile_class());
        file.setWJLJ(ioFile.getName());
        file.setHZM(file11.getSuffix_name());
       // file.setZZSJ(file11.getCreate_date());
      //  file.setWJBH(file11.getNumber());
        file.setWJMC(file11.getFile_name());
        share.getFiles().add(file);
        return this;
    }

    private ShareBuilder addFiles(List<File1> file1ss) {
        for (File1 file1 : file1ss) {
            addFile(file1);
        }
        return this;
    }

    private ShareBuilder addPhoto(Photo photo) {
        com.coolcloud.sacw.share.entity.Photo sp = new com.coolcloud.sacw.share.entity.Photo();
        /*sp.setPTAJBH(photo.getPlatCaseCode());
        sp.setPTCWBH(photo.getProperty_num());
        sp.setCWMC(photo.getProperty_name());
        sp.setZPBH(photo.getPhoto_num());*/
        sp.setZPMS(photo.getPhoto_desc());
      //  sp.setZPPSR(photo.getPhotographer());
       /* sp.setZPPSDW_BM(photo.getUnit_code());
        sp.setZPPSDW_MC(photo.getUnit_name());
        sp.setZPPSSJ(photo.getFilming_time());*/
        //sp.setWJLJ(new java.io.File(photo.getFile_path()).getName());
        sp.setHZM(photo.getSuffix_name());
       /* sp.setWJLX_BM(photo.getFile_class_code());
        sp.setWJLX_MC(photo.getFile_class_name());*/
        share.getPhotos().add(sp);
        return this;
    }

    private ShareBuilder addPhotos(List<Photo> photos) {
        for (Photo photo : photos) {
            addPhoto(photo);
        }
        return this;
    }

    private ShareBuilder addPerson(Person person) {
        com.coolcloud.sacw.share.entity.Person sp = new com.coolcloud.sacw.share.entity.Person();
        sp.setPTAJBH(person.getPlatCaseCode());
        sp.setPTRYBH(person.getPlatPersonCode());
        sp.setXM(person.getPersonName());
        sp.setGJ_BM(person.getNationCode());
        sp.setGJ_MC(person.getNation());
        sp.setJG_BM(person.getNativePlaceCode());
        sp.setJG_MC(person.getNativePlace());
        sp.setRYLX(person.getPersonType());
        sp.setRYLB(person.getPersonClass());
        sp.setZZJGDM(person.getOrgCode());
        sp.setZZJGMC(person.getOrgName());
        sp.setZZJGDZ(person.getUnitAddress());
        sp.setZM_MC(person.getCrimeName());
        sp.setZM_BM(person.getCrimeCode());
        sp.setXB_BM(person.getGenderCode());
        sp.setXB_MC(person.getGender());
        sp.setCSRQ(person.getBirthday());

        sp.setZJLX_BM(person.getIdTypeCode());
        sp.setZJLX_MC(person.getIdType());
        sp.setZJHM(person.getIdNumber());
        sp.setZSD(person.getAddress());
        sp.setAFD(person.getVenue());
        sp.setLXDH(person.getPhoneNumber());
        sp.setDJRQ(person.getRecordDate());

        share.getPersons().add(sp);
        return this;
    }

    private ShareBuilder addPersons(List<Person> persons) {
        for (Person person : persons) {
            addPerson(person);
        }
        return this;
    }

    private ShareBuilder addGood(Property property) {
        Good good = new Good();
       // String EWM = StringUtils.hasText(property.getOriginQrCode()) ? property.getOriginQrCode() : property.getQrCode();
      
        String EWM = StringUtils.hasText(property.getQrCode()) ? property.getQrCode() : property.getQrCode();
        
        
      //  good.setPTAJBH(property.getPlatCaseCode());
       // good.setPTCWBH(property.getPropertyNumber());
       // good.setFPTCWBH(property.getParentPropertyNumber());
        good.setCWMC(property.getPropertyName());
        good.setEWM(EWM);
       // good.setLY(property.getSource());
        good.setCWLX_BM(property.getPropertyTypeCode());
        good.setCWLX_MC(property.getPropertyTypeCode());
        good.setSL(property.getNumber());
        //good.setJL_DW(property.getMeteringUnit());
        //good.setJZ(property.getValue());
        //good.setJZ_DW(property.getValueUnit());
        //good.setBZ_DM(property.getMoneyCode());
        //good.setBZ_MC(property.getMoney());
       // good.setTZ(property.getGoodsSpecial());
        //good.setCYRLX_BM(property.getHavePeopleType());
        //good.setCYRLX_MC(property.getHavePeopleTypeMc());
        //good.setCYRBH(property.getHavePeopleTypeNumber());
        //good.setCYRXM(property.getHavePopleTypeName());
        //good.setCWCS_MC(property.getPropertyMeasuresMc());
        //good.setCWCS_BM(property.getPropertyMeasuresCode());
        //good.setCSZXR(property.getMeasuresExecutor());
        //good.setDJR(property.getRegistrant());
       // good.setDJRQ(property.getRegisterDate());
        good.setBZ(property.getRemake());
        //String BGDW_BM = property.getKeepUnitCode();
        //String BGDW_MC = property.getKeepUnitMc();
        //good.setBGDW_BM(BGDW_BM == null ? "" : BGDW_BM);
        //good.setBGDW_MC(BGDW_MC == null ? "" : BGDW_MC);
        good.setKWBM(property.getKwbm());
        good.setKWMC(property.getKwmc());
        good.setCWDJZT_BM(property.getPropertyStatusCode());
        good.setCWDJZT_MC(property.getPropertyStatus());
        //good.setCZR(property.getExecutor());
        //good.setCZSJ(property.getExecutorTime());

        share.getGoods().add(good);
        return this;
    }

    private ShareBuilder addGoods(List<Property> properties) {
        for (Property property : properties) {
            addGood(property);
        }
        return this;
    }

    /**
     * 创建回执
     * 
     * @param exchange
     *            入库反馈交换记录
     * @param properties
     *            财物信息
     * @param files
     *            附件
     * @return
     */
    public ShareBuilder buildReceipt(Exchange exchange, List<Property> properties, List<File1> file1s) {
    	//String type = exchange.getReceiveUnitCode();
    	int i = exchange.getCaseName().lastIndexOf("/");
    	/*if("01".equals(type)&& i!=-1) {
    		exchange.setCaseName(exchange.getCaseName().substring(0, i));
    	}*/
        setHeader(exchange);// header部分
        if (properties != null && properties.size() > 0) {
            share.setReceipts(new ArrayList<Receipt>());
        }
        for (Property property : properties) { // hz部分
            Receipt receipt = new Receipt();
           // receipt.setPTAJBH(property.getPlatCaseCode());
            //receipt.setPTCWBH(property.getPropertyNumber());
            receipt.setCZJG_BM("9916000000002");
            receipt.setCZJG_MC("接收");
        //    receipt.setCZJG_SM(exchange.getOperationResult());
            receipt.setKWBM(property.getKwbm());
            receipt.setKWMC(property.getKwmc());
          //  String BGDW_BM = property.getKeepUnitCode();
            //String BGDW_MC = property.getKeepUnitMc();
            //receipt.setBGDW_BM(BGDW_BM == null ? "" : BGDW_BM);
            //receipt.setBGDW_MC(BGDW_MC == null ? "" : BGDW_MC);
            receipt.setCWDJZT_BM(property.getPropertyStatusCode());
            receipt.setCWDJZT_MC(property.getPropertyStatus());
        //    receipt.setCZR(exchange.getOperationPersonName());
         //   receipt.setCZSJ(exchange.getOperationTime());
            share.getReceipts().add(receipt);
        }

        if (file1s != null && file1s.size() > 0) {
            share.setFiles(new ArrayList<File>());
        }
        for (File1 af : file1s) {// files部分
            java.io.File ioFile = new java.io.File(af.getFile_path());
            File file = new File();
            String path = ioFile.getName();
            String code = path.substring(0, path.lastIndexOf("."));
      //      file.setPTAJBH(exchange.getPlatCaseCode());
            file.setPTWJBH(code);
            file.setWJLJ(path);
            file.setHZM(af.getSuffix_name());
            file.setZZSJ(new Date());
            file.setWJBH(code);
            file.setWJMC(af.getFile_name());
         /*   file.setJHBH(exchange.getExchangeCode());
            file.setJHPC(exchange.getExchangeBatch());*/
            share.getFiles().add(file);
        }

        return this;
    }

    /**
     * 创建退回数据
     * 
     * @param exchange
     *            交换记录
     * @param caze
     *            案件
     * @param persons
     *            嫌疑人
     * @param properties
     *            财物
     * @param annexs
     *            附件
     * @param photos
     *            照片
     * @return
     */
    public ShareBuilder buildSendBack(Exchange exchange, Case caze, List<Person> persons, List<Property> properties, List<File1> file1ss, List<Photo> photos) {
        // header部分
    	//String type = exchange.getReceiveUnitCode();
    	int i = exchange.getCaseName().lastIndexOf("/");
    	/*if("01".equals(type)&& i!=-1) {
    		exchange.setCaseName(exchange.getCaseName().substring(0, i));
    		caze.setCaseName(caze.getCaseName().substring(caze.getCaseName().lastIndexOf("/")+1,caze.getCaseName().length()));
    	}*/
        if (exchange != null) {
            setHeader(exchange);
        }
        // case 部分
        if (caze != null) {
            setCase(caze);
        }
        // person 部分
        if (persons != null && persons.size() > 0) {
            share.setPersons(new ArrayList<com.coolcloud.sacw.share.entity.Person>());
        }
        addPersons(persons);

        // goods 部分
        if (properties != null && properties.size() > 0) {
            share.setGoods(new ArrayList<Good>());
        }
        addGoods(properties);

        // files 部分
        if (file1ss != null &&file1ss.size() > 0) {
            share.setFiles(new ArrayList<File>());
        }
        addFiles(file1ss);

        // photos 部分
        if (photos != null && photos.size() > 0) {
            share.setPhotos(new ArrayList<com.coolcloud.sacw.share.entity.Photo>());
        }
        addPhotos(photos);

        return this;
    }

    public Share build() {
        return share;
    }

}
