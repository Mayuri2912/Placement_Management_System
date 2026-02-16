<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Placement Management System</title>

<style>
body {
    margin: 0;
    font-family: Arial, sans-serif;
    background: linear-gradient(to right, #eef2f3, #d9e4f5);
    text-align: center;
}

h1 {
    margin-top: 40px;
    font-size: 40px;
    color: #1a1a2e;
}

.subtitle {
    color: #555;
    margin-bottom: 50px;
}

.container {
    display: flex;
    justify-content: center;
    gap: 50px;
    margin-bottom: 80px;
}

.card {
    background: white;
    padding: 50px 40px;
    width: 320px;
    border-radius: 15px;
    box-shadow: 0 8px 20px rgba(0,0,0,0.1);
    text-align: center;
}

.card h2 {
    margin-bottom: 15px;
}

.card p {
    color: #666;
    margin-bottom: 30px;
}

.btn {
    background-color: #304ffe;
    color: white;
    padding: 12px 25px;
    text-decoration: none;
    border-radius: 8px;
    font-weight: bold;
}
 .icon {
    width: 110px;
    height: 110px;
    object-fit: contain;
    margin-bottom: 20px;
    display: block;
    margin-left: auto;
    margin-right: auto;
}
.btn:hover {
    background-color: #1e40af;
}
</style>

</head>

<body>

<h1>Placement Management System</h1>
<p class="subtitle">Streamlining campus placements — choose your role to get started</p>

<div class="container">

    <div class="card">
    <img src="images/student-icon.png" class="icon">
        <h2>Student Login</h2>
        <p>Access placement drives, upload resume and track applications.</p>
        <a href="student-login.jsp" class="btn">Login as Student</a>
    </div>

    <div class="card">
    <img src="images/admin-icon.png" class="icon">
        <h2>Admin Login</h2>
        <p>Manage companies, schedule drives and view reports.</p>
        <a href="admin-login.jsp" class="btn">Login as Admin</a>
    </div>

</div>

</body>
</html>
