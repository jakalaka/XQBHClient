package XQBHClient.Client;


import java.io.File;

import static XQBHClient.Utils.PropertiesHandler.PropertiesReader.readKeyFromXML;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class ClientInit {
    public static boolean Init(){
        /*
        从userInfo中获取相关配置信息
         */

        String ZDJYM_=readKeyFromXML(new File("resources/Info/userInfo.properties"),"ZDJYM_");
        if (!"".equals(ZDJYM_)&&ZDJYM_!=null)
            Com.ZDJYM_=ZDJYM_;

        String ZDBH_U=readKeyFromXML(new File("resources/Info/userInfo.properties"),"ZDBH_U");
        if (!"".equals(ZDBH_U)&&ZDBH_U!=null)
            Com.ZDBH_U=ZDBH_U;

        String LogLV=readKeyFromXML(new File("resources/Info/userInfo.properties"),"LogLV");
        if (!"".equals(LogLV)&&LogLV!=null)
            Com.LogLV =LogLV;





        return true;
    }

}
