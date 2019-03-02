package XQBHClient.FreeTest;

import XQBHClient.Client.ClientInit;
import XQBHClient.ClientAPI.WarmingAction;
import XQBHClient.ClientTran.ZFWAITQuery;
import XQBHClient.ClientUI.Order;

public class querytest {
    public static void main(String[] args) {
        if (false == ClientInit.Init()) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "初始化失败，请联系维护人员");
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

                ZFWAITQuery.exec();


            System.out.println(3);
//        }
    }
}
