package XQBHClient.ClientUI.Unit;


import XQBHClient.ClientAPI.SearchBoard;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.Utils.log.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class Searchboard {
    public Stage stage;
    private Scene scene;



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

    public void onAction(javafx.event.ActionEvent actionEvent) {
        String tmp = ((Button) (actionEvent.getSource())).getText();
        Logger.log("LOG_DEBUG", "click "+tmp);
        if (tmp.equals("»ØÉ¾")) {
            if (ClientUIMain.controller.searchField.getText().length()>0)
                ClientUIMain.controller.searchField.deleteText(ClientUIMain.controller.searchField.getText().length()-1,ClientUIMain.controller.searchField.getText().length());
        }else if (tmp.equals("Çå¿Õ")) {
            ClientUIMain.controller.searchField.setText("");
        } else if (tmp.equals("ËÑË÷")) {
            ClientUIMain.controller.search();
            SearchBoard.close();
        } else {
            ClientUIMain.controller.searchField.appendText(((Button) (actionEvent.getSource())).getText());
        }
    }
    public void onMouseOut(){
        SearchBoard.close();
    }



//    @FXML
//    protected void onAction(ActionEvent event) {
//
////        System.out.println(((Button)(actionEvent.getSource())).getLabel());
//
//    }



}
