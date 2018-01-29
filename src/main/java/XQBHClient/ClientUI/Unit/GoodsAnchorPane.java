package XQBHClient.ClientUI.Unit;

import XQBHClient.Client.Com;
import XQBHClient.ClientAPI.GetSPNum;
import XQBHClient.ClientAPI.WarmingDialog;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.Model.DataModel;
import XQBHClient.Utils.log.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class GoodsAnchorPane extends AnchorPane {
    private Button buy;

    public GoodsAnchorPane(DataModel dataModel) {

        int restNum = 0;
        if ("goods".equals(dataModel.getModelType()))
            restNum = GetSPNum.exec(dataModel.getPosition());
        GridPane gridPane = new GridPane();



            /*
            ��ʼ����ͼƬ����
             */
        int imgCount = dataModel.getImgs().length;
        Pagination pagination = new Pagination(imgCount, 0);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                AnchorPane anchorPane = new AnchorPane();

                String images = ("file:/" + (new File("resources/" + dataModel.getImgs()[param])).getAbsolutePath()).replaceAll("\\\\", "/").replaceAll(" ", "%20");
                anchorPane.setStyle("-fx-background-image: url('" + images + "'); " +
                        "-fx-background-position: center; " +
                        "-fx-background-repeat: no-repeat;" +
                        " -fx-background-size: contain;"
                );
                return anchorPane;
            }
        });
            /*
            ��ʼ���ɰ�ť����
             */
        AnchorPane infoAnchor = new AnchorPane();
        BorderPane infoArea = new BorderPane();

            /*
            ��Ʒ����
             */
        Label thingsName = new Label(dataModel.getModelName());
        thingsName.getStyleClass().add("thingsName");
        HBox nameHbox = new HBox();
        nameHbox.getChildren().add(thingsName);
        nameHbox.getStyleClass().add("infoLabel");
        infoArea.setTop(nameHbox);

            /*
            ��Ʒ����
             */
        Label label = new Label(dataModel.getIntroduction());
        label.getStyleClass().add("infoLabel");
        HBox infoHbox = new HBox();
        infoHbox.getStyleClass().add("infoHbox");
        infoHbox.getChildren().add(label);
        infoArea.setCenter(infoHbox);


            /*
            �۸�͹���
             */
        GridPane priceAbuy = new GridPane();
        HBox hbPrice = new HBox();
        HBox hbrestNum = new HBox();
        hbPrice.getStyleClass().add("hbox_Price");
        hbrestNum.getStyleClass().add("hbox_restNum");
        HBox hbBuy = new HBox();
        hbBuy.getStyleClass().add("hbox_Buy");
        DecimalFormat df = new DecimalFormat("######0.00");
        Label price = new Label("�۸񣺣�" + df.format(dataModel.getUnitPrice()));
        price.setWrapText(true);
        price.getStyleClass().add("thingsPrice");
        if ("bookGoods".equals(dataModel.getModelType())) {
            buy = new Button("Ԥ��");
            buy.getStyleClass().add("buyButton");

        } else {
            buy = new Button("����");
            Label restNumLable = new Label("ʣ���棺" + restNum);
            restNumLable.setId("goodsAmount");
            restNumLable.setWrapText(true);
            restNumLable.getStyleClass().add("restNumLable");
            hbrestNum.getChildren().add(restNumLable);
            if (restNum <= 0) {
                buy.setText("������");
                buy.getStyleClass().add("buyButton_over");
            } else {
                buy.getStyleClass().add("buyButton");
            }

        }
        hbPrice.getChildren().add(price);

        hbBuy.getChildren().add(buy);
        priceAbuy.add(hbrestNum, 0, 0);
        priceAbuy.add(hbPrice, 0, 1);
        priceAbuy.add(hbBuy, 1, 1);
        ColumnConstraints column1 = new ColumnConstraints(119, 119, Double.MAX_VALUE);
        ColumnConstraints column2 = new ColumnConstraints(0, 0, Double.MAX_VALUE);
        column1.setHgrow(Priority.ALWAYS);
        column2.setHgrow(Priority.ALWAYS);
        priceAbuy.getColumnConstraints().addAll(column1, column2);
        infoArea.setBottom(priceAbuy);

            /*
            ��ӹ������¼�
             */
        buy.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if (!"OK".equals(Com.ZDZT_U)) {
                Logger.log("LOG_ERR", "δ��⵽���򼴳���!!!��ȷ���豸����������!!!");
                WarmingDialog.show(WarmingDialog.Dialog_ERR, "̽�����쳣,Ϊ�������ʽ�ȫ,��ʱ�رս��׹���!\n���������Ĳ����������Ǹ��!!!");
                return;
            }
            Integer iNum = 0;
            if ("goods".equals(dataModel.getModelType())) {
                iNum = GetSPNum.exec(dataModel.getPosition());
                if (iNum <= 0) {
                    WarmingDialog.show(WarmingDialog.Dialog_SELLOUT, "��,������,�´�����~");
                    return;
                }
            }else {
                WarmingDialog.show(WarmingDialog.Dialog_ERR, "�ݲ�֧��Ԥ������~");
                return;
            }


                /*
                ��������
                 */
            Order.Init();
            Order.JYJE_U = dataModel.getUnitPrice();
            Order.SPMC_U = dataModel.getModelName();
            Order.position = dataModel.getPosition();

            Order.controllerAdress = dataModel.getControllerAdress();
            Order.controllerIP = dataModel.getControllerIP();
            Order.controllerPort = dataModel.getControllerPort();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("OrderDialog.fxml"));

            try {
                loader.load();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            Parent root_ = loader.getRoot();
            Scene scene = new Scene(root_);
            Stage stage_dialog = new Stage(StageStyle.UNDECORATED);
            stage_dialog.initModality(Modality.WINDOW_MODAL);
            stage_dialog.setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            stage_dialog.initStyle(StageStyle.TRANSPARENT);
            stage_dialog.initOwner(ClientUIMain.primaryStage);
            OrderDialogController controller = loader.getController();

            controller.setOrderDialogstate(stage_dialog);
            controller.setOrderDialogsence(scene);

            controller.orderInfo.setText("���׽��:" + Order.JYJE_U + "\n��Ʒ����:" + Order.SPMC_U);
            stage_dialog.showAndWait();
        });

