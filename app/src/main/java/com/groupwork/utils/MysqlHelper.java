package com.groupwork.utils;
import java.sql.*;

public class MysqlHelper {

	public Connection con;
	public ResultSet rs;
	
	public MysqlHelper(String ip, String database){
		
		String driver = "com.mysql.jdbc.Driver";
		String url = String.format("jdbc:mysql://%s:3306/%s", ip, database);
		String user = "root";
		String password = "ubuntu";
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,password);
			if(!con.isClosed()){
				//System.out.println("Succeed");
			}
			else{
				System.out.println("Failed");
			}
		}
		catch(ClassNotFoundException e){
			System.out.println("Sorry can't find the Driver");
			e.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
	}
	
	public void execute(String sql){
		try{
			Statement statement = con.createStatement();
			rs = null;
			if(statement.execute(sql) == true){
				rs = statement.getResultSet();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			//System.out.println("execution error");
		}finally{
		}
	}
}
