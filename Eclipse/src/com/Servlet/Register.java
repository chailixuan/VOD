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
    //doGet ��������ר��������Ӧ GET ����
  	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		// TODO Auto-generated method stub
  		response.getWriter().append("Served at:").append(request.getContextPath());
  	}
  	//doPost ����ר��������Ӧ POST �����
  	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ID = request.getParameter("ID"); 
        String PW = request.getParameter("PW");
    	String EMAIL = request.getParameter("EMAIL"); 
      	String NAME = request.getParameter("NAME"); 
        /*�������post�ύ������ISO-8859-1�ı��뷽ʽת����UTF-8�ķ�ʽ*/
        NAME = new String(NAME.getBytes("ISO-8859-1"),"UTF-8");	
        EMAIL = new String(EMAIL.getBytes("ISO-8859-1"),"UTF-8");	
        System.out.println("�˻���"+ID);
        System.out.println("���룺"+PW);
        System.out.println("���䣺"+EMAIL);
        System.out.println("�ǳƣ�"+NAME);
        boolean type = false;
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        /* ����������һ����򵥵�ע���߼�����Ȼ�����ʵ��ҵ������൱���� */
        try
        {
        	Connection con = DBUtil.getConnection();
        	//Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��
        	Statement stmt=con.createStatement();
        	String sqlInsert = "insert into demodatabase.demotable(uid,pwd,uemail,uname) values("+ID+","+PW+",'"+EMAIL+"','"+NAME+"')";
        	//��ѯ���������һ��ResultSet���ϣ�û�в鵽���ʱResultSet�ĳ���Ϊ0
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