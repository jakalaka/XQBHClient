package XQBHClient.ClientUI.Unit;

import XQBHClient.ClientUI.ClientUIMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class GuideButton extends Button {
    public String position="";
    public GuideButton(String text, String position) {
        super(text);
        this.position =position;
        this.getStyleClass().add("guideText");
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientUIMain.controller.go(position);
            }
        });
    }
}
