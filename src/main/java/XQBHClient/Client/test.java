package XQBHClient.Client;

import XQBHClient.ClientTran.ZDLogin;

public class test {
    public static void main(String[] args) {
        if (false == ClientInit.Init())
            return;
        int i = 0;
//        while (true) {
            ZDLogin.exec();
            i++;
            System.out.println("i= " +i);
//        }
    }
}
