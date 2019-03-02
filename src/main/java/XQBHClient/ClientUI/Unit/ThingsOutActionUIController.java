package XQBHClient.ClientUI.Unit;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ThingsOutActionUIController  implements Initializable {

    public static  Stage stage;
    private Scene scene;

    public static Label text_field;

    @FXML
    private Label text_field_;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text_field=text_field_;
    }
}
