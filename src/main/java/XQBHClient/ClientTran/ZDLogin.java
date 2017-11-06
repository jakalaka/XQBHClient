package XQBHClient.ClientTran;

import XQBHClient.Client.Com;
import XQBHClient.Client.ComCall;
import XQBHClient.Utils.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class ZDLogin {
    public static boolean exec(){
        Logger.log("LOG_IO", Com.getIn);
        Map In = new HashMap();
        Map Out = new HashMap();
        String[] ERRMsg = {""};
        long startTime=System.currentTimeMillis();
        if (false == ComCall.Call("ZDLogin","ZDLogin", In, Out, ERRMsg)) {
            Logger.log("LOG_ERR", ERRMsg[0]);
            return false;
        }
        else
            Logger.log("LOG_DEBUG", Out.get("re") + "");
        long endTime=System.currentTimeMillis();
        Logger.log("LOG_DEBUG","spand "+(endTime-startTime)+"ms");
        Logger.log("LOG_IO", Com.getOut);
        return true;
    }
}
