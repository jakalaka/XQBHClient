package XQBHClient.ClientUI.Unit;


import XQBHClient.Client.Com;
import XQBHClient.ClientAPI.*;
import XQBHClient.ClientTran.AliPayBill;
import XQBHClient.ClientTran.ZFWAITQuery;
import XQBHClient.ClientTran.WxPayBill;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.log.Logger;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderDialogController {

    public static Stage orderDialogstage;
    public static Scene orderDialogsence;


    @FXML
    public Label orderInfo;

    @FXML
    public RadioButton AliPayRadio;
    @FXML
    public RadioButton WechatPayRadio;
    @FXML
    public Button AliPayButton;
    @FXML
    public Button WechatPayButton;


    @FXML
    public void cancel() {
        Logger.log("LOG_DEBUG", "cancel");
        Event.fireEvent(orderDialogstage, new WindowEvent(orderDialogstage, WindowEvent.WINDOW_CLOSE_REQUEST));

    }

    @FXML
    public void continueTran() {
        /*��ͣ���۵ļ��*/
        if (Com.pauseFLG) {
            WarmingAction.show("������ͣ", "���豸�ڽ�5�����ڻ��������!!!\n�ڴ��ڼ���ͣ���������Ժ�!!!");
            return;
        }

        if (AliPayRadio.isSelected())
            Order.ZFFS_U = "z";
        else if (WechatPayRadio.isSelected())
            Order.ZFFS_U = "w";
        else
            WarmingAction.show(WarmingAction.Dialog_ERR, "δѡ��֧����ʽ��֧����ʽ�Ƿ�!");

//        /*�����豸�ĳ�ʼ�����*/
//        try {
//            //ModbusUtil.doCheck(Order.controllerIP, Order.controllerPort, Order.controllerAdress);
//
//            ThingsOutPCB.doCheck(Com.ControllerCOMName,Order.positionX,Order.positionY);
//        } catch (Exception e) {
//            Logger.logException("LOG_ERR", e);
//            WarmingAction.show(WarmingAction.Dialog_ERR, "�����豸�쳣,����ϵ����Ա!!!");
//            return;
//        }


        ScanAction scanAction = new ScanAction();
        scanAction.show();

        Task<Void> scanTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Order.QRCODE = scanAction.read();
                System.out.println(Order.QRCODE);
                return null;
            }
        };
        scanTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                scanAction.close();
                if ("".equals(Order.QRCODE)) {
                    //ɶҲû��,donothing
                } else {
                    //�ȹ���һ��QRCODE
                    if (!isNumeric(Order.QRCODE)) {
                        Logger.log("LOG_DEBUG", Order.QRCODE);
                        WarmingAction.show(WarmingAction.Dialog_ERR, "�������Ķ�ά���Ƿ�Ϊ֧������!");
                        return;
                    }
                    CommunicateAction comAction = new CommunicateAction();
                    comAction.show();

                    Task<Void> comTask = new Task<Void>() {
                        @Override
                        public Void call() throws Exception {
                            //�����շѶ���
                            Logger.log("LOG_DEBUG", "ZFFS_U=[" + Order.ZFFS_U + "]");
                            if ("z".equals(Order.ZFFS_U)) {
                                Order.callStatus = AliPayBill.exec();
                            } else if ("w".equals(Order.ZFFS_U)) {
                                //΢��֧�������
                                Order.callStatus = WxPayBill.exec();
                            }

                            return null;
                        }
                    };
                    comTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                               @Override
                                               public void handle(WorkerStateEvent event) {
                                                   comAction.close();
                                                   if ("ZFWAIT".equals(Order.callStatus)) {
                                                       Logger.log("LOG_DEBUG", "֧��ZFWAIT״̬");

                                                       PayWaitAction zfwaitCartoon = new PayWaitAction();
                                                       zfwaitCartoon.show();

                                                       Task<Void> waitPaytask = new Task<Void>() {
                                                           @Override
                                                           public Void call() throws Exception {
                                                               int i = 0;
                                                               while ("ZFWAIT".equals(Order.callStatus) || "REQUERY".equals(Order.callStatus)) {
                                                                   Thread.sleep(5000);
                                                                   Order.callStatus = ZFWAITQuery.exec();
                                                                   if ("REQUERY".equals(Order.callStatus)) {
                                                                       i++;
                                                                       if (i > 2)//ϵͳ�쳣3��ʧ��
                                                                           break;
                                                                   }
                                                               }
                                                               return null;
                                                           }
                                                       };
                                                       new Thread(waitPaytask).start();

                                                   }

                                                   if ("SUCCESS".equals(Order.callStatus))//����
                                                   {

                                                       Logger.log("LOG_DEBUG", "֧��SUCCESS״̬");
                                                       Order.finalOut = false;
                                                       ThingsOutAction thingsOutCartoon = new ThingsOutAction(OrderDialogController.orderDialogstage);
                                                       thingsOutCartoon.show();
                                                       Task<Void> thingsOutTask = new Task<Void>() {
                                                           @Override
                                                           public Void call() throws Exception {
                                                               ThingsOutAction.exec();
                                                               return null;
                                                           }
                                                       };
                                                       thingsOutTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                                           @Override
                                                           public void handle(WorkerStateEvent event) {
                                                               if (Order.outFail) {
                                                                   thingsOutCartoon.close();
                                                                   WarmingAction.show(WarmingAction.Dialog_ERR, "�ǳ���Ǹ,��֧���ɹ����������豸�쳣,����ϵ����Ա!!\n�����������������½�!");
                                                                   Event.fireEvent(orderDialogstage, new WindowEvent(orderDialogstage, WindowEvent.WINDOW_CLOSE_REQUEST));
                                                                   ClientUIMain.controller.goHome();
                                                               } else {
                                                                   UpdateDSPXX.exec(Order.position, -1);//���ܳɹ�ʧ�ܶ�����
                                                                   thingsOutCartoon.close();
                                                                   WarmingAction.show(WarmingAction.Dialog_OVER, "лл���Ĺ���,���ȷ�Ϸ���������,������������ϵ����Ա");
                                                                   Event.fireEvent(orderDialogstage, new WindowEvent(orderDialogstage, WindowEvent.WINDOW_CLOSE_REQUEST));
                                                                   ClientUIMain.controller.goHome();
                                                               }

                                                           }
                                                       });
                                                       thingsOutTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
                                                           @Override
                                                           public void handle(WorkerStateEvent event) {
                                                               thingsOutCartoon.close();
                                                               WarmingAction.show(WarmingAction.Dialog_ERR, "�ǳ���Ǹ,�����豸�쳣,����ϵ����Ա!!\n�����������������½�!");
                                                               Event.fireEvent(orderDialogstage, new WindowEvent(orderDialogstage, WindowEvent.WINDOW_CLOSE_REQUEST));
                                                               ClientUIMain.controller.goHome();
                                                           }
                                                       });
                                                       new Thread(thingsOutTask).start();
                                                   }else {
                                                       Logger.log("LOG_DEBUG", "֧����SUCCESS��ZFWAIT״̬,Ĭ��FAIL");
                                                       WarmingAction.show(WarmingAction.Dialog_ERR, "����ʧ��,�������Ķ�ά���Ƿ���ȷ\n�����˻��ѿ���,����ϵ����Ա,�����������������½�!");
                                                       return;
                                                   }

                                               }
                                           }
                    );
                    comTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            comAction.close();
                            WarmingAction.show(WarmingAction.Dialog_ERR, "�ǳ���Ǹ,���ն��������ͨѶʧ��,������������ϵ����Ա!!!");
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

    public void singleSelect(javafx.event.ActionEvent actionEvent) {
        String radioId = "";
        if (actionEvent.getSource() instanceof RadioButton) {
            radioId = ((RadioButton) actionEvent.getSource()).getId();
        } else if (actionEvent.getSource() instanceof Button) {
            radioId = ((Button) actionEvent.getSource()).getId();
        }
        if ("AliPayRadio".equals(radioId) || "AliPayButton".equals(radioId)) {
            AliPayRadio.setSelected(true);
            WechatPayRadio.setSelected(false);
        } else if ("WechatPayRadio".equals(radioId) || "WechatPayButton".equals(radioId)) {
            WechatPayRadio.setSelected(true);
            AliPayRadio.setSelected(false);
        }
    }
}
