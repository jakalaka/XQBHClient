package XQBHClient.Client;

import XQBHClient.ClientAPI.WarmingDialog;


import XQBHClient.Utils.RSA.RSAHandler;
import XQBHClient.Utils.XML.XmlUtils;
import XQBHClient.Utils.log.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class ComCall {
    /**
     * client交易层通用调用
     *
     * @param TranMapIn
     * @param TranMapOut
     * @return
     */
    public static boolean Call(String QTJYM_, String HTJYM_, Map TranMapIn, Map TranMapOut) {
        Map XMLMapIn = new HashMap();//写方法,将TranMapIn添加报文头信息,变为XMLMapIn
        if (true != addInfo(QTJYM_, HTJYM_, TranMapIn, XMLMapIn)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "组织报文头信息失败!");
            return false;
        }

        String XMLIn = XmlUtils.map2XML(XMLMapIn);
        Logger.log("LOG_DEBUG", "XMLIn=" + XMLIn);

        /*
        加密
         */
        PrivateKey upprivatekey = null;
        try {
            upprivatekey = RSAHandler.getPrivateKey(Com.upPrivateKey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "获取私钥失败!");
            return false;
        }

        byte[] encrypt = null;
        try {
            encrypt = RSAHandler.encrypt(XMLIn.getBytes(), upprivatekey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "加密上送报文失败!");
            return false;
        }
        XMLIn = new String(encrypt);
        Logger.log("LOG_DEBUG", "after encrypt XMLIn=[" + XMLIn + "]");
        Logger.log("LOG_DEBUG", "after encrypt XMLIn.length=[" + XMLIn.length() + "]");
        Logger.log("LOG_DEBUG", "Com.upPrivateKey=[" + Com.upPrivateKey + "]");


//
//        CommonTran commonTran;
//        try {
//            commonTran = new CommonTranService().getCommonTranPort();
//        } catch (Exception e) {
//            Logger.logException("LOG_ERR", e);
//            WarmingDialog.show(WarmingDialog.Dialog_ERR, "服务器连接失败!");
//            return false;
//        }
//        String XMLOut;
//        try {
//            XMLOut = commonTran.comtran(XMLIn);
//
//        } catch (Exception e) {
//            Logger.logException("LOG_ERR", e);
//            WarmingDialog.show(WarmingDialog.Dialog_ERR, "服务器故障!");
//            return false;
//        }
        String IP = "192.168.31.62";
//        String IP="newfangledstore.com";

        int port = 9000;
        String XMLOut = "";
        try {


            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(IP, port), 3000);//设置连接请求超时时间10 s
            socket.setSoTimeout(15000);//设置读操作超时时间30 s
//2、获取输出流，向服务器端发送信息
            OutputStream os = socket.getOutputStream();//字节输出流
            PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流

            pw.write(XMLIn);
            pw.flush();
            socket.shutdownOutput();
//3、获取输入流，并读取服务器端的响应信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            StringBuilder stringBuilder = new StringBuilder();

            while ((info = br.readLine()) != null) {
                stringBuilder.append(info);
            }
            XMLOut = stringBuilder.toString();
            //4、关闭资源
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
        }


        Logger.log("LOG_DEBUG", "XMLOut=" + XMLOut);

        /*
        解密
         */

        Logger.log("LOG_ERR", XMLOut);
        PublicKey republicKey = null;
        try {
            republicKey = RSAHandler.getPublicKey(Com.rePublicKey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "获取公钥失败!");
            return false;
        }
        byte[] decrypt = null;
        try {
            decrypt = RSAHandler.decrypt(XMLOut.getBytes(), republicKey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "解密返回报文失败!");
            return false;
        }


        Map XMLMapOut = XmlUtils.XML2map(new String(decrypt));

        if (false == minusInfo(XMLMapOut, TranMapOut)) {
            return false;
        }
        return true;
    }

    /**
     * 检查合法性并添加报文头
     *
     * @param TranMapIn
     * @param XMLMapIn
     * @return
     */
    public static boolean addInfo(String QTJYM_, String HTJYM_, Map TranMapIn, Map XMLMapIn) {
        Map head = new HashMap();
        String[] date = Com.getDate().split("-");
        head.put("QTRQ_U", date[0]); //前台日期
        head.put("QTSJ_U", date[1]); //前台时间
        String sQTLS = Com.getQTLS();
        if (sQTLS.length() != 20) {
            Logger.log("LOG_ERR", "流水获取失败");
            return false;
        }
        head.put("QTLS_U", sQTLS); //前台流水

        head.put("ZDBH_U", Com.ZDBH_U); //终端编号
        head.put("QTJYM_", QTJYM_); //交易码
        head.put("HTJYM_", HTJYM_); //后台交易码
        head.put("ZDJYM_", Com.ZDJYM_); //终端校验码
        head.put("IP_UUU", Com.ClientIP); //ip地址
        XMLMapIn.put("head", head);
        XMLMapIn.put("body", TranMapIn);
        return true;
    }

    /**
     * 检查合法性并去掉报文头
     *
     * @param XMLMapOut
     * @param TranMapOut
     * @return
     */
    public static boolean minusInfo(Map XMLMapOut, Map TranMapOut) {
        TranMapOut.clear();


        /*
        Client通讯后的合法性检查,也不知道写什么好,以后加吧
         */

//        String CWXX_U = (String) ((Map) XMLMapOut.get("head")).get("CWXX_U");
        //加响应要素
//        TranMapOut.put("CWDM_U",CWDM_U);
//        TranMapOut.put("CWXX_U",CWXX_U);
        TranMapOut.putAll((Map) XMLMapOut.get("head"));
        TranMapOut.putAll((Map) XMLMapOut.get("body"));

        String CWDM_U = (String) TranMapOut.get("CWDM_U");


        if (!"AAAAAA".equals(CWDM_U)) {
            /*
            这里做失败处理
             */
            Logger.log("LOG_ERR", "调用失败");
            Logger.log("LOG_ERR", "CWDM_U=" + CWDM_U);
            return false;
        } else Logger.log("LOG_IO", "CWDM_U=" + CWDM_U);

        return true;
    }

}
