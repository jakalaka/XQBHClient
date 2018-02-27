package XQBHClient.Client;

import XQBHClient.ClientAPI.WarmingDialog;


import XQBHClient.Utils.RSA.RSAHandler;
import XQBHClient.Utils.XML.XmlUtils;
import XQBHClient.Utils.log.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class ComCall {
    /**
     * client���ײ�ͨ�õ���
     *
     * @param TranMapIn
     * @param TranMapOut
     * @return
     */
    public static boolean Call(String QTJYM_, String HTJYM_, Map TranMapIn, Map TranMapOut) {
        Map XMLMapIn = new HashMap();//д����,��TranMapIn��ӱ���ͷ��Ϣ,��ΪXMLMapIn
        if (true != addInfo(QTJYM_, HTJYM_, TranMapIn, XMLMapIn)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��֯����ͷ��Ϣʧ��!");
            return false;
        }

        String XMLIn = XmlUtils.map2XML(XMLMapIn);
        Logger.log("LOG_DEBUG", "XMLIn=" + XMLIn);

        /*
        ����
         */
        PrivateKey upprivatekey = null;
        try {
            upprivatekey = RSAHandler.getPrivateKey(Com.upPrivateKey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡ˽Կʧ��!");
            return false;
        }

        byte[] encrypt = null;
        try {
            encrypt = RSAHandler.encrypt(XMLIn.getBytes(), upprivatekey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�������ͱ���ʧ��!");
            return false;
        }
        XMLIn = new String(encrypt);
        Logger.log("LOG_DEBUG", "after encrypt XMLIn=[" + XMLIn + "]");
        Logger.log("LOG_DEBUG", "after encrypt XMLIn.length=[" + XMLIn.length() + "]");
        Logger.log("LOG_DEBUG", "Com.upPrivateKey=[" + Com.upPrivateKey + "]");


//
//        CommonTran commonTran;
//        try {
//            commonTran = new CommonTranService().getCommonTranPort();
//        } catch (Exception e) {
//            Logger.logException("LOG_ERR", e);
//            WarmingDialog.show(WarmingDialog.Dialog_ERR, "����������ʧ��!");
//            return false;
//        }
//        String XMLOut;
//        try {
//            XMLOut = commonTran.comtran(XMLIn);
//
//        } catch (Exception e) {
//            Logger.logException("LOG_ERR", e);
//            WarmingDialog.show(WarmingDialog.Dialog_ERR, "����������!");
//            return false;
//        }
        String IP = "192.168.31.62";
//        String IP="newfangledstore.com";

        int port = 9000;
        String XMLOut = "";
        try {


            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(IP, port), 3000);//������������ʱʱ��10 s
            socket.setSoTimeout(15000);//���ö�������ʱʱ��30 s
//2����ȡ���������������˷�����Ϣ
            OutputStream os = socket.getOutputStream();//�ֽ������
            PrintWriter pw = new PrintWriter(os);//���������װ�ɴ�ӡ��

            pw.write(XMLIn);
            pw.flush();
            socket.shutdownOutput();
//3����ȡ������������ȡ�������˵���Ӧ��Ϣ
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            StringBuilder stringBuilder = new StringBuilder();

            while ((info = br.readLine()) != null) {
                stringBuilder.append(info);
            }
            XMLOut = stringBuilder.toString();
            //4���ر���Դ
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
        }


        Logger.log("LOG_DEBUG", "XMLOut=" + XMLOut);

        /*
        ����
         */

        Logger.log("LOG_ERR", XMLOut);
        PublicKey republicKey = null;
        try {
            republicKey = RSAHandler.getPublicKey(Com.rePublicKey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡ��Կʧ��!");
            return false;
        }
        byte[] decrypt = null;
        try {
            decrypt = RSAHandler.decrypt(XMLOut.getBytes(), republicKey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "���ܷ��ر���ʧ��!");
            return false;
        }


        Map XMLMapOut = XmlUtils.XML2map(new String(decrypt));

        if (false == minusInfo(XMLMapOut, TranMapOut)) {
            return false;
        }
        return true;
    }

    /**
     * ���Ϸ��Բ���ӱ���ͷ
     *
     * @param TranMapIn
     * @param XMLMapIn
     * @return
     */
    public static boolean addInfo(String QTJYM_, String HTJYM_, Map TranMapIn, Map XMLMapIn) {
        Map head = new HashMap();
        String[] date = Com.getDate().split("-");
        head.put("QTRQ_U", date[0]); //ǰ̨����
        head.put("QTSJ_U", date[1]); //ǰ̨ʱ��
        String sQTLS = Com.getQTLS();
        if (sQTLS.length() != 20) {
            Logger.log("LOG_ERR", "��ˮ��ȡʧ��");
            return false;
        }
        head.put("QTLS_U", sQTLS); //ǰ̨��ˮ

        head.put("ZDBH_U", Com.ZDBH_U); //�ն˱��
        head.put("QTJYM_", QTJYM_); //������
        head.put("HTJYM_", HTJYM_); //��̨������
        head.put("ZDJYM_", Com.ZDJYM_); //�ն�У����
        head.put("IP_UUU", Com.ClientIP); //ip��ַ
        XMLMapIn.put("head", head);
        XMLMapIn.put("body", TranMapIn);
        return true;
    }

    /**
     * ���Ϸ��Բ�ȥ������ͷ
     *
     * @param XMLMapOut
     * @param TranMapOut
     * @return
     */
    public static boolean minusInfo(Map XMLMapOut, Map TranMapOut) {
        TranMapOut.clear();


        /*
        ClientͨѶ��ĺϷ��Լ��,Ҳ��֪��дʲô��,�Ժ�Ӱ�
         */

//        String CWXX_U = (String) ((Map) XMLMapOut.get("head")).get("CWXX_U");
        //����ӦҪ��
//        TranMapOut.put("CWDM_U",CWDM_U);
//        TranMapOut.put("CWXX_U",CWXX_U);
        TranMapOut.putAll((Map) XMLMapOut.get("head"));
        TranMapOut.putAll((Map) XMLMapOut.get("body"));

        String CWDM_U = (String) TranMapOut.get("CWDM_U");


        if (!"AAAAAA".equals(CWDM_U)) {
            /*
            ������ʧ�ܴ���
             */
            Logger.log("LOG_ERR", "����ʧ��");
            Logger.log("LOG_ERR", "CWDM_U=" + CWDM_U);
            return false;
        } else Logger.log("LOG_IO", "CWDM_U=" + CWDM_U);

        return true;
    }

}
