package XQBHClient.Client;

import XQBHClient.Client.Table.Mapper.CXTCSMapper;
import XQBHClient.Client.Table.Model.CXTCS;
import XQBHClient.Client.Table.Model.CXTCSKey;
import XQBHClient.Client.Table.basic.DBAccess;
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
     * ��־�ȼ�
     */
    public static String LogLV = "";
    /**
     * �ն˱��
     */
    static String ZDBH_U = "";

    /**
     * �ն�У����
     */
    static String ZDJYM_ = "";


    /**
     * �ն�У����
     */
    public static String CSBZ_U = "";

    /**
     * �ն�״̬,���ڿ����쳣���
     */
    public  static String ZDZT_U = "OK";


    public static String PowerControlRelayIP;
    public static int PowerControlPort;
    public static int PowerControlAdress;
    public static String QRReaderComName;
    public static String FinishScannerComName;
    public static String FinishScannerState="E";

    public static final String getIn = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";

    public static final String getOut = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";

    /**
     * ��ȡǰ̨��ˮ=10λ�ն˱��+6λ����ʱ��
     *
     * @return
     */
    public static String getQTLS() {
        String sQTLS_U = "";

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//�������ڸ�ʽ
        String sGYLSGZ = "";
        String sDate = df.format(new Date());
        int XH = 0;
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
        } catch (IOException e) {
            Logger.log("LOG_SYS", "ȡ��ˮ��ʱ��ѯ���ݿ����!!!");
            e.printStackTrace();
        }
        CXTCSMapper cxtcsMapper = sqlSession.getMapper(CXTCSMapper.class);
        CXTCSKey cxtcsKey = new CXTCSKey();
        cxtcsKey.setFRDM_U("9999");
        cxtcsKey.setKEY_UU("GYLSGZ");
        CXTCS cxtcs = cxtcsMapper.selectByPrimaryKey(cxtcsKey);
        if (null == cxtcs) {
            //������,����
            cxtcs = new CXTCS();
            cxtcs.setFRDM_U("9999");
            cxtcs.setKEY_UU("GYLSGZ");
            cxtcs.setVALUE_(sDate + "-" + 2);
            cxtcs.setJLZT_U("0");
            cxtcsMapper.insert(cxtcs);
            XH = 1;
        } else {
            sGYLSGZ = cxtcs.getVALUE_();

            if (sGYLSGZ.substring(0, 8).equals(sDate)) {
                XH = Integer.parseInt(sGYLSGZ.substring(9, sGYLSGZ.length()));
            } else {
                XH = 1;
            }
            cxtcs.setVALUE_(sDate + "-" + (XH + 1));
            cxtcsMapper.updateByPrimaryKey(cxtcs);
        }

        sqlSession.commit();
        sqlSession.close();

        sQTLS_U = "C" + ZDBH_U + String.format("%05d", XH);
        Logger.log("LOG_DEBUG", "ǰ̨��ˮ:" + sQTLS_U);

        return sQTLS_U;
    }

    /**
     * ��ȡǰ̨��������
     *
     * @return
     */
    public static String getDate() {
        String date = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");//�������ڸ�ʽ
        date = df.format(new Date());
        return date;
    }


    /**
     * ��ȡǰ̨����IP
     *
     * @return
     */
    public static String getIP() {
        String IPAdress = "";
        String localip = null;// ����IP,���û����������IP�򷵻���
        String netip = null;// ����IP

        Enumeration<NetworkInterface> netInterfaces =
                null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress ip = null;
        boolean finded = false;// �Ƿ��ҵ�����IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// ����IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// ����IP
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
        Logger.log("LOG_DEBUG", getIP());
    }
}
