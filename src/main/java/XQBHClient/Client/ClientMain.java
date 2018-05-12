package XQBHClient.Client;


import XQBHClient.Client.Table.Mapper.DSPXXMapper;
import XQBHClient.Client.Table.Model.DSPXX;
import XQBHClient.Client.Table.Model.DSPXXExample;
import XQBHClient.Client.Table.basic.DBAccess;
import XQBHClient.ClientAPI.*;
import XQBHClient.ClientTran.AliPayBill;
import XQBHClient.ClientTran.ZDLogin;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.ClientUI.Controller;
import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.FinishComListener.FinishComListener;
import XQBHClient.Utils.Model.DataModel;
import XQBHClient.Utils.Updater.AutoUpdateMain;
import XQBHClient.Utils.XML.XmlUtils;
import XQBHClient.Utils.log.Logger;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.WindowEvent;
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
        /*deletetmp
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
        */


//        int timeOut = 3000; //��ʱӦ����3������
//        boolean status = false;
//        int times = 0;
//        while (!status) {
//            try {
//                status = InetAddress.getByName(Com.ClientIP).isReachable(timeOut);
//            } catch (IOException e) {
//                Logger.logException("LOG_ERR", e);
//            }
//
//            if (++times > 3)
//                break;
//        }
//        if (!status) {
//            WarmingDialog.show(WarmingDialog.Dialog_ERR, "����δ���û����ǻ����ô�������");
//            return;
//        }


////��9001�˿ڷ���
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = null;//1024-65535��ĳ���˿�
                try {
                    serverSocket = new ServerSocket(9001);
                } catch (Exception e) {
                    Logger.logException("LOG_ERR",e);
                    WarmingDialog.show(WarmingDialog.Dialog_ERR,"����ϵͳ��������ʱ��������ϵ����Ա!!!");
                    System.exit(0);
                }


                while (true) {
                    try {


                        //1������һ����������Socket����ServerSocket��ָ���󶨵Ķ˿ڣ��������˶˿�
                        boolean execFlg = true;
                        boolean restatFlg = false;
                        String sXmlOut = "";

                        Socket socket = null;

                        //2������accept()������ʼ�������ȴ��ͻ��˵�����
                        socket = serverSocket.accept();

                        //3����ȡ������������ȡ�ͻ�����Ϣ
                        InputStream is = null;
                        is = socket.getInputStream();

                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String info = null;
                        info = br.readLine();
                        Logger.log("LOG_DEBUG", "Get  massage " + info);
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
                            sXmlOut = XmlUtils.map2XML(xmlMapOut);
                            pw.write(sXmlOut);
                            pw.flush();
                        } else if ("restart".equals(xmlMapIn.get("FUNCTION"))) {//����
                            restatFlg = true;
                            Map xmlMapOut = new HashMap();
                            xmlMapOut.put("CLIENT", Com.ClientIP);
                            xmlMapOut.put("CWDM_U", "AAAAAA");
                            sXmlOut = XmlUtils.map2XML(xmlMapOut);
                            pw.write(sXmlOut);
                            pw.flush();
                        } else if ("getModelFile".equals(xmlMapIn.get("FUNCTION"))) {//��ȡmodel�ļ���Ϣ
                            try {
                                Logger.log("LOG_DEBUG", "begin to zip");
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
                            Logger.log("LOG_DEBUG", "begin to getModel");
                            Map xmlMapOut = new HashMap();
                            xmlMapOut.put("CLIENT", Com.ClientIP);
                            xmlMapOut.put("CWDM_U", "AAAAAA");
                            Logger.log("LOG_DEBUG", "begin to SetGoodsAccountToModel");
                            SetGoodsAccountToModel.execAll(ClientUIMain.controller.dataModel);
                            Logger.log("LOG_DEBUG", "SetGoodsAccountToModel over");

                            Gson gson = new Gson();
                            Logger.log("LOG_DEBUG", "begin to toJson");
                            String gsonstr = gson.toJson(ClientUIMain.controller.dataModel);
                            Logger.log("LOG_DEBUG", "toJson over");
                            xmlMapOut.put("MODEL", gsonstr);
                            sXmlOut = XmlUtils.map2XML(xmlMapOut);
                            Logger.log("LOG_DEBUG", sXmlOut);
                            pw.write(sXmlOut);
                            pw.flush();
                        } else if ("updClientStock".equals(xmlMapIn.get("FUNCTION"))) {//���¿��
                            String position = "";
                            if (xmlMapIn.get("position") != null)
                                position = xmlMapIn.get("position").toString();
                            int updateNum=Integer.parseInt(xmlMapIn.get("goodsAccount").toString());

                            Map xmlMapOut = new HashMap();

                            if (xmlMapIn.get("checkPass") != null && "1".equals(xmlMapIn.get("checkPass"))) {//�Ƿ�����������¿�治��飬�������
                                //����������ѯ����
                                Order.Init();
                                Order.finalOut = false;
                                DataModel dataModel = DataModel.getDataModelByKey(ClientUIMain.controller.dataModel, position);
                                Order.controllerIP = dataModel.getControllerIP();
                                Order.controllerPort = dataModel.getControllerPort();
                                Order.controllerAdress = dataModel.getControllerAdress();

                                //���붯��
                                Controller.thingsOutPauseShow();
                                Task<Void> thingsOutTask = new Task<Void>() {
                                    @Override
                                    public Void call() throws Exception {
                                        if (!ThingOut.exec() || Order.outFail) {
                                            xmlMapOut.put("CWDM_U", "FFFFFF");
                                        } else {
                                            if (UpdateDSPXX.exec(xmlMapIn.get("position").toString(), updateNum)) {
                                                xmlMapOut.put("CWDM_U", "AAAAAA");
                                            } else
                                                xmlMapOut.put("CWDM_U", "FFFFFF");
                                        }

                                        return null;
                                    }
                                };
                                thingsOutTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                    @Override
                                    public void handle(WorkerStateEvent event) {
                                        Controller.thingsOutPauseClose();
                                        xmlMapOut.put("CWDM_U", "AAAAAA");
                                    }
                                });
                                thingsOutTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
                                    @Override
                                    public void handle(WorkerStateEvent event) {
                                        Controller.thingsOutPauseClose();
                                        xmlMapOut.put("CWDM_U", "FFFFFF");

                                    }
                                });
                                Thread t = new Thread(thingsOutTask);
                                t.start();
                                t.join();
                            }else {
                                if (UpdateDSPXX.exec(position, updateNum)) {
                                    xmlMapOut.put("CWDM_U", "AAAAAA");
                                } else
                                    xmlMapOut.put("CWDM_U", "FFFFFF");
                            }
                            xmlMapOut.put("CLIENT", Com.ClientIP);
                            sXmlOut = XmlUtils.map2XML(xmlMapOut);
                            Logger.log("LOG_DEBUG", sXmlOut);
                            pw.write(sXmlOut);
                            pw.flush();
                        } else {
                            Logger.log("LOG_ERR", "FUNCTION ERR");
                        }
                        Logger.log("LOG_DEBUG", "sXmlOut=" + sXmlOut);
                        //5���ر���Դ
                        socket.shutdownOutput();
                        pw.close();
                        os.close();
                        br.close();
                        isr.close();
                        is.close();
                        socket.close();
                        if (restatFlg) {
                            Logger.log("LOG_DEBUG", "begin to restart");
                            AutoUpdateMain.runbat();
                            System.exit(0);
                        }

                    } catch (Exception e) {
                        Logger.logException("LOG_ERR",e);
                    }

                }


            }
        }).start();

        /*
        ��������
         */
        ClientUIMain.main(args);


    }

}
