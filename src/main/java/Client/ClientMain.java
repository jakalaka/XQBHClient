package Client;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
public class ClientMain {
    public static void main(String[] args) {
            //aaa
            ClientInit.Init();
            Map In = new HashMap();
            Map Out = new HashMap();
            String []ERRMsg={""};
            if(false==ComCall.Call("ZDLogin", In, Out,ERRMsg))
                Logger.log("ERR",ERRMsg[0]);
            else
                Logger.log("DEBUG",Out.get("re")+"");
    }
}
