package XQBHClient.Utils.ThingsOutPCB;

import javax.comm.SerialPort;

public class Write {
    public static void main(String[] args) {
        SerialPort serialPort = null;                  //�򿪵Ķ�
        try {
            //�򿪴���
            serialPort = SerialTool.openPort("COM4", 9600);
            //���顰Hello World����
            byte[] bs = new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x11,0x00};
            //д������
            SerialTool.sendToPort(serialPort, bs);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            //�رմ���
            SerialTool.closePort(serialPort);
        }
    }
}