//        if (restNum <= 0) {
//            buy.setText("������");
//            buy.getStyleClass().add("buyButton_over");
//
//        } else {
//            buy.getStyleClass().add("buyButton");
//        }


        AnchorPane.setTopAnchor(infoArea, 0.0);
        AnchorPane.setBottomAnchor(infoArea, 0.0);
        AnchorPane.setLeftAnchor(infoArea, 0.0);
        AnchorPane.setRightAnchor(infoArea, 0.0);


        infoAnchor.getChildren().add(infoArea);
        gridPane.add(infoAnchor, 1, 0);


        //����
        ColumnConstraints colConstraints_0 = new ColumnConstraints();
        colConstraints_0.setPercentWidth(61.8);
        gridPane.getColumnConstraints().add(colConstraints_0);

        ColumnConstraints colConstraints_1 = new ColumnConstraints();
        colConstraints_1.setPercentWidth(38.2);
        gridPane.getColumnConstraints().add(colConstraints_1);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        gridPane.getRowConstraints().add(rowConstraints);

        gridPane.add(pagination, 0, 0);//ͼƬ����


        AnchorPane.setTopAnchor(gridPane, 0.0);
        AnchorPane.setBottomAnchor(gridPane, 0.0);
        AnchorPane.setLeftAnchor(gridPane, 0.0);
        AnchorPane.setRightAnchor(gridPane, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
        this.getChildren().add(gridPane);
        this.setVisible(false);
        this.setId(dataModel.getPosition());
    }
}
