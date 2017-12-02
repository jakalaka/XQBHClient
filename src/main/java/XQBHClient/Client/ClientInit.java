package XQBHClient.Client;


import XQBHClient.Client.Table.Mapper.CXTCSMapper;
import XQBHClient.Client.Table.Model.CXTCS;
import XQBHClient.Client.Table.Model.CXTCSKey;
import XQBHClient.Client.Table.basic.DBAccess;
import XQBHClient.ClientAPI.WarmingDialog;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.Utils.FinishComListener.FinishComListener;
import XQBHClient.Utils.QRReader.QRReader;
import XQBHClient.Utils.log.Logger;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import java.io.File;
import java.io.IOException;

import static XQBHClient.Utils.PropertiesHandler.PropertiesReader.readKeyFromXML;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class ClientInit {
    public static boolean Init(){

        Logger.log("LOG_IO", Com.getIn);
        /*��ȡ�������Ա�־*/
        DBAccess dbAccess=new DBAccess();
        SqlSession sqlSession;
        try {
            sqlSession = dbAccess.getSqlSession();
        } catch (IOException e) {
            Logger.logException("LOG_ERR",e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_SESSION);
            return false;
        }
        CXTCSMapper cxtcsMapper=sqlSession.getMapper(CXTCSMapper.class);
        CXTCSKey cxtcsKey=new CXTCSKey();
        cxtcsKey.setKEY_UU("CSBZ");
        cxtcsKey.setFRDM_U("9999");
        CXTCS cxtcs = null;
        try {
            cxtcs = cxtcsMapper.selectByPrimaryKey(cxtcsKey);
        }catch (PersistenceException e)
        {
            Logger.logException("LOG_ERR",e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_SELECT);
            return false;
        }
        if (null==cxtcs)
        {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�Ҳ����������Ա�־");
            return false;
        }
        Com.CSBZ_U=cxtcs.getVALUE_();



        /*
        ��userInfo�л�ȡ���������Ϣ
         */
        String ZDJYM_=readKeyFromXML(new File("resources/Info/userInfo.properties"),"ZDJYM_");
        if (!"".equals(ZDJYM_)&&ZDJYM_!=null)
            Com.ZDJYM_=ZDJYM_;

        String ZDBH_U=readKeyFromXML(new File("resources/Info/userInfo.properties"),"ZDBH_U");
        if (!"".equals(ZDBH_U)&&ZDBH_U!=null)
            Com.ZDBH_U=ZDBH_U;

        String LogLV=readKeyFromXML(new File("resources/Info/userInfo.properties"),"LogLV");
        if (!"".equals(LogLV)&&LogLV!=null)
            Com.LogLV =LogLV;

        /*
        ��sysInfo�л�ȡ�������
         */
        String sysinfo = "resources/Info/sysInfo.properties";
        File sysprop = new File(sysinfo);
        QRReader.timeOut = Integer.parseInt(readKeyFromXML(sysprop, "timeOut"));
        if (0 == QRReader.timeOut ) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡtimeOut����!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "timeOut=" + QRReader.timeOut);
        }
        QRReader.frequency = Integer.parseInt(readKeyFromXML(sysprop, "frequency"));
        if (0 == QRReader.frequency ) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡfrequency����!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "frequency=" +QRReader.frequency );
        }

        Com.PowerControlRelayIP = readKeyFromXML(sysprop, "PowerControlRelayIP");
        if (null == Com.PowerControlRelayIP || "".equals(Com.PowerControlRelayIP)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡPowerControlRelayIP����!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "PowerControlRelayIP=" + Com.PowerControlRelayIP);
        }

        Com.PowerControlPort = Integer.parseInt(readKeyFromXML(sysprop, "PowerControlPort"));
        if (0 == Com.PowerControlPort) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡPowerControlPort����!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "PowerControlPort=" + Com.PowerControlPort);
        }
        Com.PowerControlAdress = Integer.parseInt(readKeyFromXML(sysprop, "PowerControlAdress"));
        if (0 == Com.PowerControlAdress) {
            Logger.log("LOG_ERR", "��ȡPowerControlAdress����");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "PowerControlAdress=" + Com.PowerControlAdress);
        }

        Com.QRReaderComName = readKeyFromXML(sysprop, "QRReaderComName");
        if (null == Com.QRReaderComName || "".equals(Com.QRReaderComName)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡQRReaderComName����!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "QRReaderComName=" + Com.QRReaderComName);
        }

        Com.FinishScannerComName = readKeyFromXML(sysprop, "FinishScannerComName");
        if (null == Com.FinishScannerComName || "".equals(Com.FinishScannerComName)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡFinishScannerComName����!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "FinishScannerComName=" + Com.FinishScannerComName);
        }


        Logger.log("LOG_IO", Com.getOut);

        return true;
    }

}
