package XQBHClient.Client;
import XQBHClient.CommonTran.*;



import XQBHClient.Utils.XML.XmlUtils;
import XQBHClient.Utils.log.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class ComCall {
    /**
     * client交易层通用调用
     * @param TranMapIn
     * @param TranMapOut
     * @return
     */
    public static boolean Call(String QTJYM_,String HTJYM_,Map TranMapIn,Map TranMapOut,String [] ERRMsg){
        Map XMLMapIn=new HashMap();//写方法，将TranMapIn添加报文头信息，变为XMLMapIn
        if(false==addInfo(QTJYM_,HTJYM_,TranMapIn,XMLMapIn))
        {
            Logger.log("LOG_ERR","组织报文头信息失败");
            return false;
        }

        String XMLIn= XmlUtils.map2XML(XMLMapIn,"root");

        CommonTran commonTran;
        commonTran = new CommonTranService().getCommonTranPort();
        String XMLOut=commonTran.comtran(XMLIn);

        Map XMLMapOut=(Map) XmlUtils.XML2map(XMLOut,"root");

        if (false ==minusInfo(XMLMapOut,TranMapOut,ERRMsg))
        {
            return false;
        }
        return true;
    }

    /**
     * 检查合法性并添加报文头
     * @param TranMapIn
     * @param XMLMapIn
     * @return
     */
    public static boolean addInfo(String QTJYM_,String HTJYM_,Map TranMapIn,Map XMLMapIn){
        Map head=new HashMap();
        String []date=Com.getDate().split("-");
        head.put("QTRQ_U",date[0]); //前台日期
        head.put("QTSJ_U",date[1]); //前台时间
        head.put("QTLS_U",Com.getQTLS()); //前台流水

        head.put("ZDBH_U", Com.ZDBH_U); //终端编号
        head.put("QTJYM_",QTJYM_); //交易码
        head.put("HTJYM_","ZDLogin"); //后台交易码
        head.put("ZDJYM_", Com.ZDJYM_); //终端校验码
        head.put("IP_UUU", Com.getIP()); //ip地址
        XMLMapIn.put("head",head);
        XMLMapIn.put("body",TranMapIn);
        return true;
    }

    /**
     * 检查合法性并去掉报文头
     * @param XMLMapOut
     * @param TranMapOut
     * @return
     */
    public  static boolean minusInfo(Map XMLMapOut,Map TranMapOut,String []ERRMsg){
        /*
        Client通讯后的合法性检查，也不知道写什么好，以后加吧
         */
        String CWDM_U=(String) ((Map)XMLMapOut.get("head")).get("CWDM_U");
        String CWXX_U=(String) ((Map)XMLMapOut.get("head")).get("CWXX_U");


        if (!"AAAAAA".equals(CWDM_U)) {
            /*
            这里做失败处理
             */
            ERRMsg[0]=CWXX_U;
            TranMapOut.clear();
            Logger.log("LOG_ERR","调用失败");
            Logger.log("LOG_ERR","CWDM_U="+CWDM_U);
            return false;
        }else Logger.log("LOG_IO","CWDM_U="+CWDM_U);

        TranMapOut.putAll((Map)XMLMapOut.get("body"));
        return true;
    }

}
