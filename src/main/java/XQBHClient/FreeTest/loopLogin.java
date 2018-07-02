package XQBHClient.FreeTest;

import XQBHClient.Client.ClientInit;
import XQBHClient.ClientTran.ZDLogin;

public class loopLogin {
    public static void main(String[] args) {
        if (false == ClientInit.Init())
            return;

        for (int i=0;i<1;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    int j=0;
                    while (true) {
                        ZDLogin.exec();
                        j++;
                        System.out.println(" j="+ j);
                    }
                }
            }).start();
        }


    }
}
