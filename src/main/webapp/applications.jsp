<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, dao.UserDAO" %>

<%
String studentEmail = (String) session.getAttribute("student");
if (studentEmail == null) {
    response.sendRedirect("student-login.jsp");
    return;
}

UserDAO dao = new UserDAO();
int studentId = dao.getStudentIdByEmail(studentEmail);
List<String[]> applications = dao.getApplicationsByStudent(studentId);
%>

<!DOCTYPE html>
<html>
<head>
<title>My Applications</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

<style>
body { background:#f4f6f9; font-family:'Segoe UI'; }

.sidebar {
    height:100vh;
    background:#1e293b;
    position:fixed;
    width:220px;
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
    background:#334155;
    color:white;
}

.main-content {
    margin-left:220px;
    padding:25px;
}

.status-applied { color:#2563eb; font-weight:bold; }
.status-selected { color:#16a34a; font-weight:bold; }
.status-rejected { color:#dc2626; font-weight:bold; }

.card-box {
    background:white;
    padding:20px;
    border-radius:12px;
    box-shadow:0 5px 15px rgba(0,0,0,0.05);
}
</style>
</head>

<body>

<!-- SIDEBAR -->
<div class="sidebar">
    <h4 class="text-center">PlaceMates</h4>

    <a href="student-dashboard.jsp">
        <i class="fa fa-home mr-2"></i> Dashboard
    </a>

    <a href="applications.jsp" style="background:#334155;">
        <i class="fa fa-file-alt mr-2"></i> My Applications
    </a>

    <a href="StudentLogoutServlet" style="color:#ef4444;">
        <i class="fa fa-sign-out-alt mr-2"></i> Logout
    </a>
</div>

<!-- MAIN CONTENT -->
<div class="main-content">

    <h3>My Applications</h3>
    <hr>

    <div class="card-box">

        <table class="table table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>Job Title</th>
                    <th>Company</th>
                    <th>Status</th>
                    <th>Applied On</th>
                </tr>
            </thead>
            <tbody>

            <%
            if(applications.size() == 0){
            %>
                <tr>
                    <td colspan="4" class="text-center">
                        No applications yet.
                    </td>
                </tr>
            <%
            } else {
                for(String[] app : applications){
                    String status = app[3];
            %>

                <tr>
                    <td><%= app[1] %></td>
                    <td><%= app[2] %></td>

                    <td>
                        <span class="
                        <%= status.equals("SELECTED") ? "status-selected" :
                            status.equals("REJECTED") ? "status-rejected" :
                            "status-applied" %>">
                            <%= status %>
                        </span>
                    </td>

                    <td><%= app[4] %></td>
                </tr>

            <%
                }
            }
            %>

            </tbody>
        </table>

    </div>

</div>

</body>
</html>
