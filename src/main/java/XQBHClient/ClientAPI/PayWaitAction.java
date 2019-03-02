package XQBHClient.ClientAPI;

import XQBHClient.ClientUI.Unit.OrderDialogController;
import XQBHClient.ClientUI.Unit.PayWaitActionUIController;
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


public class PayWaitAction {


    public PayWaitAction() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PayWaitActionUIController.class.getResource("PayWaitActionUI.fxml"));

        try {
            loader.load(); //加载二维码扫描界面
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);

        PayWaitActionUIController.stage= new Stage(StageStyle.UNDECORATED);
        PayWaitActionUIController.stage.initModality(Modality.WINDOW_MODAL);
        PayWaitActionUIController.stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        PayWaitActionUIController.stage.initStyle(StageStyle.TRANSPARENT);
        PayWaitActionUIController.stage.initOwner(OrderDialogController.orderDialogstage);
        PayWaitActionUIController.stage.setAlwaysOnTop(true);

    }

    public void show() {

        PayWaitActionUIController.stage.show();


        Logger.log("LOG_DEBUG","PayWaitAction show");
    }

    public void close() {
        Event.fireEvent(PayWaitActionUIController.stage, new WindowEvent(PayWaitActionUIController.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        Logger.log("LOG_DEBUG","PayWaitAction close");
    }
}
