package test;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileUpdateServer extends HttpServlet{
	protected void doPost(HttpServletRequest request,HttpServletResponse response)   
	throws ServletException, IOException{  
	       doGet(request,response);  
	     }  
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
	throws ServletException, IOException {   
	        String a = request.getParameter("a");  
	        String b = request.getParameter("b");  
	        System.out.println("11"+a+b); //11123456333333333333  
	        InputStream in = request.getInputStream();  
//	        this.save(in, "testaa.torrent", "E://"); //这个就是个将流输出成文件的方法.....
	    }  
}
