package XQBHClient.ClientUI.Unit;

import XQBHClient.Utils.QRReader.QRReader;
import XQBHClient.Utils.log.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ScanActionUIController {
    public static Stage stage;
    private Scene scene;

    @FXML
    public   Label mainText;
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setMainText(String string){
        this.mainText.setText("xxxxxx");
    }


    @FXML
    public void cancel() {
        Logger.log("LOG_DEBUG", "cancel");
        QRReader.stopRead();
        Event.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

    }

}
