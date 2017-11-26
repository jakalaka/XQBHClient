package XQBHClient.Client;
import XQBHClient.CommonTran.*;



import XQBHClient.Utils.XML.XmlUtils;
import XQBHClient.Utils.log.Logger;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class ComCall {
    /**
     * client���ײ�ͨ�õ���
     * @param TranMapIn
     * @param TranMapOut
     * @return
     */
    public static boolean Call(String QTJYM_,String HTJYM_,Map TranMapIn,Map TranMapOut){
        Map XMLMapIn=new HashMap();//д����,��TranMapIn��ӱ���ͷ��Ϣ,��ΪXMLMapIn
        if(false==addInfo(QTJYM_,HTJYM_,TranMapIn,XMLMapIn))
        {
            Logger.log("LOG_ERR","��֯����ͷ��Ϣʧ��");
            return false;
        }

        String XMLIn= XmlUtils.map2XML(XMLMapIn);
        Logger.log("LOG_DEBUG","XMLIn="+XMLIn);
        CommonTran commonTran;
        try {
            commonTran = new CommonTranService().getCommonTranPort();
        }catch (Exception e)
        {
            Logger.log("LOG_ERR","����������ʧ��");
            return false;
        }
        String XMLOut=commonTran.comtran(XMLIn);
        Map XMLMapOut= XmlUtils.XML2map(XMLOut);
        Logger.log("LOG_DEBUG","XMLOut="+XMLOut);

        if (false ==minusInfo(XMLMapOut,TranMapOut))
        {
            return false;
        }
        return true;
    }

    /**
     * ���Ϸ��Բ���ӱ���ͷ
     * @param TranMapIn
     * @param XMLMapIn
     * @return
     */
    public static boolean addInfo(String QTJYM_,String HTJYM_,Map TranMapIn,Map XMLMapIn){
        Map head=new HashMap();
        String []date=Com.getDate().split("-");
        head.put("QTRQ_U",date[0]); //ǰ̨����
        head.put("QTSJ_U",date[1]); //ǰ̨ʱ��
        head.put("QTLS_U",Com.getQTLS()); //ǰ̨��ˮ

        head.put("ZDBH_U", Com.ZDBH_U); //�ն˱��
        head.put("QTJYM_",QTJYM_); //������
        head.put("HTJYM_",HTJYM_); //��̨������
        head.put("ZDJYM_", Com.ZDJYM_); //�ն�У����
        head.put("IP_UUU", Com.getIP()); //ip��ַ
        head.put("CSBZ_U", Com.CSBZ_U); //ip��ַ
        XMLMapIn.put("head",head);
        XMLMapIn.put("body",TranMapIn);
        return true;
    }

    /**
     * ���Ϸ��Բ�ȥ������ͷ
     * @param XMLMapOut
     * @param TranMapOut
     * @return
     */
    public  static boolean minusInfo(Map XMLMapOut,Map TranMapOut){
        /*
        ClientͨѶ��ĺϷ��Լ��,Ҳ��֪��дʲô��,�Ժ�Ӱ�
         */
        String CWDM_U=(String) ((Map)XMLMapOut.get("head")).get("CWDM_U");
        String CWXX_U=(String) ((Map)XMLMapOut.get("head")).get("CWXX_U");


        if (!"AAAAAA".equals(CWDM_U)) {
            /*
            ������ʧ�ܴ���
             */
            TranMapOut.clear();
            Logger.log("LOG_ERR","����ʧ��");
            Logger.log("LOG_ERR","CWDM_U="+CWDM_U);
            return false;
        }else Logger.log("LOG_IO","CWDM_U="+CWDM_U);

        TranMapOut.putAll((Map)XMLMapOut.get("body"));
        return true;
    }

}
