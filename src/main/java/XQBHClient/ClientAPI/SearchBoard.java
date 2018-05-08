package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.ClientUI.Unit.OrderDialogController;
import XQBHClient.ClientUI.Unit.ZFWAITCartoonController;
import XQBHClient.Utils.log.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SearchBoard {
    static Stage SearchBoardStage;
    static Parent root;
    static  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ZFWAITCartoonController.class.getResource("Searchboard.fxml"));
        try {
            loader.load(); //º”‘ÿΩÁ√Ê
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        root= loader.getRoot();
        Scene scene = new Scene(root);


        SearchBoardStage = new Stage(StageStyle.UNDECORATED);
        SearchBoardStage.initModality(Modality.WINDOW_MODAL);
        SearchBoardStage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        SearchBoardStage.initStyle(StageStyle.TRANSPARENT);
        SearchBoardStage.setAlwaysOnTop(true);
        SearchBoardStage.show();
        SearchBoardStage.hide();

    }

    public static void show(int y){
        SearchBoardStage.setY(y);
        SearchBoardStage.setX(Com.ScreenWidth-SearchBoardStage.getWidth()-20);
        SearchBoardStage.show();
        Logger.log("LOG_DEBUG","SearchBoard show");
    }
    public static void close(){
        SearchBoardStage.hide();
        Logger.log("LOG_DEBUG","SearchBoard hide");

    }
}
