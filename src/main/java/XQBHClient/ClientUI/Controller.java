package XQBHClient.ClientUI;


import XQBHClient.Utils.Model.modelHelper;
import XQBHClient.Utils.log.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public static myModel model;

    @FXML
    private AnchorPane viewPane;

    @FXML
    private Button Key_Back;

    @FXML
    private Button Key_Home;

    @FXML
    private FlowPane guidePane;


    /*
    初始化
     */
    public void initialize(URL location, ResourceBundle resources) {
        //生成模型
        String rootPath = "resources/Model";
        model=new myModel(rootPath,viewPane);

        ClientUIMain.controller=this;

    }

    @FXML
    public void  keyPressed(KeyEvent event){
        //Logger.log("LOG_DEBUG",event);
        if (event.getCode() == KeyCode.ESCAPE)
        {
            System.exit(0);
        }
    }

    @FXML
    public void  xxx(){
        Logger.log("LOG_DEBUG","xxx");

    }

    @FXML//感觉暂时没用
    public void  buttonPress(TouchEvent event){
        //Logger.log("LOG_DEBUG",event);

    }
    @FXML
    public void  mousePress(MouseEvent event){
//        Logger.log("LOG_DEBUG",((Button)event.getSource()).getId());
        if("Key_Back".equals(((Button)event.getSource()).getId()))
        {
            //Logger.log("LOG_DEBUG","back press");

            Key_Back.setStyle("-fx-effect: dropshadow(one-pass-box, black, 0px, 1px, 100, 100) ");
        }
        if("Key_Home".equals(((Button)event.getSource()).getId()))
        {
            //Logger.log("LOG_DEBUG","home press");

            Key_Home.setStyle("-fx-effect: dropshadow(one-pass-box, black, 0px, 1px, 100, 100)  ");
        }

    }

    @FXML
    public void  mouseRelease(MouseEvent event){
//        Logger.log("LOG_DEBUG",((Button)event.getSource()).getId());
        if("Key_Back".equals(((Button)event.getSource()).getId()))
        {
            //Logger.log("LOG_DEBUG","back release");
            Key_Back.setStyle("-fx-background-color: transparent");
            modelHelper.goBack();
        }
        if("Key_Home".equals(((Button)event.getSource()).getId()))
        {
            //Logger.log("LOG_DEBUG","home release");
            Key_Home.setStyle("-fx-background-color: transparent");
            modelHelper.goHome();
        }
    }
    @FXML
    public void updateGuide(){



    }
    @FXML
    public void cleanFlow(){
        guidePane.getChildren().clear();


    }
    @FXML
    public void addFlow(Node... elements){
        guidePane.getChildren().addAll(elements);

    }


}
