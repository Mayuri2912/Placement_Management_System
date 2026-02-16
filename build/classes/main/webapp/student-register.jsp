<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Student Registration</title>

    <link href="css/sb-admin-2.min.css" rel="stylesheet">
</head>

<body class="bg-gradient-primary">

<div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
            <div class="row">
                <div class="col-lg-5 d-none d-lg-flex align-items-center justify-content-center bg-light">
              <img src="images/cap-icon.jpg" 
                 class="img-fluid" 
                style="height: 500px; width: 450px; object-fit: contain;">
</div>
                

                <div class="col-lg-7">
                    <div class="p-5">

                        <div class="text-center">
                            <h1 class="h4 text-gray-900 mb-4">Student Registration</h1>
                        </div>

                        <!-- ✅ IMPORTANT FORM START -->
                        <form action="StudentRegisterServlet" method="post">

                            <div class="form-group">
                                <input type="text" name="full_name"
                                       class="form-control form-control-user"
                                       placeholder="Full Name" required>
                            </div>

                            <div class="form-group">
                                <input type="email" name="email"
                                       class="form-control form-control-user"
                                       placeholder="Email Address" required>
                            </div>

                            <div class="form-group">
                                <input type="password" name="password"
                                       class="form-control form-control-user"
                                       placeholder="Password" required>
                            </div>

                            <div class="form-group">
                                <input type="text" name="phone"
                                       class="form-control form-control-user"
                                       placeholder="Phone Number">
                            </div>

                            <div class="form-group">
                                <select name="gender"
                                        class="form-control form-control-user">
                                    <option value="">Select Gender</option>
                                    <option>Male</option>
                                    <option>Female</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <input type="date" name="dob"
                                       class="form-control form-control-user">
                            </div>

                            <div class="form-group">
                                <input type="text" name="course"
                                       class="form-control form-control-user"
                                       placeholder="Course">
                            </div>

                            <div class="form-group">
                                <input type="number" step="0.01" name="cgpa"
                                       class="form-control form-control-user"
                                       placeholder="CGPA">
                            </div>

                            <div class="form-group">
                                <input type="text" name="skills"
                                       class="form-control form-control-user"
                                       placeholder="Skills">
                            </div>

                            <!-- ✅ REAL SUBMIT BUTTON -->
                            <button type="submit"
                                    class="btn btn-primary btn-user btn-block">
                                Register
                            </button>

                        </form>
                        <!-- ✅ FORM END -->

                        <hr>

                        <div class="text-center">
                            <a class="small" href="student-login.jsp">
                                Already have an account? Login!
                            </a>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>
