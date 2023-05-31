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


public class DemoSqlServerJdbcConnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processAction(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processAction(request,response);
	}

	private void processAction(HttpServletRequest request, HttpServletResponse response) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String urlstr="jdbc:sqlserver://localhost:1433;databaseName=Haopower;user=watcher;password=P@ssw0rd;encrypt=true;trustServerCertificate=true";
			Connection conn=DriverManager.getConnection(urlstr);
			
			response.setContentType("text/html;charset=UTF-8");
			
			PrintWriter out= response.getWriter();
			out.write("Connection Status:"+!conn.isClosed()+"<br/>");
			out.close();
			conn.close();
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
