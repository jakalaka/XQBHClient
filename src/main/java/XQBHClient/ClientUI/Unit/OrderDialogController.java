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
        /*暂停销售的检查*/
        if (Com.pauseFLG) {
            WarmingAction.show("服务暂停", "该设备在近5分钟内会更新重启!!!\n在此期间暂停服务，请您稍后!!!");
            return;
        }

        if (AliPayRadio.isSelected())
            Order.ZFFS_U = "z";
        else if (WechatPayRadio.isSelected())
            Order.ZFFS_U = "w";
        else
            WarmingAction.show(WarmingAction.Dialog_ERR, "未选中支付方式或支付方式非法!");

//        /*出货设备的初始化检查*/
//        try {
//            //ModbusUtil.doCheck(Order.controllerIP, Order.controllerPort, Order.controllerAdress);
//
//            ThingsOutPCB.doCheck(Com.ControllerCOMName,Order.positionX,Order.positionY);
//        } catch (Exception e) {
//            Logger.logException("LOG_ERR", e);
//            WarmingAction.show(WarmingAction.Dialog_ERR, "集控设备异常,请联系管理员!!!");
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
                    //啥也没有,donothing
                } else {
                    //先过滤一道QRCODE
                    if (!isNumeric(Order.QRCODE)) {
                        Logger.log("LOG_DEBUG", Order.QRCODE);
                        WarmingAction.show(WarmingAction.Dialog_ERR, "请检查您的二维码是否为支付条码!");
                        return;
                    }
                    CommunicateAction comAction = new CommunicateAction();
                    comAction.show();

                    Task<Void> comTask = new Task<Void>() {
                        @Override
                        public Void call() throws Exception {
                            //发起收费动作
                            Logger.log("LOG_DEBUG", "ZFFS_U=[" + Order.ZFFS_U + "]");
                            if ("z".equals(Order.ZFFS_U)) {
                                Order.callStatus = AliPayBill.exec();
                            } else if ("w".equals(Order.ZFFS_U)) {
                                //微信支付待添加
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
                                                       Logger.log("LOG_DEBUG", "支付ZFWAIT状态");

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
                                                                       if (i > 2)//系统异常3次失败
                                                                           break;
                                                                   }
                                                               }
                                                               return null;
                                                           }
                                                       };
                                                       new Thread(waitPaytask).start();

                                                   }

                                                   if ("SUCCESS".equals(Order.callStatus))//出货
                                                   {

                                                       Logger.log("LOG_DEBUG", "支付SUCCESS状态");
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
                                                                   WarmingAction.show(WarmingAction.Dialog_ERR, "非常抱歉,您支付成功，但店内设备异常,请联系管理员!!\n给您带来不便请您谅解!");
                                                                   Event.fireEvent(orderDialogstage, new WindowEvent(orderDialogstage, WindowEvent.WINDOW_CLOSE_REQUEST));
                                                                   ClientUIMain.controller.goHome();
                                                               } else {
                                                                   UpdateDSPXX.exec(Order.position, -1);//不管成功失败都更新
                                                                   thingsOutCartoon.close();
                                                                   WarmingAction.show(WarmingAction.Dialog_OVER, "谢谢您的光临,点击确认返回主界面,如有疑问请联系管理员");
                                                                   Event.fireEvent(orderDialogstage, new WindowEvent(orderDialogstage, WindowEvent.WINDOW_CLOSE_REQUEST));
                                                                   ClientUIMain.controller.goHome();
                                                               }

                                                           }
                                                       });
                                                       thingsOutTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
                                                           @Override
                                                           public void handle(WorkerStateEvent event) {
                                                               thingsOutCartoon.close();
                                                               WarmingAction.show(WarmingAction.Dialog_ERR, "非常抱歉,店内设备异常,请联系管理员!!\n给您带来不便请您谅解!");
                                                               Event.fireEvent(orderDialogstage, new WindowEvent(orderDialogstage, WindowEvent.WINDOW_CLOSE_REQUEST));
                                                               ClientUIMain.controller.goHome();
                                                           }
                                                       });
                                                       new Thread(thingsOutTask).start();
                                                   }else {
                                                       Logger.log("LOG_DEBUG", "支付非SUCCESS和ZFWAIT状态,默认FAIL");
                                                       WarmingAction.show(WarmingAction.Dialog_ERR, "交易失败,请检查您的二维码是否正确\n如您账户已扣账,请联系管理员,给您带来不便请您谅解!");
                                                       return;
                                                   }

                                               }
                                           }
                    );
                    comTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            comAction.close();
                            WarmingAction.show(WarmingAction.Dialog_ERR, "非常抱歉,本终端与服务器通讯失败,如有疑问请联系管理员!!!");
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
