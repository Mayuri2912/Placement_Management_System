<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, dao.UserDAO" %>

<%
String adminEmail = (String) session.getAttribute("admin");
if (adminEmail == null) {
    response.sendRedirect("admin-login.jsp");
    return;
}

UserDAO dao = new UserDAO();
List<String[]> companies = dao.getCompanyDropdown();
List<String[]> jobs = dao.getAllJobsForAdmin();
%>

<!DOCTYPE html>
<html>
<head>
<title>Manage Jobs</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
body { background:#f4f6f9; font-family:'Segoe UI'; }

.sidebar {
    height:100vh;
    background:#0f172a;
    position:fixed;
    width:230px;
    padding-top:20px;
    color:white;
}

.sidebar a {
    display:block;
    padding:12px 20px;
    color:#cbd5e1;
    text-decoration:none;
}

.sidebar a:hover {
    background:#1e293b;
    color:white;
}

.main-content {
    margin-left:230px;
    padding:25px;
}

.card-box {
    background:white;
    padding:20px;
    border-radius:12px;
    box-shadow:0 5px 15px rgba(0,0,0,0.05);
    margin-bottom:20px;
}
</style>
</head>

<body>

<div class="sidebar">
    <h4 class="text-center">Admin Panel</h4>

    <a href="admin-dashboard.jsp">Dashboard</a>
    <a href="manage-students.jsp">Students</a>
    <a href="manage-companies.jsp">Companies</a>
    <a href="manage-jobs.jsp" style="background:#1e293b;">Jobs</a>
    <a href="manage-applications.jsp">Applications</a>
    <a href="AdminLogoutServlet" style="color:#ef4444;">Logout</a>
</div>

<div class="main-content">

<h3>Manage Jobs</h3>
<hr>

<!-- ADD JOB -->
<div class="card-box">
<h5>Add New Job</h5>

<form action="AddJobServlet" method="post">

<div class="row">

<div class="col-md-3">
<label>Company</label>
<select name="companyId" class="form-control" required>
<option value="">Select Company</option>
<%
for(String[] c : companies){
%>
<option value="<%= c[0] %>"><%= c[1] %></option>
<%
}
%>
</select>
</div>

<div class="col-md-3">
<label>Job Title</label>
<input type="text" name="title" class="form-control" required>
</div>

<div class="col-md-2">
<label>Salary</label>
<input type="text" name="salary" class="form-control" required>
</div>

<div class="col-md-2">
<label>Last Date</label>
<input type="date" name="lastDate" class="form-control" required>
</div>

<div class="col-md-2 d-flex align-items-end">
<button type="submit" class="btn btn-primary btn-block">
Add Job
</button>
</div>

</div>
</form>
</div>

<!-- JOB LIST -->
<div class="card-box">
<h5>Job List</h5>

<table class="table table-bordered">
<thead class="thead-dark">
<tr>
<th>ID</th>
<th>Title</th>
<th>Company</th>
<th>Salary</th>
<th>Last Date</th>
<th>Action</th>
</tr>
</thead>

<tbody>

<%
for(String[] j : jobs){
%>

<tr>
<td><%= j[0] %></td>
<td><%= j[1] %></td>
<td><%= j[2] %></td>
<td><%= j[3] %></td>
<td><%= j[4] %></td>
<td>
<a href="DeleteJobServlet?id=<%= j[0] %>" 
   class="btn btn-danger btn-sm"
   onclick="return confirm('Delete this job?')">
Delete
</a>
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
