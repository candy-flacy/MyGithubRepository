package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Dao {
	
	private static final String DRIVER=							
			"com.mysql.jdbc.Driver";
	private static final String URL=				

		"jdbc:mysql://121.40.63.45:3306/msthdb?useUnicode=true&amp;characterEncoding=UTF-8";
	//"jdbc:sqlserver://192.168.13.11:1433;databaseName=hrms";

	private static final String USER="msth_ebiz";//"root";
	private static final String PASSWORD="Msth2012";//"root";//数据库连接密码
	
	
	private static ThreadLocal<Connection> threadLocalConnection=new ThreadLocal<Connection>();
	private static ThreadLocal<Boolean> threadLocalTransaction=new ThreadLocal<Boolean>();
	
	static{
		
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
			System.out.println("没有找到驱动程序");
		}	
	}
	
	private boolean isTransaction(){
		if(threadLocalTransaction.get()==null){
			threadLocalTransaction.set(false);
		}
		return threadLocalTransaction.get();
	}
	private void setTransaction(boolean transaction){
		
		threadLocalTransaction.set(transaction);
		
	}
	
	
	private final Set<PreparedStatement> pstmts=new HashSet<PreparedStatement>();;
	private PreparedStatement pstmt;
	private final Set<ResultSet> rss=new HashSet<ResultSet>();;
	private ResultSet rs;
	
	private String array2String(Object... params){
		
		StringBuffer buffer=new StringBuffer("[");
		if(params!=null){
			for(int i=0;i<params.length;i++){
				if(i==0)buffer.append(params[i]);
				else buffer.append(","+params[i]);
			}
		}
		buffer.append("]");
		
		return buffer.toString();
		
	}
	
	private void preWork(String sql,Object... params) throws SQLException{
		
		
		pstmt = getConnection().prepareStatement(sql);	
		pstmts.add(pstmt);
		
		if(params!=null){			
			for(int i=0;i<params.length;i++){				
				if(params[i] instanceof java.util.Date){
					long time=((java.util.Date)params[i]).getTime();
					pstmt.setDate(i+1, new java.sql.Date(time));
				}else{			
					pstmt.setObject(i+1, params[i]);
				}
			}
		}
	}
	
	public void close(){
		for(ResultSet rs:rss){				
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
		}
		for(PreparedStatement pstmt:pstmts){
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
		}
		try {
			if (threadLocalConnection.get()!= null)
				if (!threadLocalConnection.get().isClosed())
					threadLocalConnection.get().close();			
		} catch (Exception e) {
		}
		threadLocalConnection.set(null);
	}
	
	public ResultSet query(String sql,Object... params) throws SQLException{
		System.out.println("----------------------------");
		System.out.println("查询sql:"+sql);
		System.out.println("sql参数:"+this.array2String(params));
		try {
			this.preWork(sql, params);		
			rs = pstmt.executeQuery();
			rss.add(rs);
			if(!isTransaction()){
				this.commitTransaction();				
			}
			return rs;
		} catch (SQLException e) {			
			e.printStackTrace();
			this.rollback();
			this.close();
			throw e;
		}catch (RuntimeException e) {			
			e.printStackTrace();
			this.rollback();
			this.close();
			throw e;
		}
		
	}
	
	public void update(String sql,Object... params) throws SQLException{
		System.out.println("----------------------------");
		System.out.println("更新sql:"+sql);
		System.out.println("sql参数:"+this.array2String(params));
		try {
			this.preWork(sql, params);
			pstmt.executeUpdate();
			if(!isTransaction()){
				this.commitTransaction();
				this.close();
			}
		}catch (SQLException e) {			
			e.printStackTrace();
			this.rollback();
			this.close();
			throw e;
		}catch (RuntimeException e) {			
			e.printStackTrace();
			this.rollback();
			this.close();
			throw e;
		}
		
	}
	
	public static Connection getConnection() throws SQLException{
		Connection conn=threadLocalConnection.get();
		if(conn==null){			
			conn = DriverManager.getConnection(
					URL,USER,PASSWORD);
			conn.setAutoCommit(false);
			threadLocalConnection.set(conn);			
		}
		return conn;
	}
	
	
	
	public void beginTransaction() throws SQLException{
		getConnection();
		this.setTransaction(true);	
		System.out.println("开始事务环境！");
	}
	
	
	
	public void commitTransaction() throws SQLException{
		
		try {
			getConnection().commit();
			if(isTransaction()){
				this.close();
				this.setTransaction(false);
				System.out.println("事务环境事务提交！");
			}else{
				System.out.println("单操作事务提交！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			this.rollback();
		}
	}
	
	private void rollback() throws SQLException{
		try{
			getConnection().rollback();
		}finally{					
			if(isTransaction()){
				this.close();
				this.setTransaction(false);
				System.out.println("事务环境事务回滚！");
			}else{
				System.out.println("单操作事务回滚！");
			}
		}
	}
	
/*	public static void main(String[] args) throws SQLException{
		Dao dao=new Dao();
		
		dao.beginTransaction();
		
		dao.update("insert into sys_user values('ddd','ddd','ddd',0) ", null);
		
		dao.update("insert into sys_user values('xxx','xxx','xxx',1) ", null);
		
		dao.commitTransaction();
	}
*/
	

}
