package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.log.Logger;
import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ThingOut {

    /**
     * @return
     * @throws Exception note ����ǰ�轫Order.finalOut��Ϊfalse�������������ݴ�ѭ�������ⲿɨ��Ҳ�Ǹ���������жϷ��Ű�ȫ����
     *                   ����������ʱOrder.outFail =false.�쳣ʱΪtrue
     */
    public static boolean exec_bak() throws Exception {
        Order.outFail = false;

        Logger.log("LOG_DEBUG", "Order.COMName=" + Order.controllerCOMName);
        Logger.log("LOG_DEBUG", "Order.positionX=" + Order.positionX);
        Logger.log("LOG_DEBUG", "Order.positionY=" + Order.positionY);

        /*����
        try {
            ModbusUtil.doThingsOut(Order.controllerIP, Order.controllerPort, Order.controllerAdress);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "ִ�г�������!");
            Order.outFail = true;
            return false;
        }*/


        int time = 0;

        while (!Order.finalOut) {//30��û�����ʹ������װ�ô���
            Logger.log("LOG_DEBUG", "scanning");
            Thread.sleep(1000);
            time++;
            if (time > 30) {
                Com.ZDZT_U = "ERR_ERR_OutNull";
                Order.outFail = true;
                Logger.log("LOG_ERR", "�쳣���豸δ��鵽��Ʒ����");
                return false;
            }
        }
        return true;
    }

    public static boolean exec() throws Exception {
        Order.outFail = false;
        Logger.log("LOG_DEBUG", "Order.QRCODE=" + Order.QRCODE);
        Logger.log("LOG_DEBUG", "Order.positionX=" + Order.positionX);
        Logger.log("LOG_DEBUG", "Order.positionY=" + Order.positionY);
        Logger.log("LOG_DEBUG", "Order.positionY=" + Order.positionZ);


        String IP = "127.0.0.1";
        int port = 5000;

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("QRCODE",Order.QRCODE);
        map.put("positionX",Order.positionX);
        map.put("positionY",Order.positionY);
        map.put("positionZ",Order.positionZ);
        map.put("positionZ_max",Order.positionZ+150);
        map.put("positionW",-90);
        String json_string = JSON.toJSONString(map);
        BufferedReader in;
        PrintWriter out;

        // Make connection and initialize streams
        Socket socket = new Socket(IP, port);

        try {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json_string);
            String feedback="";
            while((feedback=in.readLine())!=null)
            {

                Logger.log("LOG_DEBUG", "feedback=" + feedback);

            }


        } finally {
            socket.close();
        }


        return true;

    }

    public static void main(String[] args) {
        Order.QRCODE = "6914036001518";
        Order.positionX = 225;
        Order.positionY = -184  ;
        Order.positionZ = 120;
        try {
            ThingOut.exec();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
