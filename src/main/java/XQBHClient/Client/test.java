package XQBHClient.Client;

import XQBHClient.ClientTran.ZDLogin;

public class test {
    public static void main(String[] args) {
        if (false == ClientInit.Init())
            return;
//        for (int i=0;i<1000;i++) {
            ZDLogin.exec();
//        }
    }
}
