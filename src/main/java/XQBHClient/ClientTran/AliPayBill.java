package XQBHClient.ClientTran;


import XQBHClient.Client.Com;
import XQBHClient.Client.ComCall;
import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class AliPayBill {
    public static String exec() {
        Logger.log("LOG_IO", Com.getIn);
        Map In = new HashMap();
        Map Out = new HashMap();
        long startTime = System.currentTimeMillis();
        In.put("SPMC_U", Order.SPMC_U);
        In.put("JYJE_U", Order.JYJE_U);
        In.put("QRCODE", Order.QRCODE);
        In.put("ZFZHLX", "z");

        Logger.log("LOG_DEBUG", "SPMC_U=" + Order.SPMC_U);
        Logger.log("LOG_DEBUG", "JYJE_U=" + Order.JYJE_U);
        Logger.log("LOG_DEBUG", "QRCODE=" + Order.QRCODE);


        if (false == ComCall.Call("AliPayBill", "AlipayPay", In, Out)) {
            if ("ZFWAIT".equals(Out.get("CWDM_U"))) {
                Order.HTLS_U =  Out.get("HTLS_U").toString();
                Order.HTRQ_U =  Out.get("HTRQ_U").toString();
                return "ZFWAIT";
            } else {
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
