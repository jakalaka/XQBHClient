package XQBHClient.Client;


import XQBHClient.ClientTran.ZDLogin;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.Utils.Updater.AutoUpdateMain;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
public class ClientMain {

    public static void main(String[] args) {
        //aaa


        if (false == ClientInit.Init())
            return;

        /*
        ������
         */
        AutoUpdateMain.exec(args);
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
