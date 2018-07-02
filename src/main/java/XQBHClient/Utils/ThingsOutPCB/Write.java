package XQBHClient.Utils.ThingsOutPCB;

import javax.comm.SerialPort;

public class Write {
    public static void main(String[] args) {
        SerialPort serialPort = null;                  //打开的端
        try {
            //打开串口
            serialPort = SerialTool.openPort("COM4", 9600);
            //数组“Hello World！”
            byte[] bs = new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x11,0x00};
            //写入数组
            SerialTool.sendToPort(serialPort, bs);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            //关闭串口
            SerialTool.closePort(serialPort);
        }
    }
}