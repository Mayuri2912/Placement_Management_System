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
<title>Student Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

<style>
body{
    background:#f3f6fb;
    font-family:'Segoe UI';
}

/* SIDEBAR */
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

/* MAIN */
.main{
    margin-left:240px;
    padding:30px;
}

/* STAT CARDS */
.stat-card{
    padding:25px;
    border-radius:16px;
    color:white;
    box-shadow:0 10px 30px rgba(0,0,0,0.08);
    transition:0.3s;
}

.stat-card:hover{
    transform:translateY(-5px);
}

.blue{ background:linear-gradient(135deg,#2563eb,#3b82f6);}
.green{ background:linear-gradient(135deg,#059669,#10b981);}
.purple{ background:linear-gradient(135deg,#7c3aed,#a855f7);}

/* JOB CARD */
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

<!-- SIDEBAR -->
<div class="sidebar">
    <h4>PlaceMates</h4>

    <a href="studentDashboard"><i class="fa fa-home mr-2"></i> Dashboard</a>
    <a href="studentJobs"><i class="fa fa-briefcase mr-2"></i> Jobs</a>
    <a href="studentApplications"><i class="fa fa-file-alt mr-2"></i> Applications</a>
    <a href="studentProfile"><i class="fa fa-user mr-2"></i> My Profile</a>
    <a href="studentLogout" style="color:#ef4444;"><i class="fa fa-sign-out-alt mr-2"></i> Logout</a>
</div>
<a href="studentApplications"></a>

<!-- MAIN -->
<div class="main">

    <h3>Welcome back 👋</h3>
    <p class="text-muted"><strong><%= studentEmail %></strong></p>

    <!-- STATS -->
    <div class="row mt-4">

        <div class="col-md-4 mb-4">
            <div class="stat-card blue">
                <h6>Jobs Applied</h6>
                <h3>${totalApplications}</h3>
            </div>
        </div>

        <div class="col-md-4 mb-4">
            <div class="stat-card green">
                <h6>Shortlisted</h6>
                <h3>${shortlistedCount}</h3>
            </div>
        </div>

        <div class="col-md-4 mb-4">
            <div class="stat-card purple">
                <h6>Profile Views</h6>
                <h3>${profileViews}</h3>
                <small>Last 7 days</small>
            </div>
        </div>

    </div>

    <!-- RECOMMENDED JOBS -->
    <h5 class="mt-4 mb-3">Recommended Jobs</h5>

    <%
    List<String[]> jobs = (List<String[]>) request.getAttribute("recommendedJobs");
    if(jobs!=null){
        for(String[] job : jobs){
    %>

    <div class="job-card d-flex justify-content-between align-items-center">
        <div>
            <h5><%= job[0] %></h5>
            <p class="mb-1 text-muted"><%= job[1] %> • <%= job[2] %></p>
            <strong><%= job[3] %> LPA</strong>
        </div>

        <form action="ApplyJobServlet" method="post">
            <input type="hidden" name="jobId" value="<%= job[4] %>">
            <button class="btn btn-primary">Apply</button>
        </form>
    </div>

    <%
        }
    }
    %>

</div>

</body>
</html>
