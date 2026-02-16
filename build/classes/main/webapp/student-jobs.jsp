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
<title>All Jobs</title>

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

.job-card{
    background:white;
    border-radius:15px;
    padding:20px;
    box-shadow:0 5px 20px rgba(0,0,0,0.05);
    margin-bottom:20px;
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
    <a href="studentLogout" style="color:#ef4444;">
        <i class="fa fa-sign-out-alt mr-2"></i> Logout
    </a>
</div>

<div class="main">

<h3>Available Jobs</h3>
<hr>

<%
List<String[]> jobs = (List<String[]>) request.getAttribute("jobs");

if(jobs != null && !jobs.isEmpty()){
    for(String[] job : jobs){
%>

<div class="job-card d-flex justify-content-between align-items-center">
    <div>
        <h5><%= job[1] %></h5>
        <p class="mb-1 text-muted">
            <%= job[2] %> • <%= job[3] %>
        </p>
        <strong><%= job[4] %> LPA</strong>
        <p class="text-danger mt-1">
            Apply Before: <%= job[5] %>
        </p>
    </div>

    <form action="ApplyJobServlet" method="post">
        <input type="hidden" name="jobId" value="<%= job[0] %>">
        <button class="btn btn-primary">Apply</button>
    </form>
</div>

<%
    }
} else {
%>

<div class="alert alert-info">
    No jobs available right now.
</div>

<%
}
%>

</div>

</body>
</html>
