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

    public static final String upPrivateKey ="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCEar5WY48F5InUFQdudLWKI9V2teyapeDXuoiJX/t1x9/87Buxdtu/PvvI3nSJk016IIbhGP9MrmhNCBONsuKQB8S08tE4YeHmYfd7J58di24gRNRcay1ebr7EwUcW+4f3XKM6hUfQLnl/omkuFzLfv+ndbNYZu9bIRWhhazpUG3xtZh3+uodp0emT70gj7+p7VLojRreoZ204ylK5yNJKjkRl3oHJVvlXlW/dgRwXY0hJzeC57Q7YieGJ0QDkt8w1oyb5lWajp35FoCmcvwHxty9mmfnWXt3qURn8ZtJo95kYxCrnFm92jdIVr3YaZ1fQ6adVLsJUagd0s6emQh6vAgMBAAECggEAb0hZ/7YZy4T+RAsMPMq+ioKE8gf/+ROwuvwbpP/SD3DTj/ZJa8IM+VOQPIaff8MYmKtfTys32xSzuRExhaMxfoPYz41FQVIZjAkG+CwbL7Qu79WIdsbn0PXXQvl/qhPnd34V+6do688y8o3mQQLkEWByxVCjOes/nP2ftEduNKHIWCRASCtQJ5pGcgEOp3BipEXRzBPklVAbUUYTNTDXKzJxrubH90HTXjOuKXORDBk4UTiTI8cWdAg0KDxnpagAKBC9dmAloXajDzysfbgX1bhq9LlFqR10+A4YkzpTAbL0V+Xh7ZCTl25hhy+GaKpd/RSBF+ERb6o0XgVR9Elc0QKBgQDwReY1zT9ClDkYhRmGj/O95pWo/7Opkl5+p0y/NUL/pChHJ4J1GaiatXQTmFkUGa7J6CO9NTZ3zdJ7ZcAm95GyI7LXaaIfm35ZdN6fYiPSu7sys1amW7f3anF6GHKzRQRYUZgbhwPaGvS1HFUfnYDjl21CqtSbgW9BWmBwikosGQKBgQCNFZHDRtAxEkB4nSCwU68U1yfHEqz0mZSZDn0HugfeX0aiVe13/G0FHdsxI/Anou141/i8Qrb19Xn5aqTHwJNmvTlbXictbEmacHWOGoNvXSznkdG9fWE7BY4yguTLxLndD0fdjQasp1tKMTG04k64bFLonxgG9oxMkv1VZc96BwKBgHFoPKmGT+aH+Y8GO68UwPIQJPGYh19xU6KqKoJRjGcHP2+eSWgmDTvAi6I4FUt0d9ia9kt3E1dm0YMm2pRJ4/3V9bLRDBGpHfDxRaaq9sefjlL27N4mimWAW0FKytCssclR8d6EUqAeewQE9HSwrcY+kfaWlTU02aNaGgzkaO/5AoGAV7CWXsd+02FCzTTsgmwhIFTylltXQNjMca19rPXFukOBxZie9rrgkBOUj6CEvj4YV8n1Ah59Vbbzz0CnlrhtZagrJE0LEMKDpQhNKLv2AZvqMyyBLsPlUSgMz/xndPebhnje9CeZhGqo5R5ahNE8mIhLp+ZqqrlHTrj12MRlBrUCgYBbBWiHuNbQ3aR1e5nBi1zQdQPduv8y+uJiBdMeyQO1UahracwTZCIXvYL7ZIxvqhi81fMcwu3/65Aw+bOTHo1BanDSzICdOH1SSNzlzskfHmmXthIjSB2gsjryA2Smt8wBmZNDrkv0V0QsbqKXC5eGL5X2KRp5MK/c1RVdScvlOg==";
    public static final String rePublicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl6R10WXCdCwDdhozSVVsYVCkI6vnrIgGofR2y7J+d2/x7sRoPuHf7z7nMEfr/tlXA3ZH6PwWKOM2+7Kr0B6zMV4HYYywDmM7DxxBGnh2pxQKbTXcHxZHRFNiYeDKt8guT/QE3+g5UuTE5ICoYrvYhGV7PbADrBKaTMw8d+oxe0Y7m1sURwnw7v0AZrk0PcBxY4qaokEiG48Z0gtzuvOBLfsOEFfqrcBT7hp8c+GXUlwfV3x7HZDkvXUWPcEpazYje6KLjeGkD4EambQGDHh7IKJiIKZXCqITYGv778/hLtktDGfcyQPke+yQLfMQFNtu7O3zU+iFrVYbfXVU9pgZeQIDAQAB";


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
