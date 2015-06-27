package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

public class TestHttpClient {
	public static void main(String[] args) {  
        
        String url="http://127.0.0.1:8080/LearnServlet/FileUpdateServer";  
        String queryString = "a=123456&b=333333333333";  
        try {  
        FileInputStream in = new FileInputStream(new File("D:\\2015-06-10\\油条\\油条.zip"));  
            postHttpReq(queryString,url,in);   
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
  
 public static String postHttpReq(String queryString,String url,InputStream in) {  
        HttpClient httpClient = new HttpClient();  
        EntityEnclosingMethod postMethod = new PostMethod(url);  
        postMethod.setQueryString(queryString);  
        postMethod.setRequestHeader("Content-Type", "application/octet-stream");  
          
        RequestEntity request = new InputStreamRequestEntity(in);  
        postMethod.setRequestEntity(request);  
  
  
        String responseMsg = "";  
        int statusCode = 0;  
        try {  
            statusCode = httpClient.executeMethod(postMethod);// 发送请求  
            responseMsg = postMethod.getResponseBodyAsString();// 获取返回值  
        } catch (HttpException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            postMethod.releaseConnection();// 释放连接  
        }  
  
        return responseMsg;  
    }  
}
