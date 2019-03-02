package XQBHClient.ClientAPI;

import XQBHClient.ClientUI.Unit.CommunicateActionUIController;
import XQBHClient.ClientUI.Unit.OrderDialogController;
import XQBHClient.ClientUI.Unit.ScanActionUIController;
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


public class CommunicateAction {


    public CommunicateAction() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CommunicateActionUIController.class.getResource("CommunicateActionUI.fxml"));

        try {
            loader.load(); //加载二维码扫描界面
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);

        CommunicateActionUIController.stage = new Stage(StageStyle.UNDECORATED);
        CommunicateActionUIController.stage.initModality(Modality.WINDOW_MODAL);
        CommunicateActionUIController.stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        CommunicateActionUIController.stage.initStyle(StageStyle.TRANSPARENT);
        CommunicateActionUIController.stage.initOwner(OrderDialogController.orderDialogstage);
        CommunicateActionUIController.stage.setAlwaysOnTop(true);

    }

    public void show() {

        CommunicateActionUIController.stage.show();
        Logger.log("LOG_DEBUG", "CommunicateAction show");
    }

    public void close() {
        CommunicateActionUIController.stage.close();
        Logger.log("LOG_DEBUG", "CommunicateAction close");
    }
}
