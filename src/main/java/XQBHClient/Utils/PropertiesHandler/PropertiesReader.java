package XQBHClient.Utils.PropertiesHandler;

import XQBHClient.Utils.log.Logger;

import java.io.*;
import java.net.URI;
import java.util.Properties;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class PropertiesReader {
    public static String readKeyFromXML(String path, String Key){
        Properties propertie;
        InputStream inputStream=Class.class.getResourceAsStream(path);


        propertie = new Properties();
        try{
            propertie.load(inputStream);
//            propertie.loadFromXML(inputFile);//读取XML文件
            inputStream.close();
        } catch (FileNotFoundException ex){
            Logger.log("LOG_ERR","文件"+path+"无法找到");
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.log("LOG_ERR","读取文件"+path+"失败");
            ex.printStackTrace();
        }
        return propertie.getProperty(Key);
    }
//
//    public static void main(String[] args) {
//        System.out.println(readKeyFromXML(new File("resources/userInfo.properties") ,"ZDJYM_"));
//    }
}
