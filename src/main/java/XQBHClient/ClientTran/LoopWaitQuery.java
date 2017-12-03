package XQBHClient.ClientTran;

import XQBHClient.Client.Com;
import XQBHClient.Client.ComCall;
import XQBHClient.Utils.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class LoopWaitQuery {
    public static boolean exec() {
        Logger.log("LOG_IO", Com.getIn);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Logger.log("LOG_IO", Com.getOut);
        return true;
    }
}
