package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import common.SFTPUtil;

public class TestFtp {
	public static void main(String[] args) {
		SFTPUtil sf = new SFTPUtil();
        ChannelSftp channel = null;
        try {
            channel = sf.connect("121.40.63.45", 22, "root","Msth2012");
            String directory = "/root/test/";
            channel.cd(directory);
            Vector<LsEntry> lists = sf.listFiles(directory, channel);
            for(LsEntry entry:lists){
            	String fileName = entry.getFilename();
            	if(fileName.endsWith(".txt")){
//            		sf.download(directory, fileName, "C:\\Users\\Administrator\\Desktop\\"+fileName, channel);
            		BufferedReader br = new BufferedReader(new FileReader(directory+fileName));
	                  String lineTxt = null;
	                  while((lineTxt = br.readLine())!=null){
	                  	System.out.println(lineTxt);
	                  }
            	}
            }
        }catch (Exception e) {
        	e.printStackTrace();
        }
        finally {
            sf.disconnect(channel);
        }
	}
}
