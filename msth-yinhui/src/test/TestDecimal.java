package test;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDecimal {
	public static void main(String[] args) {
		//格式化数字
		double d = 3.0E7;
		System.out.println(new DecimalFormat("#,###.00").format(d));
		
		//格式化字符串
		String a = "aaa_{0}_{1}";
		System.out.println(MessageFormat.format(a, "1111","2222"));
		
		//正则完全匹配与局部匹配测试
		System.out.println(Pattern.matches("\\d{5}", "aa12345aa"));
		System.out.println("aa12345aa".matches("\\d{5}"));
		Pattern p = Pattern.compile("\\d{5}");
		Matcher m = p.matcher("aa(12345)aa");
//		boolean b = m.matches();
		if(m.find()){//matches完全匹配,find局部匹配
			System.out.println(m.group());
		}
	}
}
