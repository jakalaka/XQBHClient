package XQBHClient.Utils.FinishComListener;

import XQBHClient.Client.Com;
import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.log.Logger;

import javax.comm.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

/**
 * Com21EventListener��ʹ�á��¼�����ģʽ����������COM21��
 * ��ͨ��COM21����������������ȡ�ö˿ڽ��յ������ݣ��ڱ������������Դ���COM11����
 * ʹ�á��¼�����ģʽ���������ڣ������ֶ���һ���¼������࣬����ʵ��SerialPortEventListener
 * �ӿڲ���дserialEvent��������serialEvent�����б�д�����߼���
 */
public class FinishComListener implements SerialPortEventListener {

    //1.�������
    CommPortIdentifier com21 = null;//δ�򿨵Ķ˿�
    static SerialPort serialComPort = null;//�򿪵Ķ˿�
    InputStream inputStream = null;//������
    public  static boolean isClose;
    public  static boolean subIsClose;

    //2.���캯����
    //ʵ�ֳ�ʼ����������ȡ����COM21���򿪴��ڡ���ȡ��������������Ϊ��������¼���������
    public FinishComListener(){
        try {
            //��ȡ���ڡ��򿪴����ڡ���ȡ���ڵ���������
            com21 = CommPortIdentifier.getPortIdentifier(Com.FinishScannerComName);
            serialComPort = (SerialPort) com21.open("FinishComListener", 1000);
            inputStream = serialComPort.getInputStream();

            //�򴮿�����¼���������
            serialComPort.addEventListener(this);
            //���õ��˿��п�������ʱ�����¼��������ñز����١�
            serialComPort.notifyOnDataAvailable(true);
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TooManyListenersException e) {
            e.printStackTrace();
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
            if (!isClose) {
                isClose=true;
            }
            subIsClose=true;

        }
    }

    //��main�����д������ʵ��
    public static void start() {
        new FinishComListener();
//        wirte();
        Closer closer=new Closer();
        Thread t=new Thread(closer);
        t.start();
        while (true) {
            wirte();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void wirte(){
        CommPortIdentifier com11 = null;//���ڼ�¼���ش���
        SerialPort serialCom11 = null;//���ڱ�ʶ�򿪵Ĵ���

        try {
            //2.��ȡCOM11��

            //3.��COM11
            serialCom11 = serialComPort;

            //4.������д���ݣ�ʹ�ô��ڶ�Ӧ�����������
            //4.1.��ȡ���ڵ����������
            OutputStream outputStream = serialCom11.getOutputStream();

            //4.2.ͨ�����ڵ�������򴮿�д���ݡ�Hello World!����
            //ʹ�������������д���ݵ�ʱ����뽫����ת��Ϊbyte�����ʽ��int��ʽ��
            //����һ�����ڽ��յ�����֮���ٸ���˫��Լ���Ĺ��򣬶����ݽ��н��롣
            outputStream.write(new byte[]{'H','e','l','l','o',
                    ' ','W','o','r','l','d','!'});


        }  catch (IOException e) {
            //�����ȡ�����ʧ�ܣ����׳����쳣
            e.printStackTrace();
        }
    }

}

class Closer implements  Runnable{
    @Override
    public void run() {
        while (true)
        {
            if(FinishComListener.subIsClose==false) {
                if(FinishComListener.isClose == true) {
                    if (!Order.finalOut) {
                        Logger.log("LOG_DEBUG", "�ѳ���");
                        Order.finalOut = true;
                    }else {
                        Logger.log("LOG_ERR", "δ��⵽���򼴳���������");
                        Com.ZDZT_U="ERR_OutAgain";
                        /*������Ҫ�����Ͽ����ʹ��ĵ�Դ*/

                    }
                }
                FinishComListener.isClose = false;
            }
            FinishComListener.subIsClose=false;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}