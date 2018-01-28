package XQBHClient.Utils.Modbus;

import XQBHClient.Utils.log.Logger;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.net.TCPMasterConnection;

import java.io.*;
import java.net.InetAddress;

public class ModbusUtil {

    public static int adress;

    /**
     * 执行出货
     *
     * @param ip
     * @param port \     * @param address
     */
    public static void doThingsOut(String ip, int port,
                                   int address) throws Exception {

        String host = ip;
        int timeOut = 3000;
        boolean status = InetAddress.getByName(host).isReachable(timeOut);
        if (!status)
            throw new Exception();

        ModbusUtil.adress = address;
        InetAddress addr = InetAddress.getByName(ip);

        TCPMasterConnection connection = new TCPMasterConnection(addr);
        connection.setPort(port);
        connection.connect();
        ModbusTCPTransaction trans = new ModbusTCPTransaction(connection);
        MyModbusThingsOutRequest myModbusThingsOutRequest = new MyModbusThingsOutRequest();
        Logger.log("LOG_DEBUG", myModbusThingsOutRequest.getHexMessage());
        trans.setRequest(myModbusThingsOutRequest);
        trans.execute();
        connection.close();
        ModbusUtil.adress = -1;


    }

    /**
     * 检查对应出货装置是否联通
     *
     * @param ip
     * @param port \     * @param address
     */
    public static void doCheck(String ip, int port,
                               int address) throws Exception {

        String host = ip;
        int timeOut = 3000; //超时应该在3钞以上
        boolean status = InetAddress.getByName(host).isReachable(timeOut);
        if (!status)
            throw new Exception();


        ModbusUtil.adress = address;
        InetAddress addr = InetAddress.getByName(ip);

        TCPMasterConnection connection = new TCPMasterConnection(addr);
        connection.setPort(port);


        connection.connect();
        Logger.log("LOG_DEBUG","connected");

        ModbusTCPTransaction trans = new ModbusTCPTransaction(connection);
        MyModbusCheckRequest myMyModbusCheckRequest = new MyModbusCheckRequest();
        Logger.log("LOG_DEBUG", myMyModbusCheckRequest.getHexMessage());
        trans.setRequest(myMyModbusCheckRequest);
        trans.execute();
        connection.close();
        ModbusUtil.adress = -1;


    }

    public static void main(String[] args) {
//        try {
//            ModbusUtil.doThingsOut("192.168.31.177",8080,3);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            ModbusUtil.doCheck("192.168.31.177", 8080, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class MyModbusThingsOutRequest extends ModbusRequest {
    public MyModbusThingsOutRequest() {
        this.setFunctionCode(16);
        this.setDataLength(9);
        this.setUnitID(254);
    }

    @Override
    public ModbusResponse createResponse() {
        return null;
    }

    @Override
    public void writeData(DataOutput dataOutput) throws IOException {
//        dataOutput.write(00);//地址 00
        dataOutput.writeShort(ModbusUtil.adress * 5);

        dataOutput.write(00);//命令个数
        dataOutput.write(02);

        dataOutput.write(04);//字节数

        dataOutput.write(00);//闪开闪闭
        dataOutput.write(02);

        dataOutput.write(00); //延时时间
        dataOutput.write(5);
    }

    @Override
    public void readData(DataInput dataInput) throws IOException {
        int re = dataInput.readUnsignedShort();


        dataInput.readByte();
    }


}


class MyModbusCheckRequest extends ModbusRequest {
    public MyModbusCheckRequest() {
        this.setFunctionCode(1);
        this.setDataLength(4);
        this.setUnitID(254);
    }

    @Override
    public ModbusResponse createResponse() {
        return null;
    }

    @Override
    public void writeData(DataOutput dataOutput) throws IOException {
//        dataOutput.write(00);//地址 00
        dataOutput.writeShort(ModbusUtil.adress);

        dataOutput.write(00);
        dataOutput.write(01);

    }

    @Override
    public void readData(DataInput dataInput) throws IOException {
        int re = dataInput.readUnsignedShort();


        dataInput.readByte();
    }


}