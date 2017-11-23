package XQBHClient.Utils.Updater;

import XQBHClient.Utils.log.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FTPClientUtil {
    private static String localRootPath = null;
    private static String remoteRootPath = null;
    private static String hostName = null;
    private static int port = 21;
    private static String user = null;
    private static String pwd = null;
    private static FTPClient ftp = null;

    public FTPClientUtil(String hostName, int port, String user, String pwd, String remoteRootPath, String localRootPath) {
        this.localRootPath = localRootPath;
        this.remoteRootPath = remoteRootPath;
        this.hostName = hostName;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    public void FTPClientRun(boolean flg) {
        getInstance();
        String path = remoteRootPath;
        traverse(hostName, ftp, path, flg);
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
            Logger.log("LOG_SYS","��������:" + hostName + "ʧ��!");
        } catch (SecurityException ex) {
            Logger.log("LOG_SYS","�û�����������ܲ��ԣ���Ȩ��������:" + hostName + "����!");
        }
        return ftp;
    }

    // ����ftpվ����Դ��Ϣ,��ȡFTP���������ƶ�Ŀ¼��Ӧ�ó���
    public void traverse(String host, FTPClient client, String path, boolean flg) {
        String prefix = "";
        try {
            Logger.log("LOG_SYS","��ǰĿ¼:"+path);
            client.changeWorkingDirectory(path);
            client.enterLocalPassiveMode();
            FTPFile[] files = client.listFiles(path);
            for (int i = 0; i < files.length; i++) {
                // ������ļ��о͵ݹ鷽����������

                if (files[i].isDirectory()) {

					/*
                     * ������Ŀ¼ʱ���Զ����������ļ���: . �� .. ��ָ��ǰĿ¼ ���ָ��Ŀ¼
					 */
                    // ע��������жϣ�����������ѭ��
                    if (!files[i].getName().equals(".") && !files[i].getName().equals("..")) {
                        String tempDir = client.printWorkingDirectory() + "/" + files[i].getName();
                        File localFile = new File(localRootPath + tempDir.replace(remoteRootPath, "/"));
                        if (!localFile.exists()) {
                            localFile.mkdirs();
                        }
                        client.changeWorkingDirectory(tempDir);
                        // ���ļ��о͵ݹ����
                        traverse(host, client, tempDir, flg);
                        prefix += client.printWorkingDirectory();
                        client.changeToParentDirectory();
                    }
                    // ������ļ���ɨ����Ϣ
                } else {
                    if (!flg && files[i].getName().equals("version.txt")) {
                        String temp = client.printWorkingDirectory();
                        String RemFileName = files[i].getName();
                        String tempPath = temp.replaceAll(remoteRootPath, "") + "/" + files[i].getName();
                        Logger.log("LOG_SYS","��ʼ���� ��" + tempPath);
                        File dir = new File(localRootPath + temp.replaceAll(remoteRootPath, ""));
                        if (!dir.exists())
                            dir.mkdir();
                        String filepath = localRootPath + tempPath;
                        try {
                            File localFile = new File(filepath);

                            Logger.log("LOG_SYS","��:"+localFile.toString());
                            if (!localFile.exists()) {
                                localFile.createNewFile();
                            }
                            FileOutputStream out = new FileOutputStream(localFile);
                            client.retrieveFile(files[i].getName(), out);
                            out.flush();
                            out.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else if (flg && (files[i].getName().equals(Const.programName) || files[i].getName().equals("run.bat"))) {
                        String temp = client.printWorkingDirectory();
                        String RemFileName = files[i].getName();
                        String tempPath = temp.replaceAll(remoteRootPath, "") + "/" + files[i].getName();
                        Logger.log("LOG_SYS","��ʼ���� ��" + tempPath);
                        String filepath = localRootPath + tempPath;

                        try {
                            File localFile = new File(filepath);
                            Logger.log("LOG_SYS","��:"+localFile.toString());
                            if (!localFile.exists()) {
                                localFile.createNewFile();
                            }
                            FileOutputStream out = new FileOutputStream(localFile);
                            client.retrieveFile(files[i].getName(), out);
                            out.flush();
                            out.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }else {

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
