package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcel {
	//读
	public static void test1(){
		try {
//			Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\Administrator\\Desktop\\TestPoi\\账户余额报告.xlsx"));
//			Sheet sheet = workbook.getSheet(0);
//			for(int i=0;i<sheet.getRows();i++){
//				System.out.println(sheet.getCell(0,i).getContents()+"="+sheet.getCell(1,i).getContents());
//			}
			XSSFWorkbook xwb = new XSSFWorkbook(new File("C:\\Users\\Administrator\\Desktop\\TestPoi\\账户余额报告.xlsx"));// 构造XSSFWorkbook对象
			XSSFSheet sheet = xwb.getSheetAt(0);// 读取第一章表格内容
			XSSFRow row;
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				System.out.println(row.getCell(0)+"==="+row.getCell(1));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//写
	public static void test2(){
		try {
//			Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\Administrator\\Desktop\\TestPoi\\账户余额报告.xlsx"));
//			Sheet sheet = workbook.getSheet(0);
//			for(int i=0;i<sheet.getRows();i++){
//				System.out.println(sheet.getCell(0,i).getContents()+"="+sheet.getCell(1,i).getContents());
//			}
			XSSFWorkbook xwb = new XSSFWorkbook();// 构造XSSFWorkbook对象
			XSSFSheet sheet = xwb.createSheet("Sheet0");
			XSSFRow row = sheet.createRow(0);
			row.createCell(0).setCellValue("aaaa");
			row.createCell(1).setCellValue("bbbb");
			FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\TestPoi\\1.xlsx"));
			xwb.write(out);
			out.close();
			xwb.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//读
	public static void WriteToTemplate(){
		try {
			XSSFWorkbook xwb = new XSSFWorkbook(new File("C:\\Users\\Administrator\\Desktop\\示例\\TestTemplate\\单元资产_数据源.xlsx"));// 构造XSSFWorkbook对象
			XSSFSheet sheet = xwb.getSheetAt(0);// 读取第一章表格内容
			XSSFRow row;
			XSSFRow firstRow = sheet.getRow(0);
			XSSFWorkbook xwb1 = new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\示例\\TestTemplate\\单元资产_模板.xlsx")));// 构造XSSFWorkbook对象
			XSSFSheet sheet1 = xwb1.getSheetAt(0);
			XSSFRow row0 = sheet1.getRow(0);
			XSSFRow row1 = sheet1.getRow(1);
			Map map = new HashMap<String, String>();
			double fundNo = 0;
			FileOutputStream out = null;
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				fundNo = row.getCell(0).getNumericCellValue();
				for(int j = 0;j< row.getPhysicalNumberOfCells();j++){
					map.put(firstRow.getCell(j), row.getCell(j));
				}
				for(int k=0;k<row0.getPhysicalNumberOfCells();k++){
					 for (Object key : map.keySet()) { 
				        if(row0.getCell(k).getStringCellValue().equals(key.toString())){
				        	System.out.println(map.get(key).toString());
				        	row1.getCell(k).setCellValue(map.get(key).toString());
				        }
				        continue;
					 } 
				}
				out = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\示例\\TestTemplate\\单元资产_模板_"+fundNo+".xlsx"));
				xwb1.write(out);
			}
			out.close();
			xwb1.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		test1();
//		test2();
		WriteToTemplate();
	}
}
