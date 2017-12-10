package XQBHClient.ClientTran;

import XQBHClient.Client.Com;
import XQBHClient.Client.ComCall;
import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class AlipayZFWAITQuery {
    public static String exec() {
        Logger.log("LOG_IO", Com.getIn);
        long startTime = System.currentTimeMillis();
        Map In = new HashMap();
        Map Out = new HashMap();

        Logger.log("LOG_DEBUG", "HTRQ_U=" + Order.HTRQ_U);
        Logger.log("LOG_DEBUG", "HTLS_U=" + Order.HTLS_U);

        In.put("YHTLS_", Order.HTLS_U);
        In.put("YHTRQ_", Order.HTRQ_U);

        if (false == ComCall.Call("AlipayQuery", "AlipayQuery", In, Out)) {
            if ("ZFWAIT".equals(Out.get("CWDM_U"))) {
                return "ZFWAIT";
            }if ("ZF0004".equals(Out.get("CWDM_U"))||"ZFSYSE".equals(Out.get("CWDM_U")))
            {
                return "REQUERY";
            }
            else {
                return "FAIL";
            }
        } else
            Logger.log("LOG_DEBUG", "re=" + Out.get("re"));
        long endTime = System.currentTimeMillis();
        Logger.log("LOG_DEBUG", "spand " + (endTime - startTime) + "ms");

        Logger.log("LOG_IO", Com.getOut);
        return "SUCCESS";
    }
}
