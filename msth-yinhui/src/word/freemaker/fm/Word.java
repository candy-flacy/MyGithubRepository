    package word.freemaker.fm;  
      
    import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
      
    public class Word {  
      
        private Configuration configuration = null;  
      
        public Word() {  
            configuration = new Configuration();  
            configuration.setDefaultEncoding("utf-8");  
        }  
      
        public void createDoc(List products,String outputPath,String balanceRptFname,String date) {  
//        	System.out.println("输出路径："+outputPath);
      
            // 要填入模本的数据文件  
            Map dataMap = new HashMap(); 
            dataMap.put("balances", products);
//            getData(dataMap,products);  
      
            // 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，  
            // ftl文件存放路径  
            configuration.setClassForTemplateLoading(this.getClass(), "/word/freemaker/fm");  
      
            Template t = null;  
            try {  
                // test.ftl为要装载的模板  
                t = configuration.getTemplate("balance.ftl");  
                t.setEncoding("utf-8");  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
      
            // 输出文档路径及名称  
            File destPath = new File(outputPath);
            if(!destPath.exists()){
            	destPath.mkdirs();
            }
            File outFile = null;
			try {
				outFile = new File(destPath+File.separator+new String(balanceRptFname.getBytes("ISO-8859-1"),"UTF-8")+"_"+date+".doc");
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}  
            Writer out = null;  
            try {  
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));  
            } catch (Exception e1) {  
                e1.printStackTrace();  
            }  
      
            try {  
                t.process(dataMap, out);  
                if(outFile.exists()&&outFile.length()>0){
                	JOptionPane.showMessageDialog(null, "已成功生成\"账户余额报告_"+date+"\"！");
                }else{
                	JOptionPane.showMessageDialog(null, "\"账户余额报告_"+date+"\"生成失败！");
                }
                out.close();  
            } catch (TemplateException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
      
//        /** 
//         *  
//         * 注意dataMap里存放的数据Key值要与模板中的参数相对应 
//         * @param dataMap 
//         */  
//        private void getData(Map dataMap,List products) {  
//        	
//            dataMap.put("products", products);  
//            
//        }  
      
//        public static void main(String[] args) {  
//            new Word().createDoc();  
//        }  
      
    }  
