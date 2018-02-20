package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.Utils.log.Logger;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipModel {
    private static final int  BUFFER_SIZE = 2 * 1024;
    public static void exec() throws Exception {


        FileOutputStream fos1 = new FileOutputStream(new File(Com.modelZipFile));
        Logger.log("LOG_DEBUG","target zip="+new File(Com.modelZipFile).getAbsolutePath());
        Logger.log("LOG_DEBUG","source ="+new File(Com.modelFile).getAbsolutePath());

        toZip(Com.modelFile, fos1,true);
    }
    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
            throws RuntimeException{

        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
            long end = System.currentTimeMillis();
            Logger.log("LOG_DEBUG","ѹ����ɣ���ʱ��" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    /**
     * �ݹ�ѹ������
     * @param sourceFile Դ�ļ�
     * @param zos		 zip�����
     * @param name		 ѹ���������
     * @param KeepDirStructure  �Ƿ���ԭ����Ŀ¼�ṹ,true:����Ŀ¼�ṹ;
     * 							false:�����ļ��ܵ�ѹ������Ŀ¼��(ע�⣺������Ŀ¼�ṹ���ܻ����ͬ���ļ�,��ѹ��ʧ��)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // ��zip����������һ��zipʵ�壬��������nameΪzipʵ����ļ�������
            zos.putNextEntry(new ZipEntry(name));
            // copy�ļ���zip�������
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // ��Ҫ����ԭ�����ļ��ṹʱ,��Ҫ�Կ��ļ��н��д���
                if(KeepDirStructure){
                    // ���ļ��еĴ���
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // û���ļ�������Ҫ�ļ���copy
                    zos.closeEntry();
                }

            }else {
                for (File file : listFiles) {
                    // �ж��Ƿ���Ҫ����ԭ�����ļ��ṹ
                    if (KeepDirStructure) {
                        // ע�⣺file.getName()ǰ����Ҫ���ϸ��ļ��е����ּ�һб��,
                        // ��Ȼ���ѹ�����оͲ��ܱ���ԭ�����ļ��ṹ,���������ļ����ܵ�ѹ������Ŀ¼����
                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(),KeepDirStructure);
                    }

                }
            }
        }
    }
//    public static void main(String[] args) {
//        try {
//            ZipModel.exec();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
