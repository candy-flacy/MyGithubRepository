package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class TestOthers {
	@Test
	public void testBase64(){
		byte[] data1 = Base64.encodeBase64("abcd".getBytes());
		System.out.println(new String(data1));
		byte[] data2 = Base64.decodeBase64(data1);
		System.out.println(new String(data2));
	}
	
	@Test
	public void testMd5(){
		String data1 = DigestUtils.md5Hex("abcd");
		System.out.println(data1);
	}
	
	@Test
	public void testCalendar(){
		Calendar c = Calendar.getInstance();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
	}
	
	@Test
	public void testJson(){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> user = new HashMap<String,Object>();
		user.put("name", "张三");
		user.put("sex", "女");
		user.put("phone", "112421");
		map.put("001",user);
		map.put("002","李四");
		map.put("003","王五");
		System.out.println(JSON.toJSONString(map));
	}
}
