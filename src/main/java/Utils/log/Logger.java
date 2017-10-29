package Utils.log;

import Client.Com;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.*;


public class Logger {
    private static final String LOG_FOLDER_NAME = "Log";

    private static final String LOG_FILE_SUFFIX = ".log";
    static int XH = 0;
    private static final MyLogHander myLogHander = new MyLogHander();
    private static final int miss=128;
    public static FileHandler fileHandler=null;
    public static String path="";
    private static ConsoleHandler consoleHandler;
    static{

        consoleHandler = new ConsoleHandler();


    }

    public static void log(String LogLV, String Msg) {

    java.util.logging.Logger log = java.util.logging.Logger.getLogger("myLogger");
        log.setUseParentHandlers(false);
        log.setLevel(Level.FINEST);  //总阀门

        String pathtmp = getLogPath(Msg);
        if(!path.equals(pathtmp))
        {
            if (fileHandler!=null)
                fileHandler.close();
            path=pathtmp;
            try {
                fileHandler = new FileHandler(path, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileHandler.setFormatter(myLogHander);
        }
        if (Com.LogLV.equals("SYS"))
            fileHandler.setLevel(Level.SEVERE);
        else if (Com.LogLV.equals("ERR"))
            fileHandler.setLevel(Level.WARNING);
        else if(Com.LogLV.equals("IO"))
            fileHandler.setLevel(Level.INFO);
        else if(Com.LogLV.equals("DEBUG"))
            fileHandler.setLevel(Level.CONFIG);
        else
            fileHandler.setLevel(Level.FINEST);


        while (log.getHandlers().length > 0)
        {
            //System.out.println(log.getHandlers().length);
            log.removeHandler(log.getHandlers()[0]);
        }
        for(Handler handler : log.getHandlers()) {
            log.removeHandler(handler);
        }

        log.addHandler(fileHandler);


        LogRecord lr;//log的等级
        if("LOG_SYS".equals(LogLV))
          lr = new LogRecord(Level.SEVERE, Msg);
        else if("LOG_ERR".equals(LogLV))
            lr = new LogRecord(Level.WARNING, Msg);
        else if("LOG_IO".equals(LogLV))
            lr = new LogRecord(Level.INFO, Msg);
        else if("LOG_DEBUG".equals(LogLV))
            lr = new LogRecord(Level.CONFIG, Msg);
        else
            lr = new LogRecord(Level.ALL, Msg);


        lr.setParameters(new Object[]{Thread.currentThread().getStackTrace()[2].getClassName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber(),
                LogLV
        });
        consoleHandler.setLevel(Level.WARNING);//console阀门 需实时调整
        log.addHandler(consoleHandler);

        log.log(lr);
    }

    private static String getLogPath(String Msg) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        StringBuffer logFilePath = new StringBuffer();
        logFilePath.append(LOG_FOLDER_NAME);
        logFilePath.append(File.separatorChar);
        logFilePath.append(year);
        logFilePath.append(File.separatorChar);
        logFilePath.append(month);
        logFilePath.append(File.separatorChar);
        logFilePath.append(day);

        File dir = new File(logFilePath.toString());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        StringBuffer tmplogFilePath = new StringBuffer();

        while (true) {
            tmplogFilePath.delete(0, tmplogFilePath.length());
            tmplogFilePath.append(logFilePath.toString());
            tmplogFilePath.append(File.separatorChar);
            tmplogFilePath.append(sdf.format(new Date()));
            tmplogFilePath.append("_");
            tmplogFilePath.append(XH);
            tmplogFilePath.append(LOG_FILE_SUFFIX);

            File file = new File(tmplogFilePath.toString());
            if (file.exists() && file.isFile()) {
//                System.out.println(file);
//                System.out.println(file.length()+Msg);
                if (10240000 < file.length()+Msg.length()+miss) {
                    XH++;

                } else
                    break;
            } else {
                break;
            }
        }
        logFilePath.append(File.separatorChar);
        logFilePath.append(sdf.format(new Date()));
        logFilePath.append("_");
        logFilePath.append(XH);
        logFilePath.append(LOG_FILE_SUFFIX);
        return logFilePath.toString();
    }
}
