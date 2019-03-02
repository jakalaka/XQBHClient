package XQBHClient.ClientUI;


import XQBHClient.Client.Com;
import XQBHClient.ClientAPI.*;
import XQBHClient.ClientUI.Unit.GoodsAnchorPane;
import XQBHClient.ClientUI.Unit.GuideButton;
import XQBHClient.ClientUI.Unit.NormalAnchorPane;
import XQBHClient.ClientUI.Unit.RootAnchorPane;
import XQBHClient.Utils.Model.*;
import XQBHClient.Utils.log.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.io.*;
import java.net.URL;
import java.util.*;


public class Controller implements Initializable {


    public static Stack<DataModel> pathTracer = new Stack();

    public static DataModel dataModel;

    public static ThingsOutAction thingsOutCartoon;

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
    ��ʼ��
     */
    public void initialize(URL location, ResourceBundle resources) {


        //����ģ��
        String rootPath = Com.modelFile;
        dataModel = new DataModel(rootPath);

        //������Ʒ���ݿ��¼״̬
        if (true != InitDSPXX.initTable()) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "��ʼ�����ݿ�ʧ��,����ϵ����Ա");
            System.exit(0);
        }
        if (false == updateDSPXX(dataModel)) {
            Logger.log("LOG_DEBUG", "������Ʒ��Ϣʧ�ܣ��˳�");
            System.exit(0);
        }


        goHome();

        ClientUIMain.controller = this;


//
//
//                ThingsOutAction thingsOutCartoon = new ThingsOutAction();
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
                thingsOutCartoon = new ThingsOutAction(ClientUIMain.primaryStage);
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

    @FXML//�о���ʱû��
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
        if (pathTracer.size() <= 1)
            return;
        pathTracer.pop();
        DataModel targetDataModel = pathTracer.pop();
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
//            WarmingAction.show("ϵͳ����", "��������\"" + Key + "\"��������ģ�ͻ���ϵ����Ա!\n���������Ĳ������Ǹ��!");
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
//            WarmingAction.show("���ô���", "�Ƿ���ģ������[" + subDataModel.getModelType() + "]!!!");
//        }
//    }

    public void go(DataModel dataModel) {
        Com.continue_updateUI=false;


        try {
            SetGoodsAccountToModel.exec(dataModel);
        } catch (IOException e) {
            Logger.log("LOG_ERR", "��ȡ��Ʒ��Ϣ����");
            Logger.logException("LOG_ERR", e);
            return;
        }
        cleanFlow();
        String Key = dataModel.getPosition();
        String[] strings = Key.split(File.separator);
        for (int i = 0; i < strings.length; i++) {
            String sub_position = "";
            for (int j = 0; j <= i; j++) {
                if (j == 0)
                    sub_position += strings[j];
                else
                    sub_position += File.separator + strings[j];

            }
            addFlow(new GuideButton(strings[i], sub_position));
        }
        if (dataModel == null) {
            WarmingAction.show("ϵͳ����", "ָ���Ľ���Ϊ��");
            return;
        }

        if (dataModel.getModelType().equals("root")) {
            setViewPaneShowNode(new RootAnchorPane(dataModel));

        } else if (dataModel.getModelType().equals("normal")) {
            setViewPaneShowNode(new NormalAnchorPane(dataModel));

        } else if (dataModel.getModelType().equals("goods") || dataModel.getModelType().equals("bookGoods")) {
            setViewPaneShowNode(new GoodsAnchorPane(dataModel));
        } else {
            WarmingAction.show("���ô���", "�Ƿ���ģ������[" + dataModel.getModelType() + "]!!!");
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
                WarmingAction.show("���ݿ����", "��Ʒ" + subModel.getPosition() + "��Ϣ��ʼ��ʧ��");
                return false;
            }
        }
        //            //�������ݿ�����
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

        SearchBoardAction.show((int) (searchField.getLayoutY() + searchField.getHeight()));
    }

    public void search() {
        Logger.log("LOG_DEBUG", "begin to search");
        String sKeywords = searchField.getText();
        Logger.log("LOG_DEBUG", "sKeywords=" + sKeywords);
        DataModel tmpDatamodel = DataModel.getMatchSearchDataModel(dataModel, sKeywords);
        if (tmpDatamodel.getElements().size() == 0) {
            Logger.log("LOG_DEBUG", "no match resoult");
            WarmingAction.show(WarmingAction.Dialog_ERR, "��ƥ����Ʒ");

            return;
        }
        for (Map.Entry<String, DataModel> entry :
                tmpDatamodel.getElements().entrySet()) {
            Logger.log("LOG_DEBUG", "match resoult " + entry.getValue().getPosition());
        }
        go(tmpDatamodel);


    }


}
