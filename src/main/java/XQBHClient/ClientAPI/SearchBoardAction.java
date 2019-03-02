package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.ClientUI.Unit.SearchBoardActionUIController;
import XQBHClient.Utils.log.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SearchBoardAction {
    static Parent root;
    static  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SearchBoardActionUIController.class.getResource("SearchBoardActionUI.fxml"));
        try {
            loader.load(); //º”‘ÿΩÁ√Ê
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        root= loader.getRoot();
        Scene scene = new Scene(root);


        SearchBoardActionUIController.stage = new Stage(StageStyle.UNDECORATED);
        SearchBoardActionUIController.stage.initModality(Modality.WINDOW_MODAL);
        SearchBoardActionUIController.stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        SearchBoardActionUIController.stage.initStyle(StageStyle.TRANSPARENT);
        SearchBoardActionUIController.stage.setAlwaysOnTop(true);
        SearchBoardActionUIController.stage.show();
        SearchBoardActionUIController.stage.hide();

    }

    public static void show(int y){
        SearchBoardActionUIController.stage.setY(y);
        SearchBoardActionUIController.stage.setX(Com.ScreenWidth-SearchBoardActionUIController.stage.getWidth()-20);
        SearchBoardActionUIController.stage.show();
        Logger.log("LOG_DEBUG","SearchBoardAction show");
    }
    public static void close(){
        SearchBoardActionUIController.stage.hide();
        Logger.log("LOG_DEBUG","SearchBoardAction hide");

    }
}
