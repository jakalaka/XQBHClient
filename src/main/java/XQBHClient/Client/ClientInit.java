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
        ��userInfo�л�ȡ���������Ϣ
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
        ��sysInfo�л�ȡ�������
         */
        String sysinfo = "resources/Info/sysInfo.properties";
        File sysprop = new File(sysinfo);
        QRReader.timeOut = Integer.parseInt(readKeyFromXML(sysprop, "timeOut"));
        if (0 == QRReader.timeOut) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡtimeOut����!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "timeOut=" + QRReader.timeOut);
        }
        QRReader.frequency = Integer.parseInt(readKeyFromXML(sysprop, "frequency"));
        if (0 == QRReader.frequency) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡfrequency����!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "frequency=" + QRReader.frequency);
        }

        Com.PowerControlRelayIP = readKeyFromXML(sysprop, "PowerControlRelayIP");
        if (null == Com.PowerControlRelayIP || "".equals(Com.PowerControlRelayIP)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡPowerControlRelayIP����!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "PowerControlRelayIP=" + Com.PowerControlRelayIP);
        }

        Com.PowerControlPort = Integer.parseInt(readKeyFromXML(sysprop, "PowerControlPort"));
        if (0 == Com.PowerControlPort) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡPowerControlPort����!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "PowerControlPort=" + Com.PowerControlPort);
        }
        Com.PowerControlAdress = Integer.parseInt(readKeyFromXML(sysprop, "PowerControlAdress"));
        if (0 == Com.PowerControlAdress) {
            Logger.log("LOG_ERR", "��ȡPowerControlAdress����");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "PowerControlAdress=" + Com.PowerControlAdress);
        }

        Com.QRReaderComName = readKeyFromXML(sysprop, "QRReaderComName");
        if (null == Com.QRReaderComName || "".equals(Com.QRReaderComName)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡQRReaderComName����!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "QRReaderComName=" + Com.QRReaderComName);
        }

        Com.FinishScannerComName = readKeyFromXML(sysprop, "FinishScannerComName");
        if (null == Com.FinishScannerComName || "".equals(Com.FinishScannerComName)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡFinishScannerComName����!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "FinishScannerComName=" + Com.FinishScannerComName);
        }

        //��һ��socket
        Com.ClientIP = readKeyFromXML(sysprop, "OrayIP");
        if (null == Com.ClientIP || "".equals(Com.ClientIP)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡClientIP����!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "ClientIP=" + Com.ClientIP);
        }


        int timeOut = 3000; //��ʱӦ����3������
        boolean status = false;
        try {
            status = InetAddress.getByName(Com.ClientIP).isReachable(timeOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!status) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "����δ���û����ǻ����ô�������");
            return false;
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //1������һ����������Socket����ServerSocket��ָ���󶨵Ķ˿ڣ��������˶˿�
                        boolean execFlg=true;
                        boolean restatFlg=false;
                        ServerSocket serverSocket = null;//1024-65535��ĳ���˿�

                        serverSocket = new ServerSocket(9001);

                        //2������accept()������ʼ�������ȴ��ͻ��˵�����
                        Socket socket = null;
                        socket = serverSocket.accept();

                        //3����ȡ������������ȡ�ͻ�����Ϣ
                        InputStream is = null;
                        is = socket.getInputStream();

                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String info = null;
                        info = br.readLine();
                        Logger.log("LOG_DEBUG", "Get Server massage " + info);
                        Map xmlMapIn = XmlUtils.XML2map(info);
                        if ("pause".equals(xmlMapIn.get("FUNCTION"))) {//�ݶ�Ӫҵ
                            Logger.log("LOG_DEBUG", "begin to pause");
                            Com.pauseFLG=true;
                        } else if ("restart".equals(xmlMapIn.get("FUNCTION"))) {//����
                            restatFlg=true;
                        }else{
                            Logger.log("LOG_ERR","FUNCTION ERR");
                            execFlg=false;
                        }


                        socket.shutdownInput();//�ر�������

                        //4����ȡ���������Ӧ�ͻ��˵�����
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


                        //5���ر���Դ
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
