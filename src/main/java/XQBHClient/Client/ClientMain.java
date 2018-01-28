package XQBHClient.Client;


import XQBHClient.Client.Table.Mapper.DSPXXMapper;
import XQBHClient.Client.Table.Model.DSPXX;
import XQBHClient.Client.Table.Model.DSPXXExample;
import XQBHClient.Client.Table.basic.DBAccess;
import XQBHClient.ClientAPI.SetGoodsAccountToModel;
import XQBHClient.ClientAPI.UpdateDSPXX;
import XQBHClient.ClientAPI.WarmingDialog;
import XQBHClient.ClientAPI.ZipModel;
import XQBHClient.ClientTran.ZDLogin;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.Utils.FinishComListener.FinishComListener;
import XQBHClient.Utils.Updater.AutoUpdateMain;
import XQBHClient.Utils.XML.XmlUtils;
import XQBHClient.Utils.log.Logger;
import com.google.gson.Gson;
import javafx.scene.control.Alert;
import org.apache.ibatis.session.SqlSession;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
public class ClientMain {

    public static void main(String[] args) {

        /*
        ������
         */
        AutoUpdateMain autoUpdateMain = new AutoUpdateMain();
        if (true != autoUpdateMain.exec(args)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡ����ʧ�ܣ����������Ƿ�ͨ��");

            return;
        }

        /*
        ��ʼ������
         */
        if (false == ClientInit.Init()) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ʼ��ʧ�ܣ�����ϵά����Ա");
            return;
        }
        /*
        ǩ��
         */
        if (!ZDLogin.exec()) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�ն�ǩ��ʧ�ܣ�����ϵά����Ա");
            return;
        }

        /*
        ����ɨ������ʼ��
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                FinishComListener.start();
            }
        }).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Logger.logException("LOG_ERR", e);
        }
        if (!Com.FinishScannerState.equals("N")) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "FinishScannerState ������ʧ��");
            return;
        }


        int timeOut = 3000; //��ʱӦ����3������
        boolean status = false;
        int times = 0;
        while (!status) {
            try {
                status = InetAddress.getByName(Com.ClientIP).isReachable(timeOut);
            } catch (IOException e) {
                Logger.logException("LOG_ERR", e);
            }

            if (++times > 3)
                break;
        }
        if (!status) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "����δ���û����ǻ����ô�������");
            return;
        }
        //��9001�˿ڷ���
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //1������һ����������Socket����ServerSocket��ָ���󶨵Ķ˿ڣ��������˶˿�
                        boolean execFlg = true;
                        boolean restatFlg = false;
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
                        socket.shutdownInput();//�ر�������

                        //4����ȡ���������Ӧ�ͻ��˵�����
                        OutputStream os = null;
                        os = socket.getOutputStream();
                        PrintWriter pw = new PrintWriter(os);
                        Logger.log("LOG_DEBUG", "get cmd:" + xmlMapIn.get("FUNCTION"));

                        if ("pause".equals(xmlMapIn.get("FUNCTION"))) {//�ݶ�Ӫҵ
                            Com.pauseFLG = true;
                            Map xmlMapOut = new HashMap();
                            xmlMapOut.put("CLIENT", Com.ClientIP);
                            xmlMapOut.put("CWDM_U", "AAAAAA");
                            String sXmlOut = XmlUtils.map2XML(xmlMapOut);
                            pw.write(sXmlOut);
                            pw.flush();
                        } else if ("restart".equals(xmlMapIn.get("FUNCTION"))) {//����
                            restatFlg = true;
                            Map xmlMapOut = new HashMap();
                            xmlMapOut.put("CLIENT", Com.ClientIP);
                            xmlMapOut.put("CWDM_U", "AAAAAA");
                            String sXmlOut = XmlUtils.map2XML(xmlMapOut);
                            pw.write(sXmlOut);
                            pw.flush();
                        } else if ("getModelFile".equals(xmlMapIn.get("FUNCTION"))) {//��ȡmodel�ļ���Ϣ
                            try {
                                ZipModel.exec();
                                File file = new File(Com.modelZipFile);
                                FileInputStream fis = new FileInputStream(file);

                                byte[] buf = new byte[1024];
                                int length = 0;

                                while ((length = fis.read(buf, 0, buf.length)) > 0) {
                                    os.write(buf);
                                    os.flush();

                                }
                                fis.close();
                                Logger.log("LOG_DEBUG", "transfile finish");

                            } catch (Exception e) {
                                Logger.logException("LOG_ERR", e);
                            }

                        } else if ("getModel".equals(xmlMapIn.get("FUNCTION"))) {//��ȡmodel��Ϣ
                            Map xmlMapOut = new HashMap();
                            xmlMapOut.put("CLIENT", Com.ClientIP);
                            xmlMapOut.put("CWDM_U", "AAAAAA");
                            SetGoodsAccountToModel.exec(ClientUIMain.controller.dataModel);
                            Gson gson = new Gson();
                            String gsonstr = gson.toJson(ClientUIMain.controller.dataModel);

                            xmlMapOut.put("MODEL", gsonstr);
                            String sXmlOut = XmlUtils.map2XML(xmlMapOut);
                            Logger.log("LOG_DEBUG", sXmlOut);
                            pw.write(sXmlOut);
                            pw.flush();
                        } else if ("updateClientStock".equals(xmlMapIn.get("FUNCTION"))) {//��ȡmodel��Ϣ
                            String position = "";
                            if (xmlMapIn.get("position") != null)
                                position = xmlMapIn.get("position").toString();

                            String goodsAccount = "";
                            if (xmlMapIn.get("goodsAccount") != null)
                                goodsAccount = xmlMapIn.get("goodsAccount").toString();

                            Map xmlMapOut = new HashMap();
                            xmlMapOut.put("CLIENT", Com.ClientIP);
                            if (UpdateDSPXX.exec(position, Integer.parseInt(goodsAccount)))
                                xmlMapOut.put("CWDM_U", "AAAAAA");
                            else
                                xmlMapOut.put("CWDM_U", "FFFFFF");


                            String sXmlOut = XmlUtils.map2XML(xmlMapOut);
                            Logger.log("LOG_DEBUG", sXmlOut);
                            pw.write(sXmlOut);
                            pw.flush();
                        } else {
                            Logger.log("LOG_ERR", "FUNCTION ERR");
                        }

                        //5���ر���Դ
                        pw.close();
                        os.close();
                        br.close();
                        isr.close();
                        is.close();
                        socket.close();
                        serverSocket.close();
                        if (restatFlg) {
                            Logger.log("LOG_DEBUG", "begin to restart");
                            AutoUpdateMain.runbat();
                            System.exit(0);
                        }

                    } catch (Exception e) {
                        Logger.logException("LOG_ERR", e);
                    }
                }
            }
        });
        thread.start();
        /*
        ��������
         */
        ClientUIMain.main(args);


    }

}
