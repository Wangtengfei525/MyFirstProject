package com.coolcloud.sacw.store;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coolcloud.sacw.common.exception.OperationFailedException;

/**
 * 开箱，使用原系统开箱代码，未做修改
 * 
 * @author 袁永祥
 *
 * @date 2017年9月7日 下午3:13:54
 */
public class Cabinet {

    private static final Logger logger = LoggerFactory.getLogger(Cabinet.class);

    private String host = "192.168.1.12";// 服务器IP地址
    private int port = 50000;// 端口 不变

    public Cabinet() {

    }

    public Cabinet(String _host, int _port) {
        this.host = _host;
        this.port = _port;
    }

    /**
     * 开箱
     * 
     * @param boxNo
     * @return
     */
    public boolean Open(int boxNo) {
        if (boxNo <= 0 || boxNo > 1200) {
            return false;
        }

        // 根据箱号 计算 IP地址,箱柜一个1200个，120个一组，共10组，IP从0开始，9结束。
        int ip = (boxNo - 1) / 120;
        host = host + ip;

        // 根据箱号计算1-120 的新箱号
        int boxNum = boxNo - (ip * 120);
        if (boxNum <= 0 || boxNum > 120) {
            return false;
        }

        byte rec = 0;
        byte right = 2;

        try {
            if (logger.isDebugEnabled()) {
                logger.debug("try connect to {}:{}", host, port);
            }
            // 连接
            Socket clientSocket = new Socket(host, port);

            // 发送
            OutputStream os = clientSocket.getOutputStream();
            DataOutputStream bos = new DataOutputStream(os);
            byte[] dataSend = GetCmd_Open(boxNum);
            bos.write(dataSend);
            bos.flush();

            // 返回
            InputStream is = clientSocket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            rec = dis.readByte();

            // 关闭
            os.close();
            bos.close();
            is.close();
            dis.close();
            clientSocket.close();
        } catch (Exception e) {
            throw new OperationFailedException(e);
        }

        if (rec == right)// 0x32 正确应答(ACK_OK)
            return true;
        else
            return false;
    }

    /**
     * 开箱-命令
     * 
     * @param boxNum
     * @return
     */
    public byte[] GetCmd_Open(int boxNum) {
        List<Byte> data = new ArrayList<Byte>();
        data.add((byte) 0x01);
        data.add((byte) 0x30);
        data.add((byte) 0x31);

        byte[] result = ConvertToSendData(boxNum);
        for (int i = 0; i < result.length; i++) {
            data.add(result[i]);
        }

        data.add((byte) 0x06);
        data.add((byte) 0x30);
        data.add((byte) 0x30);
        return listTobytearray(data);
    }

    /**
     * 箱子号转换
     * 
     * @param num
     * @return
     */
    private byte[] ConvertToSendData(int num) {
        String num16 = Integer.toHexString(num).toUpperCase();
        if (num16.length() == 1) {
            num16 = "0" + num16;
        }
        char[] ch = num16.toCharArray();
        List<Byte> result = new ArrayList<Byte>();
        for (int i = 0; i < ch.length; i++) {
            result.add((byte) ch[i]);
        }

        return listTobytearray(result);
    }

    /**
     * 转换
     * 
     * @param list
     * @return
     */
    private byte[] listTobytearray(List<Byte> list) {
        if (list == null || list.size() < 0)
            return null;

        byte[] bytes = new byte[list.size()];
        int i = 0;
        Iterator<Byte> iterator = list.iterator();
        while (iterator.hasNext()) {
            bytes[i] = iterator.next();
            i++;
        }

        return bytes;
    }
}
