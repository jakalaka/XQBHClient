package XQBHClient.Client;


import XQBHClient.Client.Table.Mapper.CXTCSMapper;
import XQBHClient.Client.Table.Model.CXTCS;
import XQBHClient.Client.Table.Model.CXTCSKey;
import XQBHClient.Client.Table.basic.DBAccess;
import XQBHClient.ClientAPI.WarmingDialog;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.Utils.FinishComListener.FinishComListener;
import XQBHClient.Utils.QRReader.QRReader;
import XQBHClient.Utils.Updater.AutoUpdateMain;
import XQBHClient.Utils.XML.XmlUtils;
import XQBHClient.Utils.log.Logger;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static XQBHClient.Utils.PropertiesHandler.PropertiesReader.readKeyFromXML;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class ClientInit {
    public static boolean Init() {


        Logger.log("LOG_IO", Com.getIn);



        /*
        从userInfo中获取相关配置信息
         */
        String ZDJYM_ = readKeyFromXML(new File("resources/Info/userInfo.properties"), "ZDJYM_");
        if (!"".equals(ZDJYM_) && ZDJYM_ != null)
            Com.ZDJYM_ = ZDJYM_;

        String ZDBH_U = readKeyFromXML(new File("resources/Info/userInfo.properties"), "ZDBH_U");
        if (!"".equals(ZDBH_U) && ZDBH_U != null)
            Com.ZDBH_U = ZDBH_U;

        String LogLV = readKeyFromXML(new File("resources/Info/userInfo.properties"), "LogLV");
        if (!"".equals(LogLV) && LogLV != null)
            Com.LogLV = LogLV;

        /*
        从sysInfo中获取相关配置
         */
        String sysinfo = "resources/Info/sysInfo.properties";
        File sysprop = new File(sysinfo);
        QRReader.timeOut = Integer.parseInt(readKeyFromXML(sysprop, "timeOut"));
        if (0 == QRReader.timeOut) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取timeOut错误!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "timeOut=" + QRReader.timeOut);
        }
        QRReader.frequency = Integer.parseInt(readKeyFromXML(sysprop, "frequency"));
        if (0 == QRReader.frequency) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取frequency错误!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "frequency=" + QRReader.frequency);
        }

        Com.PowerControlRelayIP = readKeyFromXML(sysprop, "PowerControlRelayIP");
        if (null == Com.PowerControlRelayIP || "".equals(Com.PowerControlRelayIP)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取PowerControlRelayIP错误!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "PowerControlRelayIP=" + Com.PowerControlRelayIP);
        }

        Com.PowerControlPort = Integer.parseInt(readKeyFromXML(sysprop, "PowerControlPort"));
        if (0 == Com.PowerControlPort) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取PowerControlPort错误!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "PowerControlPort=" + Com.PowerControlPort);
        }
        Com.PowerControlAdress = Integer.parseInt(readKeyFromXML(sysprop, "PowerControlAdress"));
        if (0 == Com.PowerControlAdress) {
            Logger.log("LOG_ERR", "读取PowerControlAdress错误");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "PowerControlAdress=" + Com.PowerControlAdress);
        }

        Com.QRReaderComName = readKeyFromXML(sysprop, "QRReaderComName");
        if (null == Com.QRReaderComName || "".equals(Com.QRReaderComName)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取QRReaderComName错误!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "QRReaderComName=" + Com.QRReaderComName);
        }

        Com.FinishScannerComName = readKeyFromXML(sysprop, "FinishScannerComName");
        if (null == Com.FinishScannerComName || "".equals(Com.FinishScannerComName)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取FinishScannerComName错误!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "FinishScannerComName=" + Com.FinishScannerComName);
        }

        //启一个socket
        Com.ClientIP = readKeyFromXML(sysprop, "OrayIP");
        if (null == Com.ClientIP || "".equals(Com.ClientIP)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取ClientIP错误!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "ClientIP=" + Com.ClientIP);
        }


        int timeOut = 3000; //超时应该在3钞以上
        boolean status = false;
        try {
            status = InetAddress.getByName(Com.ClientIP).isReachable(timeOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!status) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "本机未配置花生壳或配置错误，请检查");
            return false;
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
                        boolean execFlg=true;
                        boolean restatFlg=false;
                        ServerSocket serverSocket = null;//1024-65535的某个端口

                        serverSocket = new ServerSocket(9001);

                        //2、调用accept()方法开始监听，等待客户端的连接
                        Socket socket = null;
                        socket = serverSocket.accept();

                        //3、获取输入流，并读取客户端信息
                        InputStream is = null;
                        is = socket.getInputStream();

                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String info = null;
                        info = br.readLine();
                        Logger.log("LOG_DEBUG", "Get Server massage " + info);
                        Map xmlMapIn = XmlUtils.XML2map(info);
                        if ("pause".equals(xmlMapIn.get("FUNCTION"))) {//暂定营业
                            Logger.log("LOG_DEBUG", "begin to pause");
                            Com.pauseFLG=true;
                        } else if ("restart".equals(xmlMapIn.get("FUNCTION"))) {//重启
                            restatFlg=true;
                        }else{
                            Logger.log("LOG_ERR","FUNCTION ERR");
                            execFlg=false;
                        }


                        socket.shutdownInput();//关闭输入流

                        //4、获取输出流，响应客户端的请求
                        OutputStream os = null;
                        os = socket.getOutputStream();
                        PrintWriter pw = new PrintWriter(os);

                        if (execFlg) {
                            Map xmlMapOut = new HashMap();
                            xmlMapOut.put("CLIENT", Com.ClientIP);
                            xmlMapOut.put("CWDM_U", "AAAAAA");
                            String sXmlOut = XmlUtils.map2XML(xmlMapOut);
                            pw.write(sXmlOut);
                            pw.flush();
                        }


                        //5、关闭资源
                        pw.close();
                        os.close();
                        br.close();
                        isr.close();
                        is.close();
                        socket.close();
                        serverSocket.close();
                        if (restatFlg){
                            Logger.log("LOG_DEBUG", "begin to restart");
                            AutoUpdateMain.runbat();
                            System.exit(0);
                        }

                    } catch (IOException e) {
                        Logger.logException("LOG_ERR", e);
                    }
                }
            }
        });
        thread.start();

        Logger.log("LOG_IO", Com.getOut);

        return true;
    }

}
