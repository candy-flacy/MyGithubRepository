package zip.unzip;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class JavaUtilZip {
	/**
	 * 读操作
	 * @param zipPath
	 * @throws Exception
	 */
	public static void readZipFile(String zipPath) throws Exception { 
        ZipFile zf = new ZipFile(zipPath);//用于后面从zip包中读取entrys
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipPath)));  
        ZipEntry ze = null;  
        while ((ze = zis.getNextEntry()) != null) {  
            System.out.println(ze.getName()+"==============读内容"); 
	        BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));  
	        String line = null;  
	        while ((line = br.readLine()) != null) {  
	           System.out.println(line);  
	        }  
	        br.close();  
        }  
        zis.closeEntry();  
        zf.close();
    }  
	
	/**
	 * 写操作
	 * @param zipPath
	 * @param filePath 写操作对应的具体文件
	 * @throws Exception
	 */
	public static void writeZipFile(String zipPath,String filePath) throws Exception {
		File file= new File(filePath);
		File zipFile = new File(zipPath);
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
		zos.putNextEntry(new ZipEntry(file.getName()));
		zos.write("saggadgasdahh".getBytes());
		zos.close();
	}
	
	/**
	 * 压缩单个文件
	 * @param zipPath
	 * @param filePath 所要压缩文件的路径
	 * @throws Exception
	 */
	public static void zipFile(String zipPath,String filePath) throws Exception{
		InputStream is = new FileInputStream(filePath);//读文件
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath));//写到zip中
		zos.putNextEntry(new ZipEntry(new File(filePath).getName()));
		int temp = 0;
		while((temp = is.read()) != -1){
			zos.write(temp);
		}
		is.close();
		zos.close();
	}
	
	/**
	 * 压缩多个文件（文件夹）
	 * @param zipPath
	 * @param filePath 所要压缩文件夹的路径
	 * @throws Exception
	 */
	public static void zipMultiFile(String zipPath,String filePath) throws Exception{
		InputStream is = null;
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath));
		File files = new File(filePath);
		for(File file:files.listFiles()){
			System.out.println(file.getName()+"===========压缩");
			is = new FileInputStream(file);
			zos.putNextEntry(new ZipEntry(file.getName()));
			int temp = 0;
			while((temp = is.read()) != -1){
				zos.write(temp);
			}
		}
		is.close();
		zos.close();
	}
	
	/**
	 * 解压单个文件
	 * @param zipPath
	 * @param destPath 解压后的文件存放路径
	 * @param fileName 所要解压的文件名
	 * @throws Exception
	 */
	public static void unZipFile(String zipPath,String destPath,String fileName) throws Exception{
		File file = new File(zipPath);//压缩文件路径
        File outFile = new File(destPath);//解压后路径
        ZipFile zipFile = new ZipFile(file);
        ZipEntry entry = zipFile.getEntry(fileName);//所解压的文件名
        InputStream input = zipFile.getInputStream(entry);//从zip中读取文件
        OutputStream output = new FileOutputStream(outFile);//写入到指定文件中
        int temp = 0;
        while((temp = input.read()) != -1){
            output.write(temp);
        }
        input.close();
        output.close();
        zipFile.close();
	}
	
	/**
	 * 解压多个文件
	 * @param zipPath
	 * @param destPath 解压后的文件夹存放路径
	 * @throws Exception
	 */
	public static void unZipMultiFile(String zipPath,String destPath) throws Exception{
		 File file = new File(zipPath);
	        File outFile = null;
	        ZipFile zipFile = new ZipFile(file);
	        ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file));
	        ZipEntry entry = null;
	        InputStream input = null;
	        OutputStream output = null;
	        while((entry = zipInput.getNextEntry()) != null){
	            System.out.println(entry.getName()+"==========解压");
	            outFile = new File(destPath + File.separator + entry.getName());
	            if(!outFile.getParentFile().exists()){
	                outFile.getParentFile().mkdir();
	            }
	            if(!outFile.exists()){
	                outFile.createNewFile();
	            }
	            input = zipFile.getInputStream(entry);
	            output = new FileOutputStream(outFile);
	            int temp = 0;
	            while((temp = input.read()) != -1){
	                output.write(temp);
	            }
	            input.close();
	            output.close();
	        }
	        zipInput.close();
	        zipFile.close();
	}
	
	/**
	 * 将内容追加到zip包中  1.解压 2.删除原zip包 3.生成新的zip包
	 * @param zipPath
	 * @param filePath 所要追加到zip包的文件
	 * @throws Exception
	 */
	public static void fileAppendToZip(String zipPath,String filePath) throws Exception{
		File tempFile = new File("D:/Test/temp");
		//解压
		unZipMultiFile(zipPath,tempFile.getAbsolutePath());
		//添加文件到临时文件夹
		File file = new File(filePath);
		file.renameTo(new File(tempFile+File.separator+file.getName()));
		//删除原zip包
		File zipFile = new File(zipPath);
		if(zipFile.exists()&&zipFile.isFile()){
			zipFile.delete();
		}else{
			System.out.println("文件不存在");
		}
		//生成新的zip包
		zipMultiFile("D:/Test/temp.zip",tempFile.getAbsolutePath());
	}
	
	public static void main(String[] args) throws Exception {
		readZipFile("D:/测试/zipTest.zip");
//		writeZipFile("D:/Test/zipTest.zip","4.txt");
//		zipFile("D:/Test/2.zip","D:/Test/2.txt");
//		zipMultiFile("D:/Test/zipTest.zip","D:/Test/zipTest");
//		unZipFile("D:/Test/zipTest.zip","D:/Test/zipTestCCCC.txt","4.txt");
//		unZipMultiFile("D:/Test/zipTest.zip","D:/Test/zipTestCCCC");
//		fileAppendToZip("D:/Test/zipTest.zip","D:/Test/3.txt");
	}
}
