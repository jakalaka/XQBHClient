package XQBHClient.ClientUI.Unit;

import XQBHClient.Utils.Data.DataUtils;
import XQBHClient.Utils.Model.DataModel;
import XQBHClient.Utils.log.Logger;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;


public class Obj_Goods_Sell {
    public GridPane SP = null;

    private static final String MARGIN_CONSTRAINT = "gridpane-margin";
    private static final String HALIGNMENT_CONSTRAINT = "gridpane-halignment";
    private static final String VALIGNMENT_CONSTRAINT = "gridpane-valignment";
    private static final String HGROW_CONSTRAINT = "gridpane-hgrow";
    private static final String VGROW_CONSTRAINT = "gridpane-vgrow";
    private static final String ROW_INDEX_CONSTRAINT = "gridpane-row";
    private static final String COLUMN_INDEX_CONSTRAINT = "gridpane-column";
    private static final String ROW_SPAN_CONSTRAINT = "gridpane-row-span";
    private static final String COLUMN_SPAN_CONSTRAINT = "gridpane-column-span";
    private static final String FILL_WIDTH_CONSTRAINT = "gridpane-fill-width";
    private static final String FILL_HEIGHT_CONSTRAINT = "gridpane-fill-height";


    private static GridPane sample;
    static FXMLLoader loader = new FXMLLoader();    // 创建对象

    static {
        loader.setBuilderFactory(new JavaFXBuilderFactory());    // 设置BuilderFactory
        loader.setLocation(Obj_Goods_Sell.class.getResource("Goods_Sell.fxml"));    // 设置路径基准
        InputStream in = null;

        try {
            in = Obj_Goods_Sell.class.getResourceAsStream("Goods_Sell.fxml");
            if (loader.getRoot() == null)
                loader.load(in);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        sample = loader.getRoot();
    }

    public void generateElement(Pane parent, Pane parent_sample) {
        if (parent_sample.getChildren().size() == 0)
            return;


        boolean grid_par = false;
        if(parent instanceof GridPane)
            grid_par=true;


        for (Node node : parent_sample.getChildren()) {

            if (node instanceof AnchorPane) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setId(node.getId());
                for (String style:node.getStyleClass())
                    anchorPane.getStyleClass().add(style);


                anchorPane.setPrefHeight(((AnchorPane) node).getPrefHeight());
                anchorPane.setPrefWidth(((AnchorPane) node).getPrefWidth());
                if (grid_par) {

                    int col_index = node.getProperties().get(COLUMN_INDEX_CONSTRAINT) == null ? 0 : Integer.parseInt(node.getProperties().get(COLUMN_INDEX_CONSTRAINT).toString());
                    int row_index = node.getProperties().get(ROW_INDEX_CONSTRAINT) == null ? 0 : Integer.parseInt(node.getProperties().get(ROW_INDEX_CONSTRAINT).toString());
                    int col_span = node.getProperties().get(COLUMN_SPAN_CONSTRAINT) == null ? 1 : Integer.parseInt(node.getProperties().get(COLUMN_SPAN_CONSTRAINT).toString());
                    int row_span = node.getProperties().get(ROW_SPAN_CONSTRAINT) == null ? 1 : Integer.parseInt(node.getProperties().get(ROW_SPAN_CONSTRAINT).toString());
                    ((GridPane) parent).add(anchorPane,col_index,row_index,col_span,row_span);

                } else {
                    parent.getChildren().add(anchorPane);
                }
                generateElement(anchorPane, (AnchorPane) node);

            } else if (node instanceof HBox) {
                HBox hBox = new HBox();
                hBox.setId(node.getId());
                hBox.setStyle(node.getStyle());
                hBox.setPrefHeight(40);
                hBox.setPrefWidth(((HBox) node).getPrefWidth());
                hBox.setAlignment(Pos.CENTER);
                if (grid_par) {
                    int col_index = node.getProperties().get(COLUMN_INDEX_CONSTRAINT) == null ? 0 : Integer.parseInt(node.getProperties().get(COLUMN_INDEX_CONSTRAINT).toString());
                    int row_index = node.getProperties().get(ROW_INDEX_CONSTRAINT) == null ? 0 : Integer.parseInt(node.getProperties().get(ROW_INDEX_CONSTRAINT).toString());
                    int col_span = node.getProperties().get(COLUMN_SPAN_CONSTRAINT) == null ? 1 : Integer.parseInt(node.getProperties().get(COLUMN_SPAN_CONSTRAINT).toString());
                    int row_span = node.getProperties().get(ROW_SPAN_CONSTRAINT) == null ? 1 : Integer.parseInt(node.getProperties().get(ROW_SPAN_CONSTRAINT).toString());

                    ((GridPane) parent).add(hBox,col_index,row_index,col_span,row_span);

                } else {
                    parent.getChildren().add(hBox);
                }
                generateElement(hBox, (HBox) node);
            } else if (node instanceof Label) {
                Label label = new Label();
                label.setStyle(node.getStyle());
                label.setAlignment(Pos.CENTER_LEFT);
                label.setTextFill(((Label)node).getTextFill());
                label.setFont(((Label)node).getFont());
                label.setText(((Label)node).getText());
                label.setId(node.getId());
                label.setTextOverrun(((Label)node).getTextOverrun());
                parent.getChildren().add(label);
            } else if (node instanceof TextField) {
                TextField textField = new TextField();
                textField.setId(node.getId());
                textField.setVisible(false);

                textField.setMaxSize(0,0);
                textField.setPrefSize(0,0);
                textField.resize(0,0);
                textField.setPrefColumnCount(0);
                textField.setPadding(Insets.EMPTY);

                parent.getChildren().add(textField);
            } else if (node instanceof ImageView) {
                ImageView imageView = new ImageView();
                imageView.setId(node.getId());
                imageView.setStyle(node.getStyle());
                imageView.setFitWidth(((ImageView) node).getFitWidth());
                imageView.setFitHeight(((ImageView) node).getFitHeight());
                parent.getChildren().add(imageView);
            }
        }

    }

    public Obj_Goods_Sell(DataModel dataModel) {


        SP = new GridPane();
        SP.setStyle(sample.getStyle());
        SP.resize(220,240);

        SP.setPrefHeight(240);
        SP.setPrefWidth(220);
        SP.setPadding(new Insets(0,10,0,10));
        generateElement(SP,sample);








        SP.setId(dataModel.getPosition());
        DataUtils.setValue(SP, "goodsName", dataModel.getModelName().trim());
        DataUtils.setValue(SP, "goodsNum", dataModel.getGoodsAmount() + "");
        DataUtils.setValue(SP, "unitPrice", "" + dataModel.getUnitPrice());

        DataUtils.setValue(SP, "position", dataModel.getPosition());

//        if(dataModel.getModelType().equals("goods")) {
//            DataUtils.setValue(SP, "positionX", dataModel.getpositionX() + "");
//            DataUtils.setValue(SP, "positionY", dataModel.getpositionY() + "");
//        }

        String imagePath = "resources/" + dataModel.getImgs()[0];
        File file = new File(imagePath);
        Image image = new Image(String.valueOf(file.toURI()),200,200, true,false);

        ((ImageView) DataUtils.getTarget(SP, "image")).setImage(image);
    }
}
