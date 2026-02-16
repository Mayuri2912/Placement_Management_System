<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<%
String studentEmail = (String) session.getAttribute("student");
if (studentEmail == null) {
    response.sendRedirect("student-login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<title>My Applications</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

<style>
body{
    background:#f3f6fb;
    font-family:'Segoe UI';
}

.sidebar{
    height:100vh;
    width:240px;
    background:#111827;
    position:fixed;
    color:white;
    padding-top:20px;
}

.sidebar h4{
    text-align:center;
    margin-bottom:30px;
}

.sidebar a{
    display:block;
    padding:12px 20px;
    color:#cbd5e1;
    text-decoration:none;
    border-radius:8px;
    margin:5px 10px;
}

.sidebar a:hover{
    background:#1f2937;
    color:white;
}

.main{
    margin-left:240px;
    padding:30px;
}

.card{
    background:white;
    border-radius:15px;
    padding:20px;
    box-shadow:0 5px 20px rgba(0,0,0,0.05);
}
</style>
</head>

<body>

<div class="sidebar">
    <h4>PlaceMates</h4>

    <a href="studentDashboard"><i class="fa fa-home mr-2"></i> Dashboard</a>
    <a href="studentJobs"><i class="fa fa-briefcase mr-2"></i> Jobs</a>
    <a href="studentApplications"><i class="fa fa-file-alt mr-2"></i> Applications</a>
    <a href="studentProfile"><i class="fa fa-user mr-2"></i> My Profile</a>
    <a href="studentLogout" style="color:#ef4444;"><i class="fa fa-sign-out-alt mr-2"></i> Logout</a>
</div>

<div class="main">

<h3>My Applications</h3>
<hr>

<div class="card">

<table class="table table-bordered">
<thead class="thead-dark">
<tr>
    <th>Job</th>
    <th>Company</th>
    <th>Status</th>
    <th>Applied Date</th>
</tr>
</thead>

<tbody>

<%
List<String[]> apps =
        (List<String[]>) request.getAttribute("applications");

if(apps != null && !apps.isEmpty()){
    for(String[] row : apps){
%>

<tr>
    <td><%= row[1] %></td>
    <td><%= row[2] %></td>
    <td>
<%
    String status = row[3];
    String badgeClass = "badge-secondary";

    if("APPLIED".equalsIgnoreCase(status)){
        badgeClass = "badge-primary";     // Blue
    }
    else if("SHORTLISTED".equalsIgnoreCase(status)){
        badgeClass = "badge-success";     // Green
    }
    else if("REJECTED".equalsIgnoreCase(status)){
        badgeClass = "badge-danger";      // Red
    }
    else if("SELECTED".equalsIgnoreCase(status)){
        badgeClass = "badge-dark";        // Purple alternative below
    }
%>

<span class="badge <%= badgeClass %>">
    <%= status %>
</span>

</td>
        <td><%= row[4] %></td>
</tr>

<%
    }
} else {
%>
<tr>
    <td colspan="4" class="text-center">
        No applications found.
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
