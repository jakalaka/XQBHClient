package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.Modbus.ModbusUtil;
import XQBHClient.Utils.log.Logger;

public class ThingOut {
    public static boolean exec() throws Exception{
        Order.outFail = false;
        try {
            ModbusUtil.doThingsOut(Order.controllerIP, Order.controllerPort, Order.controllerAdress);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "执行出货错误!");
            Order.outFail = true;
            return false;
        }
        int time = 0;
        while (!Order.finalOut) {//30秒没出货就代表出货装置错误
            Thread.sleep(1000);
            time++;
            if (time > 30) {
                Com.ZDZT_U = "ERR_ERR_OutNull";
                Order.outFail = true;
                return false;
            }
        }
        return true;
    }
}
