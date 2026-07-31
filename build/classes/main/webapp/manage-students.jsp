<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, dao.UserDAO, model.Student" %>

<%
String adminEmail = (String) session.getAttribute("admin");
if (adminEmail == null) {
    response.sendRedirect("admin-login.jsp");
    return;
}

UserDAO dao = new UserDAO();
List<Student> students = dao.getAllStudents();
%>

<!DOCTYPE html>
<html>
<head>
<title>Manage Students</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

<style>
body { background:#f4f6f9; font-family:'Segoe UI'; }

.sidebar {
    height:100vh;
    background:#0f172a;
    position:fixed;
    width:240px;
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
    margin-left:240px;
    padding:25px;
}

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
    <h4 class="text-center">Admin Panel</h4>

    <a href="adminDashboard">
        <i class="fa fa-home mr-2"></i> Dashboard
    </a>

    <a href="manage-students.jsp" style="background:#1e293b;">
        <i class="fa fa-users mr-2"></i> Students
    </a>

    <a href="manage-companies.jsp">
        <i class="fa fa-building mr-2"></i> Companies
    </a>

    <a href="manage-jobs.jsp">
        <i class="fa fa-briefcase mr-2"></i> Jobs
    </a>

    <a href="manage-applications.jsp">
        <i class="fa fa-file-alt mr-2"></i> Applications
    </a>

    <a href="AdminLogoutServlet" style="color:#ef4444;">
        <i class="fa fa-sign-out-alt mr-2"></i> Logout
    </a>
</div>

<!-- MAIN CONTENT -->
<div class="main-content">

    <h3>Manage Students</h3>
    <hr>

    <div class="card-box">

        <table class="table table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Course</th>
                    <th>CGPA</th>
                    <th>Skills</th>
                    <th>Resume</th>
                </tr>
            </thead>

            <tbody>

            <%
            for(Student s : students){
            %>

                <tr>
                    <td><%= s.getStudentId() %></td>
                    <td><%= s.getFullName() %></td>
                    <td><%= s.getEmail() %></td>
                    <td><%= s.getPhone() %></td>
                    <td><%= s.getCourse() %></td>
                    <td><%= s.getCgpa() %></td>
                    <td><%= s.getSkills() %></td>
                    <td>
                        <% if (s.getResumeLink() != null && !s.getResumeLink().trim().isEmpty()) { %>
                            <a href="downloadResume?id=<%= s.getStudentId() %>"
                               target="_blank" class="btn btn-success btn-sm">
                                <i class="fa fa-file-pdf mr-1"></i> View
                            </a>
                        <% } else { %>
                            <span class="badge badge-secondary">Not Uploaded</span>
                        <% } %>
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
