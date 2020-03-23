package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }
    //doGet 方法就是专门用来响应 GET 请求
  	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		// TODO Auto-generated method stub
  		response.getWriter().append("Served at:").append(request.getContextPath());
  	}
  	//doPost 方法专门用来响应 POST 请求的
  	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ID = request.getParameter("ID"); 
        String PW = request.getParameter("PW");
    	String EMAIL = request.getParameter("EMAIL"); 
      	String NAME = request.getParameter("NAME"); 
        /*将浏览器post提交过来的ISO-8859-1的编码方式转换成UTF-8的方式*/
        NAME = new String(NAME.getBytes("ISO-8859-1"),"UTF-8");	
        EMAIL = new String(EMAIL.getBytes("ISO-8859-1"),"UTF-8");	
        System.out.println("账户："+ID);
        System.out.println("密码："+PW);
        System.out.println("邮箱："+EMAIL);
        System.out.println("昵称："+NAME);
        boolean type = false;
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        /* 这里我们做一个最简单的注册逻辑，当然，你的实际业务可以相当复杂 */
        try
        {
        	Connection con = DBUtil.getConnection();
        	//Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
        	Statement stmt=con.createStatement();
        	String sqlInsert = "insert into demodatabase.demotable(uid,pwd,uemail,uname) values("+ID+","+PW+",'"+EMAIL+"','"+NAME+"')";
        	//查询类操作返回一个ResultSet集合，没有查到结果时ResultSet的长度为0
			stmt.executeUpdate(sqlInsert);
			type = true;
//			while(rs.next())
//		    {
//		    	type = true;
//		    	UN = rs.getString("user_name");
//		    	EM = rs.getString("email");
//		    	PRO = rs.getString("property");
//		    }
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        finally
        {
        	DBUtil.Close();
        	out.print(type);
//        	out.print(ID);
//        	out.print(PW);
//        	out.print(UN);
//        	out.print(EM);
//        	out.print(PRO);
        	out.flush();
        	out.close();
        }
	}
}