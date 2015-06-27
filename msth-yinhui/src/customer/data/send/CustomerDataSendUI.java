package customer.data.send;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class CustomerDataSendUI extends JFrame{
	JFrame frame = new JFrame();
	//客户数据 面板
	private JPanel customerDataPnl = new JPanel();
	//客户名称
    private JPanel cusNamePnl = new JPanel();
    private JLabel cusNameLbl = new JLabel("客户名称：");
    private JComboBox cusNameComBox = new JComboBox();
    private String[] customerNames = new String[]{"安华","昆仑","渤海","民安","天安","民生传统","民生万能","通惠聚优","万能新产品","通惠先进力","优选1号","中国龙"};
    private String xlsxPath = "C:\\Users\\Administrator\\Desktop\\test.xlsx";
    //基金编号
    private JPanel fundNoPnl = new JPanel();
    private JLabel fundNoLbl = new JLabel("基金编号：");
    private JTextField fundNoText = new JTextField(15);
    private JLabel fundNoTip = new JLabel("请用\";\"隔开");
    //适用发送任务
    private JPanel cusTypePnl = new JPanel();
    private JLabel cusTypeLbl = new JLabel("适用发送任务：");
    private ButtonGroup cusTypeGroup = new ButtonGroup();
    private JRadioButton trusteeBtn = new JRadioButton("受托客户");
    private JRadioButton assetsManageBtn = new JRadioButton("资管产品");
    //邮件接收人
    private JPanel mailRecievePnl = new JPanel();
    private JLabel mailRecieveLbl = new JLabel("邮件接收人：");
    private JTextField mailRecieveText = new JTextField(15);
    private JButton mailRecieveBtn = new JButton("添加");
    //查询日期
    private JPanel queryDatePnl = new JPanel();
    private JLabel queryDateLbl = new JLabel("查询日期：");
    private JPanel jp = new JPanel();  
    private CalendarDialog queryDateCalendar = new CalendarDialog(jp);
    
    //发送任务 面板
    private JPanel sendTaskPnl = new JPanel();
    //发送时间
    private JPanel sendTimePnl = new JPanel();
    private JLabel sendTimeLbl = new JLabel("发送时间：");
    private JSpinner startHourSpinner;
    private JSpinner startMinuteSpinner;
    private JLabel sendTimeSep = new JLabel("-");
    private JSpinner endHourSpinner;
    private JSpinner endMinuteSpinner;
    private SpinnerNumberModel startHourModel = new SpinnerNumberModel(0, 0, 23, 1); 
    private SpinnerNumberModel startMinuteModel = new SpinnerNumberModel(0, 0, 59, 1);
    private SpinnerNumberModel endHourModel = new SpinnerNumberModel(0, 0, 23, 1);
    private SpinnerNumberModel endMinuteModel = new SpinnerNumberModel(0, 0, 59, 1);
    private Calendar c = new GregorianCalendar();
    //数据范围
    private JPanel dataRangePnl = new JPanel();
    private JLabel  dataRangeLbl = new JLabel("数据范围："); 
    private String[] dataRangeValue1 = new String[]{"成交回报","成交汇总表","清算数据包","单元资产","组合证券"};
    private String[] dataRangeValue2 = new String[]{"成交回报","成交汇总表"};
    private JList dataRangeList = new JList();
    //发送频率
    private JPanel sendFrequencyPnl = new JPanel();
    private JLabel sendFrequencyLbl = new JLabel("发送频率：");
    private JTextField sendFrequencyText = new JTextField("每交易日");
    //关键字
    private JPanel keyWordPnl = new JPanel();
    private JLabel keyWordLbl = new JLabel("关键字：");
    private String[] keyWordValue1 = new String[]{"基金编号","结算编号","证券账号"}; 
    private String[] keyWordValue2 = new String[]{"基金编号"};
    private JList keyWordList = new JList();
    //邮件主题
    private JPanel mailSubjectPnl = new JPanel();
    private JLabel mailSubjectLbl = new JLabel("邮件主题：");
    private JTextField mailSubjectText = new JTextField();
    
    //模板数据导出路径
    private JPanel dataExportPnl = new JPanel();
    private JLabel dataExportLbl = new JLabel("模板数据导出：");
    private JTextField dataExportText = new JTextField(25);
    private JButton dataExportBtn = new JButton("选择");
    private String exportPath = "C:\\Users\\Administrator\\Desktop\\";
    
    //确定、取消按钮
    private JPanel bottomPnl = new JPanel();
    private JButton yesBtn = new JButton("确定");
    private JButton noBtn = new JButton("取消");
    
    //邮件接收人 增加行
    private JPanel addRowPnl = new JPanel();
	private JButton addRowBtn = new JButton("增加行");
	private JButton delRowBtn = new JButton("删除行");
	private JButton sureRowBtn = new JButton("确定");
	private String[] columnNames = {"邮箱地址","后缀"};
	private DefaultTableModel addRowModel = new DefaultTableModel(null,columnNames); 
	private JTable addRowTable = new JTable(addRowModel);
	private JScrollPane addRowPane = new JScrollPane();
	private JDialog dialog = null;
	
	//主面板
	private JPanel mainPnl = new JPanel();
	
//	private JLabel customerDataLbl = new JLabel("客户信息");
//	private JLabel sendTaskLbl = new JLabel("发送任务");
	
	CustomerData cd = new CustomerData();
    public static void main(String[] args) {  
    	CustomerDataSendUI cds=new CustomerDataSendUI();  
    	cds.initUI();
    }  
    private void initUI() {  
        //设置窗体的属性值  
    	frame.setLocation(400, 50);//设置显示位置
    	frame.setSize(500, 680);//设置大小  
    	frame.setTitle("客户数据发送");//设置标题  
        frame.setDefaultCloseOperation(3);//设置关闭方式  
        frame.setResizable(true);//设置窗体是否可以最大化  
//        frame.setLayout(new GridLayout(2,1));
//        frame.setLayout(new BoxLayout(frame,BoxLayout.Y_AXIS));
        //主面板
        mainPnl.setLayout(new BoxLayout(mainPnl,BoxLayout.Y_AXIS));
        
        //客户信息 面板
        customerDataPnl.setLayout(new BoxLayout(customerDataPnl,BoxLayout.Y_AXIS));
        
        //客户名称
        cusNamePnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        cusNameComBox.setEditable(true);
        cusNameComBox.addItem("请选择");
        if(new File(xlsxPath).exists()){
        	String[] customerNamesNew = getCellValuesByColNum(xlsxPath,0);
        	for(int i = 0;i<customerNamesNew.length;i++){
            	cusNameComBox.addItem(customerNamesNew[i]);
            }
        }else{
        	for(int i = 0;i<customerNames.length;i++){
            	cusNameComBox.addItem(customerNames[i]);
            }
        }
        cusNamePnl.add(cusNameLbl);
        cusNamePnl.add(cusNameComBox);
        //基金编号
        fundNoPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        fundNoPnl.add(fundNoLbl);
        fundNoPnl.add(fundNoText);
        fundNoPnl.add(fundNoTip);
        //适用发送任务
        cusTypePnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        trusteeBtn.setSelected(true);
        cd.setCusType(trusteeBtn.getText());
        cusTypeGroup.add(trusteeBtn);
        cusTypeGroup.add(assetsManageBtn);
        cusTypePnl.add(cusTypeLbl);
        cusTypePnl.add(trusteeBtn);
        cusTypePnl.add(assetsManageBtn);
        //邮件接收人
        mailRecievePnl.setLayout(new FlowLayout(FlowLayout.LEFT));
//        mailRecieveComBox.addItem("@qq.com");
//        mailRecieveComBox.addItem("@163.com");
        mailRecieveText.setPreferredSize(new Dimension(200,25));
        mailRecievePnl.add(mailRecieveLbl);
        mailRecievePnl.add(mailRecieveText);
        mailRecievePnl.add(mailRecieveBtn);
//        mailRecievePnl.add(mailRecieveComBox);
        //查询日期
        queryDatePnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        jp.setSize(100, 50);  
        queryDatePnl.add(queryDateLbl);
        queryDatePnl.add(queryDateCalendar);
        
        //客户信息 面板 添加组件
//        customerDataPnl.add(customerDataLbl);
        customerDataPnl.add(cusNamePnl);
        customerDataPnl.add(fundNoPnl);
        customerDataPnl.add(cusTypePnl);
        customerDataPnl.add(mailRecievePnl);
        customerDataPnl.add(queryDatePnl);
        customerDataPnl.add(new JSeparator());
        
        //发送任务 面板
        sendTaskPnl.setLayout(new BoxLayout(sendTaskPnl,BoxLayout.Y_AXIS));
        //发送时间
        sendTimePnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        sendTimePnl.add(sendTimeLbl);
        int currentHour = c.get(Calendar.HOUR_OF_DAY);  
//        int currentMinute = c.get(Calendar.MINUTE);  
        //开始时分
        startHourModel.setValue(currentHour);
        startHourSpinner = new JSpinner(startHourModel);
        startHourSpinner.setPreferredSize(new Dimension(35, 20));  
        startHourSpinner.setName("Hour");  
//        hourSpinner.addChangeListener(this);
        JLabel startHourLabel = new JLabel("时");  
        startHourLabel.setForeground(Color.GRAY);
        sendTimePnl.add(startHourSpinner);
        sendTimePnl.add(startHourLabel);
        startMinuteSpinner = new JSpinner(startMinuteModel);  
        startMinuteSpinner.setPreferredSize(new Dimension(35, 20));  
        startMinuteSpinner.setName("Minute");  
//        minuteSpinner.addChangeListener(this); 
        JLabel startMinuteLabel = new JLabel("分");  
        startMinuteLabel.setForeground(Color.GRAY);
        sendTimePnl.add(startMinuteSpinner);
        sendTimePnl.add(startMinuteLabel);
        
//        sendTimePnl.add(sendTimeSep);
        //结束时分
        endHourModel.setValue(currentHour);
        endHourSpinner = new JSpinner(endHourModel);  
        endHourSpinner.setPreferredSize(new Dimension(35, 20));  
        endHourSpinner.setName("Hour");  
//        hourSpinner.addChangeListener(this);
        JLabel endHourLabel = new JLabel("时");  
        endHourLabel.setForeground(Color.GRAY);
//        sendTimePnl.add(endHourSpinner);
//        sendTimePnl.add(endHourLabel);
        endMinuteSpinner = new JSpinner(endMinuteModel);  
        endMinuteSpinner.setPreferredSize(new Dimension(35, 20));  
        endMinuteSpinner.setName("Minute");  
//        minuteSpinner.addChangeListener(this); 
        JLabel endMinuteLabel = new JLabel("分");  
        endMinuteLabel.setForeground(Color.GRAY);
//        sendTimePnl.add(endMinuteSpinner);
//        sendTimePnl.add(endMinuteLabel);
        //数据范围
        dataRangePnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        dataRangePnl.add(dataRangeLbl);
        dataRangeList.setListData(dataRangeValue1);
        dataRangeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        dataRangeList.setVisibleRowCount(5);
        dataRangeList.setFixedCellWidth(200);  
        dataRangeList.setFixedCellHeight(15);
        JScrollPane dataRangeScroll = new JScrollPane(dataRangeList);
        dataRangePnl.add(dataRangeScroll);  
        //发送频率
        sendFrequencyPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        sendFrequencyText.setPreferredSize(new Dimension(200,25));
        sendFrequencyPnl.add(sendFrequencyLbl);
        sendFrequencyPnl.add(sendFrequencyText);
        //关键字
        keyWordPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        keyWordPnl.add(keyWordLbl);
        keyWordList.setListData(keyWordValue1);
        keyWordList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        keyWordList.setVisibleRowCount(3);
        keyWordList.setFixedCellWidth(200);  
        keyWordList.setFixedCellHeight(15);
//        keyWordList.setVisible(false);
        JScrollPane keyWordScroll = new JScrollPane(keyWordList);
        keyWordPnl.add(keyWordScroll);
        //邮件主题
        mailSubjectPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        mailSubjectText.setPreferredSize(new Dimension(200,25));
        mailSubjectPnl.add(mailSubjectLbl);
        mailSubjectPnl.add(mailSubjectText);
        
      //模板数据导出路径
        dataExportPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        dataExportText.setText(exportPath);
        dataExportPnl.add(dataExportLbl);
        dataExportText.setText(exportPath+queryDateCalendar.getText().replaceAll("-",""));
        dataExportPnl.add(dataExportText);
        dataExportPnl.add(dataExportBtn);
        dataExportBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(exportPath);  
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int i = fileChooser.showSaveDialog(getContentPane());  
                if (i == JFileChooser.APPROVE_OPTION) {  
                    File selectedFile = fileChooser.getSelectedFile();
                    dataExportText.setText(selectedFile.getPath());  
                }  
			}
        });
        
        //确定、取消按钮
        bottomPnl.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPnl.add(yesBtn);
        bottomPnl.add(noBtn);
        
        //发送任务 面板 添加组件
