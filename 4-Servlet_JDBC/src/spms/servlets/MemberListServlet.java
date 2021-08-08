package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {

  @Override
  public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
      ServletContext sc = this.getServletContext();
      Class.forName(sc.getInitParameter("driver"));
      conn = DriverManager.getConnection(sc.getInitParameter("url"),
          sc.getInitParameter("username"), sc.getInitParameter("password"));
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select mno, mname, email, cre_date from members order by mno asc");
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<html><head><title>회원목록</title></head>");
      out.println("<body><h1>회원목록</h1>");
      out.println("<p><a href='add'>신규 회원</a><br></p>");
      while(rs.next()) {
        out.println(
            rs.getInt("MNO") + "," +
            "<a href='update?no=" + rs.getInt("mno") + "'>" + rs.getString("MNAME") + "</a>," +
            rs.getString("EMAIL") + "," +
            rs.getDate("CRE_DATE") + "<br>"
        );
      }
      out.println("</body></html>");
    } catch(Exception e) {
      throw new ServletException(e);
    } finally {
      try { if(rs != null) rs.close();} catch(Exception e) {}
      try { if(stmt != null) stmt.close();} catch(Exception e) {}
      try { if(stmt != null) stmt.close();} catch(Exception e) {}
    }
  }
}