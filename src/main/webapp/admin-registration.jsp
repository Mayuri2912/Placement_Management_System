<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body style="background: linear-gradient(to right, #36b9cc, #4e73df); height:100vh;">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5" style="margin-top:80px;">
            <div class="card shadow-lg p-4">

                <h3 class="text-center">Admin Registration</h3>

                <form action="<%= request.getContextPath() %>/AdminRegister" method="post">

                    <div class="form-group">
                        <label>Full Name</label>
                        <input type="text" name="name" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>

                    <button type="submit" class="btn btn-primary btn-block">
                        Register
                    </button>
                </form>

                <%
                    if ("1".equals(request.getParameter("error"))) {
                %>
                    <div class="alert alert-danger mt-3">
                        Email already exists!
                    </div>
                <%
                    }
                %>

                <div class="text-center mt-3">
                    <a href="admin-login.jsp">Already have account? Login</a>
                </div>

            </div>
        </div>
    </div>
</div>

</body>
</html>
