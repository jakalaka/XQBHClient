package XQBHClient.ClientUI.Unit;

import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.ClientUI.Controller;
import XQBHClient.Utils.Model.DataModel;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.Map;

public class NormalAnchorPane extends AnchorPane {
    public NormalAnchorPane(DataModel dataModel) {
        ScrollPane scrollPane = new ScrollPane();
        FlowPane flowPane = new FlowPane();
        for (Map.Entry<String, DataModel> entry_ :
                dataModel.getElements().entrySet()) {
            if (entry_.getValue().getModelType().equals("goods") || entry_.getValue().getModelType().equals("bookGoods")) {
                Parent parent = new Obj_Goods_Sell(entry_.getValue()).SP;
                flowPane.getChildren().add(parent);
                parent.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                    ClientUIMain.controller.go(entry_.getValue());
                });

            } else {
                Button button = new Button(entry_.getKey());
                button.setWrapText(true);
                button.getStyleClass().add("rootButton");
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
//                        UnitedModel.position.add(entry_.getKey());
                    ClientUIMain.controller.go(entry_.getValue());
                });
                HBox hBox = new HBox();
                hBox.getChildren().add(button);
                hBox.getStyleClass().add("hbox_CENTER");
                flowPane.getChildren().add(hBox);
            }
        }

//        flowPane.setStyle("-fx-pa: black");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(flowPane);
        scrollPane.setPickOnBounds(false);
        scrollPane.setMouseTransparent(true);

        scrollPane.getStyleClass().add("normalPane");
        scrollPane.setMouseTransparent(false);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);

        scrollPane.setFitToWidth(true);

        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);


        this.getChildren().add(scrollPane);
        this.setId(dataModel.getPosition());
        this.setVisible(false);

    }
}
