package XQBHClient.ClientUI.Unit;

import XQBHClient.Utils.Data.DataUtils;
import XQBHClient.Utils.Model.DataModel;
import XQBHClient.Utils.log.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class Obj_Goods_Sell_bak {
    public GridPane SP = null;


    public Obj_Goods_Sell_bak(DataModel dataModel) {
        Logger.log("LOG_DEBUG", "begin");


        InputStream in = null;

        FXMLLoader loader = new FXMLLoader();    // 创建对象

        loader.setBuilderFactory(new JavaFXBuilderFactory());    // 设置BuilderFactory
        loader.setLocation(Obj_Goods_Sell_bak.class.getResource("Goods_Sell.fxml"));    // 设置路径基准


        try {
            in = Obj_Goods_Sell_bak.class.getResourceAsStream("Goods_Sell.fxml");
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
        SP = loader.getRoot();


        Logger.log("LOG_DEBUG", "");

        SP.setId(dataModel.getPosition());
        DataUtils.setValue(SP, "goodsName", dataModel.getModelName());
        DataUtils.setValue(SP, "goodsNum", dataModel.getGoodsAmount() + "");
        DataUtils.setValue(SP, "unitPrice", "" + dataModel.getUnitPrice());

        DataUtils.setValue(SP, "position", dataModel.getPosition());

//        if(dataModel.getModelType().equals("goods")) {
//            DataUtils.setValue(SP, "positionX", dataModel.getpositionX() + "");
//            DataUtils.setValue(SP, "positionY", dataModel.getpositionY() + "");
//        }
        Logger.log("LOG_DEBUG", "");

        String imagePath = "resources/" + dataModel.getImgs()[0];
        File file = new File(imagePath);
        Logger.log("LOG_DEBUG", "");
        Image image = null;
        image = new Image(String.valueOf(file.toURI()), true);
        Logger.log("LOG_DEBUG", "");
        Logger.log("LOG_DEBUG", String.valueOf(file.toURI()));
        ((ImageView) DataUtils.getTarget(SP, "image")).setImage(image);
    }
}
