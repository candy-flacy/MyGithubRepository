package socket;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer{
 
//       public static void main(String[] args)throws Exception{
//              File file=new File("C:\\Users\\Administrator\\Desktop\\网络传输\\test\\油条.rar");
//              file.createNewFile();
//              RandomAccessFile raf=new RandomAccessFile(file,"rw");
//              Socket server=new Socket(InetAddress.getLocalHost(),3108);
//              InputStream netIn= server.getInputStream();
//              DataInputStream in=new DataInputStream(new BufferedInputStream(netIn));
//              byte[] buf=new byte[2048];
//              int num=-1;              
//              while((num = in.read(buf))!= -1){
//            	  raf.write(buf,0,num);
//                  raf.skipBytes(num);
//              }
//              in.close();
//              raf.close();
//       }
       
       public static void main(String[] args)throws Exception{
           File file=new File("C:\\Users\\Administrator\\Desktop\\网络传输\\test\\油条.rar");
           file.createNewFile();
           RandomAccessFile raf=new RandomAccessFile(file,"rw");
           ServerSocket ss = new ServerSocket(3108);
           Socket client = ss.accept();
           InputStream netIn=client.getInputStream();
           DataInputStream in=new DataInputStream(new BufferedInputStream(netIn));
           byte[] buf=new byte[2048];
           int num=-1;              
           while((num = in.read(buf))!= -1){
         	  raf.write(buf,0,num);
               raf.skipBytes(num);
           }
           in.close();
           raf.close();
           
    }
 
}