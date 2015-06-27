package zip.unzip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.apache.tools.tar.TarOutputStream;


public class ApacheAnt {
	public static void read(String zipPath) throws Exception{
//		ZipFile zf = new ZipFile(zipPath);//用于后面从zip包中读取entrys
//        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipPath)));  
//        ZipEntry ze = null;  
//        while ((ze = zis.getNextEntry()) != null) {  
//            System.out.println(ze.getName()+"==============读内容"); 
//	        BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));  
//	        String line = null;  
//	        while ((line = br.readLine()) != null) {  
//	           System.out.println(line);  
//	        }  
//	        br.close();  
//        }  
//        zis.closeEntry();  
//        zf.close();
		TarInputStream tis = new TarInputStream(new FileInputStream(new File(zipPath)));
		TarEntry te = null;
		while((te = tis.getNextEntry())!=null){
			System.out.println(te.getName());
			tis = new TarInputStream(new FileInputStream(te.getName())); 
			BufferedReader br = new BufferedReader(new InputStreamReader(tis));  
	        String line = null;  
	        while ((line = br.readLine()) != null) {  
	           System.out.println(line);  
	        }  
	        br.close(); 
		}
		tis.close();
	}
	
	public static void tarFile(String tarPath,String filePath) throws Exception{
		InputStream is = new FileInputStream(filePath);//读文件
		TarOutputStream tos = new TarOutputStream(new FileOutputStream(tarPath));//写到zip中
		tos.putNextEntry(new TarEntry(new File(filePath).getName()));
		int temp = 0;
		while((temp = is.read()) != -1){
			tos.write(temp);
		}
		is.close();
		tos.close();
	}
	
	/**
	* 打zip压缩包
	* src要压缩的路径
	* 压缩文件file
	*/

    public static void compressZip(String srcPathName,File zipFile) {  
        File srcdir = new File(srcPathName);  
        if (!srcdir.exists()){  
            throw new RuntimeException(srcPathName + "不存在！");  
        }
        Project prj = new Project();  
        Zip zip = new Zip();  
        zip.setEncoding("GBK");//设置编码，防止压缩文件名字乱码，还有被压缩文件的乱码
        zip.setProject(prj);  
        zip.setDestFile(zipFile);  
        FileSet fileSet = new FileSet();  
        fileSet.setProject(prj);  
        fileSet.setDir(srcdir);  
        //fileSet.setIncludes("**/*.java"); 包括哪些文件或文件夹 eg:zip.setIncludes("*.java");  
        //fileSet.setExcludes(...); 排除哪些文件或文件夹  
        zip.addFileset(fileSet); 
        zip.execute();  //执行生成
    }
	    
	
	public static void main(String[] args) throws Exception {
		read("D:/Test/5.tar");
//		tarFile("D:/Test/4.tar","D:/Test/4.txt");
//		File zipFile = new File("D:/Test/1.zip");
//		compressZip("D:/Test/1",zipFile);
	}
}
