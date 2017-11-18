package XQBHClient.Utils.QRReader;

import XQBHClient.ClientAPI.warmingDialog;
import XQBHClient.Utils.log.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class QRReader {
    public String comName;
    public int baudRate;  //波特率

    public int dataBits;  //数据位
    public int stopBits;  //停止位
    public int parity;    //校验位
    public static int timeOut = 9000;  //超时时间
    public static int frequency = 500;
    QRReaderUtil reader;


    public QRReader(String comName, int baudRate, int dataBits, int stopBits, int parity)  {
        this.comName = comName;
        this.baudRate = baudRate;
        this.parity = parity;
        this.dataBits = dataBits;
        this.stopBits = stopBits;


    }
    public boolean Init() throws PortInUseException, UnsupportedCommOperationException, NoSuchPortException, IOException{
        String LIB_BIN = "/lib-bin/";
        String win32com = "win32com";
        String properties = "javax.comm.properties";

        File prop = new File(System.getProperty("java.home") + "/lib/" + properties);
        Logger.log("LOG_DEBUG", "target propfile=" + prop.getAbsolutePath());
        if (!prop.exists()) {
            // have to use a stream
            InputStream in = QRReader.class.getResourceAsStream(LIB_BIN + properties);
            File fileOut = new File(System.getProperty("java.home") + "/lib/" + properties);
            OutputStream out = FileUtils.openOutputStream(fileOut);
            IOUtils.copy(in, out);
            in.close();
            out.close();

        }

        Logger.log("LOG_DEBUG","Loading DLL");
        try {
            System.loadLibrary(win32com);
            Logger.log("LOG_DEBUG","DLL is loaded from memory");
        } catch (UnsatisfiedLinkError e) {
            loadLib(LIB_BIN, win32com);
        }
        reader = new QRReaderUtil();
        reader.initComm(comName, baudRate, dataBits, stopBits, parity);

        return true;
    }


    public String getQRCode() {
        String cmdHand = "$108000-ADB0";
        Thread readThread = new Thread(reader);
        reader.writeComm(cmdHand);
        readThread.start();
        try {
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        reader.re=reader.re.trim();
        int iLength=reader.re.length();
        if (iLength>0&&iLength%2==0)//默认QRCODE大于0位
        {
            int iTemp=iLength/2;
            if (reader.re.substring(0,iTemp).equals(reader.re.substring(iTemp,iLength)))
            {
                reader.re=reader.re.substring(0,iTemp);
            }
        }
        return reader.re.trim();
    }

//    public static void main(String[] args) {
//        QRReader qrReader = new QRReader("COM3", 9600, 8, 1, 0);
//        Logger.log("LOG_DEBUG",qrReader.getQRCode());
//    }

    private void loadLib(String LIB_BIN, String win32com) {
        try {
            // have to use a stream
            InputStream in = QRReader.class.getResourceAsStream(LIB_BIN + win32com + ".dll");
            File fileOut = new File(System.getProperty("java.home") + "/bin/" + win32com + ".dll");
            OutputStream out = FileUtils.openOutputStream(fileOut);
            IOUtils.copy(in, out);
            in.close();
            out.close();
            System.load(fileOut.toString());
        } catch (Exception e) {

        }


    }


}
