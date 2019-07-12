package com.coolcloud.sacw.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;

import com.coolcloud.sacw.common.exception.OperationFailedException;
import com.spreada.utils.chinese.ZHConverter;

public class TxmUtil {

    private String printerURI = null;// 打印机完整路径
    private PrintService printService = null;// 打印机服务
    private byte[] dotFont;
    private String begin = "^XA"; // 标签格式以^XA开始
    private String end = "^XZ"; // 标签格式以^XZ结束
    private String content = "";
    
    /**
     * 构造方法
     * 
     * @param printerURI
     *            打印机路径
     */
    public TxmUtil(String printerURI) {
        this.printerURI = printerURI;
        // 加载字体
        File file = new File("C:\\ts24.lib");
        if (!file.exists()) {
            throw new OperationFailedException("字体文件C:\\ts24.lib不存在");
        }
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            dotFont = new byte[fis.available()];
            fis.read(dotFont);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 初始化打印机
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        
        //由于不确定打印的格式,设置为自动打印
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        
        //寻找本地的打印机服务  获取客户端的打印机  查找所有的可用的打印服务
        PrintService[] services = PrintServiceLookup.lookupPrintServices(flavor, pras);
        
        
      
        
        
        if (services != null && services.length > 0) {
            for (PrintService service : services) {
                if (printerURI.equals(service.getName())) {
                    printService = service;
                    break;
                }
            }
        }
        Objects.requireNonNull(printService, "没有找到打印机：[\"" + printerURI + "\"]");
    }

    /**
     * 设置条形码
     * 
     * @param barcode
     *            条码字符
     * @param zpl
     *            条码样式模板
     */
    public void setBarcode(String barcode, String zpl) {
        content += zpl.replace("${data}", barcode);
        System.out.println("content" + content);
    }
    

    /**
     * 中文字符、英文字符(包含数字)混合
     * 
     * @param str
     *            中文、英文
     * @param x
     *            x6坐标
     * @param y
     *            y坐标
     * @param eh
     *            英文字体高度height
     * @param ew
     *            英文字体宽度width
     * @param es
     *            英文字体间距spacing
     * @param mx
     *            中文x轴字体图形放大倍率。范围1-10，默认1
     * @param my
     *            中文y轴字体图形放大倍率。范围1-10，默认1
     * @param ms
     *            中文字体间距。24是个比较合适的值。
     */
    public void setText(String str, int x, int y, int eh, int ew, int es, int mx, int my, int ms) {
        ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
        str = converter.convert(str);

        if (str != null && !"".equals(str)) {
            str = str.replace("“", "'");
            str = str.replace("〞", "'");
            str = str.replace("〝", "'");
            str = str.replace("│", " ");
            str = str.replace(".", ".");
            str = str.replace("。", ".");
            str = str.replace("‘", "'");
            str = str.replace("’", "'");
            str = str.replace("”", "'");
            str = str.replace("（", "(");
            str = str.replace("）", ")");
            str = str.replace("，", "");
            str = str.replace("、", " ");
            char[] charArry = str.toCharArray();
            for (int i = 0; i < charArry.length; i++) {
                if (charArry[i] == '①') {
                    charArry[i] = '1';
                }
                if (charArry[i] == '②') {
                    charArry[i] = '2';
                }

                int chavalue = (int) charArry[i];
                if (chavalue >= 65281 && chavalue <= 65347) {
                    charArry[i] = (char) (chavalue - 65248);
                } else if (chavalue == 12288) {
                    charArry[i] = (char) 32;
                }
            }
            str = new String(charArry);
            
            
        }
        byte[] ch = str2bytes(str);
        for (int off = 0; off < ch.length;) {
            if (((int) ch[off] & 0x00ff) >= 0xA0) {
                int qcode = ch[off] & 0xff;
                int wcode = ch[off + 1] & 0xff;
                content += String.format("^FO%d,%d^XG0000%01X%01X,%d,%d^FS\n", x, y, qcode, wcode, mx, my);
                begin += String.format("~DG0000%02X%02X,00072,003,\n", qcode, wcode);
                qcode = (qcode + 128 - 32) & 0x00ff;
                wcode = (wcode + 128 - 32) & 0x00ff;
                int offset = ((int) qcode - 16) * 94 * 72 + ((int) wcode - 1) * 72;
                for (int j = 0; j < 72; j += 3) {
                    qcode = (int) dotFont[j + offset] & 0x00ff;
                    wcode = (int) dotFont[j + offset + 1] & 0x00ff;
                    int qcode1 = (int) dotFont[j + offset + 2] & 0x00ff;
                    begin += String.format("%02X%02X%02X\n", qcode, wcode, qcode1);
                }
                x = x + ms * mx;
                off = off + 2;
            } else if (((int) ch[off] & 0x00FF) < 0xA0) {
                setChar(String.format("%c", ch[off]), x, y, eh, ew);
                x = x + es;
                off++;
               
            }
        }
    }
    
    //使用一个新的故事和结局应该是一件特别幸福的事情把，但是我觉得 使用心得基础力量或许是一件好的事情把，但是我们应该觉得这也是件幸福的事情把
    //如果我们在这里获得新的故事体系的话，这也许是一件不错的事情把，但是彼此应该成就更好的彼此把使用一件新的事情 的话这应该是一件特别幸福的事情把，但是我觉得如果我们取得了好的效果的话，这也不是为一件好的基础成功的结束日期
    //使用新的技术知识体系的话，有可能获得的这不是一件的好的事情把，但是我们的想法有可能这不仅仅是自己的意识和只是想法的的事情把了，danshiowmfhdlisdjf.ljfldf
    
    

    /**
     * 英文字符串(包含数字)
     * 
     * @param str
     *            英文字符串
     * @param x
     *            x坐标
     * @param y
     *            y坐标
     * @param h
     *            高度
     * @param w
     *            宽度
     */
    public void setChar(String str, int x, int y, int h, int w) {
        content += "^FO" + x + "," + y + "^A0," + h + "," + w + "^FD" + str + "^FS";
    }

    /**
     * 英文字符(包含数字)顺时针旋转90度
     * 
     * @param str
     *            英文字符串
     * @param x
     *            x坐标
     * @param y
     *            y坐标
     * @param h
     *            高度
     * @param w
     *            宽度
     */
    public void setCharR(String str, int x, int y, int h, int w) {
        content += "^FO" + x + "," + y + "^A0R," + h + "," + w + "^FD" + str + "^FS";
    }

    /**
     * 获取完整的ZPL
     * 
     * @return
     */
    public String getZpl() {
        return begin + content + end;
    }

    /**
     * 重置ZPL指令，当需要打印多张纸的时候需要调用。
     */
    public void resetZpl() {
        begin = "^XA";
        end = "^XZ";
        content = "";
    }

    /**
     * 打印
     * 
     * @param zpl
     *            完整的ZPL
     */
    public boolean print(String zpl) {
        if (printService == null) {
            System.out.println("打印出错：没有找到打印机：[" + printerURI + "]");
            return false;
        }
        DocPrintJob job = printService.createPrintJob();
        byte[] by = zpl.getBytes();
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(by, flavor, null);
        try {
            job.print(doc, null);

            System.out.println("已打印");
            return true;
        } catch (PrintException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 字符串转byte[]
     * 
     * @param s
     * @return
     */
    private byte[] str2bytes(String s) {
        if (null == s || "".equals(s)) {
            return null;
        }
        
        
        byte[] abytes = null;
        try {
            abytes = s.getBytes("gb2312");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return abytes;
    }

}
