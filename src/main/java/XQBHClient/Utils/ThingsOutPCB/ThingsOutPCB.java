package XQBHClient.Utils.ThingsOutPCB;

import XQBHClient.Client.Com;
import XQBHClient.Utils.log.Logger;

import javax.comm.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ThingsOutPCB implements SerialPortEventListener {

    CommPortIdentifier com21 = null;//未打卡的端口
    static SerialPort serialComPort = null;//打开的端口
    static byte resoult;
    static boolean isReadOver = true;
    static InputStream inputStream = null;


    public ThingsOutPCB() throws Exception {
        Logger.log("LOG_DEBUG", "Com.FinishScannerComName=" + Com.FinishScannerComName);
        com21 = CommPortIdentifier.getPortIdentifier(Com.ControllerCOMName);
        serialComPort = (SerialPort) com21.open("ThingsOutPCB", 1000);
        inputStream = serialComPort.getInputStream();

        //向串口添加事件监听对象。
        serialComPort.addEventListener(this);
        //设置当端口有可用数据时触发事件,此设置必不可少。
        serialComPort.notifyOnDataAvailable(true);


    }

    public static boolean doCheck(String COMNAME, int positionX, int positionY) {
        boolean resoult = false;

        return resoult;
    }

    public static void main(String[] args) {
        Com.ControllerCOMName = "COM7";

        try {
            ThingsOutPCB thingsOutPCB = new ThingsOutPCB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            for (int i = 0; i < 8; i++) {
                try {
                    boolean resoult = ThingsOutPCB.exec(0,1

                    );
                    Logger.log("LOG_DEBUG", resoult + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    static public boolean exec(int positionX, int positionY) throws Exception {
        isReadOver = false;
        OutputStream outputStream = serialComPort.getOutputStream();
        byte bytes[] = getcmdBytes(positionX, positionY);


        try {
            outputStream.write(bytes);
            outputStream.flush();

        } catch (IOException e) {
            throw new Exception();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
            } catch (IOException e) {
                throw new Exception();
            }
        }
        while (!isReadOver) {
            Thread.sleep(100);
        }

        if (resoult == 0) {
            return true;
        }
        Logger.log("LOG_ERR", "resoult=[" + resoult + "]");

        return false;

    }


    @Override
    public void serialEvent(SerialPortEvent event) {

        //定义用于缓存读入数据的数组
        resoult = 127;
        byte[] cache = new byte[1024];
        //记录已经到达串口COM21且未被读取的数据的字节（Byte）数。
        int availableBytes = 0;
        //如果是数据可用的时间发送,则进行数据的读写
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {

            try {

                availableBytes = inputStream.available();
                while (availableBytes > 0) {
                    inputStream.read(cache);
                    resoult = cache[0];
                    Logger.log("LOG_ERR", "resoult=[" + resoult + "]");
                    availableBytes = inputStream.available();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                isReadOver = true;
            }


        }


    }

    public static byte[] getcmdBytes(int x, int y) {
        byte resoult[] = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        byte model[] = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, (byte) 0x80};
        if (x >= 32 || y >= 32) {

        }
        int p = x / 8;
        int q = x % 8;
        resoult[4 - p] = model[q];
        p = y / 8;
        q = y % 8;
        resoult[8 - p] = model[q];
        StringBuilder sb = new StringBuilder();
        for (byte sub :
                resoult) {

            sb.append(sub);
            sb.append(" ");
        }
        Logger.log("LOG_DEBUG", sb.toString());

        return resoult;
    }

    ;
}
