package test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class TestMail {
	public static void main(String[] args) {
		TestMail.sendMail();
//		 TestMail.receiveMail();
		// TestMail.deleteMail();
	}

	/**
	 * send mail
	 */
	public static void sendMail() {
		String host = "smtp.163.com";// 邮件服务器
		String from = "yinhui_candy@163.com";// 发件人地址
		String to = "1546896629@qq.com";// 接受地址（必须支持pop3协议）
		String userName = "yinhui_candy@163.com";// 发件人邮件名称
		String pwd = "yh437215";// 发件人邮件密码
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);

		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));// 发送
			msg.setSubject("多个附件...........");// 邮件主题
			msg.setText("测试内容。。。。。。。");// 邮件内容
			Multipart multipart = new MimeMultipart();
			File attachment = new File("D:\\2015-06-10\\油条\\");
            for(File f:attachment.listFiles()){
            // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
            
            //MimeUtility.encodeWord可以避免文件名乱码
            	BodyPart attachmentBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(f);
                attachmentBodyPart.setDataHandler(new DataHandler(source));
            	try {
					attachmentBodyPart.setFileName(MimeUtility.encodeWord(f.getName()));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	multipart.addBodyPart(attachmentBodyPart);
            }
            msg.setContent(multipart);
			msg.saveChanges();

			Transport transport = session.getTransport("smtp");
			transport.connect(host, userName, pwd);// 邮件服务器验证
			transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
			System.out.println("send ok...........................");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * receive mail
	 */
	public static void receiveMail() {
		String host = "smtp.163.com";
		String userName = "yinhui_candy@163.com";
		String passWord = "******";

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
		try {
			System.out.println("receive...............................");
			Store store = session.getStore("pop3");
			store.connect(host, userName, passWord);// 验证
			Folder folder = store.getFolder("INBOX");// 取得收件文件夹
			folder.open(Folder.READ_WRITE);
			Message msg[] = folder.getMessages();
			System.out.println("邮件个数:" + msg.length);

			for (int i = 0; i < msg.length; i++) {
				Message message = msg[i];
				Address address[] = message.getFrom();
				StringBuffer from = new StringBuffer();
				/**
				 * 此for循环是我项目测试用的
				 */
				for (int j = 0; j < address.length; j++) {
					if (j > 0)
						from.append(";");
					from.append(address[j].toString());
				}
				System.out.println(message.getMessageNumber());
				System.out.println("来自：" + from.toString());
				System.out.println("大小：" + message.getSize());
				System.out.println("主题：" + message.getSubject());
				System.out.println("时间：：" + message.getSentDate());
				System.out
						.println("===================================================");
			}
			folder.close(true);// 设置关闭
			store.close();
			System.out.println("receive over............................");
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * delete mail
	 */
	public static void deleteMail() {
		String host = "pop3.sina.com";
		String userName = "xingui5624";
		String passWord = "******";

		Properties props = new Properties();
		// Properties props = System.getProperties();这种方法创建 Porperties 同上
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
		try {
			System.out.println("begin delete ...........");
			Store store = session.getStore("pop3");
			store.connect(host, userName, passWord);// 验证邮箱
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);// 设置我读写方式打开
			int countOfAll = folder.getMessageCount();// 取得邮件个数
			int unReadCount = folder.getUnreadMessageCount();// 已读个数
			int newOfCount = folder.getNewMessageCount();// 未读个数
			System.out.println("总个数：" + countOfAll);
			System.out.println("已读个数：" + unReadCount);
			System.out.println("未读个数：" + newOfCount);
			for (int i = 1; i <= countOfAll; i++) {
				Message message = folder.getMessage(i);
				message.setFlag(Flags.Flag.DELETED, true);// 设置已删除状态为true
				if (message.isSet(Flags.Flag.DELETED))
					System.out.println("已经删除第" + i + "邮件。。。。。。。。。");
			}
			folder.close(true);
			store.close();
			System.out.println("delete ok.................................");
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * reply mail
	 */
	public static void replyMail() {
		// test
	}

}
