package com.Servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;
import entity.Info;



/**
 * Servlet implementation class RecordServlet
 */
@WebServlet("/RecordServlet")
public class RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ID = request.getParameter("ID"); 
		response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
			Connection con =DBUtil.getConnection();
			Statement statement = con.createStatement();
			String sql="select vid,vname,vpath,vtype,uid from videotable where uid ='"+ID+"'";
			ResultSet rs = statement.executeQuery(sql);
			List<Info>info_list = new ArrayList<Info>();
			while(rs.next()) {
				String vid = rs.getString("vid");
				String vname = rs.getString("vname");
				String vpath = rs.getString("vpath");
				String vtype = rs.getString("vtype");
				String uid = rs.getString("uid");
				Info info = new Info(vid,vname,vpath,vtype);
				info_list.add(info); 
				
			}
			System.out.println("ด๓ะก"+info_list.size());
			for(Info info:info_list) {
				out.print(info.getVid()+",");
				out.print(info.getVname()+",");
				out.print(info.getVpath()+",");
			    out.print(info.getVtype());
		     	out.println();
			}
			out.flush();
			out.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
