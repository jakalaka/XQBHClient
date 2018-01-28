package XQBHClient.Utils.Model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static XQBHClient.Utils.PropertiesHandler.PropertiesReader.readKeyFromXML;

public class DataModel {


    private Map<String, DataModel> Elements;
    private String modelType;
    private double unitPrice;
    private int goodsAmount;
    private String imgs[];
    private String modelName;
    private boolean buildSuccess = false;
    private String introduction;
    private int controllerAdress;
    private String controllerIP;
    private int controllerPort;
    private String position;


    public DataModel(String resourcePath) {
        Elements = new HashMap<String, DataModel>();
        File file = new File(resourcePath);
        if (!file.exists())
            return;
        position = resourcePath;

        position = position.substring(position.indexOf("主页"));





        /*
        读取配置文件,生成树对象
         */
        File prop = new File(resourcePath + "/model.properties");
        modelType = readKeyFromXML(prop, "modelType");

        modelName = file.getName();

        String sPrice = readKeyFromXML(prop, "unitPrice");
        if (null == sPrice || "".equals(sPrice)) {
            unitPrice = 0;
        } else {
            unitPrice = Double.parseDouble(sPrice);
        }
        introduction = readKeyFromXML(prop, "introduction");
        imgs = readKeyFromXML(prop, "imgs").split(";");
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = resourcePath + "/" + imgs[i];
        }

        controllerIP = readKeyFromXML(prop, "controllerIP");

        String port = readKeyFromXML(prop, "controllerPort");
        if (null == port || "".equals(port))
            port = "9999";
        controllerPort = Integer.parseInt(port);

        String adress = readKeyFromXML(prop, "controllerAdress");
        if (null == adress || "".equals(adress))
            adress = "9999";
        controllerAdress = Integer.parseInt(adress);


        File[] files = file.listFiles();
        for (File subfile :
                files) {
            if (subfile.isDirectory()) {
                DataModel dataModel = new DataModel(subfile.getPath());
                if (dataModel.isBuildSuccess())
                    Elements.put(dataModel.getModelName(), dataModel);
                else
                    return;
            }
        }

        buildSuccess = true;
    }

    public int getGoodsAmount() {
        return goodsAmount;
    }

    public String getModelType() {
        return modelType;
    }

    public Map<String, DataModel> getElements() {
        return Elements;
    }

    public void setGoodsAmount(int goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public boolean isBuildSuccess() {
        return buildSuccess;
    }

    public String getModelName() {
        return modelName;
    }

    public String[] getImgs() {
        return imgs;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getControllerAdress() {
        return controllerAdress;
    }

    public String getControllerIP() {
        return controllerIP;
    }

    public int getControllerPort() {
        return controllerPort;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getPosition() {
        return position;
    }

    public static DataModel getDataModelByKey(DataModel dataModel, String Key) {
        if (Key.equals(dataModel.getPosition()))
            return dataModel;
        else {
            for (Map.Entry<String, DataModel> entry :
                    dataModel.getElements().entrySet()) {
                DataModel subDataModel = getDataModelByKey(entry.getValue(), Key);
                if (null != subDataModel) {
                    return subDataModel;
                }
            }
        }
        return null;
    }
}
