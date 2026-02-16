<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, dao.UserDAO" %>

<%
String adminEmail = (String) session.getAttribute("admin");
if (adminEmail == null) {
    response.sendRedirect("admin-login.jsp");
    return;
}

UserDAO dao = new UserDAO();
List<String[]> applications = dao.getAllApplications();
%>

<!DOCTYPE html>
<html>
<head>
<title>Manage Applications</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
body { background:#f4f6f9; font-family:'Segoe UI'; }
.sidebar {
    height:100vh;
    background:#0f172a;
    position:fixed;
    width:230px;
    padding-top:20px;
}
.sidebar a {
    display:block;
    padding:12px 20px;
    color:#cbd5e1;
    text-decoration:none;
}
.sidebar a:hover { background:#1e293b; color:white; }
.main-content { margin-left:230px; padding:25px; }
.card-box {
    background:white;
    padding:20px;
    border-radius:12px;
    box-shadow:0 5px 15px rgba(0,0,0,0.05);
}
</style>
</head>

<body>

<div class="sidebar">
    <h4 class="text-center text-white">Admin Panel</h4>
    <a href="admin-dashboard.jsp">Dashboard</a>
    <a href="manage-students.jsp">Students</a>
    <a href="manage-companies.jsp">Companies</a>
    <a href="manage-jobs.jsp">Jobs</a>
    <a href="manage-applications.jsp" style="background:#1e293b;">Applications</a>
    <a href="AdminLogoutServlet" style="color:red;">Logout</a>
</div>

<div class="main-content">

<h3>Manage Applications</h3>
<hr>

<div class="card-box">

<table class="table table-bordered">
<thead class="thead-dark">
<tr>
<th>ID</th>
<th>Student</th>
<th>Job</th>
<th>Company</th>
<th>Applied On</th>
<th>Status</th>
<th>Action</th>
</tr>
</thead>

<tbody>

<%
for(String[] a : applications){
%>

<tr>
<td><%= a[0] %></td>
<td><%= a[1] %></td>
<td><%= a[2] %></td>
<td><%= a[3] %></td>
<td><%= a[5] %></td>
<td>
<form action="UpdateApplicationStatusServlet" method="post" class="form-inline">

<input type="hidden" name="applicationId" value="<%= a[0] %>">

<select name="status" class="form-control mr-2">

<option <%= a[4].equals("APPLIED")?"selected":"" %>>APPLIED</option>
<option <%= a[4].equals("SHORTLISTED")?"selected":"" %>>SHORTLISTED</option>
<option <%= a[4].equals("REJECTED")?"selected":"" %>>REJECTED</option>
<option <%= a[4].equals("SELECTED")?"selected":"" %>>SELECTED</option>

</select>

<button type="submit" class="btn btn-primary btn-sm">
Update
</button>

</form>
</td>

</tr>

<%
}
%>

</tbody>
</table>

</div>

</div>

</body>
</html>
