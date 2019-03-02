package XQBHClient.Client;


import XQBHClient.ClientAPI.WarmingAction;
import XQBHClient.Utils.QRReader.QRReader;
import XQBHClient.Utils.log.Logger;

import java.awt.*;
import java.io.*;

import static XQBHClient.Utils.PropertiesHandler.PropertiesReader.readFromProperties;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class ClientInit {
    public static boolean Init() {


        Logger.log("LOG_IO", Com.getIn);

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Com.ScreenWidth = screensize.width;
        Com.ScreenHeight = screensize.height;


        /*
        ��userInfo�л�ȡ���������Ϣ
         */
        String ZDJYM_ = readFromProperties(new File("resources/Info/userInfo.properties"), "ZDJYM_");
        if (!"".equals(ZDJYM_) && ZDJYM_ != null)
            Com.ZDJYM_ = ZDJYM_;

        String ZDBH_U = readFromProperties(new File("resources/Info/userInfo.properties"), "ZDBH_U");
        if (!"".equals(ZDBH_U) && ZDBH_U != null)
            Com.ZDBH_U = ZDBH_U;

        String LogLV = readFromProperties(new File("resources/Info/userInfo.properties"), "LogLV");
        if (!"".equals(LogLV) && LogLV != null)
            Com.LogLV = LogLV;

        /*
        ��sysInfo�л�ȡ�������
         */
        String sysinfo = "resources/Info/sysInfo.properties";
        File sysprop = new File(sysinfo);
        QRReader.timeOut = Integer.parseInt(readFromProperties(sysprop, "timeOut"));
        if (0 == QRReader.timeOut) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "��ȡtimeOut����!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "timeOut=" + QRReader.timeOut);
        }
        QRReader.frequency = Integer.parseInt(readFromProperties(sysprop, "frequency"));
        if (0 == QRReader.frequency) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "��ȡfrequency����!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "frequency=" + QRReader.frequency);
        }

        /*
        Com.PowerControlRelayIP = readFromProperties(sysprop, "PowerControlRelayIP");
        if (null == Com.PowerControlRelayIP || "".equals(Com.PowerControlRelayIP)) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "��ȡPowerControlRelayIP����!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "PowerControlRelayIP=" + Com.PowerControlRelayIP);
        }

        Com.PowerControlPort = Integer.parseInt(readFromProperties(sysprop, "PowerControlPort"));
        if (0 == Com.PowerControlPort) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "��ȡPowerControlPort����!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "PowerControlPort=" + Com.PowerControlPort);
        }
        Com.PowerControlAdress = Integer.parseInt(readFromProperties(sysprop, "PowerControlAdress"));
        if (0 == Com.PowerControlAdress) {
            Logger.log("LOG_ERR", "��ȡPowerControlAdress����");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "PowerControlAdress=" + Com.PowerControlAdress);
        }
        */


        Com.ControllerCOMName = readFromProperties(sysprop, "ControllerComName");
        if (null == Com.ControllerCOMName || "".equals(Com.ControllerCOMName)) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "��ȡControllerComName����!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "controllerCOMName=" + Com.ControllerCOMName);
        }


        Com.QRReaderComName = readFromProperties(sysprop, "QRReaderComName");
        if (null == Com.QRReaderComName || "".equals(Com.QRReaderComName)) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "��ȡQRReaderComName����!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "QRReaderComName=" + Com.QRReaderComName);
        }

        Com.FinishScannerComName = readFromProperties(sysprop, "FinishScannerComName");
        if (null == Com.FinishScannerComName || "".equals(Com.FinishScannerComName)) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "��ȡFinishScannerComName����!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "FinishScannerComName=" + Com.FinishScannerComName);
        }



        Com.Robot_IP = readFromProperties(sysprop, "Robot_IP");
        if (null == Com.Robot_IP || "".equals(Com.Robot_IP)) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "��ȡRobot_IP����!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "Robot_IP=" + Com.Robot_IP);
        }

        Com.Robot_port = Integer.parseInt(readFromProperties(sysprop, "Robot_port"));

        if (0 == Com.Robot_port ) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "��ȡRobot_port����!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "Robot_port=" + Com.Robot_port);
        }


        //��һ��socket
        Com.ClientIP = readFromProperties(sysprop, "OrayIP");
        if (null == Com.ClientIP || "".equals(Com.ClientIP)) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "��ȡClientIP����!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "ClientIP=" + Com.ClientIP);
        }







        Logger.log("LOG_IO", Com.getOut);

        return true;
    }

}
