package XQBHClient.FreeTest;

import XQBHClient.Client.ClientInit;
import XQBHClient.ClientTran.ZDLogin;

public class loopLogin {
    public static void main(String[] args) {
        if (false == ClientInit.Init())
            return;
        int i = 0;
        while (true) {
            ZDLogin.exec();
            i++;
            System.out.println("i= " +i);
        }
    }
}
