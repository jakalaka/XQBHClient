package XQBHClient.ClientTran;

import XQBHClient.Client.Com;
import XQBHClient.Client.ComCall;
import XQBHClient.Utils.log.Logger;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZDLogin {
    public static boolean exec() {
        Logger.log("LOG_IO", Com.getIn);
        Map In = new HashMap();
        Map Out = new HashMap();
        long startTime = System.currentTimeMillis();
//        JSONArray list=new JSONArray();
//        Map map1=new HashMap();
//        map1.put("goosName","Œ“≤¡");
//        map1.put("goosID","1111");
//        Map map2=new HashMap();
//        map2.put("goosName","xxx «");
//        map2.put("goosID","2222");
//        list.add(map1);
//        list.add(map2);
//        In.put("FZDLoin1",list);



        if (false == ComCall.Call("ZDLogin", "ZDLogin", In, Out)) {
            return false;
        } else
            Logger.log("LOG_DEBUG", "re="+Out.get("re") );
        long endTime = System.currentTimeMillis();
        Logger.log("LOG_DEBUG", "spand " + (endTime - startTime) + "ms");
        Logger.log("LOG_IO", Com.getOut);
        return true;
    }
}
