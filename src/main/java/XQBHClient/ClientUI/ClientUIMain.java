package XQBHClient.ClientUI;

import XQBHClient.Utils.QRReader.QRReader;
import XQBHClient.Utils.log.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

import static XQBHClient.Utils.PropertiesHandler.PropertiesReader.readKeyFromXML;

public class ClientUIMain extends Application {

    public static Controller controller;
    public static Stage primaryStage;
    public static String relayIP;
    public static int relayPort;
    public static String comName;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientUIMain.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("新奇百货");
        Scene scene = new Scene(root);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }


    public static void main(String[] args) {

        if (false == Init())
            return;
        launch(args);
    }

    public static boolean Init() {



        File prop=new File(System.getProperty("java.home")+"/lib/");
        System.out.println("target propfile="+prop.getAbsolutePath());

        //读取配置文件
        String sysinfo = "/resources/sysinfo/sysInfo.properties";
        QRReader.timeOut = Integer.parseInt(readKeyFromXML(sysinfo, "timeOut"));
        if (0 == QRReader.timeOut ) {
            Logger.log("LOG_ERR", "读取timeOut错误");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "timeOut=" + QRReader.timeOut);
        }
        QRReader.frequency = Integer.parseInt(readKeyFromXML(sysinfo, "frequency"));
        if (0 == QRReader.frequency ) {
            Logger.log("LOG_ERR", "读取frequency错误");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "frequency=" +QRReader.frequency );
        }

        ClientUIMain.relayIP = readKeyFromXML(sysinfo, "relayIP");
        if (null == ClientUIMain.relayIP || "".equals(ClientUIMain.relayIP)) {
            Logger.log("LOG_ERR", "读取继电器IP错误");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "relayIP=" + ClientUIMain.relayIP);
        }

        ClientUIMain.relayPort = Integer.parseInt(readKeyFromXML(sysinfo, "relayPort"));
        if (0 == ClientUIMain.relayPort) {
            Logger.log("LOG_ERR", "读取继电器port错误");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "relayIP=" + ClientUIMain.relayPort);
        }

        ClientUIMain.comName = readKeyFromXML(sysinfo, "comName");
        if (null == ClientUIMain.comName || "".equals(ClientUIMain.comName)) {
            Logger.log("LOG_ERR", "读取qrScanner错误");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "qrScanner=" + ClientUIMain.comName);
        }

        return true;
    }

}
