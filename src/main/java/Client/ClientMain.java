package Client;
import ClientUI.ClientUIMain;
import ClientUI.Controller;
import Utils.log.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;
import java.util.Map;

import static javafx.application.Application.launch;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
public class ClientMain  {

    public static void main(String[] args) {
            //aaa


        if (false ==  ClientInit.Init())
            return;
      //  new ClientUIMain(args);

//        for (int i=0;i<1000;i++) {
//            Map In = new HashMap();
//            Map Out = new HashMap();
//            String[] ERRMsg = {""};
//            long startTime=System.currentTimeMillis();
//            if (false == ComCall.Call("ZDLogin", In, Out, ERRMsg))
//                Logger.log("LOG_ERR", ERRMsg[0]);
//            else
//                Logger.log("LOG_DEBUG", Out.get("re") + "");
//            long endTime=System.currentTimeMillis();
//            System.out.println((endTime-startTime));
//        }

    }

}
