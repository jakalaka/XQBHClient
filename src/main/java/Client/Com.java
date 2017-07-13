package Client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class Com {

    /**
     * 日志等级
     *
     */
    static  String LOGLV="";
    /**
     * 终端编号
     */
    static String ZDBH_U="";

    /**
     * 终端校验码
     */
    static String ZDJYM_="";
    /**
     * 获取前台流水=10位终端编号+6位交易时间
     * @return
     */
    public static String getQTLS(){
        String QTLS_U="";
        QTLS_U=ZDBH_U+getTime();
        return QTLS_U;
    }
    /**
     * 获取前台机器日期
     * @return
     */
    public static String getDate(){
        String Date="";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        Date =df.format(new Date());
        return Date;
    }
    /**
     * 获取前台机器时间
     * @return
     */
    public static String getTime(){
        String Time="";
        SimpleDateFormat df = new SimpleDateFormat("HHmmss");//设置日期格式
        Time =df.format(new Date());
        return Time;
    }
    /**
     * 获取前台机器IP
     * @return
     */
    public static String getIP(){
        String IPAdress="";
        try {
            IPAdress= InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
            Logger.log("ERR","获取IP时出错");
            e.printStackTrace();
        }
        return IPAdress;
    }

    public static void main(String[] args) {
        System.out.println(getIP());
    }
}
