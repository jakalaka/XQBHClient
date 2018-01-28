package XQBHClient.FreeTest;


import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class testModel extends Application {

    public static void main(String[] args) {
//生成模型
//        AnchorPane viewPane=new AnchorPane();
//        MyModel   model=new MyModel(rootPath,viewPane);
//        System.out.println("OK");
//        String str= JSON.toJSONString(model);
//        System.out.println(str);

//        launch(args);


        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("testModel.fxml"));
        primaryStage.setTitle("新奇百货");
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();


    }
}
