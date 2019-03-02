package XQBHClient.ClientTran;

import XQBHClient.Client.Com;
import XQBHClient.Client.ComCall;
import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class ZFWAITQuery {
    public static String exec() {
        Logger.log("LOG_IO", Com.getIn);
        long startTime = System.currentTimeMillis();
        Map In = new HashMap();
        Map Out = new HashMap();

        Logger.log("LOG_DEBUG", "HTRQ_U=" + Order.HTRQ_U);
        Logger.log("LOG_DEBUG", "HTLS_U=" + Order.HTLS_U);

        In.put("YHTLS_", Order.HTLS_U);
        In.put("YHTRQ_", Order.HTRQ_U);


        if("z".equals(Order.ZFFS_U))
        {
            if (false == ComCall.Call("AlipayQuery", "AlipayQuery", In, Out)) {
                if ("ZF0004".equals(Out.get("CWDM_U"))||"ZFSYSE".equals(Out.get("CWDM_U")))
                {
                    return "REQUERY";
                }
                else {
                    return "FAIL";
                }
            }
            Logger.log("LOG_DEBUG", "TranState=" + Out.get("TSTAT_"));
            if ("WAIT_BUYER_PAY".equals(Out.get("TSTAT_"))) {
                return "ZFWAIT";
            }else if("TRADE_CLOSED".equals(Out.get("TSTAT_")))
            {
                return "FAIL";
            }
        }else if("w".equals(Order.ZFFS_U))
        {
            if (false == ComCall.Call("WxpayQuery", "WxpayQuery", In, Out)) {
                if ("ZF0004".equals(Out.get("CWDM_U"))||"ZFSYSE".equals(Out.get("CWDM_U")))
                {
                    return "REQUERY";
                }
                else {
                    return "FAIL";
                }
            }
            Logger.log("LOG_DEBUG", "TranState=" + Out.get("TSTAT_"));
            if ("USERPAYING".equals(Out.get("TSTAT_"))) {
                return "ZFWAIT";
            }else if(!"SUCCESS".equals(Out.get("TSTAT_")))
            {
                return "FAIL";
            }
        }






        long endTime = System.currentTimeMillis();
        Logger.log("LOG_DEBUG", "spand " + (endTime - startTime) + "ms");

        Logger.log("LOG_IO", Com.getOut);
        return "SUCCESS";
    }
}
