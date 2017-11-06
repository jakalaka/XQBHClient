package XQBHClient.Client;


import XQBHClient.ClientTran.ZDLogin;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.Utils.log.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
public class ClientMain  {

    public static void main(String[] args) {
            //aaa


        if (false ==  ClientInit.Init())
            return;

        //for (int i=0;i<1000;i++) {
         //   ZDLogin.exec();
        //}
        ClientUIMain.main(args);

    }

}
