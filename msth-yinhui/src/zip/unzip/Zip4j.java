package zip.unzip;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Zip4j {
	public static void encrypt(String zipPath,String filePath,String pwd) throws Exception{
		ZipFile zipFile = new ZipFile(zipPath);
		ArrayList filesToAdd = new ArrayList();
		filesToAdd.add(new File(filePath));

		ZipParameters parameters = new ZipParameters();  
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);  
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
//		zipFile.createZipFile(new File(filePath), parameters, true, 65536);可分卷压缩

		//设置密码
		parameters.setEncryptFiles(true);  
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);   
		parameters.setPassword(pwd);  
		zipFile.addFiles(filesToAdd, parameters);
	}
	
	public static void decrypt(String zipPath,String destPath,String pwd) throws Exception{
		ZipFile zipFile = new ZipFile(zipPath);
		if(zipFile.isEncrypted()){
			zipFile.setPassword(pwd);
		}
		zipFile.extractAll(destPath);
	}
	
	public static void read(String zipPath) throws Exception{
		ZipFile zf = new ZipFile(zipPath);
		List fHeaders = zf.getFileHeaders();
		for(Object fHeader:fHeaders){
			FileHeader fh = (FileHeader)fHeader;
			fh.setFileNameUTF8Encoded(true);
			System.out.println(fh.getFileName());
			BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(fh)));  
	        String line = null;  
	        while ((line = br.readLine()) != null) {  
	           System.out.println(line);  
	        }  
	        br.close();  
		}
	}
	
	public static void main(String[] args) throws Exception {
		encrypt("D:/测试/1.zip","D:/测试/1.doc","111");
//		decrypt("D:/Test/4.zip","D:/Test/1","111");
//		read("D:/测试/zipTest.zip");
	}
}
