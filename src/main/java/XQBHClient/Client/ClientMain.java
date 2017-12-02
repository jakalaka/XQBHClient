package XQBHClient.Client;


import XQBHClient.ClientAPI.WarmingDialog;
import XQBHClient.ClientTran.ZDLogin;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.Utils.FinishComListener.FinishComListener;
import XQBHClient.Utils.Updater.AutoUpdateMain;
import XQBHClient.Utils.log.Logger;
import javafx.scene.control.Alert;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
public class ClientMain {

    public static void main(String[] args) {

        /*
        ������
         */
        AutoUpdateMain autoUpdateMain = new AutoUpdateMain();
        if (true != autoUpdateMain.exec(args)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡ����ʧ�ܣ����������Ƿ�ͨ��");

            return;
        }

        /*
        ��ʼ������
         */
        if (false == ClientInit.Init())
        {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ʼ��ʧ�ܣ�����ϵά����Ա");
            return;
        }
        /*
        ǩ��
         */
        if (!ZDLogin.exec())
        {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�ն�ǩ��ʧ�ܣ�����ϵά����Ա");
            return;
        }

        /*
        ����ɨ������ʼ��
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                FinishComListener.start();
            }
        }).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Logger.logException("LOG_ERR",e);
        }
        if (!Com.FinishScannerState.equals("N")) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "FinishScannerState ������ʧ��");
            return ;
        }


        /*
        ��������
         */
        ClientUIMain.main(args);


    }

}
