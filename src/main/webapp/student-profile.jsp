<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Student" %>

<%
Student student = (Student) request.getAttribute("studentData");

if (student == null) {
    response.sendRedirect("studentDashboard");
    return;
}

String resumeMsg = request.getParameter("resumeMsg");
String resumeReason = request.getParameter("reason");
String resumeAlertClass = "success".equals(resumeMsg) ? "alert-success" : "alert-danger";
String resumeAlertText = "";

if ("success".equals(resumeMsg)) {
    resumeAlertText = "Resume uploaded successfully.";
} else if ("error".equals(resumeMsg)) {
    if ("type".equals(resumeReason)) {
        resumeAlertText = "Only PDF files are allowed.";
    } else if ("size".equals(resumeReason)) {
        resumeAlertText = "File is too large. Maximum allowed size is 5 MB.";
    } else if ("empty".equals(resumeReason)) {
        resumeAlertText = "Please choose a PDF file before uploading.";
    } else if ("db".equals(resumeReason)) {
        resumeAlertText = "Upload succeeded but saving to your profile failed. Please try again.";
    } else if ("io".equals(resumeReason)) {
        resumeAlertText = "Could not save the file on the server. Please try again.";
    } else {
        resumeAlertText = "Something went wrong while uploading your resume. Please try again.";
    }
}
%>

<!DOCTYPE html>
<html>
<head>
<title>My Profile</title>

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

.main{
    margin-left:240px;
    padding:40px;
}

.profile-card{
    background:white;
    border-radius:20px;
    padding:30px;
    box-shadow:0 10px 30px rgba(0,0,0,0.08);
}
.profile-header{
    text-align:center;
    margin-bottom:30px;
}
.profile-icon{
    font-size:80px;
    color:#3b82f6;
}
.profile-info h5{
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
    <a href="studentLogout" style="color:#ef4444;">
        <i class="fa fa-sign-out-alt mr-2"></i> Logout
    </a>
</div>

<!-- MAIN -->
<div class="main">

    <div class="profile-card">

        <% if (resumeMsg != null && !resumeAlertText.isEmpty()) { %>
        <div class="alert <%= resumeAlertClass %> alert-dismissible fade show" role="alert">
            <%= resumeAlertText %>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <% } %>

        <div class="profile-header">
            <i class="fa fa-user-circle profile-icon"></i>
            <h3 class="mt-3"><%= student.getFullName() %></h3>
            <p class="text-muted"><%= student.getEmail() %></p>
        </div>

        <div class="row profile-info">

            <div class="col-md-6">
                <h5><i class="fa fa-phone mr-2"></i> Phone</h5>
                <p><%= student.getPhone() %></p>
            </div>

            <div class="col-md-6">
                <h5><i class="fa fa-venus-mars mr-2"></i> Gender</h5>
                <p><%= student.getGender() %></p>
            </div>

            <div class="col-md-6">
                <h5><i class="fa fa-calendar mr-2"></i> Date of Birth</h5>
                <p><%= student.getDob() %></p>
            </div>

            <div class="col-md-6">
                <h5><i class="fa fa-graduation-cap mr-2"></i> Course</h5>
                <p><%= student.getCourse() %></p>
            </div>

            <div class="col-md-6">
                <h5><i class="fa fa-chart-line mr-2"></i> CGPA</h5>
                <p><%= student.getCgpa() %></p>
            </div>

            <div class="col-md-6">
                <h5><i class="fa fa-code mr-2"></i> Skills</h5>
                <p><%= student.getSkills() %></p>
            </div>

            <div class="col-md-6">
                <h5><i class="fa fa-file-pdf mr-2"></i> Resume</h5>
                <% if (student.getResumeLink() != null && !student.getResumeLink().trim().isEmpty()) { %>
                    <p>
                        <span class="badge badge-success mr-2">Uploaded</span>
                        <%= student.getResumeLink() %>
                        <br>
                        <a href="downloadResume?id=<%= student.getStudentId() %>"
                           target="_blank" class="btn btn-outline-primary btn-sm mt-2">
                            <i class="fa fa-eye mr-1"></i> View Resume
                        </a>
                    </p>
                <% } else { %>
                    <p><span class="badge badge-secondary">Not uploaded</span></p>
                <% } %>
            </div>

        </div>

        <hr>

        <div class="row">
            <div class="col-md-8">
                <h5>
                    <i class="fa fa-upload mr-2"></i>
                    <%= (student.getResumeLink() != null && !student.getResumeLink().trim().isEmpty())
                            ? "Replace Resume" : "Upload Resume" %>
                </h5>

                <form action="uploadResume" method="post" enctype="multipart/form-data" class="form-inline">
                    <input type="file" name="resumeFile" accept="application/pdf" class="form-control mr-2 mb-2" required>
                    <button type="submit" class="btn btn-primary mb-2">
                        <i class="fa fa-upload mr-1"></i> Upload
                    </button>
                </form>
                <small class="text-muted">PDF only, maximum size 5 MB.</small>
            </div>
        </div>

    </div>

</div>

</body>
</html>
