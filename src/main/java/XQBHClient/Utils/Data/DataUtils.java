package XQBHClient.Utils.Data;

import XQBHClient.ClientAPI.WarmingAction;
import XQBHClient.Utils.log.Logger;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class DataUtils {
    public static String getValue(Node root, Object object) {
        String resoult = "";

        if (object instanceof TextField) {
            resoult = ((TextField) object).getText();
        } else if (object instanceof ComboBox) {
            resoult = ((ComboBox) object).getValue().toString();
        } else if (object instanceof ChoiceBox) {
            resoult = ((ChoiceBox) object).getValue().toString();
        } else if (object instanceof DatePicker) {
            resoult = ((DatePicker) object).getValue().toString().replaceAll("-","");
        } else if (object instanceof Label) {
            resoult = ((Label) object).getText();
        }else if (object instanceof String) {
            Node targetNode = getTarget(root, (String) object);
            if (targetNode instanceof TextField) {
                resoult = ((TextField) targetNode).getText();
            } else if (targetNode instanceof ComboBox) {
                resoult = ((ComboBox) targetNode).getValue().toString();
            } else if (targetNode instanceof ChoiceBox) {
                resoult = ((ChoiceBox) targetNode).getValue().toString();
            } else if (targetNode instanceof Label) {
                resoult = ((Label) targetNode).getText();
            } else if (targetNode instanceof DatePicker) {
                resoult = ((DatePicker) targetNode).getValue().toString().replaceAll("-","");
            }
        }

        return resoult;
    }
    public static String getValue(Map map, String sKey) {
        String resoult="";
        if (map.get(sKey)==null)
            return resoult;
        resoult=map.get(sKey).toString();
        return resoult;
    }

    public static void setValue(Node root, String targetName, String valueString) {
        Node targetNode = getTarget(root, targetName);
        if (targetNode == null) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "无" + targetName + "字段,请联系管理员!");
            return;
        }
        if (targetNode instanceof TextField) {
            ((TextField) targetNode).setText(valueString);
        } else if (targetNode instanceof ChoiceBox) {
            ((ChoiceBox) targetNode).setValue(valueString);
        } else if (targetNode instanceof ComboBox) {
            ((ComboBox) targetNode).setValue(valueString);
        } else if (targetNode instanceof Label) {
            ((Label) targetNode).setText(valueString);
        } else {
            WarmingAction.show(WarmingAction.Dialog_ERR, "传入的" + targetName + "类型错误,请联系管理员!");
            return;
        }


        return;
    }

    public static void setVisible(Node root, String targetName, boolean flg) {
        Node targetNode = getTarget(root, targetName);
        if (targetNode == null) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "无" + targetName + "字段,请联系管理员!");
            return;
        }
        targetNode.setVisible(flg);
    }

    public static void setEnable(Node node, String targetName, boolean flg) {
        Node targetNode = getTarget(node, targetName);
        if (targetNode == null) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "无" + targetName + "字段,请联系管理员!");
            return;
        }
        if (targetNode instanceof TextField) {
            ((TextField) targetNode).setEditable(false);
        } else {
            WarmingAction.show(WarmingAction.Dialog_ERR, "传入的" + targetName + "类型错误,请联系管理员!");
            return;
        }

    }

    public static Node getTarget(Node root, String nodeName) {
        if (nodeName.equals(root.getId()))
            return root;
        else {
            if (root instanceof Pane) {
                for (Node node_ : ((Pane) root).getChildren()
                        ) {
                    Node resoult = getTarget(node_, nodeName);
                    if (resoult != null)
                        return resoult;
                }
            }
        }
        return null;

    }



    public static String Date2StringofYear(Date date) {
        String resoult = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        resoult = df.format(date);
        return resoult;
    }

    public static String Date2StringofTime(Date date) {
        String resoult = "";
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        resoult = df.format(date);
        return resoult;
    }

    public static String getListMean(String ListName, String Value) {
        File file = new File("src/main/java/resources/list/" + ListName + ".lst");
        if (!file.exists()) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "找不到列表文件" + file.getAbsolutePath());
            return "";
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if (Value.equals(tempString.split("-")[0]))
                    return tempString.split("-")[1];
                line++;
            }
            reader.close();
        } catch (IOException e) {
            Logger.logException("LOG_ERR", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Logger.logException("LOG_ERR", e);
                }
            }
        }

        return "";

    }

    public static String getListValue(String ListName, String Mean) {
        File file = new File("src/main/java/resources/list/" + ListName + ".lst");
        if (!file.exists()) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "找不到列表文件" + file.getAbsolutePath());
            return "";
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if (Mean.equals(tempString.split("-")[1]))
                    return tempString.split("-")[0];
                line++;
            }
            reader.close();
        } catch (IOException e) {
            Logger.logException("LOG_ERR", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Logger.logException("LOG_ERR", e);
                }
            }
        }

        return "";

    }

    public static boolean isNumber(String str){
        for (int i = 0; i < str.length(); i++){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
