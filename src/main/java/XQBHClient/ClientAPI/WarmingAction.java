package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.ClientUI.ClientUIMain;

import XQBHClient.ClientUI.Unit.WarmingActionUIController;
import XQBHClient.Utils.log.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class WarmingAction extends Application {
    public static final String Dialog_OVER = "交易成功";
    public static final String Dialog_ERR = "系统错误";
    public static final String Dialog_SELLOUT = "商品已售罄";
    public static String sTitle;
    public static String sMsg;

    public static void show(String sTitle, String sMsg) {

        if (sTitle.equals(Dialog_ERR)) {

            Logger.log("LOG_ERR", sMsg,new Object[]{Thread.currentThread().getStackTrace()[2].getClassName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber()
            });
        }
        else
            Logger.log("LOG_IO", sMsg);
        if (Com.UIFinish) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WarmingActionUIController.class.getResource("WarmingActionUI.fxml"));

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
            WarmingActionUIController controller = loader.getController();

            controller.setStage(stage_dialog);
            controller.setScene(scene);

            controller.warmingInfo.setText(sMsg);
            controller.warmingTitle.setText(sTitle);
            stage_dialog.setAlwaysOnTop(true);
            stage_dialog.showAndWait();
        } else {
            WarmingAction.sTitle = sTitle;//给未初始化的信息做铺垫
            WarmingAction.sMsg = sMsg;//给未初始化的信息做铺垫
            launch();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientUIMain.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WarmingActionUIController.class.getResource("WarmingAction.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        WarmingActionUIController controller = loader.getController();
        controller.warmingInfo.setText(sMsg);
        controller.warmingTitle.setText(sTitle);

        primaryStage.show();

    }
}
