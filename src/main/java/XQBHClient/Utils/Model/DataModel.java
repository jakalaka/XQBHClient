package XQBHClient.Utils.Model;

import XQBHClient.Utils.Language.ChineseToEnglish2;
import XQBHClient.Utils.log.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
    private String searchCondition;

    public DataModel() {
        Elements = new HashMap<String, DataModel>();
        position="搜索结果";
        modelType="normal";
    }

    public DataModel(String resourcePath) {
        Elements = new HashMap<String, DataModel>();
        File file = new File(resourcePath);
        if (!file.exists())
            return;
        String tmpPath=resourcePath.substring(resourcePath.indexOf("主页"));
        position = tmpPath;
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
            imgs[i] = tmpPath + "/" + imgs[i];
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

        searchCondition =position+" "+readKeyFromXML(prop, "searchCondition");




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

    public static DataModel getDataModelByKey(DataModel rootDataModel, String Key) {
        if (Key.equals(rootDataModel.getPosition()))
            return rootDataModel;
        else {
            for (Map.Entry<String, DataModel> entry :
                    rootDataModel.getElements().entrySet()) {
                DataModel subDataModel = getDataModelByKey(entry.getValue(), Key);
                if (null != subDataModel) {
                    return subDataModel;
                }
            }
        }
        return null;
    }

    /**
     * @param rootDataModel
     * @param sKeyWords
     * @return
     */
    public static DataModel getMatchSearchDataModel(DataModel rootDataModel, String sKeyWords) {
        DataModel resoultData=new DataModel();
        String searchCondition=ChineseToEnglish2.getFirstSpell(rootDataModel.searchCondition);
        if ("goods".equals(rootDataModel.getModelType())&&isContainIgnoreCase(searchCondition,sKeyWords)) {
            Logger.log("LOG_DEBUG","searchCondition=["+searchCondition+"]");
            Logger.log("LOG_DEBUG","sKeyWords=["+sKeyWords+"]");
            resoultData.getElements().put(rootDataModel.getModelName(),rootDataModel);
        } else {
            for (Map.Entry<String, DataModel> entry :
                    rootDataModel.getElements().entrySet()) {
                resoultData.getElements().putAll(getMatchSearchDataModel(entry.getValue(),sKeyWords).getElements());
            }
        }
        return  resoultData;
    }
    public static boolean isContainIgnoreCase(String sourceSrting,String sKeyWords){
        sourceSrting=sourceSrting.toLowerCase();
        sKeyWords=sKeyWords.toLowerCase();
        for (int i=0;i<sKeyWords.length();i++)
        {
            String schar=(sKeyWords.charAt(i)+"");
            if (!sourceSrting.contains(schar)) {
                return false;
            }
            sourceSrting=sourceSrting.replaceFirst(schar,"");
        }
        return true;
    }
}
