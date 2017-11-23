package XQBHClient.Utils.Updater;

public class AutoUpdateMainPro {

	private FTPClientUtil ftpClient = null;

	public AutoUpdateMainPro(String hostName, int port, String user, String pwd, String remoteRootPath, String localRootPath,boolean flg) {
		
		ftpClient = new FTPClientUtil(hostName, port, user, pwd, remoteRootPath, localRootPath);
		execute( flg);

	}
	private void execute(boolean flg) {

		ftpClient.FTPClientRun(flg);
		// �ͷų���ռ�õ���Դ
		freeResource();

	}
	private void freeResource() {
		ftpClient.freeFTPClient();
	}


}
