package word.freemaker.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import word.freemaker.fm.Balance;
import word.freemaker.fm.Word;


public class BalanceOfAccountReport extends JFrame{
	//主入口
	 public static void main(String[] args) {  
		BalanceOfAccountReport bar=new BalanceOfAccountReport(); 
		String inputPath = "";
		String outputPath = "";
		String fundInfoFname = "";
		String shareQueryFname = "";
		String balanceRptFname = "";
		Properties p = new Properties();
		try {
			p.load(Connection.class.getResourceAsStream("/word/direction.properties"));
			if(p.containsKey("input.default.dirpath")){  
                inputPath = p.getProperty("input.default.dirpath");
            } 
			if(p.containsKey("output.default.dirpath")){  
                outputPath = p.getProperty("output.default.dirpath");
            } 
			if(p.containsKey("fund.info.filename")){  
				fundInfoFname = p.getProperty("fund.info.filename");
            } 
			if(p.containsKey("share.query.filename")){  
				shareQueryFname = p.getProperty("share.query.filename");
            } 
			if(p.containsKey("balance.report.filename")){  
				balanceRptFname = p.getProperty("balance.report.filename");
            } 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 bar.initUI(inputPath,outputPath,fundInfoFname,shareQueryFname,balanceRptFname);
	 }
	 
	 //初始化页面
	 private void initUI(final String inputPath,final String outputPath,final String fundInfoFname,final String shareQueryFname,final String balanceRptFname) {
		final Condition cdition = new Condition();
		
		//持有人份额
        JPanel sharePnl = new JPanel(new FlowLayout(0,10,10));
        JLabel shareLbl = new JLabel("持有人份额文件：");
        final JTextField shareText = new JTextField(30);
        
        //净值
        JPanel worthPnl = new JPanel(new FlowLayout(0,10,10));
        JLabel worthLbl = new JLabel("净值文件：");
        final JTextField worthText = new JTextField(33);
        
        //账户余额
        JPanel balancePnl = new JPanel(new FlowLayout(0,10,10));
        JLabel balanceLbl = new JLabel("账户余额报告：");
        final JTextField balanceText = new JTextField(31);
        
		cdition.setStartDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		 
		//设置窗体的属性值  
        this.setLocation(400, 200);//设置显示位置  
        this.setSize(600, 300);//设置大小  
        this.setTitle("账户余额报告导出");//设置标题  
        this.setDefaultCloseOperation(3);//设置关闭方式  
        this.setResizable(true);//设置窗体是否可以最大化
        
      //实例化一个中央JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
          
        JPanel panel1 = new JPanel();  
        panel1.setLayout(new GridLayout(4,1));
        
        JPanel datePnl = new JPanel(new FlowLayout(0,10,10));
        JLabel  dateLabel = new JLabel("日期选择：");  
        
        JPanel jp1 = new JPanel();  
        jp1.setSize(100, 50);  
        final CalendarDialog cdStart = new CalendarDialog(jp1); 
        Document docStart = cdStart.getDateField().getDocument();
        docStart.addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					String text = e.getDocument().getText(e.getDocument().getStartPosition().getOffset(), e.getDocument().getLength());
					cdition.setStartDate(cdStart.getText().replaceAll("-", ""));
					try {
						//更新持有人份额路径
						shareText.setText(inputPath+File.separator+cdition.getStartDate()+File.separator+new String(shareQueryFname.getBytes("ISO-8859-1"),"UTF-8")+".xls");
						//更新净值文件路径
						worthText.setText(inputPath+File.separator+cdition.getStartDate()+File.separator+fundInfoFname+cdition.getStartDate()+".txt");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
        });
        datePnl.add(dateLabel);
        datePnl.add(cdStart);
        
      //持有人份额查询文件路径
        try {
			shareText.setText(inputPath+File.separator+cdition.getStartDate()+File.separator+new String(shareQueryFname.getBytes("ISO-8859-1"),"UTF-8")+".xls");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        JButton shareBtn = new JButton("选择");
        sharePnl.add(shareLbl);
        sharePnl.add(shareText);
        sharePnl.add(shareBtn);
        shareBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(inputPath);  
                int i = fileChooser.showOpenDialog(getContentPane());  
                if (i == JFileChooser.APPROVE_OPTION) {  
                    File selectedFile = fileChooser.getSelectedFile();  
                    shareText.setText(selectedFile.getPath());  
                }  
			}
        });
        
        //净值文件路径
        worthText.setText(inputPath+File.separator+cdition.getStartDate()+File.separator+fundInfoFname+cdition.getStartDate()+".txt");
        JButton worthBtn = new JButton("选择");
        worthPnl.add(worthLbl);
        worthPnl.add(worthText);
        worthPnl.add(worthBtn);
        worthBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(inputPath);  
                int i = fileChooser.showOpenDialog(getContentPane());  
                if (i == JFileChooser.APPROVE_OPTION) {  
                    File selectedFile = fileChooser.getSelectedFile();  
                    worthText.setText(selectedFile.getPath());  
                }  
			}
        });
        
        //账户余额报告输出路径
        balanceText.setText(outputPath);
        JButton balanceBtn = new JButton("选择");
        balancePnl.add(balanceLbl);
        balancePnl.add(balanceText);
        balancePnl.add(balanceBtn);
        balanceBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(outputPath);  
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int i = fileChooser.showSaveDialog(getContentPane());  
                if (i == JFileChooser.APPROVE_OPTION) {  
                    File selectedFile = fileChooser.getSelectedFile();
                    balanceText.setText(selectedFile.getPath());  
                }  
			}
        });
        panel1.add(datePnl);
        panel1.add(sharePnl);
        panel1.add(worthPnl);
        panel1.add(balancePnl);
        
        JPanel panel3 = new JPanel();
        JButton jBntYes = new JButton("确定");
        JButton jBntNo = new JButton("取消");
        jBntNo.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		System.exit(0);
        	}
        });
        jBntYes.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		List balances = new ArrayList();
					XSSFWorkbook hwb = null;
					try {
						if(!new File(shareText.getText()).exists()||!new File(worthText.getText()).exists()){
							JOptionPane.showMessageDialog(null, "当前选择日期无相关文件，请选择其它日期！");
						}else{
							if(!shareText.getText().contains(cdition.getStartDate())||!worthText.getText().contains(cdition.getStartDate())){
								JOptionPane.showMessageDialog(null, "输入日期与目录包含日期不匹配，请核对后输入！");
							}else{
							if(!shareText.getText().endsWith(".xls")||!worthText.getText().endsWith(".txt")){
									JOptionPane.showMessageDialog(null, "文件格式有误，请重新选择！");
								}else{
								hwb = new XSSFWorkbook(new File(shareText.getText()));
								Calendar calendar = Calendar.getInstance();
								String[] date = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()).split("-");
								XSSFSheet sheet = hwb.getSheetAt(0);
								
								//根据excel的列名获取列号
								int customerColNum = getColNum(sheet.getRow(0),"客户名称");//客户
								int transAccountNoColNum = getColNum(sheet.getRow(0),"交易账号");//交易账号
								int customerAccountNoColNum = getColNum(sheet.getRow(0),"基金账号");//客户账号
								int websiteColNum = getColNum(sheet.getRow(0),"网点");//网点
//								int deadlineColNum = getColNum(sheet.getRow(0),"");//报告截止日期
								int proCodeColNum = getColNum(sheet.getRow(0),"基金代码");//产品代码
								int proNameColNum = getColNum(sheet.getRow(0),"基金名称");//产品名称
								int shareBalanceColNum = getColNum(sheet.getRow(0),"份额余额");//份额余额
//								int unitWorthColNum = getColNum(sheet.getRow(0),"");//单位净值
								int refMarketValueColNum = getColNum(sheet.getRow(0),"最新市值");//参考市值 
								
								XSSFRow row = null;
								for(int i=1;i<sheet.getPhysicalNumberOfRows()-1;i++){
									row = sheet.getRow(i);
									Balance b = new Balance();
									b.setCustomer(row.getCell(customerColNum).getStringCellValue());
									b.setCustomerAccountNo(row.getCell(customerAccountNoColNum).getStringCellValue());
									b.setDeadline(cdition.getStartDate());
									b.setProCode(row.getCell(proCodeColNum).getStringCellValue());
									b.setProName("民生通惠"+row.getCell(proNameColNum).getStringCellValue()+"资产管理产品");
									b.setRefMarketValue(row.getCell(refMarketValueColNum).getNumericCellValue()==0?"0":new DecimalFormat("#,###.00").format(row.getCell(9).getNumericCellValue()));
									b.setShareBalance(row.getCell(shareBalanceColNum).getNumericCellValue()==0?"":new DecimalFormat("#,###.00").format(row.getCell(8).getNumericCellValue()));
									b.setTransAccountNo(row.getCell(transAccountNoColNum).getStringCellValue());
									b.setWebsite(row.getCell(websiteColNum).getStringCellValue().equals("直销")?"直销中心网点":row.getCell(3).getStringCellValue());
									b.setYear(date[0]);
									b.setMonth(date[1]);
									b.setDay(date[2]);
									b.setUnitWorth(getWorthByFundNo(worthText.getText(),b.getProCode()));
									balances.add(b);
								}
							hwb.close();
							new Word().createDoc(balances,balanceText.getText(),balanceRptFname,cdition.getStartDate());
							}
						}
						
					
					}
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "文件解析失败！");
					}
        	}
        });
        panel3.add(jBntYes);
        panel3.add(jBntNo);
        
        panel.add(panel1,BorderLayout.CENTER);
        panel.add(panel3,BorderLayout.SOUTH);
        
        this.add(panel);
        
      //设置窗体显示  
        this.setVisible(true);
	 }
	 
	 //根据净值文件的基金编号，查净值
	 public String getWorthByFundNo(String worthPath,String fundNo){
		 String worth = "";
		 File fundInfo = null;
			fundInfo = new File(worthPath);
			try {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(fundInfo),"GBK");
				BufferedReader bufferedReader = new BufferedReader(isr);   
				String lineTxt = null;
				String[] lineStr = null;
				while((lineTxt = bufferedReader.readLine()) !=null){
					if(lineTxt.length()>10){
						lineStr = lineTxt.split("\\s+");
						if(fundNo.equals(lineStr[0].substring(0, 6))){
							worth = new DecimalFormat("#.0000").format(Double.parseDouble(lineStr[1].substring(1, 6))/10000);
						}
					}
				}
				isr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		 return worth;
	 }
	 
	 //根据列名获得列序号
	 public int getColNum(XSSFRow searchRow, String condition) {
	   int colNum = -1;
	   int searchCell = searchRow.getPhysicalNumberOfCells();
       for (int i = 0; i < searchCell; i++) {
           Cell cell = searchRow.getCell(i);
           String val = (String) readCell(cell);
           if (condition.equals(val)) {
        	   colNum = i;
               return colNum;
           }
       	}
       if(colNum == -1){
    	   JOptionPane.showMessageDialog(null, "请检查excel列名是否有误！"); 
       }
	    return colNum;
	   }
	 
	 //得到单元格中的值，单元格格式如果为自定义的不能正常读取
	 public Object readCell(Cell cell) {
	       Object value = null;
	       if (cell != null) {
	           switch (cell.getCellType()) {
	               case Cell.CELL_TYPE_STRING:
	                   value = cell.getStringCellValue();
	                   break;
	               case Cell.CELL_TYPE_FORMULA:
	                   try {
	                       value = cell.getNumericCellValue();
	                   }
	                   catch (Exception e) {
	                       value = null;
	                   }
	                   break;
	               case Cell.CELL_TYPE_NUMERIC:
	                   value = cell.getNumericCellValue();
	               case Cell.CELL_TYPE_BLANK:
	                   value = null;
	           }
	       }
	       return value;
	   }
}
