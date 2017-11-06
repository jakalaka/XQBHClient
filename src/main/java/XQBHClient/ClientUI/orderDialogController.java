package XQBHClient.ClientUI;


import XQBHClient.ClientTran.ZDLogin;
import XQBHClient.Utils.Modbus.ModbusUtil;
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

import java.io.IOException;

public class orderDialogController {

    public Stage stage;
    private Scene scene;


    @FXML
    public Label orderInfo;

    @FXML
    public void cancel() {
        Logger.log("LOG_DEBUG","cancel");
        Event.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

    }

    @FXML
    public void continueTran() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scanning.fxml"));

        try {
            loader.load();
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
                Logger.log("LOG_DEBUG",ClientUIMain.comName);
                QRReader qrReader=new QRReader(ClientUIMain.comName,9600,8,1,0);
                Order.QRCODE=qrReader.getQRCode();
                Logger.log("LOG_DEBUG", Order.QRCODE);

                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                stage_dialog.close();
                if ("".equals(Order.QRCODE))
                {
                    //啥也没有，donothing
                }else {
                    //发起收费动作
                    Logger.log("LOG_DEBUG","出货了");
                    //出货
                    try {
                        ModbusUtil.writeDigitalOutput(ClientUIMain.relayIP, ClientUIMain.relayPort,254, Order.ADRESS,1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.log("LOG_ERR","执行出货第1步错误");
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return;
                    }


                    try {
                        ModbusUtil.writeDigitalOutput(ClientUIMain.relayIP, ClientUIMain.relayPort,254, Order.ADRESS,0);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.log("LOG_DEBUG","执行出货第2步错误");
                        return;
                    }
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
