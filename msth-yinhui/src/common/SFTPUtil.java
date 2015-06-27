package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class SFTPUtil {
    private transient static final Logger logger = Logger.getLogger(SFTPUtil.class);
    /**
     * 连接sftp服务器
     * 
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public ChannelSftp connect(String host, int port, String username, String password) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            logger.info("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            logger.info("Session connected.");
            logger.info("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            logger.info("Connected to " + host + ".");
        }
        catch (Exception e) {

        }
        return sftp;
    }

    /**
     * 上传文件
     * 
     * @param directory 上传的目录
     * @param uploadFile 要上传的文件
     * @param sftp
     */
    public void upload(String directory, String uploadFile, ChannelSftp sftp) throws Exception{
        FileInputStream in = null;
        try {
            sftp.cd(directory);
            File file = new File(uploadFile);
            in = new FileInputStream(file);
            sftp.put(in, file.getName());
            logger.info("上传文件路径["+directory+ "/" +file.getName()+"]");
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(in != null){
               in.close();
            }
        }
    }

    /**
     * 下载文件
     * 
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param saveFile 存在本地的路径
     * @param sftp
     */
    public void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) throws Exception{
        FileOutputStream out = null;
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            out = new FileOutputStream(file);
            sftp.get(downloadFile, out);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(out != null){
                out.close();
             }
        }
    }

    /**
     * 删除文件
     * 
     * @param directory 要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @param sftp
     */
    public void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 
     * 创建文件夹
     * 
     * @param
     * @since 2015-5-21 下午3:22:25  
     * @author  ChenYing
     * @return
     */
    public void makeDirs(String directory, ChannelSftp sftp) throws SftpException{
        String[] dirs = directory.split("/");
        if(dirs.length > 1){
            int i = 0;
            String temp = "/"+dirs[i];
            boolean end = false;
            while(!end){
                try {
                    sftp.ls(temp);
                }
                catch (Exception e) {
                    sftp.mkdir(temp);
                }
                i++;
                if(i>=dirs.length){
                    end=true;
                }else{
                    temp += "/" + dirs[i];
                }
            }
        }
    }
    

    /**
     * 列出目录下的文件
     * 
     * @param directory 要列出的目录
     * @param sftp
     * @return
     * @throws SftpException
     */
    @SuppressWarnings("unchecked")
    public Vector<LsEntry> listFiles(String directory, ChannelSftp sftp) throws SftpException {
        return sftp.ls(directory);
    }
    
    public void disconnect(ChannelSftp sftp){
        try {
            if(sftp!= null){
                sftp.getSession().disconnect();
            }
        }
        catch (JSchException e) {
            logger.error("断开ftp异常", e);
        }
    }
    
    public static void main(String[] args) {
        SFTPUtil sf = new SFTPUtil();
        ChannelSftp channel = null;
        try {
            channel = sf.connect("121.40.63.45", 22, "root","Msth2012");
            sf.makeDirs("/root/ddd/eeee", channel);
            logger.info("TA文件已上传");
        }
        catch (Exception e) {
            logger.error("上传ftp异常", e);
        }
        finally {
            sf.disconnect(channel);
        }
    }

}
