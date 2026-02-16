<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            height: 100vh;
            margin: 0;
            background: linear-gradient(to right, #4e73df, #1cc88a);
        }

        .main-container {
            height: 100vh;
        }

        .left-side {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .left-side img {
            width: 75%;
            max-width: 420px;
        }

        .login-card {
            width: 100%;
            max-width: 650px;
            padding: 50px;
            border-radius: 20px;
        }

        .login-card h2 {
            font-weight: 600;
            margin-bottom: 30px;
        }

        .form-control {
            height: 55px;
            font-size: 16px;
        }

        .btn-login {
            height: 55px;
            font-size: 18px;
        }
    </style>
</head>

<body>

<div class="container-fluid main-container">
    <div class="row h-100 align-items-center">

        <!-- LEFT 30% IMAGE -->
        <div class="col-md-4 left-side">
            <img src="<%= request.getContextPath() %>/images/admin-login.png">
        </div>

        <!-- RIGHT 70% LOGIN -->
        <div class="col-md-8 d-flex align-items-center justify-content-center">

            <div class="card shadow-lg login-card">

                <h2 class="text-center">Admin Login</h2>

                <% if ("1".equals(request.getParameter("registered"))) { %>
                    <div class="alert alert-success">
                        Registration successful! Please login.
                    </div>
                <% } %>

                <form action="<%= request.getContextPath() %>/LoginHandler" method="post">

                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email"
                               class="form-control"
                               required>
                    </div>

                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" name="password"
                               class="form-control"
                               required>
                    </div>

                    <button type="submit"
                            class="btn btn-primary btn-block btn-login mt-3">
                        Login
                    </button>
                    <hr>

            <div class="text-center">
             <a class="small" href="admin-registration.jsp">
              Don't have an account? Register
    </a>
</div>
                    
                </form>

                <% if ("1".equals(request.getParameter("error"))) { %>
                    <div class="alert alert-danger mt-3">
                        Invalid Email or Password
                    </div>
                <% } %>

            </div>

        </div>

    </div>
</div>

</body>
</html>