package XQBHClient.FreeTest;

import XQBHClient.Client.ClientInit;
import XQBHClient.ClientAPI.WarmingDialog;
import XQBHClient.ClientTran.AlipayZFWAITQuery;
import XQBHClient.ClientUI.Order;

public class querytest {
    public static void main(String[] args) {
        if (false == ClientInit.Init()) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "初始化失败，请联系维护人员");
            return;
        }
        Order.HTRQ_U = "20171218";
            Order.HTLS_U = "SZD0000010000003";
//        while (true) {

//            System.out.println(1);
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(2);

                AlipayZFWAITQuery.exec();


            System.out.println(3);
//        }
    }
}
