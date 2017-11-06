package XQBHClient.Utils.QRReader;

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

    public QRReader(String comName, int baudRate, int dataBits, int stopBits, int parity) {
        this.comName = comName;
        this.baudRate = baudRate;
        this.parity = parity;
        this.dataBits = dataBits;
        this.stopBits = stopBits;

        String LIB_BIN = "/lib-bin/";
        String win32com = "win32com";
        String properties = "javax.comm.properties";

        File prop = new File(System.getProperty("java.home") + "/lib/" + properties);
        Logger.log("LOG_DEBUG","target propfile=" + prop.getAbsolutePath());
        if (!prop.exists()) {
            try {
                // have to use a stream
                InputStream in = QRReader.class.getResourceAsStream(LIB_BIN + properties);
                File fileOut = new File(System.getProperty("java.home") + "/lib/" + properties);
                OutputStream out = FileUtils.openOutputStream(fileOut);
                IOUtils.copy(in, out);
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.print("Loading DLL");
        try {
            System.loadLibrary(win32com);
            System.out.print("DLL is loaded from memory");
        } catch (UnsatisfiedLinkError e) {
            loadLib(LIB_BIN, win32com);
        }
    }

    public String getQRCode() {
        String cmdHand = "$108000-ADB0";
        QRReaderUtil reader = new QRReaderUtil();
        try {
            reader.initComm(comName, baudRate, dataBits, stopBits, parity);
        } catch (NoSuchPortException e) {
            e.printStackTrace();
            return "";
        } catch (PortInUseException e) {
            e.printStackTrace();
            return "";

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
            return "";
        }
        Thread readThread = new Thread(reader);
        reader.writeComm(cmdHand);
        readThread.start();
        try {
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return reader.re;
    }

    public static void main(String[] args) {
        QRReader qrReader = new QRReader("COM3", 9600, 8, 1, 0);
        Logger.log("LOG_DEBUG",qrReader.getQRCode());
    }

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
