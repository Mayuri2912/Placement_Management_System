<%@ page import="java.sql.*" %>

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
