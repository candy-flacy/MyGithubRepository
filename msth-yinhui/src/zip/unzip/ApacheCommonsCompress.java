package zip.unzip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

public class ApacheCommonsCompress {
	public static void tarFile(String tarPath,String filePath) throws Exception{
		FileInputStream fis = new FileInputStream(new File(filePath));
		TarArchiveOutputStream taos = new TarArchiveOutputStream(new FileOutputStream(new File(tarPath)));
		TarArchiveEntry entry = new TarArchiveEntry(new File(filePath).getName());  
	    entry.setSize(new File(filePath).length());
		taos.putArchiveEntry(entry); 
		//加密
//		Cipher enCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//	    KeyGenerator kgen = KeyGenerator.getInstance("AES");
//		kgen.init(128, new SecureRandom("111sgdasdhdfhdshsgasdashdsdh".getBytes()));
//		SecretKey secretKey = kgen.generateKey();
//	    enCipher.init(Cipher.ENCRYPT_MODE, secretKey); //secretKey：密钥  Cipher.ENCRYPT_MODE：加密    
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec("01020304".getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		IvParameterSpec iv = new IvParameterSpec("01020304".getBytes());
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey, iv, sr);
		CipherInputStream cis = new CipherInputStream(fis, cipher);
		//通过流的方式将文件读到tar包中
	    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(filePath)));  
	    int count = 0;  
	    byte data[] = new byte[1024];  
	    while ((count = cis.read(data,0,1024)) != -1) {  
	        taos.write(data, 0, count);  
	    } 
	    bis.close();
		//通过文件复制的方式，将文件放到tar包中
//	    IOUtils.copy(fis, taos);
//	    new CipherUtil().encrypt(tarPath,"D:/Test/6.tar","123");
	    taos.flush();
	    taos.closeArchiveEntry();  
	}
	
	public static void unTarFile(String tarPath,String destPath) throws Exception{
		FileOutputStream fis = null;
//		new CipherUtil().decrypt(tarPath, "D:/Test/7.tar","123");
		TarArchiveInputStream taos = new TarArchiveInputStream(new FileInputStream(new File(tarPath)));
		int count;
        byte data[] = new byte[1024];
        TarArchiveEntry te = null;
        SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec("01020304".getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		IvParameterSpec iv = new IvParameterSpec("01020304".getBytes());
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, securekey, iv, sr);
		CipherInputStream cis = null;
        while((te = taos.getNextTarEntry())!=null){
//        	System.out.println(te.isFile());
//        	if(te.isFile()){
//        		System.out.println(te.getName());
//        	}
    	File destFile = new File(destPath);
    	if(!destFile.exists()){
    		destFile.mkdirs();
    	}
    	cis = new CipherInputStream(new FileInputStream(te.getFile()), cipher);
    	fis = new FileOutputStream(destPath+File.separator+te.getName());//注意，此处te.getFile()为空
    	while ((count = cis.read(data, 0, 1024)) != -1) {
        	fis.write(data, 0, count);
        }
        fis.close();
        }
	}
	
	public static void main(String[] args) throws Exception{
//		tarFile("D:/Test/5.tar","D:/Test/5.txt");
		unTarFile("D:/Test/5.tar","D:/Test/5");
	}
}
