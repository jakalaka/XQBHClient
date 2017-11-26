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

    public boolean exec(String[] args) {
        Logger.log("LOG_SYS", "自动升级程序启动...");
        // 关闭正在运行的Java应用程序
        String oldVersion = getVersionByFile(Const.oldversionPath);
        Logger.log("LOG_SYS", "搜索FTP上版本控制文件:");
        Logger.log("LOG_DEBUG", "Const.hostName=" + Const.hostName);
        Logger.log("LOG_DEBUG", "Const.port=" + Const.port);
        Logger.log("LOG_DEBUG", "Const.user=" + Const.user);
        Logger.log("LOG_DEBUG", "Const.pwd=" + Const.pwd);
        Logger.log("LOG_DEBUG", "Const.RemotePath=" + Const.RemotePath);
        Logger.log("LOG_DEBUG", "Const.LocalPath=" + Const.LocalPath);

        AutoUpdateMainPro autoUpdateMainPro_1 = new AutoUpdateMainPro(Const.hostName, Const.port, Const.user, Const.pwd, Const.RemotePath, Const.LocalPath);
        if (true!=autoUpdateMainPro_1.execute(false))
        {
            Logger.log("LOG_DEBUG", "获取版本文件失败");
            return false;
        }
        String newVersion = getVersionByFile(Const.newversionPath);
        try {
            Logger.log("LOG_SYS", "正在进行版本文件比较...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.log("LOG_SYS", "oldVersion=" + oldVersion);
        Logger.log("LOG_SYS", "newVersion=" + newVersion);

        if (oldVersion != null && newVersion != null) {
            if (!newVersion.equals(oldVersion)) {
                //通过指以COMPLETE+日期为指定命令,来更新全部程序
                Logger.log("LOG_SYS", "检测到数据采集程序版本有更新,启动自动升级程序");
                try {
                    Logger.log("LOG_SYS", "正在进行版本文件比较...");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AutoUpdateMainPro autoUpdateMainPro_2 =new AutoUpdateMainPro(Const.hostName, Const.port, Const.user, Const.pwd, Const.RemotePath, Const.LocalPath);

                try {
                    autoUpdateMainPro_2.execute(true);
                } catch (Exception e) {
                    Logger.log("LOG_DEBUG", e.toString());
                    Logger.log("LOG_DEBUG", "下载文件、程序失败");
                    return false;
                }
                try {
                    Files.copy(new File(Const.newversionPath).toPath(), new File(Const.oldversionPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Logger.log("LOG_SYS", "update over");

            } else {
                Logger.log("LOG_SYS", "数据采集程序版本没有更新变动");
                return true;
            }
        } else {
            //版本控制文件被破坏,自动更新程序下载最新程序,并修复版本控制文件
            Logger.log("LOG_SYS", "本地数据采集程序被破坏,自动下载最新程序,并修复损坏的程序文件");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AutoUpdateMainPro autoUpdateMainPro_3=new AutoUpdateMainPro(Const.hostName, Const.port, Const.user, Const.pwd, Const.RemotePath, Const.LocalPath);

            try {
                autoUpdateMainPro_3.execute(true);
            }catch (Exception e) {
                Logger.log("LOG_DEBUG", e.toString());
                Logger.log("LOG_DEBUG", "下载文件、程序失败");
                return false;
            }

            try {
                Files.copy(new File(Const.newversionPath).toPath(), new File(Const.oldversionPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Logger.log("LOG_SYS", "update over");
        }
        try {
            Logger.log("LOG_SYS", "更新运行完毕！启动数据采集程序退出...");
            Thread.sleep(3000);
            // 启动关闭的Java应用程序
            Logger.log("LOG_SYS", "启动关闭的Java应用程序");
            runbat();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
        return true;
    }


    public static void runbat() {
        File file = new File(Const.LocalPath + "/run.bat");
        Logger.log("LOG_SYS", "准备执行更新文件:" + file.getAbsolutePath());
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
        Logger.log("LOG_SYS", "准备重新启动...");
    }
}
