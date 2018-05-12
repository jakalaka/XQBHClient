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
        检查更新
         */
        AutoUpdateMain autoUpdateMain = new AutoUpdateMain();
        if (true != autoUpdateMain.exec(args)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "获取更新失败，请检查网络是否通畅");

            return;
        }

        /*
        初始化程序
         */
        if (false == ClientInit.Init()) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "初始化失败，请联系维护人员");
            return;
        }
        /*
        签到
         */
        if (!ZDLogin.exec()) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "终端签到失败，请联系维护人员");
            return;
        }

        /*
        出货扫描器初始化
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
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "FinishScannerState 启进程失败");
            return;
        }
        */


//        int timeOut = 3000; //超时应该在3钞以上
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
//            WarmingDialog.show(WarmingDialog.Dialog_ERR, "本机未配置花生壳或配置错误，请检查");
//            return;
//        }


////启9001端口服务
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = null;//1024-65535的某个端口
                try {
                    serverSocket = new ServerSocket(9001);
                } catch (Exception e) {
                    Logger.logException("LOG_ERR",e);
                    WarmingDialog.show(WarmingDialog.Dialog_ERR,"启动系统监听服务时出错，请联系管理员!!!");
                    System.exit(0);
                }


                while (true) {
                    try {


                        //1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
                        boolean execFlg = true;
                        boolean restatFlg = false;
                        String sXmlOut = "";

                        Socket socket = null;

                        //2、调用accept()方法开始监听，等待客户端的连接
                        socket = serverSocket.accept();

                        //3、获取输入流，并读取客户端信息
                        InputStream is = null;
                        is = socket.getInputStream();

                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String info = null;
                        info = br.readLine();
                        Logger.log("LOG_DEBUG", "Get  massage " + info);
                        Map xmlMapIn = XmlUtils.XML2map(info);
                        socket.shutdownInput();//关闭输入流

                        //4、获取输出流，响应客户端的请求
                        OutputStream os = null;
                        os = socket.getOutputStream();
                        PrintWriter pw = new PrintWriter(os);
                        Logger.log("LOG_DEBUG", "get cmd:" + xmlMapIn.get("FUNCTION"));

                        if ("pause".equals(xmlMapIn.get("FUNCTION"))) {//暂定营业
                            Com.pauseFLG = true;
                            Map xmlMapOut = new HashMap();
                            xmlMapOut.put("CLIENT", Com.ClientIP);
                            xmlMapOut.put("CWDM_U", "AAAAAA");
                            sXmlOut = XmlUtils.map2XML(xmlMapOut);
                            pw.write(sXmlOut);
                            pw.flush();
                        } else if ("restart".equals(xmlMapIn.get("FUNCTION"))) {//重启
                            restatFlg = true;
                            Map xmlMapOut = new HashMap();
                            xmlMapOut.put("CLIENT", Com.ClientIP);
                            xmlMapOut.put("CWDM_U", "AAAAAA");
                            sXmlOut = XmlUtils.map2XML(xmlMapOut);
                            pw.write(sXmlOut);
                            pw.flush();
                        } else if ("getModelFile".equals(xmlMapIn.get("FUNCTION"))) {//获取model文件信息
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

                        } else if ("getModel".equals(xmlMapIn.get("FUNCTION"))) {//获取model信息
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
                        } else if ("updClientStock".equals(xmlMapIn.get("FUNCTION"))) {//更新库存
                            String position = "";
                            if (xmlMapIn.get("position") != null)
                                position = xmlMapIn.get("position").toString();
                            int updateNum=Integer.parseInt(xmlMapIn.get("goodsAccount").toString());

                            Map xmlMapOut = new HashMap();

                            if (xmlMapIn.get("checkPass") != null && "1".equals(xmlMapIn.get("checkPass"))) {//是否检查出货，更新库存不检查，出货检查
                                //出货并启轮询进程
                                Order.Init();
                                Order.finalOut = false;
                                DataModel dataModel = DataModel.getDataModelByKey(ClientUIMain.controller.dataModel, position);
                                Order.controllerIP = dataModel.getControllerIP();
                                Order.controllerPort = dataModel.getControllerPort();
                                Order.controllerAdress = dataModel.getControllerAdress();

                                //加入动画
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
                        //5、关闭资源
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
        启动界面
         */
        ClientUIMain.main(args);


    }

}
