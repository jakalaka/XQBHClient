package XQBHClient.ClientAPI;

import XQBHClient.ClientUI.comCartoonController;
import XQBHClient.ClientUI.orderDialogController;
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


public class comCartoon {
    private Stage comCartoonState;

    public comCartoon() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(comCartoonController.class.getResource("comCartoon.fxml"));

        try {
            loader.load(); //加载二维码扫描界面
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);

        comCartoonState = new Stage(StageStyle.UNDECORATED);
        comCartoonState.initModality(Modality.WINDOW_MODAL);
        comCartoonState.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        comCartoonState.initStyle(StageStyle.TRANSPARENT);
        comCartoonState.initOwner(orderDialogController.orderDialogstate);
        comCartoonState.setAlwaysOnTop(true);

    }

    public void show() {

        comCartoonState.show();


        System.out.println("cartoon show");
    }

    public void close() {
        Event.fireEvent(comCartoonState, new WindowEvent(comCartoonState, WindowEvent.WINDOW_CLOSE_REQUEST));
        System.out.println("cartoon close");
    }
}
