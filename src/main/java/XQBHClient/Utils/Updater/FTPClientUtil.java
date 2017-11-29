package XQBHClient.Utils.Updater;

import XQBHClient.ClientAPI.WarmingDialog;
import XQBHClient.Utils.log.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FTPClientUtil {
    private static String localPath = null;
    private static String localFileName = null;

    private static String remotePath = null;
    private static String remoteFileName = null;
    private static String hostName = null;
    private static int port = 21;
    private static String user = null;
    private static String pwd = null;
    private static FTPClient ftp = null;

    public static void main(String[] args) {
        FTPClientUtil ftpClient = new FTPClientUtil("119.23.250.79", 21, "ftpreader", "ftp", "Update", "version.txt", "Update/new", "version.txt");
        try {
            ftpClient.FTPClientRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FTPClientUtil(String hostName, int port, String user, String pwd, String remotePath, String remoteFile, String localPath, String localFile) {
        this.remotePath = remotePath;
        this.remoteFileName = remoteFile;

        this.localPath = localPath;
        this.localFileName = localFile;
        this.hostName = hostName;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    public void FTPClientRun() throws Exception {
        getInstance();
        traverse();
    }

    public FTPClient getInstance() {

        ftp = new FTPClient();
        try {
            ftp = new FTPClient();

            ftp.connect(hostName, port);
            ftp.login(user, pwd);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.setControlEncoding("GBK");
            FTPClientConfig config = new FTPClientConfig(FTPClientConfig.SYST_NT);
            config.setServerLanguageCode("en");

        } catch (IOException ex) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "连接主机:" + hostName + "失败!");

        } catch (SecurityException ex) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "用户或者密码可能不对,无权限与主机:" + hostName + "连接!");
        }

        return ftp;
    }

    // 遍历ftp站点资源信息,获取FTP服务器上制定目录的应用程序
    public void traverse() throws Exception {

        File localFile = new File(localPath );
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(localPath+"/"+localFileName);
        ftp.retrieveFile(remotePath+"/"+remoteFileName, out);
        out.flush();
        out.close();
    }

    public void freeFTPClient() {
        try {
            if (ftp != null)
                ftp.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
