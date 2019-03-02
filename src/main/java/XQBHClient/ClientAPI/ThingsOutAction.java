package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.ClientUI.Order;
import XQBHClient.ClientUI.Unit.ThingsOutActionUIController;
import XQBHClient.Utils.log.Logger;
import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class ThingsOutAction {


    public ThingsOutAction(Stage parentStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ThingsOutActionUIController.class.getResource("ThingsOutActionUI.fxml"));

        try {
            loader.load(); //加载二维码扫描界面
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);

        ThingsOutActionUIController.stage = new Stage(StageStyle.UNDECORATED);
        ThingsOutActionUIController.stage.initModality(Modality.WINDOW_MODAL);
        ThingsOutActionUIController.stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        ThingsOutActionUIController.stage.initStyle(StageStyle.TRANSPARENT);
        ThingsOutActionUIController.stage.initOwner(parentStage);


        ThingsOutActionUIController.stage.setAlwaysOnTop(true);

    }

    public void show() {

        ThingsOutActionUIController.stage.show();


        Logger.log("LOG_DEBUG", "ThingsOutAction show");
    }

    public void close() {
        ThingsOutActionUIController.stage.close();
        Logger.log("LOG_DEBUG", "ThingsOutAction close");

    }


    public static boolean exec() throws Exception {
        Order.outFail = true;
        Logger.log("LOG_DEBUG", "Order.positionX=" + Order.positionX);
        Logger.log("LOG_DEBUG", "Order.positionY=" + Order.positionY);
        Logger.log("LOG_DEBUG", "Order.positionZ=" + Order.positionZ);
        Logger.log("LOG_DEBUG", "Order.positionZ_max=" + Order.positionZ_max);
        Logger.log("LOG_DEBUG", "Order.positionW=" + Order.positionW);
        Logger.log("LOG_DEBUG", "Order.barcode=" + Order.barcode);


        String IP = Com.Robot_IP;
        int port = Com.Robot_port;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("QRCODE", Order.barcode);
        map.put("positionX", Order.positionX);
        map.put("positionY", Order.positionY);
        map.put("positionZ", Order.positionZ);
        map.put("positionZ_max", Order.positionZ_max);
        map.put("positionW", Order.positionW);
        String json_string = JSON.toJSONString(map);
        BufferedReader in;
        PrintWriter out;

        // Make connection and initialize streams
        Socket socket = new Socket(IP, port);

        try {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json_string);
            String feedback = "";
            while ((feedback = in.readLine()) != null) {

                Logger.log("LOG_DEBUG", "feedback=" + feedback);

                if(ThingsOutActionUIController.text_field!=null) {
                    Logger.log("LOG_DEBUG", "not null");

                    String finalFeedback = feedback;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //javaFX operations should go here
                            ThingsOutActionUIController.text_field.setText(finalFeedback);
                        }
                    });

                }
                Logger.log("LOG_DEBUG", "null");
                if (feedback.equals("success"))
                    Order.outFail = false;

            }


        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);

        } finally {
            socket.close();
        }


        return !Order.outFail;

    }

    public static void main(String[] args) {
        Order.barcode = "6954176883636";
        Order.positionX = 225;
        Order.positionY = -184;
        Order.positionZ = 120;
        Order.positionZ_max = 1000;
        Order.positionW = -90;


        try {
            ThingsOutAction.exec();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}