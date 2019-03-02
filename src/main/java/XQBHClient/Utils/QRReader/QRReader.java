package XQBHClient.Utils.QRReader;

import XQBHClient.Client.Com;
import XQBHClient.Utils.log.Logger;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

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
    public static boolean continue_read;
    public static final String start_cmd = "$108000-ADB0";
    public static final String stop_cmd =  "$108003-F8E3";
    QRReaderUtil readerUtil;


    public QRReader(String comName, int baudRate, int dataBits, int stopBits, int parity) {
        this.comName = comName;
        this.baudRate = baudRate;
        this.parity = parity;
        this.dataBits = dataBits;
        this.stopBits = stopBits;


    }

    public boolean Init() throws IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        String LIB_BIN = "/lib-bin/";
        String win32com = "win32com";
        String properties = "javafx.properties";
        continue_read = true;

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

        Logger.log("LOG_DEBUG", "Loading DLL");
        try {
            System.loadLibrary(win32com);
            Logger.log("LOG_DEBUG", "DLL is loaded from memory");
        } catch (UnsatisfiedLinkError e) {
            loadLib(LIB_BIN, win32com);
        }
        readerUtil = new QRReaderUtil();
        readerUtil.initComm(comName, baudRate, dataBits, stopBits, parity);

        return true;
    }

    public static void stopRead() {
        continue_read = false;
    }

    public String getQRCode() {
        Logger.log("LOG_DEBUG", Com.QRReaderComName);
        Thread readThread = new Thread(readerUtil);
        readerUtil.writeComm(start_cmd);
        readThread.start();
        try {
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        continue_read = true;
        readerUtil.re = readerUtil.re.trim();
        int iLength = readerUtil.re.length();
        if (iLength > 0 && iLength % 2 == 0)//默认QRCODE大于0位
        {
            int iTemp = iLength / 2;
            if (readerUtil.re.substring(0, iTemp).equals(readerUtil.re.substring(iTemp, iLength))) {
                readerUtil.re = readerUtil.re.substring(0, iTemp);
            }
        }
        return readerUtil.re.trim();
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

//    public static void main(String[] args) {
//
//        QRReader qrReader = new QRReader("/dev/ttyUSB0", 9600, 8, 1, 0);
//        boolean initOK = false;
//        try {
//            initOK = qrReader.Init();
//        } catch (PortInUseException e) {
//            WarmingAction.show(WarmingAction.Dialog_ERR, "QRReader异常,请联系管理员!!!");
//            e.printStackTrace();
//        } catch (IOException e) {
//            WarmingAction.show(WarmingAction.Dialog_ERR, "QRReader读写错误!!!请联系管理员");
//            e.printStackTrace();
//        } catch (UnsupportedCommOperationException e) {
//            WarmingAction.show(WarmingAction.Dialog_ERR, "QRReader操作指令错误!!!请联系管理员");
//            e.printStackTrace();
//        } catch (NoSuchPortException e) {
//            WarmingAction.show(WarmingAction.Dialog_ERR, "QRReader端口miss!!!请联系管理员");
//            e.printStackTrace();
//        }
//        Order.QRCODE = qrReader.getQRCode();
//        System.out.println("qr=" + Order.QRCODE);
//
//    }


}
