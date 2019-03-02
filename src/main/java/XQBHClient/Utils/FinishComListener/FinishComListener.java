package XQBHClient.Utils.FinishComListener;

import XQBHClient.Client.Com;
import XQBHClient.ClientAPI.WarmingAction;

import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.log.Logger;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.InputStream;

/**
 * Com21EventListener类使用“事件监听模式”监听串口COM21,
 * 并通过COM21的输入流对象来获取该端口接收到的数据（在本文中数据来自串口COM11）。
 * 使用“事件监听模式”监听串口,必须字定义一个事件监听类,该类实现SerialPortEventListener
 * 接口并重写serialEvent方法,在serialEvent方法中编写监听逻辑。
 */
public class FinishComListener implements SerialPortEventListener {

    //1.定义变量
    CommPortIdentifier com21 = null;//未打卡的端口
    static SerialPort serialComPort = null;//打开的端口
    InputStream inputStream = null;//输入流
    public static boolean isClose;
    public static boolean subIsClose;

    //2.构造函数：
    //实现初始化动作：获取串口COM21、打开串口、获取串口输入流对象、为串口添加事件监听对象
    public FinishComListener() throws Exception {
        //获取串口、打开窗串口、获取串口的输入流。
        Logger.log("LOG_DEBUG", "Com.FinishScannerComName=" + Com.FinishScannerComName);
        com21 = CommPortIdentifier.getPortIdentifier(Com.FinishScannerComName);
        serialComPort = (SerialPort) com21.open("FinishComListener", 1000);
        inputStream = serialComPort.getInputStream();

        //向串口添加事件监听对象。
        serialComPort.addEventListener(this);
        //设置当端口有可用数据时触发事件,此设置必不可少。
        serialComPort.notifyOnDataAvailable(true);

    }

    //重写继承的监听器方法
    @Override
    public void serialEvent(SerialPortEvent event) {
        //定义用于缓存读入数据的数组
        byte[] cache = new byte[1024];
        //记录已经到达串口COM21且未被读取的数据的字节（Byte）数。
        int availableBytes = 0;
        //如果是数据可用的时间发送,则进行数据的读写
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            if (!isClose) {
                isClose = true;
            }
            subIsClose = true;

        }
    }

    //在main方法中创建类的实例
    /*deletetmp
    public static void start() {
        try {
            new FinishComListener();
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingAction.show(WarmingAction.Dialog_ERR, "未检测到完成扫描器!");
            return;
        }
        Closer closer = new Closer();
        Thread t = new Thread(closer);
        t.start();
        Com.FinishScannerState = "N";

    }*/


}

class Closer implements Runnable {
    @Override
    public void run() {
        while (true) {
            if (FinishComListener.subIsClose == false) {
                if (FinishComListener.isClose == true) {
                    if (!Order.finalOut) {
                        Logger.log("LOG_DEBUG", "已出货");
                        Order.finalOut = true;
                    } else {
                        Logger.log("LOG_ERR", "未检测到购买即出货!!!");
                        Com.ZDZT_U = "ERR_OutAgain";
                        /*这里需要立即断开传送带的电源*/

                    }
                }
                FinishComListener.isClose = false;
            }
            FinishComListener.subIsClose = false;
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                Logger.logException("LOG_ERR", e);
                WarmingAction.show(WarmingAction.Dialog_ERR, "启FinishComListener进程异常!");
            }
        }
    }
}