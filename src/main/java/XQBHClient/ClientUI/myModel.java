package XQBHClient.ClientUI;


import XQBHClient.Client.Table.Mapper.DSPXXMapper;
import XQBHClient.Client.Table.Model.DSPXX;
import XQBHClient.Client.Table.Model.DSPXXExample;
import XQBHClient.Client.Table.Model.DSPXXKey;
import XQBHClient.Client.Table.basic.DBAccess;
import XQBHClient.ClientAPI.getSPNum;
import XQBHClient.ClientAPI.initDSPXX;
import XQBHClient.ClientAPI.warmingDialog;
import XQBHClient.Utils.Model.modelHelper;
import XQBHClient.Utils.log.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.ibatis.session.SqlSession;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static XQBHClient.Utils.PropertiesHandler.PropertiesReader.readKeyFromXML;

public class myModel {
    private Map<String, myModel> Elements;
    private String modelType;
    private double price;
    private String imgs[];
    private String Name;
    private boolean buildSuccess = false;
    private String introduction;
    private AnchorPane anchorPane;
    private int controllerAdress;
    public Vector<myModel> position;
    public Button buy;
    public Label restNumLable=null;

    public myModel(String path, AnchorPane viewPane) {
        setElements(new HashMap<String, myModel>());
        File file = new File(path);
        if (!file.exists())
            return;

        /*
        读取配置文件，生成树对象
         */
        File prop = new File(path + "/model.properties");
        setModelType(readKeyFromXML(prop, "modelType"));
        String sPrice = readKeyFromXML(prop, "price");
        if (null == sPrice || "".equals(sPrice)) {
            price = 0;
        } else {
            price = Double.parseDouble(sPrice);
        }
        setPrice(price);
        setIntroduction(readKeyFromXML(prop, "introduction"));
        setImgs(readKeyFromXML(prop, "Img").split(";"));
        String adress = readKeyFromXML(prop, "controllerAdress");
        if (null == adress || "".equals(adress))
            adress = "9999";

        setControllerAdress(Integer.parseInt(adress));


        if ("root".equals(getModelType())) {
            setName("root");
            position = new Vector<myModel>();
            position.add(this);
        } else {
            setName(file.getName());
        }
        File[] files = file.listFiles();
        for (File subfile :
                files) {
            if (subfile.isDirectory()) {
                myModel mymodel_ = new myModel(subfile.getPath(), viewPane);
                Elements.put(mymodel_.getName(), mymodel_);
            }
        }


        /*生成AnchorPane ap对接添加到viewPane*/
        AnchorPane ap = new AnchorPane();
        AnchorPane.setTopAnchor(ap, 0.0);
        AnchorPane.setBottomAnchor(ap, 0.0);
        AnchorPane.setLeftAnchor(ap, 0.0);
        AnchorPane.setRightAnchor(ap, 0.0);
        ap.setVisible(false);
        if ("root".equals(getModelType())) {
            GridPane gridPane = new GridPane();
            int num = this.getElements().size();
            int count = 0;
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100);
            gridPane.getRowConstraints().add(rowConstraints);
            for (Map.Entry<String, myModel> entry :
                    this.getElements().entrySet()) {
                Button button = new Button(entry.getKey());
                button.getStyleClass().add("rootButton");
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                    modelHelper.go(getElements().get(entry.getKey()));
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

            AnchorPane.setTopAnchor(gridPane, 0.0);
            AnchorPane.setBottomAnchor(gridPane, 0.0);
            AnchorPane.setLeftAnchor(gridPane, 0.0);
            AnchorPane.setRightAnchor(gridPane, 0.0);
            ap.getChildren().add(gridPane);
            ap.setVisible(true);


        } else if ("normal".equals(getModelType())) {

            ScrollPane scrollPane = new ScrollPane();
            FlowPane flowPane = new FlowPane();
            for (Map.Entry<String, myModel> entry :
                    this.getElements().entrySet()) {

                Button button = new Button(entry.getKey());
                button.setWrapText(true);
                button.getStyleClass().add("rootButton");
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                    modelHelper.go(getElements().get(entry.getKey()));
                });
                HBox hBox = new HBox();
                hBox.getChildren().add(button);
                hBox.getStyleClass().add("hbox_CENTER");
                flowPane.getChildren().add(hBox);
            }
//            flowPane.setStyle("-fx-background-color: black");

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

