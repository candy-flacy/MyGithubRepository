package socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class newExcel {

	private static int typeWeiZhi = 0;
	private static int dateWeiZhi = 0;

	// date url 成交汇总查询报表.xls
	private final static String transAction_Collect_Date_URl = "C:/Users/Administrator/Desktop/示例/数据源/成交汇总查询报表.xls";

	// date Url 综合信息查询_成交回报.xls
	private final static String transAction_Return_Date_URl = "C:/Users/Administrator/Desktop/示例/数据源/综合信息查询_成交回报.xls";

	// date Url 综合信息查询_单元资产.xls
	private final static String unit_Property_Date_URl = "C:/Users/Administrator/Desktop/示例/数据源/综合信息查询_单元资产.xls";

	// date Url 综合信息查询_组合证券.xls
	private final static String group_Security_Date_URl = "C:/Users/Administrator/Desktop/示例/数据源/综合信息查询_组合证券.xls";

	// model url 受托-成交回报.xls
	private final static String trustee_TransActionReturnURL = "C:/Users/Administrator/Desktop/示例/模板/受托-成交回报.xls";

	// model url 受托-成交汇总查询报表.xls
	private final static String trustee_transActionCollectURL = "C:/Users/Administrator/Desktop/示例/模板/受托-成交汇总查询报表.xls";
	// model url 受托-综合信息查询_单元资产.xls
	private final static String trustee_UnitPropertyURL = "C:/Users/Administrator/Desktop/示例/模板/受托-综合信息查询_单元资产.xls";
	// model url 受托-综合信息查询_组合证券.xls
	private final static String trustee_GroupSecurityURL = "C:/Users/Administrator/Desktop/示例/模板/受托-综合信息查询_组合证券.xls";

	// model url 资管-成交回报.xls
	private final static String danaharta_TransActionReturnURL = "C:/Users/Administrator/Desktop/示例/模板/资管-成交回报.xls";
	// model url 资管-成交汇总查询报表.xls
	private final static String danaharta_TransActionCollectURL = "C:/Users/Administrator/Desktop/示例/模板/资管-成交汇总查询报表.xls";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		diaoYong();

	}

	public static void diaoYong() {

		Map<String, Object> userMap = mapValue();

		String task = (String) userMap.get("task");

		if ("受托客户".equals(task)) {
			readExcel(transAction_Collect_Date_URl,
					trustee_transActionCollectURL, userMap);

			readExcel(transAction_Return_Date_URl,
					trustee_TransActionReturnURL, userMap);

			readExcel(unit_Property_Date_URl, trustee_UnitPropertyURL, userMap);

			readExcel(group_Security_Date_URl, trustee_GroupSecurityURL,
					userMap);

		} else if ("资管产品".equals(task)) {
			readExcel(transAction_Collect_Date_URl,
					danaharta_TransActionCollectURL, userMap);
			readExcel(transAction_Return_Date_URl,
					danaharta_TransActionReturnURL, userMap);
		} else {
			System.out.println("无效任务类型");
			return;
		}
	}

	public static void readExcel(String dataURL, String modelURL,
			Map<String, Object> userMap) {
		try {
			InputStream in = new FileInputStream(dataURL);
			HSSFWorkbook print = new HSSFWorkbook(in);
			HSSFSheet sheet1 = print.getSheetAt(0);

			int rows = sheet1.getLastRowNum();
			System.out.println(rows + "总行数");
			for (int i = 0; i <= rows; i++) {

				if (i == 0) {
					for (int y = 0; y < sheet1.getRow(i)
							.getPhysicalNumberOfCells(); y++) {
						if (sheet1.getRow(i).getCell(y).getStringCellValue()
								.equals("基金编号")) {

							typeWeiZhi = sheet1.getRow(i).getCell(y)
									.getColumnIndex();
						}

						String dateFlag = sheet1.getRow(i).getCell(y)
								.getStringCellValue();
						if (dateFlag.equals("日期") || dateFlag.equals("统计日期")
								|| dateFlag.equals("发生日期")) {
							dateWeiZhi = sheet1.getRow(i).getCell(y)
									.getColumnIndex();
						}
					}
					continue;
				}

				for (int j = 0; j < sheet1.getRow(i).getPhysicalNumberOfCells(); j++) {

					sheet1.getRow(i)
							.getCell(j)
							.setCellType(
									org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
				}

				String name = (String) userMap.get("name");
				String fundId = (String) userMap.get("fundId");
				String task = (String) userMap.get("task");
				String email = (String) userMap.get("email");
				String queryTime = (String) userMap.get("queryTime");
				String[] typeArry = fundId.split(";");
				HSSFRow firstRow = sheet1.getRow(0);

				System.out.println(sheet1.getRow(i).getCell(2)
						.getStringCellValue()
						+ "$$$$$$$$$$$$$");
				HSSFRow rowF = equalsZorS(typeArry, queryTime, sheet1.getRow(i));
				if (rowF == null) {
					System.out.println(i + "根据时间和基金类型无匹配数据！！！");
					continue;
				}

				excelImport(rowF, firstRow, modelURL, dataURL, name, queryTime);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 造客户
	 * 
	 * @return
	 */
	public static Map<String, Object> mapValue() {
		// Map<String, Object> userMap = new HashMap<String, Object>();

		Map<String, Object> userAnHua = new HashMap<String, Object>();

		userAnHua.put("name", "油条");
		userAnHua.put("fundId", "1;3;4;6;13");
		userAnHua.put("task", "受托客户");
		userAnHua.put("email", "360901061@qq.com");
		userAnHua.put("queryTime", "2015-06-10");

		Map<String, Object> shouTuoObj = new HashMap<String, Object>();
		shouTuoObj.put("sendTime", "2015-06-22");

		Map<String, Object> ziGuanObj = new HashMap<String, Object>();

		ziGuanObj.put("sendTime", "2015-06-22");

		Map<String, Object> qiTaObj = new HashMap<String, Object>();

		return userAnHua;

	}

	/**
	 * 导入受托Excel模版 受托-成交汇总查询报表.xls
	 * 
	 * @throws Exception
	 */
	public static void excelImport(HSSFRow row, HSSFRow firstRow,
			String modleUrl, String dateUrl, String fileName, String fileDate)
			throws Exception {

		InputStream imp = new FileInputStream(modleUrl);
		HSSFWorkbook shouTuoImport = new HSSFWorkbook(imp);
		HSSFSheet sheetImport = shouTuoImport.getSheetAt(0);

		createDirectory("D:/" + fileDate);
		createDirectory("D:/" + fileDate + "/" + fileName);
		String fileXSL = subFileName(dateUrl);
		File f = new File("D:/" + fileDate + "/" + fileName + "/" + fileXSL);

		Map<String, Object> rowMap = setCellValue(row, firstRow);

		if (!f.exists()) {

			HSSFRow rowImport = sheetImport.createRow(sheetImport
					.getLastRowNum() + 1);
			for (int i = 0; i < sheetImport.getRow(0)
					.getPhysicalNumberOfCells(); i++) {

				String str = (String) rowMap.get(sheetImport.getRow(0)
						.getCell(i).getStringCellValue());

				rowImport.createCell(i).setCellValue(str);

			}

			f.createNewFile();

			FileOutputStream fos = new FileOutputStream(f);
			shouTuoImport.write(fos);

		} else {
			InputStream impDate = new FileInputStream(f);
			HSSFWorkbook shouTuoImportDate = new HSSFWorkbook(impDate);
			HSSFSheet sheetImportDate = shouTuoImportDate.getSheetAt(0);

			int rowNum = sheetImportDate.getLastRowNum();

			HSSFRow rowImportDate = sheetImportDate.createRow(rowNum + 1);

			for (int i = 0; i < sheetImport.getRow(0)
					.getPhysicalNumberOfCells(); i++) {
				String str = (String) rowMap.get(sheetImport.getRow(0)
						.getCell(i).getStringCellValue());

				rowImportDate.createCell(i).setCellValue(str);

			}
			FileOutputStream fosDate = new FileOutputStream(f);
			shouTuoImportDate.write(fosDate);
		}

	}

	/**
	 * 判断基金类型
	 * 
	 * @param str
	 * @return
	 */
	public static HSSFRow equalsZorS(String[] typeArry, String queryTime,
			HSSFRow row) {

		String dateWZ = row.getCell(dateWeiZhi).getStringCellValue();

		System.out.println(dateWZ + "------------------------");
		String typeWZ = row.getCell(typeWeiZhi).getStringCellValue();

		for (int i = 0; i < typeArry.length; i++) {

			if (typeArry[i].equals(typeWZ) && queryTime.equals(dateWZ)) {
				System.out.println("匹配！！！");
				return row;
			}
			;
		}
		return null;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param fileUrl
	 * @return
	 * @throws IOException
	 */
	public static File createDirectory(String fileUrl) throws IOException {
		File fileDate = new File(fileUrl);
		if (!fileDate.exists() && !fileDate.isDirectory()) {
			fileDate.mkdir();
		}
		return fileDate;
	}

	/**
	 * 根据路径截取文件名
	 * 
	 * @param str
	 * @return
	 */
	public static String subFileName(String str) {

		int gan = str.lastIndexOf("/");

		return str.substring(gan + 1);

	}

	/**
	 * 设置 excel的值
	 * 
	 * @param row
	 * @param firstRow
	 * @return
	 */
	public static Map<String, Object> setCellValue(HSSFRow row, HSSFRow firstRow) {

		Map<String, Object> mapRow = new HashMap<String, Object>();

		for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
			mapRow.put(firstRow.getCell(i).getStringCellValue(), row.getCell(i)
					.getStringCellValue());
		}
		return mapRow;
	}

	public static void sendMessageMap(Map<String, Object> user) {
		Map<String, Object> mapMessage = new HashMap<String, Object>();
		mapMessage.put("sendpeople", ""); // 发送人
		mapMessage.put("receiveperson", user.get("email")); // 接收人
		mapMessage.put("senddate", ""); // 发送时间
		mapMessage.put("eSendpeoplepassword", ""); // 发送密码
		mapMessage.put("sendcontent", ""); // 邮件内容
	}
}
