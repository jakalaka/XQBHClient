package XQBHClient.ClientUI.Unit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ScanningController {
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

}
