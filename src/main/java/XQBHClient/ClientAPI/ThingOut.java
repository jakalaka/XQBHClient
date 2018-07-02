package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.log.Logger;

public class ThingOut {
    /**
     * @return
     * @throws Exception
     * note 调用前需将Order.finalOut置为false，本函数将根据此循环，且外部扫描也是根据这个来判断阀门安全控制
     *  在正常出货时Order.outFail =false.异常时为true
     */
    public static boolean exec() throws Exception{
        Order.outFail = false;

        Logger.log("LOG_DEBUG","Order.COMName="+Order.controllerCOMName);
        Logger.log("LOG_DEBUG","Order.positionX="+Order.positionX);
        Logger.log("LOG_DEBUG","Order.positionY="+Order.positionY);

        /*弃用
        try {
            ModbusUtil.doThingsOut(Order.controllerIP, Order.controllerPort, Order.controllerAdress);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "执行出货错误!");
            Order.outFail = true;
            return false;
        }*/




        int time = 0;

        while (!Order.finalOut) {//30秒没出货就代表出货装置错误
            Logger.log("LOG_DEBUG","scanning");
            Thread.sleep(1000);
            time++;
            if (time > 30) {
                Com.ZDZT_U = "ERR_ERR_OutNull";
                Order.outFail = true;
                Logger.log("LOG_ERR","异常，设备未检查到商品出货");
                return false;
            }
        }
        return true;
    }
}
