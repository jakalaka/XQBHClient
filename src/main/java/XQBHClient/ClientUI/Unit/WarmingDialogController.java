package XQBHClient.ClientUI.Unit;


import XQBHClient.Client.Com;
import XQBHClient.Utils.log.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class WarmingDialogController {

    public Stage stage;
    private Scene scene;


    @FXML
    public Label warmingInfo;

    @FXML
    public Label warmingTitle;

    @FXML
    public void OK() {
        Logger.log("LOG_DEBUG", "OK");
        if (Com.UIFinish)
            Event.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        else
            System.exit(0);

    }

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

}
