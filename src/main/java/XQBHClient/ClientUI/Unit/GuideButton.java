package XQBHClient.ClientUI.Unit;

import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.ClientUI.Controller;
import XQBHClient.Utils.Model.DataModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

public class GuideButton extends Button {
    public String position="";
    public GuideButton(String text, String position) {
        super(text+">");
       this.setPadding(new Insets(0,-2,0,-2));

        this.position =position;
        this.getStyleClass().add("guideText");
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientUIMain.controller.go(DataModel.getDataModelByKey(Controller.dataModel,position));
            }
        });
    }
}
