package XQBHClient.Client;

import XQBHClient.Client.Table.Mapper.CXTCSMapper;
import XQBHClient.Client.Table.Model.CXTCS;
import XQBHClient.Client.Table.Model.CXTCSKey;
import XQBHClient.Client.Table.basic.DBAccess;
import XQBHClient.ClientAPI.WarmingDialog;
import XQBHClient.Utils.log.Logger;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
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
    public static String LogLV = "";
    /**
     * 终端编号
     */
    static String ZDBH_U = "";

    /**
     * 终端校验码
     */
    static String ZDJYM_ = "";

    /**
     * 终端状态,用于控制异常情况
     */
    public static String ZDZT_U = "OK";


    public static String PowerControlRelayIP;
    public static int PowerControlPort;
    public static int PowerControlAdress;
    public static String QRReaderComName;
    public static String FinishScannerComName;
    public static String FinishScannerState = "E";

    public static final String upPrivateKey ="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDYRwlUWTrXg9jPiU9JDq8fh2pfNwtT6KpE18hPfo5yicAGUUsefxL9oC5ioWcPkV+Xu++2oxdmaGLr/ujj2m1WjCcO70Ou4SrbbNbmtcJBy7OEGlgNqf7tl1Zow0mYhT6ujcgtY57hlu8TGFQV4kGQf8XKQYJ7keVkY127YXuKU6CmLGqXCYeQJJHguP9J0TqNLny6BOlOjSS44dSpnX0UPU4NhroNS5HLDv9iamyxgctehSN9hiPY891pB82C0qXB49GxOhXmbKydhPtu75sWEeEqQDAp1YnoaaquF7DZRFAgjS0kkrcUwkV2PxPmeYRhi52ihn48evJCGiN6Hs9VAgMBAAECggEBANHm15oiY6ZIkwKQ2/8mnjX2Yfl43aiZFa9s0T69sBhfspsvCL3XTKIUdRBKX1DPoTwNLRBPZuWACAnMw1BobFdj/IBVHJY8eDCviD4vRxI+VcKvIqhYRU9n4ngYmHPLVdNpTU8n8Uo2B1+769e1WDaam2a5f57YMQ7mFVFHwfOzf2YKv9gWecvtHCPXuDmoWKvV/KYVvo8xZ4nGwGJdTyY0vZTfbjonKyEnQiljzULR9thqR/eejTbG34gaACvucvsupCdF/mKqaHPioEcNrEfwmAa+sFEMXpUemZQnrS22C4f7dAsu9PIiTZpipIm6YbjJ0lywnpf9yYJ7VYfsbIECgYEA7P078bVP3QR+XIHPqJjWaDGGAxTWJrVT16Iv5xhMBNHCB4tIwEQFVChDNVPEw8UdeQQvc3gvnIsVn2hca29zXkVYqH/degugVr1rxGLuePRGFmGitgcn9QqmWKFOm/PNiDZW576gsLZjd6b+mPaevKsdd54YdPDofJTc8LM/I7UCgYEA6aB4caAnwQ0BBUxZd2ZSGVwJrmHn5nXKDjxrL87Z6vg4OwDX6dVcAH9OtknihmAtduai2ayg4EWe8kFVnd51iXaSfzEiwdKHKPgUA6eta1huBHp3SP5DNumVTDPtD/bf4nd2vrJBor3LVbe2r6L/H2ShmPHPnQhW8kTDSz4pgSECgYBAQw69JSgpy20kUoLnucHx8PPg5AaJ6oN4pl8M8Ba0+9f8SbWJhShYwK4wyK1DVLEAPrVLP1zRuxk654agD1GeT3mR/1IkJQDuZGDTmOwHWl2i9gi0CU65cJDY2azCNyMVe36nSpayNFLWgC7rdXxntpK/+9uv4h94oLkkf8ZwPQKBgQCqtT2s0PibYDQhufMZgqN0skLEr/dx9xmII2+yxDOJNIxp2KjrzKHoHx3VptElnPs7iTTvVutKVLTakRDNRPKfWgubcrzR4VIvhm2hahEWgcwJ665joJ5ebnlP8BVFd/+Ji/8xQjEhiAsefBm55qECQFav2ej49lIJvmLxBN/w4QKBgQCuLrqQiacvOuucK/0Tv/R2czOCRV1Sc4GgpZBGZhv0R+7CXGk2VlQ/KQ+NiRdOTLO0wzisUYGBsHwj1k6IJcfKn/9qm5FvfKoXPPkamWivCWJNbwE6AzIvmwDRiZ9m6AXgWGVZTdd/DK8hc3qegbE9rjQciA5Bk2Xk+LtfI5BdWA==";
    public static final String rePublicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhdpfIPubUqSL8COWgg9dKXeOn4UQTSYi5HRi4ds7pWnwUgHklRpwwIYxtxeN8xTL/gQXwOJMILV5C2O3Z6dnJIhKcthyzQVKeqpsfwVVYoeTzhsf7o2ZvFgxOlTt5VnNlIuIJJjcYr+L5j0zbmdT8+ZRhAU6O4zyKojzT8+Mf1noh/wpiPeWMNOs78xw2L7uqcXbRX4PCqBm7k67hh2WxNHNYLet4Z/hWmLZG5PX73Cmca95TC7fATHRH4UYYBX7A6OvwpEMkt5RS9A6QrVxemMiOL8H0T+c4Ix8dUpiqtubCuzEoWjrc1R5ELU3erMxa9FnWWLLMHC0eNtjmzYwXwIDAQAB";


    public static final String getIn = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";

    public static final String getOut = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";

    public static boolean UIFinish = false;


    public  static final String SQLERR_SELECT="查询数据库失败!!!";
    public  static final String SQLERR_UPDATE="更新数据库失败!!!";
    public  static final String SQLERR_DELETE="删除数据库失败!!!";
    public  static final String SQLERR_INSERT="插入数据库失败!!!";
    public  static final String SQLERR_SESSION="获取sqlsession失败!!!";


    public static String ClientIP = "";

    public static boolean pauseFLG = false;

    public static String modelFile="resources/主页";

    public static String modelZipFile="resources/主页.zip";

    /**
     * 获取前台流水=10位终端编号+6位交易时间
     *
     * @return
     */
    public static String getQTLS() {
        String sQTLS_U = "";

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String sGYLSGZ = "";
        String sDate = df.format(new Date());
        int XH = 0;
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
        } catch (IOException e) {
            Logger.logException("LOG_ERR",e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_SESSION);
            return "";
        }
        CXTCSMapper cxtcsMapper = sqlSession.getMapper(CXTCSMapper.class);
        CXTCSKey cxtcsKey = new CXTCSKey();
        cxtcsKey.setFRDM_U("9999");
        cxtcsKey.setKEY_UU("GYLSGZ");
        CXTCS cxtcs = null;
        try {
            cxtcs = cxtcsMapper.selectByPrimaryKey(cxtcsKey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR",e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_SELECT);
            return "";
        }
        if (null == cxtcs) {
            //无数据,插入
            cxtcs = new CXTCS();
            cxtcs.setFRDM_U("9999");
            cxtcs.setKEY_UU("GYLSGZ");
            cxtcs.setVALUE_(sDate + "-" + 2);
            cxtcs.setJLZT_U("0");
            try {
                cxtcsMapper.insert(cxtcs);
            } catch (Exception e) {
                Logger.logException("LOG_ERR",e);
                WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_INSERT);
                return "";
            }
            XH = 1;
        } else {
            sGYLSGZ = cxtcs.getVALUE_();

            if (sGYLSGZ.substring(0, 8).equals(sDate)) {
                XH = Integer.parseInt(sGYLSGZ.substring(9, sGYLSGZ.length()));
            } else {
                XH = 1;
            }
            cxtcs.setVALUE_(sDate + "-" + (XH + 1));
            try {
                cxtcsMapper.updateByPrimaryKey(cxtcs);
            } catch (Exception e) {
                Logger.logException("LOG_ERR",e);
                WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_DELETE);
                return "";
            }
        }

        sqlSession.commit();
        sqlSession.close();

        //C+8位终端号+7位流水序号
        sQTLS_U = "C" + ZDBH_U + String.format("%07d", XH);
        Logger.log("LOG_DEBUG", "前台流水:" + sQTLS_U);

        return sQTLS_U;
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



    public static void main(String[] args) {
    }
}
