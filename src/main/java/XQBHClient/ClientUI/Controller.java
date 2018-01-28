package XQBHClient.ClientUI;


import XQBHClient.Client.Com;
import XQBHClient.ClientAPI.InitDSPXX;
import XQBHClient.ClientAPI.WarmingDialog;
import XQBHClient.ClientUI.Unit.GoodsAnchorPane;
import XQBHClient.ClientUI.Unit.GuideButton;
import XQBHClient.ClientUI.Unit.NormalAnchorPane;
import XQBHClient.ClientUI.Unit.RootAnchorPane;
import XQBHClient.Utils.Model.*;
import XQBHClient.Utils.log.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class Controller implements Initializable {


    public static DataModel dataModel;


    @FXML
    private BorderPane viewPane;

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
        String rootPath = Com.modelFile;
        dataModel = new DataModel(rootPath);

        //更新商品数据库记录状态
        if (true != InitDSPXX.initTable()) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "初始化数据库失败,请联系管理员");
            System.exit(0);
        }
        if (false==updateDSPXX(dataModel)) {
            Logger.log("LOG_DEBUG","更新商品信息失败，退出");
            System.exit(0);
        }


        goHome();

        ClientUIMain.controller = this;

    }


    @FXML
    public void mouseRelease(MouseEvent event) {
//        Logger.log("LOG_DEBUG",((Button)event.getSource()).getId());
        if ("Key_Back".equals(((Button) event.getSource()).getId())) {
            //Logger.log("LOG_DEBUG","back release");
            Key_Back.setStyle("-fx-background-color: transparent");
//            ModelHelper_.goBack();
        }
        if ("Key_Home".equals(((Button) event.getSource()).getId())) {
            //Logger.log("LOG_DEBUG","home release");
            Key_Home.setStyle("-fx-background-color: transparent");
//            ModelHelper_.goHome();
        }
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        //Logger.log("LOG_DEBUG",event);
        if (event.getCode() == KeyCode.ESCAPE) {
            System.exit(0);
        }
    }

    @FXML
    public void xxx() {
        Logger.log("LOG_DEBUG", "xxx");

    }

    @FXML//感觉暂时没用
    public void buttonPress(TouchEvent event) {
        //Logger.log("LOG_DEBUG",event);

    }

    @FXML
    public void mousePress(MouseEvent event) {
//        Logger.log("LOG_DEBUG",((Button)event.getSource()).getId());
        if ("Key_Back".equals(((Button) event.getSource()).getId())) {
            //Logger.log("LOG_DEBUG","back press");

            Key_Back.setStyle("-fx-effect: dropshadow(one-pass-box, black, 0px, 1px, 100, 100) ");
        }
        if ("Key_Home".equals(((Button) event.getSource()).getId())) {
            //Logger.log("LOG_DEBUG","home press");

            Key_Home.setStyle("-fx-effect: dropshadow(one-pass-box, black, 0px, 1px, 100, 100)  ");
        }

    }

    @FXML
    public void moDSPXXelease(MouseEvent event) {
//        Logger.log("LOG_DEBUG",((Button)event.getSource()).getId());
        if ("Key_Back".equals(((Button) event.getSource()).getId())) {
            //Logger.log("LOG_DEBUG","back release");
            Key_Back.setStyle("-fx-background-color: transparent");
//            ModelHelper_.goBack();
        }
        if ("Key_Home".equals(((Button) event.getSource()).getId())) {
            //Logger.log("LOG_DEBUG","home release");
            Key_Home.setStyle("-fx-background-color: transparent");
//            ModelHelper_.goHome();
        }
    }

    @FXML
    public void updateGuide() {


    }

    @FXML
    public void cleanFlow() {
        guidePane.getChildren().clear();


    }

    @FXML
    public void addFlow(Node... elements) {
        guidePane.getChildren().addAll(elements);

    }

    @FXML
    public void goHome() {
        go(dataModel.getPosition());
    }

    @FXML
    public void goBack() {
        ObservableList list = guidePane.getChildren();
        String targetPosition = "";
        if (list.size() <= 1)
            return;

        targetPosition = ((GuideButton) list.get(list.size() - 2)).position;


        go(targetPosition);
    }

    @FXML
    public void go(String Key) {
        cleanFlow();
        String[] strings = Key.split("\\\\");
        for (int i = 0; i < strings.length; i++) {
            String sub_position = "";
            for (int j = 0; j <= i; j++) {
                if (j == 0)
                    sub_position += strings[j];
                else
                    sub_position += "\\" + strings[j];

            }
            addFlow(new GuideButton(strings[i], sub_position));
        }
        DataModel subDataModel = DataModel.getDataModelByKey(dataModel, Key);
        if (subDataModel == null) {
            WarmingDialog.show("系统出错", "加载数据\"" + Key + "\"出错，请检查模型或联系管理员!\n给您带来的不便深表歉意!");
            return;
        }
        if (subDataModel.getModelType().equals("root")) {
            setViewPaneShowNode(new RootAnchorPane(subDataModel));

        } else if (subDataModel.getModelType().equals("normal")) {
            setViewPaneShowNode(new NormalAnchorPane(subDataModel));

        } else if (subDataModel.getModelType().equals("goods") || subDataModel.getModelType().equals("bookGoods")) {
            setViewPaneShowNode(new GoodsAnchorPane(subDataModel));
        } else {
            WarmingDialog.show("配置错误", "非法的模型类型[" + subDataModel.getModelType() + "]!!!");
        }
    }

    public void setViewPaneShowNode(AnchorPane anchorPane) {
        viewPane.getChildren().clear();
        viewPane.setCenter(anchorPane);
        anchorPane.setVisible(true);

    }

    public boolean updateDSPXX(DataModel subModel) {

        if ("goods".equals(subModel.getModelType())) {
            if (true != InitDSPXX.createData(subModel)) {
                WarmingDialog.show("数据库错误", "商品" + subModel.getPosition() + "信息初始化失败");
                return false;
            }
        }
        //            //创建数据库数据
        for (Map.Entry<String, DataModel> entry :
                subModel.getElements().entrySet()) {
            if (false == updateDSPXX(entry.getValue())) {
                return false;
            }
        }
        return true;
    }


}
