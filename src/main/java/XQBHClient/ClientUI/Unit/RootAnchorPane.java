package XQBHClient.ClientUI.Unit;

import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.Utils.Model.DataModel;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.Map;

public class RootAnchorPane extends AnchorPane {
    public RootAnchorPane( DataModel dataModel){
        GridPane gridPane = new GridPane();
        int num = dataModel.getElements().size();
        int count = 0;
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        gridPane.getRowConstraints().add(rowConstraints);
        for (Map.Entry<String, DataModel> entry :
                dataModel.getElements().entrySet()) {

            Button button = new Button(entry.getKey());
            button.getStyleClass().add("rootButton");
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
//                    UnitedModel.position.add(entry.getKey());
                ClientUIMain.controller.go(entry.getValue());
            });
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(100 / num);
            gridPane.getColumnConstraints().add(colConstraints);


            HBox hBox = new HBox();
            hBox.getChildren().add(button);
            hBox.getStyleClass().add("hbox_CENTER");
            gridPane.add(hBox, count, 0);
            count++;
        }
        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);

        AnchorPane.setTopAnchor(gridPane, 0.0);
        AnchorPane.setBottomAnchor(gridPane, 0.0);
        AnchorPane.setLeftAnchor(gridPane, 0.0);
        AnchorPane.setRightAnchor(gridPane, 0.0);
        this.getChildren().add(gridPane);
        this.setId(dataModel.getPosition());
        this.setVisible(false);

    }
}
