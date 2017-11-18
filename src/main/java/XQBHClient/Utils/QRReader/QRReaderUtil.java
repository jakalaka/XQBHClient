package XQBHClient.Utils.QRReader;


import XQBHClient.Utils.log.Logger;

import javax.comm.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class QRReaderUtil implements Runnable {
    static CommPortIdentifier portId;
    static SerialPort serialPort;
    static OutputStream outputStream;
    String re = "";

    InputStream inputStream;
    Thread readThread;

    public void run() {
        boolean finish = false;
        int count = QRReader.timeOut / QRReader.frequency;
        int i = 0;
        while (!finish) {
            Logger.log("LOG_DEBUG", "get");
            try {
                byte[] readBuffer = new byte[128];

                try {
                    while (inputStream.available() > 0) {
                        int numBytes = inputStream.read(readBuffer);
                        re = new String(readBuffer);
                        finish = true;
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Thread.sleep(QRReader.frequency);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            if (i >= count)
                break;
        }
        serialPort.close();
    }


    public void initComm(String comName, int baudRate, int dataBits, int stopBits, int parity) throws NoSuchPortException, PortInUseException, IOException, UnsupportedCommOperationException {

        portId = CommPortIdentifier.getPortIdentifier(comName);
//            if (serialPort==null)
        serialPort = (SerialPort) portId.open("XQBHClient", 3000);


        outputStream = serialPort.getOutputStream();
        inputStream = serialPort.getInputStream();

        serialPort.setSerialPortParams(baudRate,
                dataBits,
                stopBits,
                parity);


    }

    public void writeComm(String cmd) {
        try {
            outputStream.write(cmd.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