//        sendTaskPnl.add(sendTaskLbl);
        sendTaskPnl.add(sendTimePnl);
        sendTaskPnl.add(dataRangePnl);
        sendTaskPnl.add(sendFrequencyPnl);
        sendTaskPnl.add(keyWordPnl);
        sendTaskPnl.add(mailSubjectPnl);
        sendTaskPnl.add(new JSeparator());
//        sendTaskPnl.add(dataExportPnl);
        sendTaskPnl.add(bottomPnl);
        
        dataRangeList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(dataRangeList.getSelectedIndex()!=-1){
					if(dataRangeList.getSelectedValues().length==1){
		            	  mailSubjectText.setText((queryDateCalendar.getText().replaceAll("-", "")+dataRangeList.getSelectedValue()).replace("成交汇总表", "成交汇总"));
		            	  cd.setDataRange(String.valueOf(dataRangeList.getSelectedValue()));
					}else{
		            	  StringBuffer sb = new StringBuffer();
		            	  for(int i=0;i<dataRangeList.getSelectedValues().length;i++){
		            		  sb.append(dataRangeList.getSelectedValues()[i]+",");
		            	  }
		            	  if(sb.toString().contains("清算")){
		            		  mailSubjectText.setText(queryDateCalendar.getText().replaceAll("-", "")+"清算数据");  
		            	  }
		            	  cd.setDataRange(sb.toString().substring(0, sb.toString().length()-1));
		              }
				}
			}
        	
        });
        
        //关键字
        keyWordList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(keyWordList.getSelectedIndex()!=-1){
	            	  StringBuffer sb = new StringBuffer();
	            	  for(int i=0;i<keyWordList.getSelectedValues().length;i++){
	            		  sb.append(keyWordList.getSelectedValues()[i]+",");
	            	  }
	            	  cd.setKeyWord(sb.toString().substring(0, sb.toString().length()-1));
				}
			}
        	
        });
        
        //查询日期选择事件
        Document queryDate = queryDateCalendar.getDateField().getDocument();
        queryDate.addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				String mailSubjectTemp = mailSubjectText.getText();
				if(mailSubjectText != null && !mailSubjectTemp.equals("")){
					mailSubjectText.setText(mailSubjectTemp.replace(mailSubjectTemp.substring(0, 8),queryDateCalendar.getText().replaceAll("-", "")));
				}
				dataExportText.setText(exportPath+queryDateCalendar.getText().replaceAll("-","")+"\\"+cusNameComBox.getSelectedItem());
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
        });
        
        //受托客户按钮
        trusteeBtn.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(trusteeBtn.isSelected()){
					keyWordList.setListData(keyWordValue1);
					dataRangeList.setListData(dataRangeValue1);
					cd.setCusType(trusteeBtn.getText());
				}
			}
        });
        
        //资管产品按钮
        assetsManageBtn.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(assetsManageBtn.isSelected()){
					keyWordList.setListData(keyWordValue2);
					dataRangeList.setListData(dataRangeValue2);
					cd.setCusType(assetsManageBtn.getText());
				}
			}
        });
        
        //客户名称改变
        cusNameComBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){//加上条件判断，监听事件执行一次，否则，会执行两次
				//改变导出路径
				dataExportText.setText(exportPath+queryDateCalendar.getText().replaceAll("-","")+"\\"+cusNameComBox.getSelectedItem());
				if(new File(xlsxPath).exists()){
				try {
					XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(xlsxPath));
					XSSFSheet sheet = xwb.getSheet("0");
					int rowNum = isContainsStr(sheet,cusNameComBox.getSelectedItem().toString(),0);
					if(rowNum > 0){//所选客户名称存在于excel中
						XSSFRow row = sheet.getRow(rowNum);
						fundNoText.setText(row.getCell(1).getStringCellValue());
						String cusType = row.getCell(2).getStringCellValue();
						if(cusType.equals("受托客户")){
							trusteeBtn.setSelected(true);
							dataRangeList.setListData(dataRangeValue1);
							keyWordList.setListData(keyWordValue1);
							for(String str :row.getCell(5).getStringCellValue().split(",")){
								dataRangeList.setSelectedValue(str, true);
							}
							for(String str :row.getCell(7).getStringCellValue().split(",")){
								keyWordList.setSelectedValue(str, true);
							}
						}else{
							assetsManageBtn.setSelected(true);
							dataRangeList.setListData(dataRangeValue2);
							keyWordList.setListData(keyWordValue2);
							for(String str :row.getCell(5).getStringCellValue().split(",")){
								dataRangeList.setSelectedValue(str, true);
							}
							for(String str :row.getCell(7).getStringCellValue().split(",")){
								keyWordList.setSelectedValue(str, true);
							}
						}
						mailRecieveText.setText(row.getCell(3).getStringCellValue());
//						sendTimeChoose.setText(row1.getCell(4).getStringCellValue());
						//发送时间设置
						String cell4Value = row.getCell(4).getStringCellValue();
						startHourModel.setValue(Integer.parseInt(cell4Value.split("-")[0].split(":")[0]));
						startMinuteModel.setValue(Integer.parseInt(cell4Value.split("-")[0].split(":")[1]));
						endHourModel.setValue(Integer.parseInt(cell4Value.split("-")[1].split(":")[0]));
						endMinuteModel.setValue(Integer.parseInt(cell4Value.split("-")[1].split(":")[1]));
						sendFrequencyText.setText(row.getCell(6).getStringCellValue());
//						keyWordList.setListData(row1.getCell(7).getStringCellValue().split(","));
						mailSubjectText.setText(row.getCell(8).getStringCellValue());
					}else{
						fundNoText.setText("");
						trusteeBtn.setSelected(true);
						mailRecieveText.setText("");
						dataRangeList.setListData(dataRangeValue1);
						startHourModel.setValue(c.get(Calendar.HOUR_OF_DAY));
						startMinuteModel.setValue(0);
						endHourModel.setValue(c.get(Calendar.HOUR_OF_DAY));
						endMinuteModel.setValue(0);
						sendFrequencyText.setText("每交易日");
						keyWordList.setListData(keyWordValue1);
						mailSubjectText.setText("");
					}
								
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} }
				}
			}
        	
        });
        
        
        //取消按钮
        noBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		System.exit(0);
        	}
        });
        
      //确定按钮
        yesBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		cd.setCusName(String.valueOf(cusNameComBox.getSelectedItem()));
        		cd.setFundNo(fundNoText.getText());
        		cd.setMailRecieves(mailRecieveText.getText());
        		cd.setMailSubject(mailSubjectText.getText());
        		cd.setQueryDate(queryDateCalendar.getText());
        		cd.setSendFrequency(sendFrequencyText.getText());
