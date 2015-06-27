package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.Dao;

public class TestDataBase {
	public static void test() throws SQLException{
		Dao dao = new Dao();
		ResultSet rs = dao.query("select * from ebiz_user t where t.channel_source = '01'");
		while(rs.next()){
			System.out.println(rs.getString("name"));
		}
	}
	
	public static void main(String[] args) throws SQLException {
		test();
	}
}
