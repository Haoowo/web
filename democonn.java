package ke.te.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Ex2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	private Connection conn;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processAction(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processAction(request,response);
	}
	private void processAction(HttpServletRequest request, HttpServletResponse response)throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		out=response.getWriter();
		
//		String userName=request.getParameter("uesrName");
//		String userPwd=request.getParameter("uesrPwd");
//		
		out.write("test<br>");
		
		try {
			createconn();
			
			//...
			out.write("<hr/>");
			
			//queryinfo();
//			boolean r1=querybyinfo("ra","123tt");
//			out.write("result1:"+r1+"<br/>");
//			
//			boolean r2=querybyinfo("rabbit","tt123");
//			out.write("result2:"+r2+"<br/>");
//			
//			boolean r3=querybyinfo(userName,userPwd);
//			out.write("result3:"+r3+"<br/>");
			
			//insertInfo("mary","nosecret");
			
			//updateInfo("mary","XD123");
			queryinfo();
			deleteInfo("mary", "XD123");
			queryinfo();
			
			out.write("<hr/>");
						
			closeconn();				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		out.close();
	}
	private void deleteInfo(String user,String userpwd) throws SQLException {
		String sqlstr="delete from Profiles where username='"+user+"'and userpwd='"+userpwd+"'";
		out.write("sqlstr"+sqlstr+"<br/>");
		
		Statement state= conn.createStatement();
		state.execute(sqlstr);
		state.close();

}
	private void updateInfo(String user,String newpwd) throws SQLException {
		String sqlstr="update Profiles set userPwd='"+newpwd+"'where username='"+user+"'";
		
		out.write("sqlstr"+sqlstr+"<br/>");
		
		Statement state= conn.createStatement();
		state.execute(sqlstr);
		state.close();
		
		
	}
	private void insertInfo(String user,String pwd) throws SQLException {
		String sqlstr="insert into Profiles(username,userpwd) values('"+user+"','"+pwd+"')";
		out.write("sqlstr"+sqlstr+"<br/>");
		
		Statement state= conn.createStatement();
		state.execute(sqlstr);
		state.close();
		
	}
	
	private boolean querybyinfo(String user,String pwd) throws SQLException {
		String sqlstr="select*from Profiles where username='"+user+"'and userpwd='"+pwd+"'";
		out.write("sqlstr:"+sqlstr+"<br/>");
		
		Statement state=conn.createStatement();
		ResultSet rs =state.executeQuery(sqlstr);
		boolean status =rs.next();
		rs.close();
		state.close();
		if(status) {
			return true;
		}
		return false;
	}
	
	private void queryinfo() throws SQLException {
		String sqlstr="select*from Profiles";
		Statement state=conn.createStatement();
		ResultSet rs =state.executeQuery(sqlstr);
		
		while(rs.next()){
			out.write(rs.getInt(1)+" | "+rs.getString(2)+" | "+rs.getString(3)+"<br/>");
		}
		rs.close();
		state.close();
	}
	
	private void createconn() throws SQLException,ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String urlstr="jdbc:sqlserver://localhost:1433;databaseName=Haopower;user=watcher;password=P@ssw0rd;encrypt=true;trustServerCertificate=true";
		conn=DriverManager.getConnection(urlstr);
		out.write("Connection Status:　"+!conn.isClosed()+"<br/>");
		
	}
	private void closeconn() throws SQLException {
		if(conn!=null) {
			conn.close();
			out.write("Closed");
		}
	}

}
