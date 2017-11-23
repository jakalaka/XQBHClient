package XQBHClient.Utils.Updater;

import XQBHClient.Utils.log.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AutoUpdateMain {


    private static String getVersionByFile(String path) {
        String version = null;
        byte[] b = new byte[1024];
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return null;
        }
        try {
            FileInputStream in = new FileInputStream(file);
            DataInputStream dIn = new DataInputStream(in);

            while (dIn.read(b) > 0) {
                String res = new String(b, "GBK");
                version = res;
            }
            dIn.close();
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static void exec(String[] args) {
        Logger.log("LOG_SYS","�Զ�������������...");
        // �ر��������е�JavaӦ�ó���
        String oldVersion = getVersionByFile(Const.oldversionPath);
        Logger.log("LOG_SYS","����FTP�ϰ汾�����ļ�:");
        new AutoUpdateMainPro(Const.hostName, Const.port, Const.user, Const.pwd, Const.RemoteVersion, Const.LocalVersion, false);
        String newVersion = getVersionByFile(Const.newversionPath);
        try {
            Logger.log("LOG_SYS","���ڽ��а汾�ļ��Ƚ�...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.log("LOG_SYS","oldVersion=" + oldVersion);
        Logger.log("LOG_SYS","newVersion=" + newVersion);

        if (oldVersion != null && newVersion != null) {
            if (!newVersion.equals(oldVersion)) {
                //ͨ��ָ��COMPLETE+����Ϊָ�����������ȫ������
                    Logger.log("LOG_SYS","��⵽���ݲɼ�����汾�и��£������Զ���������");
                    try {
                        Logger.log("LOG_SYS","���ڽ��а汾�ļ��Ƚ�...");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    new AutoUpdateMainPro(Const.hostName, Const.port, Const.user, Const.pwd, Const.RemoteVersion, Const.LocalVersion, true);
                    try {
                        Files.copy(new File(Const.newversionPath).toPath(), new File(Const.oldversionPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Logger.log("LOG_SYS","update over");

            } else {
                Logger.log("LOG_SYS","���ݲɼ�����汾û�и��±䶯");
                return;
            }
        } else {
            //�汾�����ļ����ƻ����Զ����³����������³��򣬲��޸��汾�����ļ�
            Logger.log("LOG_SYS","�������ݲɼ������ƻ����Զ��������³��򣬲��޸��𻵵ĳ����ļ�");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new AutoUpdateMainPro(Const.hostName, Const.port, Const.user, Const.pwd, Const.RemoteVersion, Const.LocalVersion, true);

            try {
                Files.copy(new File(Const.newversionPath).toPath(), new File(Const.oldversionPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Logger.log("LOG_SYS","update over");
        }
        try {
            Logger.log("LOG_SYS","����������ϣ��������ݲɼ������˳�...");
            Thread.sleep(3000);
            // �����رյ�JavaӦ�ó���
            Logger.log("LOG_SYS","�����رյ�JavaӦ�ó���");
            runbat();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }


    public static void runbat() {
        File file = new File(Const.LocalVersion + "/run.bat");
        Logger.log("LOG_SYS","׼��ִ�и����ļ�:"+file.getAbsolutePath());
        String cmd = "cmd /c start " + file.getAbsolutePath();// pass
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            ps.waitFor();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Logger.log("LOG_SYS","׼����������...");
    }
}
