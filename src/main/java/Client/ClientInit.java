package Client;

import java.io.File;

import static Client.PropertiesReader.readKeyFromXML;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class ClientInit {
    public static boolean Init(){
        /*
        从userInfo中获取相关配置信息
         */
        String ZDJYM_=readKeyFromXML(new File("src/main/resources/userInfo.properties") ,"ZDJYM_");
        if (!"".equals(ZDJYM_)&&ZDJYM_!=null)
            Com.ZDJYM_=ZDJYM_;

        String ZDBH_U=readKeyFromXML(new File("src/main/resources/userInfo.properties") ,"ZDBH_U");
        if (!"".equals(ZDBH_U)&&ZDBH_U!=null)
            Com.ZDBH_U=ZDBH_U;

        String LOGLV=readKeyFromXML(new File("src/main/resources/userInfo.properties") ,"LOGLV");
        if (!"".equals(LOGLV)&&LOGLV!=null)
            Com.LOGLV=LOGLV;

        return true;
    }
}
