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


public class setaccount extends HttpServlet {
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
		
		
		try {
			String userName = request.getParameter("userName");
			String pwd = request.getParameter("userPassword");
			String con = request.getParameter("action");
			createconn();
			
			//...
			
			out.write("<hr/>");
			queryinfo();
			out.write("執行過後<br/>");
			if("修改帳號".equals(con)) {
				out.write("修改中<hr/>");
				updateInfo(userName,pwd);
			}else {
				out.write("刪除中<hr/>");
				deleteInfo(userName,pwd);
			}
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
