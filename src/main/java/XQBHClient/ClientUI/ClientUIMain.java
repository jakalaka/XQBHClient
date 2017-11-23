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


    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientUIMain.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));

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
        Logger.log("LOG_DEBUG","target propfile="+prop.getAbsolutePath());

        //读取配置文件


        return true;
    }

}
