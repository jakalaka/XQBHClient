package Client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class Com {

    /**
     * 日志等级
     */
    static String LOGLV = "";
    /**
     * 终端编号
     */
    static String ZDBH_U = "";

    /**
     * 终端校验码
     */
    static String ZDJYM_ = "";

    /**
     * 获取前台流水=10位终端编号+6位交易时间
     *
     * @return
     */
    public static String getQTLS() {
        String QTLS_U = "";
        QTLS_U = ZDBH_U + getTime();
        return QTLS_U;
    }

    /**
     * 获取前台机器日期
     *
     * @return
     */
    public static String getDate() {
        String date = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");//设置日期格式
        date = df.format(new Date());
        return date;
    }

    /**
     * 获取前台机器时间
     *
     * @return
     */
    public static String getTime() {
        String Time = "";
        SimpleDateFormat df = new SimpleDateFormat("HHmmss");//设置日期格式
        Time = df.format(new Date());
        return Time;
    }

    /**
     * 获取前台机器IP
     *
     * @return
     */
    public static String getIP() {
        String IPAdress = "";
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP

        Enumeration<NetworkInterface> netInterfaces =
                null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }

        if (netip != null && !"".equals(netip)) {
            IPAdress = netip;
        } else {
            IPAdress = localip;
        }

        return IPAdress;
    }

    public static void main(String[] args) {
        System.out.println(getIP());
    }
}
