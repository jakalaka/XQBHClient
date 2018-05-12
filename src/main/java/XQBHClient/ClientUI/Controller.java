package XQBHClient.ClientUI;


import XQBHClient.Client.Com;
import XQBHClient.ClientAPI.*;
import XQBHClient.ClientUI.Unit.GoodsAnchorPane;
import XQBHClient.ClientUI.Unit.GuideButton;
import XQBHClient.ClientUI.Unit.NormalAnchorPane;
import XQBHClient.ClientUI.Unit.RootAnchorPane;
import XQBHClient.Utils.Model.*;
import XQBHClient.Utils.Updater.AutoUpdateMain;
import XQBHClient.Utils.XML.XmlUtils;
import XQBHClient.Utils.log.Logger;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.*;


public class Controller implements Initializable {


    public static Stack<DataModel> pathTracer=new Stack();

    public static DataModel dataModel;

    public static ThingsOutCartoon thingsOutCartoon;

    @FXML
    private BorderPane viewPane;

    @FXML
    private Button Key_Back;

    @FXML
    private Button Key_Home;

    @FXML
    private FlowPane guidePane;

    @FXML
    public TextField searchField;


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
        if (false == updateDSPXX(dataModel)) {
            Logger.log("LOG_DEBUG", "更新商品信息失败，退出");
            System.exit(0);
        }


        goHome();

        ClientUIMain.controller = this;


//
//
//                ThingsOutCartoon thingsOutCartoon = new ThingsOutCartoon();
//                thingsOutCartoon.show();
//                Order.Init();
//                Order.finalOut = false;
//                Order.controllerIP = "192.168.31.177";
//                Order.controllerPort = 8080;
//                Order.controllerAdress = 0;
//
//                Task<Void> thingsOutTask = new Task<Void>() {
//                    @Override
//                    public Void call() throws Exception {
//                        ThingOut.exec();
//                        return null;
//                    }
//                };
//                thingsOutTask.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
//
//                    @Override
//                    public void handle(WorkerStateEvent event) {
//                        thingsOutCartoon.close();
//                    }
//                });
//
//                new Thread(thingsOutTask).start();
//
//
//
//            }
//        });


    }

    public static void thingsOutPauseShow() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                thingsOutCartoon = new ThingsOutCartoon(ClientUIMain.primaryStage);
                thingsOutCartoon.show();
            }
        });

    }

    public static void thingsOutPauseClose() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                thingsOutCartoon.close();
            }
        });

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
        pathTracer.clear();
        go(dataModel);
    }

    @FXML
    public void goBack() {
//        ObservableList list = guidePane.getChildren();
//        if (list.size() <= 1)
//            return;
//        DataModel targetDataModel = DataModel.getDataModelByKey(dataModel,((GuideButton) list.get(list.size() - 2)).position);
        if (pathTracer.size()<=1)
            return;
        pathTracer.pop();
        DataModel targetDataModel=pathTracer.pop();
        go(targetDataModel);
    }

//    @FXML
//    public void go(String Key) {
//        cleanFlow();
//        String[] strings = Key.split("\\\\");
//        for (int i = 0; i < strings.length; i++) {
//            String sub_position = "";
//            for (int j = 0; j <= i; j++) {
//                if (j == 0)
//                    sub_position += strings[j];
//                else
//                    sub_position += "\\" + strings[j];
//
//            }
//            addFlow(new GuideButton(strings[i], sub_position));
//        }
//        DataModel subDataModel = DataModel.getDataModelByKey(dataModel, Key);
//        if (subDataModel == null) {
//            WarmingDialog.show("系统出错", "加载数据\"" + Key + "\"出错，请检查模型或联系管理员!\n给您带来的不便深表歉意!");
//            return;
//        }
//        if (subDataModel.getModelType().equals("root")) {
//            setViewPaneShowNode(new RootAnchorPane(subDataModel));
//
//        } else if (subDataModel.getModelType().equals("normal")) {
//            setViewPaneShowNode(new NormalAnchorPane(subDataModel));
//
//        } else if (subDataModel.getModelType().equals("goods") || subDataModel.getModelType().equals("bookGoods")) {
//            setViewPaneShowNode(new GoodsAnchorPane(subDataModel));
//        } else {
//            WarmingDialog.show("配置错误", "非法的模型类型[" + subDataModel.getModelType() + "]!!!");
//        }
//    }

    public void go(DataModel dataModel) {
        try {
            SetGoodsAccountToModel.exec(dataModel);
        } catch (IOException e) {
            Logger.log("LOG_ERR","获取商品信息出错");
            Logger.logException("LOG_ERR",e);
            return;
        }
        cleanFlow();
        String Key = dataModel.getPosition();
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
        if (dataModel == null) {
            WarmingDialog.show("系统出错", "指定的界面为空");
            return;
        }

        if (dataModel.getModelType().equals("root")) {
            setViewPaneShowNode(new RootAnchorPane(dataModel));

        } else if (dataModel.getModelType().equals("normal")) {
            setViewPaneShowNode(new NormalAnchorPane(dataModel));

        } else if (dataModel.getModelType().equals("goods") || dataModel.getModelType().equals("bookGoods")) {
            setViewPaneShowNode(new GoodsAnchorPane(dataModel));
        } else {
            WarmingDialog.show("配置错误", "非法的模型类型[" + dataModel.getModelType() + "]!!!");
        }
        pathTracer.push(dataModel);

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

    @FXML
    public void onSearchTouch() {

        SearchBoard.show((int) (searchField.getLayoutY() + searchField.getHeight()));
    }

    public void search() {
        Logger.log("LOG_DEBUG", "begin to search");
        String sKeywords = searchField.getText();
        Logger.log("LOG_DEBUG", "sKeywords=" + sKeywords);
        DataModel tmpDatamodel = DataModel.getMatchSearchDataModel(dataModel, sKeywords);
        if (tmpDatamodel.getElements().size() == 0) {
            Logger.log("LOG_DEBUG", "no match resoult");
            WarmingDialog.show(WarmingDialog.Dialog_ERR,"无匹配商品");

            return;
        }
        for (Map.Entry<String, DataModel> entry :
                tmpDatamodel.getElements().entrySet()) {
            Logger.log("LOG_DEBUG", "match resoult " + entry.getValue().getPosition());
        }
        go(tmpDatamodel);


    }


}
