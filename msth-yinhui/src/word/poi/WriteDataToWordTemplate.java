package word.poi;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WriteDataToWordTemplate {
	public static void searchAndReplace(String srcPath, String destPath,
			Map<String, String> map) {
		try {
			XWPFDocument document = new XWPFDocument(POIXMLDocument
					.openPackage(srcPath));
			// Iterator it = document.getTablesIterator();
			// while (it.hasNext()) {
			// XWPFTable table = (XWPFTable) it.next();
			// int rcount = table.getNumberOfRows();
			// System.out.println(rcount);
			// for (int i = 0; i < rcount; i++) {
			// XWPFTableRow row = table.getRow(i);
			// List<XWPFTableCell> cells = row.getTableCells();
			// for (XWPFTableCell cell : cells) {
			// for (Entry<String, String> e : map.entrySet()) {
			// if (cell.getText().equals(e.getKey())) {
			// cell.removeParagraph(0);
			// cell.setText(e.getValue());
			// }
			// }
			// }
			// }
			// }
			Iterator<XWPFParagraph> iterator = document.getParagraphsIterator();
			System.out.println("段落："+document.getParagraphs().size());
			XWPFParagraph para;
			List<XWPFRun> runs;
			while (iterator.hasNext()) {
				para = iterator.next();
				runs = para.getRuns();
				if(runs.size()>0){
//					System.out.println("runs:"+runs.size());
					for (int i = 0; i < runs.size(); i++) {
						XWPFRun run = runs.get(i);
						String runText = run.toString();
						if(runText.contains("$")){
//						System.out.println("run内容：====================="+runText.replaceAll("\r|\n",""));
						Pattern pattern = Pattern.compile("(\\$\\{.+?\\})",Pattern.CASE_INSENSITIVE);
						String new_runText = runText.replaceAll("\r|\n","");
						Matcher matcher = pattern.matcher(new_runText);
						while (matcher.find()) {
							System.out.println(matcher.groupCount() + "====");
							for (int j = 0; j < matcher.groupCount(); j++) {
//								runText = matcher.replaceFirst(String.valueOf(map.get(matcher.group(j))));
								System.out.println(new_runText+"========================1====="+matcher.group(j));
								runText = new_runText.replace(matcher.group(j), String.valueOf(map.get(matcher.group(j))));
								System.out.println(runText+"========================2");
								para.removeRun(i);
								para.insertNewRun(i).setText(runText);
								new_runText = runText;
							}
						}
					}
	
					}
				}
			}
			FileOutputStream outStream = null;
			outStream = new FileOutputStream(destPath);
			document.write(outStream);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		HashMap map = new HashMap();
		map.put("${name}", "王五");
		map.put("${code}", "8886666");
		String srcPath = "C:\\Users\\Administrator\\Desktop\\TestPoi\\账户余额报告.docx";
		String destPath = "C:\\Users\\Administrator\\Desktop\\TestPoi\\账户余额报告1.docx";
		searchAndReplace(srcPath, destPath, map);
	}
}
