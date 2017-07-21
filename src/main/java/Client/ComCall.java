package Client;




import CommonTran.*;

import java.util.HashMap;
import java.util.Map;

import static Client.Com.getDate;
import static Client.Com.getQTLS;

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
    public static boolean Call(String JYM_UU,Map TranMapIn,Map TranMapOut,String [] ERRMsg){
        Map XMLMapIn=new HashMap();//写方法，将TranMapIn添加报文头信息，变为XMLMapIn
        if(false==addInfo(JYM_UU,TranMapIn,XMLMapIn))
        {
            Logger.log("ERR","组织报文头信息失败");
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
    public static boolean addInfo(String JYM_UU,Map TranMapIn,Map XMLMapIn){
        Map head=new HashMap();
        String []date=getDate().split("-");
        head.put("QTRQ_U",date[0]); //前台日期
        head.put("QTSJ_U",date[1]); //前台时间
        head.put("QTLS_U",getQTLS()); //前台流水
        head.put("ZDBH_U", Com.ZDBH_U); //终端编号
        head.put("JYM_UU",JYM_UU); //交易码
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

        Logger.log("ERR","CWDM_U="+CWDM_U);
        if (!"AAAAAA".equals(CWDM_U)) {
            /*
            这里做失败处理
             */
            ERRMsg[0]=CWXX_U;
            TranMapOut.clear();
            Logger.log("ERR","调用失败");
            return false;
        }
        TranMapOut.putAll((Map)XMLMapOut.get("body"));
        return true;
    }

}
