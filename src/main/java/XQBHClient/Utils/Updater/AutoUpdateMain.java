package XQBHClient.Utils.Updater;

import XQBHClient.Utils.log.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AutoUpdateMain {


    public static void main(String[] args) {
        AutoUpdateMain autoUpdateMain = new AutoUpdateMain();
        if (true != autoUpdateMain.exec(args))
            return;
    }

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
        Logger.log("LOG_SYS", "搜索FTP上版本控制文件:");
        Logger.log("LOG_DEBUG", "Const.hostName=" + Const.hostName);
        Logger.log("LOG_DEBUG", "Const.port=" + Const.port);
        Logger.log("LOG_DEBUG", "Const.user=" + Const.user);
        Logger.log("LOG_DEBUG", "Const.pwd=" + Const.pwd);
        Logger.log("LOG_DEBUG", "Const.RemotePath=" + Const.RemotePath);
        Logger.log("LOG_DEBUG", "Const.LocalPath=" + Const.LocalPath);

        String oldVersion = getVersionByFile(Const.LocalOld + "/version.txt");
        AutoUpdateMainPro version_down = new AutoUpdateMainPro(Const.hostName, Const.port, Const.user, Const.pwd, Const.RemotePath, "version.txt", Const.LocalPath, "version.txt");
        if (true != version_down.execute()) {
            Logger.log("LOG_DEBUG", "获取版本文件失败");
            return false;
        }
        String newVersion = getVersionByFile(Const.LocalPath + "/version.txt");
        try {
            Logger.log("LOG_SYS", "正在进行版本文件比较...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.log("LOG_SYS", "oldVersion=" + oldVersion);
        Logger.log("LOG_SYS", "newVersion=" + newVersion);
        if (null != newVersion && newVersion.equals(oldVersion)) {
            Logger.log("LOG_SYS", "数据采集程序版本没有更新变动");
            return true;
        }



        //通过指以COMPLETE+日期为指定命令,来更新全部程序
        Logger.log("LOG_SYS", "检测到数据采集程序版本有更新,启动自动升级程序");
        try {
            Logger.log("LOG_SYS", "正在进行版本文件比较...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Logger.log("LOG_DEBUG", "开始更新exe");
                /*下载exe*/
        String exeName = "XQBHClient.exe";
        AutoUpdateMainPro exe_down = new AutoUpdateMainPro(Const.hostName, Const.port, Const.user, Const.pwd, Const.RemotePath, exeName, Const.LocalPath, exeName);
        try {
            exe_down.execute();
        } catch (Exception e) {
            Logger.logException("LOG_ERR",e);
            Logger.log("LOG_DEBUG", "下载文件、程序失败");
            return false;
        }
        Logger.log("LOG_DEBUG", "exe更新完毕");

        Logger.log("LOG_DEBUG", "开始更新bat");
                /*下载bat*/
        String batName = "run.bat";
        AutoUpdateMainPro bat_down = new AutoUpdateMainPro(Const.hostName, Const.port, Const.user, Const.pwd, Const.RemotePath, batName, Const.LocalPath, batName);
        try {
            bat_down.execute();
        } catch (Exception e) {
            Logger.logException("LOG_ERR",e);
            Logger.log("LOG_DEBUG", "下载文件、程序失败");
            return false;
        }
        Logger.log("LOG_DEBUG", "bat更新完毕");

        try {
            Files.copy(new File(Const.LocalPath + "/version.txt" ).toPath(), new File(Const.LocalOld + "/version.txt" ).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.log("LOG_SYS", "update over");


        Logger.log("LOG_SYS", "begin restart...");
        try {
            Logger.log("LOG_SYS", "更新运行完毕!启动数据采集程序退出...");
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
