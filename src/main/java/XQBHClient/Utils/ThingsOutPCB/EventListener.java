package XQBHClient.Utils.ThingsOutPCB;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;

public class EventListener implements SerialPortEventListener {
    //1.�������
    SerialPort serialPort = null;
    InputStream inputStream = null;//������
    Thread readThread = null;
    //2.���캯����
    //ʵ�ֳ�ʼ����������ȡ����COM21���򿪴��ڡ���ȡ��������������Ϊ��������¼���������

    public EventListener() throws NoSuchPortException, PortInUseException {
        try {
            //��ȡ���ڡ��򿪴����ڡ���ȡ���ڵ���������
            serialPort = SerialTool.openPort("COM3", 115200);
            inputStream = serialPort.getInputStream();
            //�򴮿�����¼���������
            serialPort.addEventListener(this);
            //���õ��˿��п�������ʱ�����¼��������ñز����١�
            serialPort.notifyOnDataAvailable(true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TooManyListenersException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }finally {
            //  SerialTool.closePort(serialPort);
        }
    }

    //��д�̳еļ���������
    @Override
    public void serialEvent(SerialPortEvent event) {
        //�������ڻ���������ݵ�����
        byte[] cache = new byte[1024];
        //��¼�Ѿ����ﴮ��COM21��δ����ȡ�����ݵ��ֽڣ�Byte������
        int availableBytes = 0;

        //��������ݿ��õ�ʱ�䷢�ͣ���������ݵĶ�д
        if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE){
            try {
                availableBytes = inputStream.available();
                while(availableBytes > 0){
                    inputStream.read(cache);
                    for(int i = 0; i < cache.length && i < availableBytes; i++){
                        //���벢�������
                        System.out.print((char)cache[i]);
                    }
                    availableBytes = inputStream.available();
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws NoSuchPortException, PortInUseException {
        new EventListener();
    }
}
