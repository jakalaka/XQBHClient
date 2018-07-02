package XQBHClient.ClientUI.Unit;

import XQBHClient.Utils.Data.DataUtils;
import XQBHClient.Utils.Model.DataModel;
import XQBHClient.Utils.log.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class Obj_Goods_Sell {
    public Parent SP = null;

    public Obj_Goods_Sell(DataModel dataModel) {
        FXMLLoader loader = new FXMLLoader();

        try {
            SP = loader.load(getClass().getResource("Goods_Sell.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        SP.setId(dataModel.getPosition());
        DataUtils.setValue(SP, "goodsName", dataModel.getModelName());
        DataUtils.setValue(SP, "goodsNum",dataModel.getGoodsAmount()+"");
        DataUtils.setValue(SP, "unitPrice",""+dataModel.getUnitPrice())    ;

        DataUtils.setValue(SP, "position", dataModel.getPosition());
        DataUtils.setValue(SP, "positionX", dataModel.getpositionX()+"");
        DataUtils.setValue(SP, "positionY", dataModel.getpositionY()+"");



        String imagePath = "resources/"+dataModel.getImgs()[0];
        File file = new File(imagePath);
        Image image = null;
        image = new Image(String.valueOf(file.toURI()));
        Logger.log("LOG_DEBUG",String.valueOf(file.toURI()));
        ((ImageView) DataUtils.getTarget(SP, "image")).setImage(image);
    }
}
