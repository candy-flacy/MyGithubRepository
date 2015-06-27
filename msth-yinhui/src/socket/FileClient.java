package socket;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import zip.unzip.ApacheAnt;
 
public class FileClient{
 
//       public static void main(String[] args)throws Exception{
//	    	  ServerSocket ss = new ServerSocket(3108);
//	          Socket client = ss.accept();
//	          OutputStream netOut=client.getOutputStream();
//	          DataOutputStream doc=new DataOutputStream(new BufferedOutputStream(netOut));
//	          new ApacheAnt().compressZip("D:\\2015-06-10\\油条",new File("D:\\2015-06-10\\油条\\油条.zip"));
//              File file=new File("D:\\2015-06-10\\油条\\油条.zip");
//              FileInputStream fos=new FileInputStream(file);
//              byte[] buf=new byte[2048];
//              int num=-1;
//              while((num = fos.read(buf))!=-1){
//            	  doc.write(buf,0,num);
//            	  doc.flush();
//              }
//              fos.close();
//              doc.close();
//       }
       
       public static void main(String[] args)throws Exception{
    	   Socket server=new Socket(InetAddress.getLocalHost(),3108);
    	   OutputStream netOut= server.getOutputStream();
           DataOutputStream doc=new DataOutputStream(new BufferedOutputStream(netOut));
	       new ApacheAnt().compressZip("D:\\2015-06-10\\油条",new File("D:\\2015-06-10\\油条\\油条.zip"));
           File file=new File("D:\\2015-06-10\\油条\\油条.zip");
           FileInputStream fos=new FileInputStream(file);
           byte[] buf=new byte[2048];
           int num=-1;
           while((num = fos.read(buf))!=-1){
         	  doc.write(buf,0,num);
         	  doc.flush();
           }
           fos.close();
           doc.close();
    }
 
}