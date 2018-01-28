package XQBHClient.ClientAPI;

import XQBHClient.ClientUI.Unit.ComCartoonController;
import XQBHClient.ClientUI.Unit.OrderDialogController;
import XQBHClient.Utils.log.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ScannerCartoon {
    public Stage stage;


    public ScannerCartoon() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ComCartoonController.class.getResource("Scanning.fxml"));

        try {
            loader.load(); //加载二维码扫描界面
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);

        stage = new Stage(StageStyle.UNDECORATED);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(OrderDialogController.orderDialogstage);
        stage.setAlwaysOnTop(true);

    }

    public void show() {
        stage.show();
        Logger.log("LOG_DEBUG","Scanning show");
    }

    public void close() {
        stage.close();
        Logger.log("LOG_DEBUG","Scanning close");
    }
}
