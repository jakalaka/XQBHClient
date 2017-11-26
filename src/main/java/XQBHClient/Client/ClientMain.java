package XQBHClient.Client;


import XQBHClient.ClientTran.ZDLogin;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.Utils.Updater.AutoUpdateMain;
import XQBHClient.Utils.log.Logger;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
public class ClientMain {

    public static void main(String[] args) {
        /*
        检查更新
         */
        AutoUpdateMain autoUpdateMain = new AutoUpdateMain();
        if (true != autoUpdateMain.exec(args))
            return;


        /*
        初始化程序
         */
        if (false == ClientInit.Init())
            return;


        /*
        签到
         */
        if (!ZDLogin.exec())
            return;

        /*
        启动界面
         */
        ClientUIMain.main(args);


    }

}
