package XQBHClient.ClientUI;


import XQBHClient.Client.Com;
import XQBHClient.ClientAPI.*;
import XQBHClient.ClientTran.PayBill;
import XQBHClient.Utils.Modbus.ModbusUtil;
import XQBHClient.Utils.Model.modelHelper;
import XQBHClient.Utils.QRReader.QRReader;
import XQBHClient.Utils.log.Logger;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderDialogController {

    public static Stage orderDialogstage;
    public static Scene orderDialogsence;


    @FXML
    public Label orderInfo;

    @FXML
    public void cancel() {
        Logger.log("LOG_DEBUG", "cancel");
        Event.fireEvent(orderDialogstage, new WindowEvent(orderDialogstage, WindowEvent.WINDOW_CLOSE_REQUEST));

    }

    @FXML
    public void continueTran() {
        /*�����豸�ĳ�ʼ�����*/
        try {
            ModbusUtil.doCheck(Order.controllerIP, Order.controllerPort, Order.controllerAdress);
        } catch (Exception e) {
            Logger.logException("LOG_ERR",e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�����豸�쳣,����ϵ����Ա!!!");
            return;
        }

        /*ɨ���豸�ĳ�ʼ�����*/
        QRReader qrReader = new QRReader(Com.QRReaderComName, 9600, 8, 1, 0);
        boolean initOK = false;
        try {
            initOK = qrReader.Init();
        } catch (PortInUseException e) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "QRReader�쳣,����ϵ����Ա!!!");
            e.printStackTrace();
        } catch (IOException e) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "QRReader��д����!!!����ϵ����Ա");
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "QRReader����ָ�����!!!����ϵ����Ա");
            e.printStackTrace();
        } catch (NoSuchPortException e) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "QRReader�˿�miss!!!����ϵ����Ա");
            e.printStackTrace();
        }
        if (!initOK)
            return;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Scanning.fxml"));

        try {
            loader.load(); //���ض�ά��ɨ�����
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }



        ScannerCartoon scannerCartoon = new ScannerCartoon();
        scannerCartoon.show();
        Task<Void> scanTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Logger.log("LOG_DEBUG", Com.QRReaderComName);

                Order.QRCODE = qrReader.getQRCode();
                return null;
            }
        };
        scanTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                scannerCartoon.close();
                if ("".equals(Order.QRCODE)) {
                    //ɶҲû��,donothing
                } else {
                    //�ȹ���һ��QRCODE
                    if (!isNumeric(Order.QRCODE)) {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, "�������Ķ�ά���Ƿ�Ϊ֧������!");
                        return;
                    }
                    ComCartoon comCartoon = new ComCartoon();
                    comCartoon.show();

                    Task<Void> comTask = new Task<Void>() {
                        @Override
                        public Void call() throws Exception {
                            //�����շѶ���
                            if (false == PayBill.exec()) {
                                Order.callFail = true;
                            } else {
                                Order.callFail = false;
                            }
                            return null;
                        }
                    };
                    comTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                               @Override
                                               public void handle(WorkerStateEvent event) {
                                                   comCartoon.close();
                                                   if (Order.callFail) {
                                                       WarmingDialog.show(WarmingDialog.Dialog_ERR, "����ʧ��,�������Ķ�ά���Ƿ���ȷ\n�����˻��ѿ���,����ϵ����Ա,�����������������½�!");
                                                       return;
                                                   }
                                                   Order.finalOut=false;
                                                   ThingsOutCartoon thingsOutCartoon = new ThingsOutCartoon();
                                                   thingsOutCartoon.show();
                                                   Task<Void> thingsOutTask = new Task<Void>() {
                                                       @Override
                                                       public Void call() throws Exception {
                                                           Order.outFail=false;
                                                           try {
                                                               ModbusUtil.doThingsOut(Order.controllerIP, Order.controllerPort, Order.controllerAdress);
                                                           } catch (Exception e) {
                                                               e.printStackTrace();
                                                               Logger.logException("LOG_ERR",e);
                                                               WarmingDialog.show(WarmingDialog.Dialog_ERR, "ִ�г�������!");
                                                               Order.outFail=true;

                                                               return null;
                                                           }
                                                           int time=0;
                                                           while (!Order.finalOut)
                                                           {
                                                               Thread.sleep(1000);
                                                               time++;
                                                               if (time>30) {
                                                                   Com.ZDZT_U="ERR_ERR_OutNull";
                                                                   break;
                                                               }
                                                           }

                                                           return null;
                                                       }
                                                   };
                                                   thingsOutTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                                       @Override
                                                       public void handle(WorkerStateEvent event) {
                                                           if (Order.outFail)
                                                           {
                                                               thingsOutCartoon.close();
                                                               WarmingDialog.show(WarmingDialog.Dialog_ERR, "�ǳ���Ǹ,�����豸�쳣,����ϵ����Ա!!\n�����������������½�!");

                                                           }else {
                                                               UpdateDSPXX.exec(Order.SPMC_U, -1);
                                                               thingsOutCartoon.close();
                                                               WarmingDialog.show(WarmingDialog.Dialog_OVER, "лл���Ĺ���,���ȷ�Ϸ���������,������������ϵ����Ա");
                                                               Event.fireEvent(orderDialogstage, new WindowEvent(orderDialogstage, WindowEvent.WINDOW_CLOSE_REQUEST));
                                                               modelHelper.goHome();
                                                           }

                                                       }
                                                   });
                                                   new Thread(thingsOutTask).start();
                                               }
                                           }
                    );
                    comTask.setOnFailed(new EventHandler<WorkerStateEvent>()
                    {

                        @Override
                        public void handle(WorkerStateEvent event) {
                            comCartoon.close();
                            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�ǳ���Ǹ,���ն��������ͨѶʧ��,������������ϵ����Ա!!!");
                        }
                    });
                    new Thread(comTask).start();
                }
            }
        });
        new Thread(scanTask).start();


    }

    public Stage getOrderDialogstate() {
        return orderDialogstage;
    }

    public void setOrderDialogstate(Stage orderDialogstate) {
        this.orderDialogstage = orderDialogstate;
    }

    public Scene getOrderDialogsence() {
        return orderDialogsence;
    }

    public void setOrderDialogsence(Scene orderDialogsence) {
        this.orderDialogsence = orderDialogsence;
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
