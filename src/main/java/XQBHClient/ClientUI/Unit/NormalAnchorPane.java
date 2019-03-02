package XQBHClient.ClientUI.Unit;

import XQBHClient.Client.Com;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.ClientUI.Controller;
import XQBHClient.Utils.Model.DataModel;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


import javax.swing.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class NormalAnchorPane extends AnchorPane {
    public NormalAnchorPane(DataModel dataModel) {
        Com.continue_updateUI=true;
        ScrollPane scrollPane = new ScrollPane();
        FlowPane flowPane = new FlowPane();


        //大量异步更新UI最优方案
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                final AtomicLong counter = new AtomicLong(-1);
                long count = 0;
                for (Map.Entry<String, DataModel> entry_ :
                        dataModel.getElements().entrySet()) {
                    if(!Com.continue_updateUI)
                        break;
                    count++;
                    while (counter.getAndSet(count) != -1) {

                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            counter.getAndSet(-1);
                            if (entry_.getValue().getModelType().equals("goods") || entry_.getValue().getModelType().equals("bookGoods")) {
                                GridPane gridpane = new Obj_Goods_Sell(entry_.getValue()).SP;
                                flowPane.getChildren().add(gridpane);
                                gridpane.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                                    ClientUIMain.controller.go(entry_.getValue());

                                });
                                gridpane.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                                    gridpane.getChildren().get(0).setStyle("-fx-background-color: rgba(50, 50, 50, 0.5);");
                                });
                                gridpane.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                                    gridpane.getChildren().get(0).setStyle("-fx-background-color: rgba(150, 150, 150, 0.3);");

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
                    });
                }


                return null;
            }
        };


        Thread th = new Thread(task);
        th.setDaemon(true);

        th.start();


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
