package XQBHClient.Utils.Updater;

import XQBHClient.Utils.log.Logger;

public class AutoUpdateMainPro {

	private FTPClientUtil ftpClient = null;

	public AutoUpdateMainPro(String hostName, int port, String user, String pwd,String remotePath, String remoteFile, String localPath, String localFile)  {
		
		ftpClient = new FTPClientUtil( hostName,  port,  user,  pwd,  remotePath,  remoteFile,  localPath,  localFile);

	}
	public boolean execute()  {

		try {
			ftpClient.FTPClientRun();
		} catch (Exception e) {
			Logger.logException("LOG_ERR",e);
			return false;
		}
//		finally {
			freeResource();
//		}
		// 释放程序占用的资源
		return true;
	}
	public void freeResource() {
		ftpClient.freeFTPClient();
	}


}
