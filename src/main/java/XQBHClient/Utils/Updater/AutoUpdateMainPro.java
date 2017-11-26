package XQBHClient.Utils.Updater;

import XQBHClient.Utils.log.Logger;

public class AutoUpdateMainPro {

	private FTPClientUtil ftpClient = null;

	public AutoUpdateMainPro(String hostName, int port, String user, String pwd, String remoteRootPath, String localRootPath)  {
		
		ftpClient = new FTPClientUtil(hostName, port, user, pwd, remoteRootPath, localRootPath);

	}
	public boolean execute(boolean flg)  {

		try {
			ftpClient.FTPClientRun(flg);
		} catch (Exception e) {
			Logger.log("LOG_DEBUG",e.toString());
			return false;
		}finally {
			freeResource();
		}
		// 释放程序占用的资源
		return true;
	}
	public void freeResource() {
		ftpClient.freeFTPClient();
	}


}
