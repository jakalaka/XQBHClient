package XQBHClient.ClientAPI;

import XQBHClient.ClientUI.ComCartoonController;
import XQBHClient.ClientUI.OrderDialogController;
import XQBHClient.Utils.log.Logger;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class ComCartoon {
    public Stage comCartoonStage;


    public ComCartoon() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ComCartoonController.class.getResource("ComCartoon.fxml"));

        try {
            loader.load(); //加载二维码扫描界面
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);

        comCartoonStage = new Stage(StageStyle.UNDECORATED);
        comCartoonStage.initModality(Modality.WINDOW_MODAL);
        comCartoonStage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        comCartoonStage.initStyle(StageStyle.TRANSPARENT);
        comCartoonStage.initOwner(OrderDialogController.orderDialogstage);
        comCartoonStage.setAlwaysOnTop(true);

    }

    public void show() {

        comCartoonStage.show();
        Logger.log("LOG_DEBUG", "ComCartoon show");
    }

    public void close() {
        Event.fireEvent(comCartoonStage, new WindowEvent(comCartoonStage, WindowEvent.WINDOW_CLOSE_REQUEST));
        Logger.log("LOG_DEBUG", "ComCartoon close");
    }
}