            ap.getChildren().add(scrollPane);


        } else if ("things".equals(getModelType()) || "bookThings".equals(getModelType())) {
            int restNum=0;
            GridPane gridPane = new GridPane();
            //创建数据库数据
            initDSPXX.createData(getName());
            restNum=getSPNum.exec(getName());

            /*
            开始生成图片区域
             */
            int imgCount = getImgs().length;
            Pagination pagination = new Pagination(imgCount, 0);
            pagination.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer param) {
                    AnchorPane anchorPane = new AnchorPane();

                    String image = ("file:/" + file.getAbsolutePath() + "\\" + imgs[param]).replaceAll("\\\\", "/").replaceAll(" ", "%20");

                    anchorPane.setStyle("-fx-background-image: url('" + image + "'); " +
                            "-fx-background-position: center; " +
                            "-fx-background-repeat: no-repeat;" +
                            " -fx-background-size: contain;"
                    );
                    return anchorPane;
                }
            });
            /*
            开始生成按钮区域
             */
            AnchorPane infoAnchor = new AnchorPane();
            BorderPane infoArea = new BorderPane();

            /*
            商品标题
             */
            Label thingsName = new Label(getName());
            thingsName.getStyleClass().add("thingsName");
            HBox nameHbox = new HBox();
            nameHbox.getChildren().add(thingsName);
            nameHbox.getStyleClass().add("infoLabel");
            infoArea.setTop(nameHbox);

            /*
            商品详情
             */
            Label label = new Label(getIntroduction());
            label.getStyleClass().add("infoLabel");
            HBox infoHbox = new HBox();
            infoHbox.getStyleClass().add("infoHbox");
            infoHbox.getChildren().add(label);
            infoArea.setCenter(infoHbox);


            /*
            价格和购买
             */
            GridPane priceAbuy = new GridPane();
            HBox hbPrice = new HBox();
            HBox hbrestNum = new HBox();
            hbPrice.getStyleClass().add("hbox_Price");
            hbrestNum.getStyleClass().add("hbox_restNum");
            HBox hbBuy = new HBox();
            hbBuy.getStyleClass().add("hbox_Buy");
            DecimalFormat df = new DecimalFormat("######0.00");
            Label price = new Label("价格：￥" + df.format(getPrice()));
            price.setWrapText(true);
            price.getStyleClass().add("thingsPrice");
            if ("bookThings".equals(getModelType())) {
                buy = new Button("预定");
            } else {
                buy = new Button("购买");
                restNumLable= new Label("剩余库存："+restNum);
                restNumLable.setWrapText(true);
                restNumLable.getStyleClass().add("restNumLable");
                hbrestNum.getChildren().add(restNumLable);

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
            添加购买点击事件
             */
            buy.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                if (getSPNum.exec(getName())<=0)
                {
                    warmingDialog.show("亲,售罄了,下次来吧~");
                    return;
                }
                /*
                创建订单
                 */
                Order.Init();
                Order.JYJE_U = getPrice();
                Order.SPMC_U = getName();
                Order.ADRESS = getControllerAdress();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("orderDialog.fxml"));

                try {
                    loader.load();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                Stage stage_dialog = new Stage(StageStyle.UNDECORATED);
                stage_dialog.initModality(Modality.WINDOW_MODAL);
                stage_dialog.setScene(scene);
                scene.setFill(Color.TRANSPARENT);
                stage_dialog.initStyle(StageStyle.TRANSPARENT);
                stage_dialog.initOwner(ClientUIMain.primaryStage);
                orderDialogController controller = loader.getController();

                controller.setStage(stage_dialog);
                controller.setScene(scene);

                controller.orderInfo.setText("交易金额:" + Order.JYJE_U + "\n商品名称:" + Order.SPMC_U);
                stage_dialog.showAndWait();
            });

            if (restNum <= 0) {
                buy.setText("已售罄");
                buy.getStyleClass().add("buyButton_over");

            }else {
                buy.getStyleClass().add("buyButton");
            }


            AnchorPane.setTopAnchor(infoArea, 0.0);
            AnchorPane.setBottomAnchor(infoArea, 0.0);
            AnchorPane.setLeftAnchor(infoArea, 0.0);
            AnchorPane.setRightAnchor(infoArea, 0.0);


            infoAnchor.getChildren().add(infoArea);
            gridPane.add(infoAnchor, 1, 0);


            //行列
            ColumnConstraints colConstraints_0 = new ColumnConstraints();
            colConstraints_0.setPercentWidth(61.8);
            gridPane.getColumnConstraints().add(colConstraints_0);

            ColumnConstraints colConstraints_1 = new ColumnConstraints();
            colConstraints_1.setPercentWidth(38.2);
            gridPane.getColumnConstraints().add(colConstraints_1);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100);
            gridPane.getRowConstraints().add(rowConstraints);

            gridPane.add(pagination, 0, 0);//图片部分


            AnchorPane.setTopAnchor(gridPane, 0.0);
            AnchorPane.setBottomAnchor(gridPane, 0.0);
            AnchorPane.setLeftAnchor(gridPane, 0.0);
            AnchorPane.setRightAnchor(gridPane, 0.0);
            ap.getChildren().add(gridPane);



        } else {
            Logger.log("LOG_ERR", "model类型错误:" + getModelType() + ":" + file.getName());
        }

        this.setAnchorPane(ap);

        viewPane.getChildren().add(ap);

        buildSuccess = true;

    }


    public Map<String, myModel> getElements() {
        return Elements;
    }


    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public boolean isBuildSuccess() {
        return buildSuccess;
    }

    public void setBuildSuccess(boolean buildSuccess) {
        this.buildSuccess = buildSuccess;
    }


    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    public void logAll() {
        log(this);
    }

    public void log(myModel model) {
        for (String subname :
                model.getElements().keySet()) {
            log(model.getElements().get(subname));
        }
    }

    public void setElements(Map<String, myModel> elements) {
        Elements = elements;
    }


    public static void main(String[] args) {
        String rootPath = "resources/Model_";
        AnchorPane anchorPane = new AnchorPane();
        myModel myModel_ = new myModel(rootPath, anchorPane);


    }


    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getControllerAdress() {
        return controllerAdress;
    }

    public void setControllerAdress(int controllerAdress) {
        this.controllerAdress = controllerAdress;
    }
}
