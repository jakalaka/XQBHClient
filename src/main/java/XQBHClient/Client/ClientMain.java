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
        ������
         */
        AutoUpdateMain autoUpdateMain = new AutoUpdateMain();
        if (true != autoUpdateMain.exec(args))
            return;


        /*
        ��ʼ������
         */
        if (false == ClientInit.Init())
            return;


        /*
        ǩ��
         */
        if (!ZDLogin.exec())
            return;

        /*
        ��������
         */
        ClientUIMain.main(args);


    }

}
