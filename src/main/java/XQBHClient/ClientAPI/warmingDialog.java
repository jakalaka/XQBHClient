package XQBHClient.ClientAPI;

import XQBHClient.ClientUI.ClientUIMain;

import XQBHClient.ClientUI.warmingDialogController;
import XQBHClient.Utils.log.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class warmingDialog {
    public static final String Dialog_OVER="交易成功";
    public static final String Dialog_ERR="程序垮掉了";
    public static final String Dialog_SELLOUT="商品已售罄";

    public static void show(String sTitle,String sMsg){
        Logger.log("LOG_IO","sMsg="+sMsg);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(warmingDialogController.class.getResource("warmingDialog.fxml"));
        try {
            loader.load();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        Stage stage_dialog = new Stage(StageStyle.UNDECORATED);
        stage_dialog.initModality(Modality.WINDOW_MODAL);
        stage_dialog.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage_dialog.initStyle(StageStyle.TRANSPARENT);
        stage_dialog.initOwner(ClientUIMain.primaryStage);
        warmingDialogController controller = loader.getController();

        controller.setStage(stage_dialog);
        controller.setScene(scene);

        controller.warmingInfo.setText(sMsg);
        controller.warmingTitle.setText(sTitle);
        stage_dialog.showAndWait();
    }
}
