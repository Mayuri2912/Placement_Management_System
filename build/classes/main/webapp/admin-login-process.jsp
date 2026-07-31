<%@ page import="java.sql.*" %>

<%--
    LEGACY / UNUSED PAGE - left in place, not deleted, per project rules.

    This page is a raw-JDBC duplicate of the admin login logic that now
    lives properly in LoginHandler.java + UserDAO.validateUser(). The
    current admin-login.jsp form posts to "<contextPath>/LoginHandler",
    NOT to this file, and nothing else in the application references
    "admin-login-process.jsp" either. Left untouched and unused.
--%>

<%
String email = request.getParameter("email");
String password = request.getParameter("password");

Connection con = null;
PreparedStatement ps = null;
ResultSet rs = null;

try {
    Class.forName("com.mysql.cj.jdbc.Driver");

    con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/placement_db",
        "root",
        "root");  

    ps = con.prepareStatement(
        "SELECT * FROM admin WHERE email=? AND password=?");

    ps.setString(1, email);
    ps.setString(2, password);

    rs = ps.executeQuery();

    if(rs.next()) {
        session.setAttribute("admin", email);
        response.sendRedirect("index.jsp");
    } else {
        response.sendRedirect("admin-login.jsp?error=1");
    }

} catch(Exception e) {
    out.println(e);
}
%>
