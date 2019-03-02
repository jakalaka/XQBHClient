package XQBHClient.Utils.Model;

import XQBHClient.Utils.Language.ChineseToEnglish2;
import XQBHClient.Utils.log.Logger;

import java.io.File;
import java.util.*;

import static XQBHClient.Utils.PropertiesHandler.PropertiesReader.readFromProperties;

public class DataModel {

    private Map<String, DataModel> Elements;
    private String modelType;
    private double unitPrice;
    private int goodsAmount;
    private String imgs[];
    private String modelName;
    private boolean buildSuccess = false;
    private String introduction;
    private int positionX; //商品位置
    private int positionY; //商品位置
    private int positionZ; //商品位置
    private int positionZ_max; //商品位置
    private int positionW;
    private String barcode;//商品条形码

    /*
    private int controllerAdress;
    private String controllerIP;
    private int controllerPort;
    */

    private String position; //商品树路径
    private String searchCondition;

    public DataModel() {
        Elements = new HashMap<String, DataModel>();
        position = "搜索结果";
        modelType = "normal";
    }

    public DataModel(String resourcePath) {
        Elements = new HashMap<String, DataModel>();
        File file = new File(resourcePath);
        if (!file.exists())
            return;
        String tmpPath = resourcePath.substring(resourcePath.indexOf("主页"));
        position = tmpPath;
        /*
        读取配置文件,生成树对象
         */
        File prop = new File(resourcePath + "/model.properties");
        modelType = readFromProperties(prop, "modelType");

        modelName = file.getName();

        String sPrice = readFromProperties(prop, "unitPrice");
        if (null == sPrice || "".equals(sPrice)) {
            unitPrice = 0;
        } else {
            unitPrice = Double.parseDouble(sPrice);
        }
        introduction = readFromProperties(prop, "introduction");
        imgs = readFromProperties(prop, "imgs").split(";");
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = tmpPath + "/" + imgs[i];
        }




        String positionXtmp = readFromProperties(prop, "positionX");
        if (null == positionXtmp || "".equals(positionXtmp.trim()))
            positionX = 65535;
        else
            positionX = Integer.parseInt(positionXtmp);

        String positionYtmp = readFromProperties(prop, "positionY");
        if (null == positionYtmp || "".equals(positionYtmp.trim()))
            positionY = 65535;
        else
            positionY= Integer.parseInt(positionYtmp);


        String positionZtmp = readFromProperties(prop, "positionZ");
        if (null == positionZtmp || "".equals(positionZtmp.trim()))
            positionZ = 0;
        else
            positionZ= Integer.parseInt(positionZtmp);


        String positionZ_maxtmp = readFromProperties(prop, "positionZ_max");
        if (null == positionZ_maxtmp || "".equals(positionZ_maxtmp.trim()))
            positionZ_max = 1000;
        else
            positionZ_max= Integer.parseInt(positionZ_maxtmp);

        barcode = readFromProperties(prop, "barcode");




        searchCondition = position + " " + readFromProperties(prop, "searchCondition");


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

    public int getpositionX() {
        return positionX;
    }

    public int getpositionY() {
        return positionY;
    }
    public int getpositionZ() {
        return positionZ;
    }
    public int getPositionZ_max() {
        return positionZ_max;
    }
    public String getBarcode() {
        return barcode;
    }
    public int getPositionW() {
        return positionW;
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
        DataModel resoultData = new DataModel();
        String searchCondition = ChineseToEnglish2.getFirstSpell(rootDataModel.searchCondition);
        if ("goods".equals(rootDataModel.getModelType()) && isContainIgnoreCase(searchCondition, sKeyWords)) {
            Logger.log("LOG_DEBUG", "searchCondition=[" + searchCondition + "]");
            Logger.log("LOG_DEBUG", "sKeyWords=[" + sKeyWords + "]");
            resoultData.getElements().put(rootDataModel.getModelName(), rootDataModel);
        } else {
            for (Map.Entry<String, DataModel> entry :
                    rootDataModel.getElements().entrySet()) {
                resoultData.getElements().putAll(getMatchSearchDataModel(entry.getValue(), sKeyWords).getElements());
            }
        }
        return resoultData;
    }

    public static boolean isContainIgnoreCase(String sourceSrting, String sKeyWords) {
        sourceSrting = sourceSrting.toLowerCase();
        sKeyWords = sKeyWords.toLowerCase();
        for (int i = 0; i < sKeyWords.length(); i++) {
            String schar = (sKeyWords.charAt(i) + "");
            if (!sourceSrting.contains(schar)) {
                return false;
            }
            sourceSrting = sourceSrting.replaceFirst(schar, "");
        }
        return true;
    }

    /**
     * @param dataModel
     * @return
     * @apiNote 获取指定datamodel的所有商品列表
     */
    public static List<String> getAllGoodsList(DataModel dataModel) {
        List<String> goodsList = new ArrayList<>();

        for (Map.Entry<String, DataModel> entry :
                dataModel.getElements().entrySet()) {
            if ("goods".equals(entry.getValue().getModelType()))
            {
                goodsList.add(entry.getValue().getPosition());
                Logger.log("LOG_DEBUG","add  " + entry.getValue().getPosition());
            }else {
                goodsList.addAll(getAllGoodsList(entry.getValue()));
            }
        }

        return goodsList;
    }

    /**
     * @param dataModel
     * @return
     * @apiNote 只获取指定datamodel的表层商品列表
     */
    public static List<String> getGoodsList(DataModel dataModel) {
        List<String> goodsList = new ArrayList<>();

        for (Map.Entry<String, DataModel> entry :
                dataModel.getElements().entrySet()) {
            if (entry.getValue().getModelType().equals("goods")) {
                goodsList.add(entry.getValue().getPosition());
                Logger.log("LOG_DEBUG","add  " + entry.getValue().getPosition());
            }
        }

        return goodsList;
    }
}
