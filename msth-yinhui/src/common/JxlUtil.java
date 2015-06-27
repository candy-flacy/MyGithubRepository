/*
 * JxlUtil.java Created on 2012-02
 * Copyright 2012 
 * All right reserved. 
 */
package common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * JXL工具类 
 * @author John
 */
public class JxlUtil {
	
	/**
	 * 读取excel数据
	 * 
	 * @param file
	 *            Excel文件
	 * @param rowFrom
	 *            起始行
	 * @param colFrom
	 *            起始列
	 * @return 列表
	 * @throws Exception
	 *             异常
	 */
	public static List readExcelData(File file, int rowFrom, int colFrom)
			throws Exception {
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet[] sheet = workbook.getSheets();
		List cellList = new ArrayList();
		// 遍历所有的工作簿，并循环取得每个单元格的数据
		for (int i = 0; i < sheet.length; i++) {
			Cell[][] cellArray = new Cell[sheet[i].getRows() - rowFrom][sheet[i]
					.getColumns()
					- colFrom];
			for (int j = 0 + rowFrom; j < sheet[i].getRows(); j++) {
				for (int k = 0 + colFrom; k < sheet[i].getColumns(); k++) {
					cellArray[j - rowFrom][k - colFrom] = sheet[i]
							.getCell(k, j);
				}
			}
			cellList.add(cellArray);
		}

		// 关闭，释放资源
		workbook.close();

		return cellList;
	}

	/**
	 * 读取excel数据
	 * 
	 * @param file
	 *            Excel文件
	 * @return 列表
	 * @throws Exception
	 *             异常
	 */
	public static List readExcelData(File file) throws Exception {
		return readExcelData(file, 0, 0);
	}

	/**
	 * 读取excel数据
	 * 
	 * @param file
	 *            Excel文件
	 * @return sheet名称列表
	 * @throws Exception
	 *             异常
	 */
	public static String[] readSheetName(File file) throws Exception {
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet[] sheet = workbook.getSheets();
		String[] sheetNameArr = new String[sheet.length];
		// 遍历所有的工作簿，并循环取得各sheet名称
		for (int i = 0; i < sheet.length; i++) {
			sheetNameArr[i] = sheet[i].getName();
		}

		// 关闭，释放资源
		workbook.close();

		return sheetNameArr;
	}

	/**
	 * 读取excel数据指定单元格的内容
	 * 
	 * @param c
	 *            cell数组
	 * @param postion
	 *            位置
	 * @return 指定单元格的内容
	 */
	public static String getContent(Cell[][] c, String postion) {
		String contents = c[Integer.parseInt(postion.substring(1))][Integer
				.parseInt(postion.substring(0, 1))] != null ? c[Integer
				.parseInt(postion.substring(1))][Integer.parseInt(postion
				.substring(0, 1))].getContents() : null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < contents.length(); i++) { // 处理结尾带问号的问题
			char ch = contents.charAt(i);
			if ((int) ch != 160 && (int) ch != 65533) {
				sb.append(ch);
			}
		}
		contents = sb.toString();
		return contents;
	}
	
	
}
