package XQBHClient.Utils.Modbus;

import XQBHClient.ClientUI.ClientUIMain;
import XQBHClient.ClientUI.Order;
import XQBHClient.Utils.log.Logger;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.ModbusSlaveException;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.net.TCPMasterConnection;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ModbusUtil {
    public static int adress;

    /**
     * 写入数据到真机的DO类型的寄存器上面
     *
     * @param ip
     * @param port
    \     * @param address
     */
    public static void doThingsOut(String ip, int port,
                                   int address) throws Exception {

        ModbusUtil.adress=address;
        InetAddress addr = InetAddress.getByName(ip);

        TCPMasterConnection connection = new TCPMasterConnection(addr);
        connection.setPort(port);
        connection.connect();
        ModbusTCPTransaction trans = new ModbusTCPTransaction(connection);
        MyModbusRequest myModbusRequest=new MyModbusRequest();
        trans.setRequest(myModbusRequest);
        trans.execute();
        connection.close();
        ModbusUtil.adress=-1;


    }
    public static void main(String[] args) {
        try {
            ModbusUtil.doThingsOut("192.168.31.177",8080,60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
class MyModbusRequest extends ModbusRequest {
    public MyModbusRequest() {
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
        dataOutput.writeShort(ModbusUtil.adress*5);

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


