package XQBHClient.Client;

import XQBHClient.ClientAPI.WarmingDialog;
import XQBHClient.CommonTran.*;


import XQBHClient.Utils.RSA.RSASignature;
import XQBHClient.Utils.XML.XmlUtils;
import XQBHClient.Utils.log.Logger;

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
        /*
        ��ǩ��
         */
        String signstr = RSASignature.sign(XMLIn, Com.upPrivateKey);
        XMLIn = XMLIn + "sign=" + signstr;

        Logger.log("LOG_DEBUG", "XMLIn=" + XMLIn);
        CommonTran commonTran;
        try {
            commonTran = new CommonTranService().getCommonTranPort();
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "����������ʧ��!");
            return false;
        }
        String XMLOut;
        try {
            XMLOut = commonTran.comtran(XMLIn);

        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "����������!");
            return false;
        }
        Logger.log("LOG_DEBUG", "XMLOut=" + XMLOut);

        /*
        ��ǩ��
         */
        String[] str = XMLOut.split("sign=");
        try {
            if (true != RSASignature.doCheck(str[0], str[1], Com.rePublicKey)) {
                Logger.log("LOG_ERR", XMLOut);
                WarmingDialog.show(WarmingDialog.Dialog_ERR, "����ͨѶ���ܱ��۸ģ�����ʧ��");
                return false;
            } else {
                XMLOut = str[0];
            }
        } catch (Exception e) {
            Logger.log("LOG_ERR", XMLOut);
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "����ͨѶ����ʧ��");
            return false;
        }


        Map XMLMapOut = XmlUtils.XML2map(XMLOut);

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
        if (sQTLS.length() != 16) {
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
