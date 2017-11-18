package XQBHClient.ClientUI;


import XQBHClient.ClientAPI.updateDSPXX;
import XQBHClient.ClientAPI.warmingDialog;
import XQBHClient.ClientTran.PayBill;
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class orderDialogController {

    public Stage stage;
    private Scene scene;


    @FXML
    public Label orderInfo;

    @FXML
    public void cancel() {
        Logger.log("LOG_DEBUG", "cancel");
        Event.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

    }

    @FXML
    public void continueTran() {


        QRReader qrReader = new QRReader(ClientUIMain.comName, 9600, 8, 1, 0);
        boolean initOK = false;
        try {
            initOK = qrReader.Init();
        }  catch (PortInUseException e) {
            warmingDialog.show(warmingDialog.Dialog_ERR,"QRReader被霸占了！！！请联系管理员");
            e.printStackTrace();
        } catch (IOException e) {
            warmingDialog.show(warmingDialog.Dialog_ERR,"QRReader读写错误！！！请联系管理员");
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            warmingDialog.show(warmingDialog.Dialog_ERR,"QRReader操作指令错误！！！请联系管理员");
            e.printStackTrace();
        } catch (NoSuchPortException e) {
            warmingDialog.show(warmingDialog.Dialog_ERR,"QRReader端口miss！！！请联系管理员");
            e.printStackTrace();
        }
        if (!initOK)
            return;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scanning.fxml"));

        try {
            loader.load(); //加载二维码扫描界面
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        Stage stage_dialog;
        stage_dialog = new Stage(StageStyle.UNDECORATED);
        stage_dialog.initModality(Modality.WINDOW_MODAL);
        stage_dialog.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage_dialog.initStyle(StageStyle.TRANSPARENT);
        stage_dialog.initOwner(stage);
        stage_dialog.show();

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Logger.log("LOG_DEBUG", ClientUIMain.comName);

                Order.QRCODE = qrReader.getQRCode();

//                catch (PortInUseException e)
//                {
//                    System.out.println("hey");
//                    warmingDialog.show("QRReader被霸占了！！！请联系管理员");
//                }catch (IOException e)
//                {
//                    System.out.println("hey");
//                    warmingDialog.show("QRReader读写错误！！！请联系管理员");
//                }catch (UnsupportedCommOperationException e)
//                {
//                    System.out.println("hey");
//                    warmingDialog.show("QRReader操作指令错误！！！请联系管理员");
//                }catch (NoSuchPortException e)
//                {
//                    System.out.println("hey");
//                    warmingDialog.show("QRReader端口miss！！！请联系管理员");
//                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                stage_dialog.close();
                if ("".equals(Order.QRCODE)) {
                    //啥也没有，donothing
                } else {
                    //发起收费动作
                    if (false==PayBill.exec()) {
                        warmingDialog.show(warmingDialog.Dialog_ERR,"交易失败，如有疑问请联系管理员");
                        return;
                    }
                    Logger.log("LOG_DEBUG", "出货了");
                    //出货
//                    try {
//                        ModbusUtil.writeDigitalOutput(ClientUIMain.relayIP, ClientUIMain.relayPort, 254, Order.ADRESS, 1);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Logger.log("LOG_ERR", "执行出货第1步错误");
//                        return;
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                        return;
//                    }
//
//
//                    try {
//                        ModbusUtil.writeDigitalOutput(ClientUIMain.relayIP, ClientUIMain.relayPort, 254, Order.ADRESS, 0);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Logger.log("LOG_DEBUG", "执行出货第2步错误");
//                        return;
//                    }
                    updateDSPXX.exec(Order.SPMC_U,-1);
                    warmingDialog.show(warmingDialog.Dialog_OVER,"谢谢您的光临，点击确认返回主界面，如有疑问请联系管理员");
                    modelHelper.goHome();
                    Event.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));


                }
            }
        });
        new Thread(sleeper).start();


    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
