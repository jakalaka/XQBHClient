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
        检查更新
         */
        AutoUpdateMain autoUpdateMain = new AutoUpdateMain();
        if (true != autoUpdateMain.exec(args)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "获取更新失败，请检查网络是否通畅");

            return;
        }

        /*
        初始化程序
         */
        if (false == ClientInit.Init())
        {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "初始化失败，请联系维护人员");
            return;
        }
        /*
        签到
         */
        if (!ZDLogin.exec())
        {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "终端签到失败，请联系维护人员");
            return;
        }

        /*
        出货扫描器初始化
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
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "FinishScannerState 启进程失败");
            return ;
        }


        /*
        启动界面
         */
        ClientUIMain.main(args);


    }

}
