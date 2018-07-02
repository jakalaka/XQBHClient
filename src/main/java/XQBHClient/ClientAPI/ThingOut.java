package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.log.Logger;

public class ThingOut {
    /**
     * @return
     * @throws Exception
     * note ����ǰ�轫Order.finalOut��Ϊfalse�������������ݴ�ѭ�������ⲿɨ��Ҳ�Ǹ���������жϷ��Ű�ȫ����
     *  ����������ʱOrder.outFail =false.�쳣ʱΪtrue
     */
    public static boolean exec() throws Exception{
        Order.outFail = false;

        Logger.log("LOG_DEBUG","Order.COMName="+Order.controllerCOMName);
        Logger.log("LOG_DEBUG","Order.positionX="+Order.positionX);
        Logger.log("LOG_DEBUG","Order.positionY="+Order.positionY);

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
            Logger.log("LOG_DEBUG","scanning");
            Thread.sleep(1000);
            time++;
            if (time > 30) {
                Com.ZDZT_U = "ERR_ERR_OutNull";
                Order.outFail = true;
                Logger.log("LOG_ERR","�쳣���豸δ��鵽��Ʒ����");
                return false;
            }
        }
        return true;
    }
}
