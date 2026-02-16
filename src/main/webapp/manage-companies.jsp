<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, dao.UserDAO" %>

<%
String adminEmail = (String) session.getAttribute("admin");
if (adminEmail == null) {
    response.sendRedirect("admin-login.jsp");
    return;
}

UserDAO dao = new UserDAO();
List<String[]> companies = dao.getAllCompanies();
%>

<!DOCTYPE html>
<html>
<head>
<title>Manage Companies</title>

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

<!-- SIDEBAR -->
<div class="sidebar">
    <h4 class="text-center">Admin Panel</h4>

    <a href="admin-dashboard.jsp">Dashboard</a>
    <a href="manage-students.jsp">Students</a>
    <a href="manage-companies.jsp" style="background:#1e293b;">Companies</a>
    <a href="manage-jobs.jsp">Jobs</a>
    <a href="manage-applications.jsp">Applications</a>
    <a href="AdminLogoutServlet" style="color:#ef4444;">Logout</a>
</div>

<!-- MAIN CONTENT -->
<div class="main-content">

    <h3>Manage Companies</h3>
    <hr>

    <!-- ADD COMPANY FORM -->
    <div class="card-box">
        <h5>Add New Company</h5>

        <form action="AddCompanyServlet" method="post" class="row">
            <div class="col-md-4">
                <input type="text" name="name" class="form-control" placeholder="Company Name" required>
            </div>

            <div class="col-md-4">
                <input type="text" name="location" class="form-control" placeholder="Location" required>
            </div>

            <div class="col-md-4">
                <input type="email" name="email" class="form-control" placeholder="Email" required>
            </div>

            <div class="col-md-12 mt-3">
                <button class="btn btn-primary">Add Company</button>
            </div>
        </form>
    </div>

    <!-- COMPANY LIST -->
    <div class="card-box">
        <h5>Company List</h5>

        <table class="table table-bordered mt-3">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Location</th>
                    <th>Email</th>
                </tr>
            </thead>

            <tbody>
                <% for(String[] company : companies){ %>
                <tr>
                    <td><%= company[0] %></td>
                    <td><%= company[1] %></td>
                    <td><%= company[2] %></td>
                    <td><%= company[3] %></td>
                </tr>
                <% } %>
            </tbody>

        </table>
    </div>

</div>

</body>
</html>
