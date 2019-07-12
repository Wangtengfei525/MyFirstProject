package com.coolcloud.sacw.share.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;


import com.coolcloud.sacw.annex.entity.File1;
import com.coolcloud.sacw.annex.entity.File1Example;
import com.coolcloud.sacw.annex.service.File1Service;
import com.coolcloud.sacw.cases.entity.Case;
import com.coolcloud.sacw.cases.service.CaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.exchange.entity.Exchange;
import com.coolcloud.sacw.exchange.service.ExchangeService;
import com.coolcloud.sacw.ftp.service.FtpService;
import com.coolcloud.sacw.person.entity.Person;
import com.coolcloud.sacw.person.entity.PersonExample;
import com.coolcloud.sacw.person.service.PersonService;
import com.coolcloud.sacw.photo.entity.Photo;
import com.coolcloud.sacw.photo.entity.PhotoExample;
import com.coolcloud.sacw.photo.service.PhotoService;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.service.PropertyService;
import com.coolcloud.sacw.share.DataShare;
import com.coolcloud.sacw.share.DataShareSoap;
import com.coolcloud.sacw.share.builder.ShareBuilder;
import com.coolcloud.sacw.share.entity.Share;
import com.coolcloud.sacw.zip.service.ZipService;

/**
 * 数据共享服务类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月23日 下午1:01:04
 */
@Service
public class ShareService {

    private Logger logger = LoggerFactory.getLogger(ShareService.class);

    /**
     * 可发送回执信息的节点编号（入库、借调、归还、出库的确认记录）
     */
    private static final List<String> NODES_FEED_BACK = Arrays.asList(new String[] { "1002002", "1003002", "1004002", "1011002" });

    /**
     * 可发送退回信息的节点编号（入库、借调、归还、出库的申请记录）
     */
    private static final List<String> NODES_FALL_BACK = Arrays.asList(new String[] { "1002003", "1003003", "1004003", "1011003" });

    /**
     * 和zip共用目录
     */
    @Value("${app.zipdir}")
    private String tmpdir;

    /**
     * webservice 地址<br/>
     * 如：http://192.168.1.59:8181/DataShare.asmx
     */
    @Value("${app.webservice}")
    private String webservice;

    /**
     * 是否进行BASE64编码
     */
    @Value("${app.base64-enable}")
    private Boolean base64Enable;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private CaseService caseService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PersonService personService;

    @Autowired
    private File1Service file1Service;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private ZipService zipService;

    @Autowired
    private FtpService ftpService;

