package com.DBTool;

import java.sql.*;

public class FunctionOfMysql {
	private Connection conn=null;
	public void getUserConn() {
		String url="jdbc:mysql://localhost:3306/demodatabase";
        String user="root";
        String password="root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int UserLogin(String id,String pw)throws SQLException{
		String  sql="select * from demodatabase.demotable where uid=\'"+id+"\'";
		getUserConn();
		Statement stat=conn.createStatement();
		ResultSet result=stat.executeQuery(sql);
		if(!result.next()) {
			System.out.println("������");
			return 0;
		}
		if(!result.getString(2).equals(pw)) {
			System.out.println("�������");
			return 2;
		}
		System.out.println("��½�ɹ�1");
		return 1; 
	}

}
