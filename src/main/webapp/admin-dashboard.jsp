<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<%
String adminEmail = (String) session.getAttribute("admin");
if (adminEmail == null) {
    response.sendRedirect("admin-login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<title>Admin Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<style>
body {
    background:#f1f5f9;
    font-family:'Segoe UI';
}

/* SIDEBAR */
.sidebar {
    height:100vh;
    background:#0f172a;
    position:fixed;
    width:240px;
    padding-top:20px;
    color:white;
}

.sidebar h4 {
    font-weight:bold;
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

/* MAIN */
.main-content {
    margin-left:240px;
    padding:30px;
}

/* STAT CARDS */
.stat-card {
    border-radius:15px;
    padding:25px;
    color:white;
    box-shadow:0 10px 25px rgba(0,0,0,0.08);
    transition:0.3s;
}

.stat-card:hover {
    transform:translateY(-5px);
}

.bg-blue {
    background: linear-gradient(135deg,#2563eb,#3b82f6);
}

.bg-green {
    background: linear-gradient(135deg,#16a34a,#22c55e);
}

.bg-orange {
    background: linear-gradient(135deg,#f97316,#fb923c);
}

.bg-purple {
    background: linear-gradient(135deg,#7c3aed,#a855f7);
}

.bg-darkgreen {
    background: linear-gradient(135deg,#15803d,#22c55e);
}

.bg-darkblue {
    background: linear-gradient(135deg,#1d4ed8,#2563eb);
}

.stat-card h6 {
    font-size:14px;
    opacity:0.9;
}

.stat-card h3 {
    font-size:30px;
    font-weight:bold;
}

/* CHART CARD */
.chart-card {
    background:white;
    padding:25px;
    border-radius:15px;
    box-shadow:0 5px 20px rgba(0,0,0,0.05);
}

/* TABLE CARD */
.table-card {
    background:white;
    padding:25px;
    border-radius:15px;
    box-shadow:0 5px 20px rgba(0,0,0,0.05);
}
</style>
</head>

<body>

<!-- SIDEBAR -->
<div class="sidebar">
    <h4 class="text-center">Admin Panel</h4>

    <a href="adminDashboard" style="background:#1e293b;">
        <i class="fa fa-home mr-2"></i> Dashboard
    </a>

    <a href="manage-students.jsp">
        <i class="fa fa-users mr-2"></i> Students
    </a>

    <a href="manage-companies.jsp">
        <i class="fa fa-building mr-2"></i> Companies
    </a>

    <a href="manage-jobs.jsp">
        <i class="fa fa-briefcase mr-2"></i> Jobs
    </a>

    <a href="view-applications.jsp">
        <i class="fa fa-file-alt mr-2"></i> Applications
    </a>

    <a href="admin-logout.jsp" style="color:#ef4444;">
        <i class="fa fa-sign-out-alt mr-2"></i> Logout
    </a>
</div>

<!-- MAIN CONTENT -->
<div class="main-content">

    <h3 class="mb-1">Welcome Admin 👋</h3>
    <p class="text-muted"><strong><%= adminEmail %></strong></p>

    <!-- TOP STATS -->
    <div class="row mt-4">

        <div class="col-md-3 mb-4">
            <div class="stat-card bg-blue">
                <h6><i class="fa fa-users mr-2"></i>Students</h6>
                <h3>${totalStudents}</h3>
            </div>
        </div>

        <div class="col-md-3 mb-4">
            <div class="stat-card bg-green">
                <h6><i class="fa fa-building mr-2"></i>Companies</h6>
                <h3>${totalCompanies}</h3>
            </div>
        </div>

        <div class="col-md-3 mb-4">
            <div class="stat-card bg-orange">
                <h6><i class="fa fa-briefcase mr-2"></i>Jobs</h6>
                <h3>${totalJobs}</h3>
            </div>
        </div>

        <div class="col-md-3 mb-4">
            <div class="stat-card bg-purple">
                <h6><i class="fa fa-file-alt mr-2"></i>Applications</h6>
                <h3>${totalApplications}</h3>
            </div>
        </div>

    </div>

    <!-- SECOND ROW -->
    <div class="row">

        <div class="col-md-6 mb-4">
            <div class="stat-card bg-darkgreen text-center">
                <h6>Selected Students</h6>
                <h3>${selectedCount}</h3>
            </div>
        </div>

        <div class="col-md-6 mb-4">
            <div class="stat-card bg-darkblue text-center">
                <h6>Placement Percentage</h6>
                <h3>${placementPercentage}%</h3>
            </div>
        </div>

    </div>

    <!-- CHART -->
    <div class="chart-card mt-3">
        <h5 class="mb-4">Application Status Overview</h5>
        <canvas id="statusChart"></canvas>
    </div>

    <!-- RECENT APPLICATIONS -->
    <div class="table-card mt-4">
        <h5>Recent Applications</h5>

        <table class="table table-bordered mt-3">
            <thead class="thead-dark">
                <tr>
                    <th>Student</th>
                    <th>Job</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<String[]> list = (List<String[]>) request.getAttribute("recentApplications");
                if (list != null) {
                    for (String[] row : list) {
            %>
                <tr>
                    <td><%= row[0] %></td>
                    <td><%= row[1] %></td>
                    <td><%= row[2] %></td>
                </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>

</div>

<!-- CHART SCRIPT -->
<script>
const ctx = document.getElementById('statusChart').getContext('2d');

new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['Applied', 'Shortlisted', 'Rejected', 'Selected'],
        datasets: [{
            label: 'Applications',
            data: [
                ${appliedCount},
                ${shortlistedCount},
                ${rejectedCount},
                ${finalSelectedCount}
            ],
            backgroundColor: [
                '#3b82f6',
                '#facc15',
                '#ef4444',
                '#22c55e'
            ],
            borderRadius: 8
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: { display: false }
        },
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});
</script>

</body>
</html>