//        		System.out.println("开始时间："+startHourSpinner.getValue()+":"+startMinuteSpinner.getValue());
        		String startTime = startHourSpinner.getValue()+":"+startMinuteSpinner.getValue();
        		String endTime = endHourSpinner.getValue()+":"+endMinuteSpinner.getValue();
        		cd.setSendTime(startTime+"-"+endTime);
        		addRecordToExcel(xlsxPath,cd);
        		//更新客户名称下拉菜单
        		String[] customerNamesNew = getCellValuesByColNum(xlsxPath,0);
        		cusNameComBox.removeAllItems();
            	for(int i = 0;i<customerNamesNew.length;i++){
                	cusNameComBox.addItem(customerNamesNew[i]);
                }
            	cusNameComBox.setSelectedItem(cd.getCusName());
        	}
        });
        
        //选择邮件接收人
        mailRecieveBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//先清空邮件接收人
            	if(addRowModel.getRowCount()>=1){
            		for(int i=0;i<addRowModel.getRowCount();i++){
            			addRowModel.removeRow(i);
            		}
            		addRowTable.setModel(addRowModel);
				}
				addRowBtn.setPreferredSize(new Dimension(80,20));
				delRowBtn.setPreferredSize(new Dimension(80,20));
				sureRowBtn.setPreferredSize(new Dimension(80,20));
				addRowPnl.add(addRowBtn);
				addRowPnl.add(delRowBtn);
				addRowPnl.add(sureRowBtn);
				//设置单元格编辑器为下拉框
				TableColumn tableColumn = addRowTable.getColumn("后缀");
				JComboBox comboBox = new JComboBox();
				comboBox.addItem("@qq.com");
				comboBox.addItem("@163.com");
				tableColumn.setCellEditor(new DefaultCellEditor(comboBox));
				addRowPane.setViewportView(addRowTable);
				dialog = new JDialog(frame);
				dialog.setLocation(495, 250);//设置显示位置
				dialog.setSize(300, 250);
				dialog.add(addRowPnl,BorderLayout.SOUTH);
				dialog.add(addRowPane,BorderLayout.CENTER);
				dialog.setVisible(true);
			}
        });
        
        //添加行
        addRowBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] tableVales={"","@qq.com"};
				addRowModel.addRow(tableVales);
				addRowTable.setModel(addRowModel); 
				System.out.println("增加行");
			}
        });
        
        //删除行
        delRowBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(addRowModel.getRowCount()>=1){
					addRowModel.removeRow(addRowModel.getRowCount()-1);
					addRowTable.setModel(addRowModel); 
					System.out.println("删除行");
				}
			}
        });
        
      //确定行
        sureRowBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int rowCount = addRowModel.getRowCount();
				StringBuffer sb = new StringBuffer();
				for(int i=0;i<rowCount;i++){
					//如果有单元格处于编辑状态，则停止编辑，否则获取不到单元格的值
					if(addRowTable.isEditing()){
						addRowTable.getCellEditor().stopCellEditing();
					}
					if(!addRowModel.getValueAt(i, 0).equals("")){
						sb.append(addRowModel.getValueAt(i, 0)+""+addRowModel.getValueAt(i,1)+",");
					}
				}
				if(!sb.toString().equals("")){
					mailRecieveText.setText(sb.toString().substring(0, sb.toString().length()-1));
				}
				dialog.setVisible(false);
			}
        });
        mainPnl.add(customerDataPnl);
        mainPnl.add(sendTaskPnl);
        frame.add(mainPnl);
        
        //设置窗体显示  
        frame.setVisible(true);  
    }  
    
    //Excel追加记录
    public void addRecordToExcel(String outPath,CustomerData cd){
    	File f = new File(outPath);
		if(!f.getParentFile().exists()){
			f.getParentFile().mkdirs();
		}
		XSSFRow currentRow = null;
		XSSFWorkbook xwb = null;
		if(!f.exists()){//判断文件是否存在
			try {
				f.createNewFile();
				String[] head = new String[]{"客户名称","基金编号","适用发送任务","邮件接收人","发送时间","数据范围","发送频率","关键字","邮件主题"};
		    	xwb = new XSSFWorkbook();
				XSSFSheet sheet = xwb.createSheet("0");
				XSSFRow row0 = sheet.createRow(0);
				for(int i=0;i<head.length;i++){
					row0.createCell(i).setCellValue(head[i]);
				}
				currentRow = sheet.createRow(1);
				currentRow.createCell(0).setCellValue(cd.getCusName());
				currentRow.createCell(1).setCellValue(cd.getFundNo());
				currentRow.createCell(2).setCellValue(cd.getCusType());
				currentRow.createCell(3).setCellValue(cd.getMailRecieves());
				currentRow.createCell(4).setCellValue(cd.getSendTime());
				currentRow.createCell(5).setCellValue(cd.getDataRange());
				currentRow.createCell(6).setCellValue(cd.getSendFrequency());
				currentRow.createCell(7).setCellValue(cd.getKeyWord());
				currentRow.createCell(8).setCellValue(cd.getMailSubject());
				FileOutputStream out = new FileOutputStream(outPath);
				xwb.write(out);
				out.flush();
				out.close();
				xwb.close();
				if(f.length() > 0){
					JOptionPane.showMessageDialog(null, "记录添加成功！");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				xwb = new XSSFWorkbook(new FileInputStream(outPath));
				XSSFSheet sheet = xwb.getSheet("0");
				int currentRows = sheet.getLastRowNum();
				int rowNum = isContainsStr(sheet,cd.getCusName(),0);
				if(rowNum > 0){//客户名称存在，则修改记录
					currentRow = sheet.getRow(rowNum);
					currentRow.getCell(0).setCellValue(cd.getCusName());
					currentRow.getCell(1).setCellValue(cd.getFundNo());
					currentRow.getCell(2).setCellValue(cd.getCusType());
					currentRow.getCell(3).setCellValue(cd.getMailRecieves());
					currentRow.getCell(4).setCellValue(cd.getSendTime());
					currentRow.getCell(5).setCellValue(cd.getDataRange());
					currentRow.getCell(6).setCellValue(cd.getSendFrequency());
					currentRow.getCell(7).setCellValue(cd.getKeyWord());
					currentRow.getCell(8).setCellValue(cd.getMailSubject());
					FileOutputStream out = new FileOutputStream(outPath);
					xwb.write(out);
					out.flush();
					out.close();
					xwb.close();
					if(f.length() > 0){
						JOptionPane.showMessageDialog(null, "记录修改成功！");
					}
				}else{//客户名称不存在，新增记录
					currentRow = sheet.createRow(currentRows+1);
					currentRow.createCell(0).setCellValue(cd.getCusName());
					currentRow.createCell(1).setCellValue(cd.getFundNo());
					currentRow.createCell(2).setCellValue(cd.getCusType());
					currentRow.createCell(3).setCellValue(cd.getMailRecieves());
					currentRow.createCell(4).setCellValue(cd.getSendTime());
					currentRow.createCell(5).setCellValue(cd.getDataRange());
					currentRow.createCell(6).setCellValue(cd.getSendFrequency());
					currentRow.createCell(7).setCellValue(cd.getKeyWord());
					currentRow.createCell(8).setCellValue(cd.getMailSubject());
					FileOutputStream out = new FileOutputStream(outPath);
					xwb.write(out);
					out.flush();
					out.close();
					xwb.close();
					if(f.length() > 0){
						JOptionPane.showMessageDialog(null, "记录添加成功！");
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
    }
    //判断给定的字符串是否在excel指定列中
    public int isContainsStr(XSSFSheet sheet,String str,int searchCol){
    	int rowNum = -1;
    	int rows = sheet.getPhysicalNumberOfRows();
    	for(int i=0;i<rows;i++){
    		Cell cell = sheet.getRow(i).getCell(searchCol);
			String val = (String)readCell(cell);
    		if(val.equals(str)){
    			rowNum = i;
    			return rowNum;
    		}
    	}
    	return rowNum;
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
	                   break;
	               case Cell.CELL_TYPE_BLANK:
	                   value = null;
	           }
	       }
	       return value;
	   }
	//获取指定列的所有单元格内容
	 public String[] getCellValuesByColNum(String excelPath,int colNum){
		 String[] cellVals = null;
		 try {
			XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(excelPath));
			 XSSFSheet sheet = xwb.getSheet("0");
			 cellVals = new String[sheet.getPhysicalNumberOfRows()-1];
			 for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
				 Cell cell = sheet.getRow(i).getCell(colNum);
				 cellVals[i-1] = (String)readCell(cell);
			 }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return cellVals;
	 }
}