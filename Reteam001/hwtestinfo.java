package ke.te.action;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import bean.Profiles;

public class hwtestinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	private PrintWriter out;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		processAction(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		processAction(request,response);

	}


	private void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		out=response.getWriter();
		try {
			String userName = request.getParameter("userName");
			String pwd = request.getParameter("userPassword");
			
			createconn();
			
			boolean res= querybyinfo(userName, pwd);
			
			Profiles profiles=null;
			
			if(res) {
				profiles=new Profiles(userName,pwd);
				out.write(userName+" 登入成功!!<br/>");
			}else {
				out.write("帳號錯誤! <br/>請重新輸入!!<br/>");
			}
			out.write("<hr/>");
			HttpSession sess=request.getSession();
			
			sess.setAttribute("loginSession",profiles);
			
			Profiles props=(Profiles)sess.getAttribute("loginSession");
			
			closeconn();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		out.close();
	}

	private void createconn() throws NamingException, SQLException{
		InitialContext context =new InitialContext();
		DataSource ds =(DataSource)context.lookup("java:comp/env/connectSQLServer/SystemService");
		conn = ds.getConnection();
		boolean status=!conn.isClosed();			
	}
	private void closeconn() throws SQLException{
		if(conn!=null) {
			conn.close();

			
		}
		
	}
	
	private boolean querybyinfo(String user,String pwd) throws SQLException {
		String sqlstr="select*from Profiles where username=? and userpwd=?" ;
		PreparedStatement sta=conn.prepareStatement(sqlstr);
		sta.setString(1, user);
		sta.setString(2, pwd);
		
		ResultSet rs =sta.executeQuery();
		boolean status=rs.next();
		rs.close();
		sta.close();
		
		if(status) {
			return true;
		}
		return false;
		
	}
	
}

