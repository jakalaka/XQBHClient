package XQBHClient.ClientAPI;

import XQBHClient.ClientUI.Unit.OrderDialogController;
import XQBHClient.ClientUI.Unit.ZFWAITCartoonController;
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


public class ZFWAITCartoon {
    public Stage ZFWAITCartoonStage;


    public ZFWAITCartoon() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ZFWAITCartoonController.class.getResource("ZFWAITCartoon.fxml"));

        try {
            loader.load(); //加载二维码扫描界面
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);

        ZFWAITCartoonStage = new Stage(StageStyle.UNDECORATED);
        ZFWAITCartoonStage.initModality(Modality.WINDOW_MODAL);
        ZFWAITCartoonStage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        ZFWAITCartoonStage.initStyle(StageStyle.TRANSPARENT);
        ZFWAITCartoonStage.initOwner(OrderDialogController.orderDialogstage);
        ZFWAITCartoonStage.setAlwaysOnTop(true);

    }

    public void show() {

        ZFWAITCartoonStage.show();


        Logger.log("LOG_DEBUG","ZFWAITCartoon show");
    }

    public void close() {
        Event.fireEvent(ZFWAITCartoonStage, new WindowEvent(ZFWAITCartoonStage, WindowEvent.WINDOW_CLOSE_REQUEST));
        Logger.log("LOG_DEBUG","ZFWAITCartoon close");
    }
}