    /**
     * 生成xml文件
     * 
     * @param share
     *            通过ShareBuilder生成的共享数据类
     * @param filename
     *            要保存的xml文件名称
     * @return xml文件绝对路径
     */
    private String toXml(Share share, String filename) {
        File dir = new File(tmpdir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File xmlfile = new File(dir, filename);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(xmlfile));
            JAXBContext jaxbContext = JAXBContext.newInstance(Share.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(share, bos);
            bos.close();
            bos = null;

            if (base64Enable.booleanValue()) {
                base64(xmlfile);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return xmlfile.getAbsolutePath();
    }

    /**
     * 对生成的xml文件中的文本节点进行BASE64编码
     * 
     * @param xmlfile
     *            xml文件对象
     * @throws Exception
     */
    private void base64(File xmlfile) throws Exception {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(xmlfile);
        process(document.getRootElement());
        XMLWriter xmlWriter = new XMLWriter(new FileWriter(xmlfile), OutputFormat.createPrettyPrint());
        xmlWriter.write(document);
        xmlWriter.close();
    }

    /**
     * 递归处理xml节点，对文本节点进行BASE64编码
     * 
     * @param root
     *            根节点
     */
    private void process(Element root) {
        for (Element element : root.elements()) {
            process(element);
        }
        String text = root.getText();
        if (text == null || text.trim().equals("")) {
            return;
        }
        Charset charset = Charset.forName("utf-8");
        byte[] bytes = Base64Utils.encode(text.getBytes(charset));
        String newText = new String(bytes, charset);
        root.setText(newText);
    }

    /**
     * 调用webservice，发送消息
     * 
     * @param DWLX
     *            发送单位类型
     * @param DWBM
     *            发送单位编码
     * @param WJLJ
     *            文件路径
     * @return 调用结果
     */
    private String sendMsg(String DWLX, String DWBM, String WJLJ) {
        DataShareSoap soap = null;
        try {
            soap = new DataShare(new URL(webservice)).getPort(DataShareSoap.class);
        } catch (MalformedURLException e) {
        }
        String msg = "{\"DWLX\":\"" + DWLX + "\",\"DWBM\":\"" + DWBM + "\",\"WJLJ\":\"" + WJLJ + "\"}";
        logger.debug("调用webservice，参数：" + msg);
        String result = soap.sendMsg(msg);
        if (!"0".equals(result)) {
            throw new RuntimeException("Web Service 调用失败：" + result);
        }
        logger.debug("调用结果：" + result);
        return result;

    }

    /**
     * 根据交换记录id生成并发送反馈信息
     * 
     * @param exchangeId
     */
    public void feedBack(final String exchangeId) {
        Exchange exchange = getExchange(exchangeId);
        String nodeCode = exchange.getNodeCode();
        Assert.isTrue(NODES_FEED_BACK.contains(nodeCode), "节点编号为" + nodeCode + "的交换记录不可发送回执");

        // 查询附件
        File1Example file1Example = new File1Example();
        file1Example.setExchangeId(exchangeId);
        List<File1> file1s = file1Service.getByExample(file1Example);

        // 查询财物
        List<Property> properties = propertyService.getByExchangeId(exchangeId);

        // 生成压缩文件
        List<String> paths = new ArrayList<>();
        for (File1 file1 : file1s) {
            paths.add(file1.getFile_path());
        }
     //   String zipName = exchange.getExchangeBatch() + ".zip";
   //     String zipPath = zipService.zip(zipName, paths);
        Exchange temp = new Exchange();
        temp.setId(exchangeId);
   //     temp.setZipPath(new File(zipName).getName());
   //     exchange.setZipPath(temp.getZipPath());
        exchangeService.update(temp);
        temp = null;

        // 生成xml
    //    String xmlName = exchange.getExchangeBatch() + ".xml";

        Share share = ShareBuilder.instance().buildReceipt(exchange, properties, file1s).build();
   //     String xmlPath = toXml(share, xmlName);

        // 上传xml
    //    ftpService.upload(xmlName, xmlPath);
        // 上传zip
   //     ftpService.upload(zipName, zipPath);

        // 发送通知
    //    sendMsg(exchange.getSendUnitTypeCode(), exchange.getSendUnitCode(), exchange.getExchangeBatch());

        if (logger.isInfoEnabled()) {
           // logger.info("反馈交换批次：{}，案件：{}，单位：{}", //
         //           exchange.getExchangeBatch(), //
          //          exchange.getCaseName(), //
             //       exchange.getReceiveUnitName());
        }

    }

    /**
     * 根据交换记录id生成并发送退回信息
     * 
     * @param exchangeId
     */
    public void fallBack(String exchangeId) {

        Exchange exchange = getExchange(exchangeId);
        String nodeCode = exchange.getNodeCode();
        Assert.isTrue(NODES_FALL_BACK.contains(nodeCode), "节点编号为" + nodeCode + "的交换记录不可退回");

        // 查询案件
        Case caze = caseService.get(exchange.getCaseId());

        // 查询嫌疑人
        PersonExample personExample = new PersonExample();
        personExample.setCaseId(exchange.getCaseId());
        List<Person> persons = personService.getByExample(personExample);

        // 查询财物
        List<Property> properties = propertyService.getByExchangeId(exchangeId);

        // 查询附件
        // 退回时附件不一致会导致退回失败，2018年5月8日改为不发送附件信息
        // AnnexExample annexExample = new AnnexExample();
        // annexExample.setExchangeId(exchangeId);
        // List<Annex> annexs = annexService.getByExample(annexExample);

        List<File1> file1ss = new ArrayList<>();

        // 查询照片
        // List<String> pids = new ArrayList<>();
        // for (Property property : properties) {
        // pids.add(property.getId());
        // }
        // List<Photo> photos = photoService.getByProperties(pids);

        PhotoExample photoExample = new PhotoExample();
        photoExample.setExchangeId(exchangeId);
        List<Photo> photos = photoService.getByExample(photoExample);

        // 生成zip
        List<String> paths = new ArrayList<>();
        for (Photo photo : photos) {
          //  paths.add(photo.getFile_path());
        }
        for (File1 file1 : file1ss) {
            paths.add(file1.getFile_path());
        }

  //      String zipName = exchange.getExchangeBatch() + ".zip";
   //     String zipPath = zipService.zip(zipName, paths);

        Exchange temp = new Exchange();
        temp.setId(exchangeId);
  //      temp.setZipPath(new File(zipName).getName());
  //      exchange.setZipPath(temp.getZipPath());
        exchangeService.update(temp);
        temp = null;

        // 生成xml
   //     String xmlName = exchange.getExchangeBatch() + ".xml";
        Share share = ShareBuilder.instance().buildSendBack(exchange, caze, persons, properties, file1ss, photos).build();
     //   String xmlPath = toXml(share, xmlName);

        // 上传xml
    //    ftpService.upload(xmlName, xmlPath);
        // 上传zip
   //     ftpService.upload(zipName, zipPath);

        // 发送通知
     //   sendMsg(exchange.getSendUnitTypeCode(), exchange.getSendUnitCode(), exchange.getExchangeBatch());

        if (logger.isInfoEnabled()) {
         //   logger.info("退回交换批次：{}，案件：{}，单位：{}", //
         //           exchange.getExchangeBatch(), //
         //           exchange.getCaseName(), 
         //           exchange.getReceiveUnitName());
        }

    }

    /**
     * 获取交换记录
     * 
     * @param id
     *            交换记录id
     * @return
     */
    private Exchange getExchange(String id) {
        Assert.isTrue(!StringUtils.isEmpty(id), "请指定交换记录id");
        Exchange exchange = exchangeService.get(id);
        Assert.notNull(exchange, "未找到id为" + id + "的交换记录");
        return exchange;
    }

}
