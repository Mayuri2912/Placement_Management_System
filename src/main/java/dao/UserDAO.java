package dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import model.Student;
//import database.DBConnection;

public class UserDAO {

    private String url = "jdbc:mysql://localhost:3306/placement_db";
    private String username = "root";
    private String password = "root";

    // ================= ADMIN LOGIN =================
    public boolean validateUser(String email, String pass) {

        boolean status = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM admin WHERE email=? AND password=?");

            ps.setString(1, email);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                status = true;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // ================= STUDENT REGISTRATION =================
    public boolean registerStudent(
            String fullName,
            String email,
            String pass,
            String phone,
            String gender,
            String dob,
            String course,
            double cgpa,
            String skills) {

        boolean status = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO students(full_name,email,password,phone,gender,dob,course,cgpa,skills) VALUES(?,?,?,?,?,?,?,?,?)");

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, phone);
            ps.setString(5, gender);
            ps.setDate(6, Date.valueOf(dob));   // convert String to SQL Date
            ps.setString(7, course);
            ps.setDouble(8, cgpa);
            ps.setString(9, skills);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                status = true;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
 // ================= Login Student =================
    
    public boolean validateStudent(String email, String pass) {

        boolean status = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM students WHERE email=? AND password=?");

            ps.setString(1, email);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                status = true;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
    
 // ================= ADMIN REGISTRATION =================
    public boolean registerAdmin(String name, String email, String pass) {

        boolean status = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO admin(name,email,password) VALUES(?,?,?)");

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, pass);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                status = true;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
 // ================= GET STUDENT BY EMAIL =================

    public Student getStudentByEmail(String email) {

        Student student = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM students WHERE email=?");

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                student = new Student();

                student.setStudentId(rs.getInt("student_id"));
                student.setFullName(rs.getString("full_name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setGender(rs.getString("gender"));
                student.setDob(rs.getString("dob"));
                student.setCourse(rs.getString("course"));
                student.setCgpa(rs.getDouble("cgpa"));
                student.setSkills(rs.getString("skills"));
                student.setResumeLink(rs.getString("resume_link"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return student;
    }

 // ================= GET ALL STUDENTS =================

    public java.util.List<Student> getAllStudents() {

        java.util.List<Student> studentList = new java.util.ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM students");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Student student = new Student();

                student.setStudentId(rs.getInt("student_id"));
                student.setFullName(rs.getString("full_name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setGender(rs.getString("gender"));
                student.setDob(rs.getString("dob"));
                student.setCourse(rs.getString("course"));
                student.setCgpa(rs.getDouble("cgpa"));
                student.setSkills(rs.getString("skills"));
                student.setResumeLink(rs.getString("resume_link"));

                studentList.add(student);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentList;
    }
 // ================= TOTAL COMPANIES =================
    public int getTotalCompanies() {
        int count = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM companies");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // ================= TOTAL JOBS =================
    public int getTotalJobs() {
        int count = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM jobs");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // ================= TOTAL APPLICATIONS =================
    public int getTotalApplications() {
        int count = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM applications");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


    // ================= TOTAL STUDENTS =================
    public int getTotalStudents() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement ps = con.prepareStatement(
                "SELECT COUNT(*) FROM students");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
//================= RECENT APPLICATIONS =================
public java.util.List<String[]> getRecentApplications() {
	

 java.util.List<String[]> list = new java.util.ArrayList<>();

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT s.full_name, j.job_title, a.application_status " +
         "FROM applications a " +
         "JOIN students s ON a.student_id = s.student_id " +
         "JOIN jobs j ON a.job_id = j.job_id " +
         "ORDER BY a.applied_at DESC LIMIT 5"
     );

     ResultSet rs = ps.executeQuery();

     while (rs.next()) {
         String[] row = new String[3];
         row[0] = rs.getString("full_name");
         row[1] = rs.getString("job_title");
         row[2] = rs.getString("application_status");
         list.add(row);
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return list;
}
//================= GET STUDENT ID =================
public int getStudentIdByEmail(String email) {

 int studentId = 0;

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT student_id FROM students WHERE email=?"
     );

     ps.setString(1, email);
     ResultSet rs = ps.executeQuery();

     if (rs.next()) {
         studentId = rs.getInt("student_id");
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return studentId;
}


//================= JOBS APPLIED COUNT =================
public int getJobsAppliedCount(int studentId) {

 int count = 0;

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT COUNT(*) FROM applications WHERE student_id=?"
     );

     ps.setInt(1, studentId);
     ResultSet rs = ps.executeQuery();

     if (rs.next()) {
         count = rs.getInt(1);
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return count;
}


//================= GET ALL JOBS =================
public java.util.List<String[]> getAllJobs() {

  java.util.List<String[]> list = new java.util.ArrayList<>();

  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url, username, password);

      PreparedStatement ps = con.prepareStatement(
          "SELECT j.job_id, j.job_title, c.company_name, c.location, " +
          "j.salary_package, j.last_date_to_apply " +
          "FROM jobs j JOIN companies c ON j.company_id = c.company_id"
      );

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

          String[] row = new String[6];

          row[0] = rs.getString("job_id");
          row[1] = rs.getString("job_title");
          row[2] = rs.getString("company_name");
          row[3] = rs.getString("location");
          row[4] = rs.getString("salary_package");
          row[5] = rs.getString("last_date_to_apply");

          list.add(row);
      }

      con.close();

  } catch (Exception e) {
      e.printStackTrace();
  }

  return list;
}



//================= APPLY FOR JOB =================
public boolean applyForJob(int studentId, int jobId) {

 boolean status = false;

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "INSERT INTO applications(student_id, job_id) VALUES(?,?)"
     );

     ps.setInt(1, studentId);
     ps.setInt(2, jobId);

     ps.executeUpdate();
     status = true;

     con.close();

 } catch (Exception e) {
     System.out.println("Already Applied");
 }

 return status;
}
//================= GET ALL JOBS =================
public java.util.List<model.Job> getAllJobsAsObjects() {

 java.util.List<model.Job> jobList = new java.util.ArrayList<>();

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT j.job_id, j.job_title, j.salary_package, c.company_name, c.location " +
         "FROM jobs j JOIN companies c ON j.company_id = c.company_id"
     );

     ResultSet rs = ps.executeQuery();

     while (rs.next()) {

         model.Job job = new model.Job();

         job.setJobId(rs.getInt("job_id"));
         job.setJobTitle(rs.getString("job_title"));
         job.setSalaryPackage(rs.getString("salary_package"));
         job.setCompanyName(rs.getString("company_name"));
         job.setLocation(rs.getString("location"));

         jobList.add(job);
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return jobList;
}
//================= GET STUDENT APPLICATIONS =================
public java.util.List<String[]> getApplicationsByStudent(int studentId) {

 java.util.List<String[]> list = new java.util.ArrayList<>();

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT a.application_id, j.job_title, c.company_name, " +
         "a.application_status, a.applied_at " +
         "FROM applications a " +
         "JOIN jobs j ON a.job_id = j.job_id " +
         "JOIN companies c ON j.company_id = c.company_id " +
         "WHERE a.student_id=? " +
         "ORDER BY a.applied_at DESC"
     );

     ps.setInt(1, studentId);
     ResultSet rs = ps.executeQuery();

     while (rs.next()) {

         String[] row = new String[5];

         row[0] = rs.getString("application_id");
         row[1] = rs.getString("job_title");
         row[2] = rs.getString("company_name");
         row[3] = rs.getString("application_status");
         row[4] = rs.getString("applied_at");

         list.add(row);
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return list;
}
//================= CHECK IF ALREADY APPLIED =================
public boolean hasStudentApplied(int studentId, int jobId) {

  boolean status = false;

  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url, username, password);

      PreparedStatement ps = con.prepareStatement(
          "SELECT * FROM applications WHERE student_id=? AND job_id=?"
      );

      ps.setInt(1, studentId);
      ps.setInt(2, jobId);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
          status = true;
      }

      con.close();

  } catch (Exception e) {
      e.printStackTrace();
  }

  return status;
}
//================= GET ALL COMPANIES =================
public java.util.List<String[]> getAllCompanies() {

  java.util.List<String[]> list = new java.util.ArrayList<>();

  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url, username, password);

      PreparedStatement ps = con.prepareStatement(
          "SELECT company_id, company_name, location, hr_email FROM companies"
      );

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

          String[] row = new String[4];

          row[0] = rs.getString("company_id");
          row[1] = rs.getString("company_name");
          row[2] = rs.getString("location");
          row[3] = rs.getString("hr_email");

          list.add(row);
      }

      con.close();

  } catch (Exception e) {
      e.printStackTrace();
  }

  return list;
}


//================= ADD COMPANY =================
public boolean addCompany(String name, String location, String email) {

  boolean status = false;

  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url, username, password);

      PreparedStatement ps = con.prepareStatement(
          "INSERT INTO companies(company_name, location, hr_email) VALUES(?,?,?)"
      );

      ps.setString(1, name);
      ps.setString(2, location);
      ps.setString(3, email);

      ps.executeUpdate();
      status = true;

      con.close();

  } catch (Exception e) {
      e.printStackTrace();
  }

  return status;
}
//================= GET COMPANIES FOR DROPDOWN =================
public java.util.List<String[]> getCompanyDropdown() {

  java.util.List<String[]> list = new java.util.ArrayList<>();

  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url, username, password);

      PreparedStatement ps = con.prepareStatement(
          "SELECT company_id, company_name FROM companies"
      );

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
          String[] row = new String[2];
          row[0] = rs.getString("company_id");
          row[1] = rs.getString("company_name");
          list.add(row);
      }

      con.close();

  } catch (Exception e) {
      e.printStackTrace();
  }

  return list;
}
//================= ADD JOB =================
public boolean addJob(int companyId, String title, String salary, String lastDate) {

  boolean status = false;

  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url, username, password);

      PreparedStatement ps = con.prepareStatement(
          "INSERT INTO jobs(company_id, job_title, salary_package, last_date_to_apply) VALUES(?,?,?,?)"
      );

      ps.setInt(1, companyId);
      ps.setString(2, title);
      ps.setString(3, salary);
      ps.setDate(4, Date.valueOf(lastDate));

      ps.executeUpdate();
      status = true;

      con.close();

  } catch (Exception e) {
      e.printStackTrace();
  }

  return status;
}
//================= GET ALL JOBS FOR ADMIN =================
public java.util.List<String[]> getAllJobsForAdmin() {

  java.util.List<String[]> list = new java.util.ArrayList<>();

  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url, username, password);

      PreparedStatement ps = con.prepareStatement(
          "SELECT j.job_id, j.job_title, c.company_name, j.salary_package, j.last_date_to_apply " +
          "FROM jobs j JOIN companies c ON j.company_id = c.company_id"
      );

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

          String[] row = new String[5];

          row[0] = rs.getString("job_id");
          row[1] = rs.getString("job_title");
          row[2] = rs.getString("company_name");
          row[3] = rs.getString("salary_package");
          row[4] = rs.getString("last_date_to_apply");

          list.add(row);
      }

      con.close();

  } catch (Exception e) {
      e.printStackTrace();
  }

  return list;
}
//================= DELETE JOB =================
public void deleteJob(int jobId) {

  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url, username, password);

      PreparedStatement ps = con.prepareStatement(
          "DELETE FROM jobs WHERE job_id=?"
      );

      ps.setInt(1, jobId);
      ps.executeUpdate();

      con.close();

  } catch (Exception e) {
      e.printStackTrace();
  }
}

//================== GET ALL APPLICATIONS FOR ADMIN =================
public java.util.List<String[]> getAllApplications() {

 java.util.List<String[]> list = new java.util.ArrayList<>();

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT a.application_id, s.full_name, j.job_title, c.company_name, " +
         "a.application_status, a.applied_at " +
         "FROM applications a " +
         "JOIN students s ON a.student_id = s.student_id " +
         "JOIN jobs j ON a.job_id = j.job_id " +
         "JOIN companies c ON j.company_id = c.company_id " +
         "ORDER BY a.applied_at DESC"
     );

     ResultSet rs = ps.executeQuery();

     while (rs.next()) {

         String[] row = new String[6];

         row[0] = rs.getString("application_id");
         row[1] = rs.getString("full_name");
         row[2] = rs.getString("job_title");
         row[3] = rs.getString("company_name");
         row[4] = rs.getString("application_status");
         row[5] = rs.getString("applied_at");

         list.add(row);
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return list;
}
//================== UPDATE APPLICATION STATUS =================
public boolean updateApplicationStatus(int applicationId, String status) {

 boolean result = false;

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "UPDATE applications SET application_status=? WHERE application_id=?"
     );

     ps.setString(1, status);
     ps.setInt(2, applicationId);

     ps.executeUpdate();
     result = true;

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return result;
}
//================= TOTAL SELECTED STUDENTS =================
public int getTotalSelectedStudents() {

 int count = 0;

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT COUNT(*) FROM applications WHERE application_status='SELECTED'"
     );

     ResultSet rs = ps.executeQuery();

     if (rs.next()) {
         count = rs.getInt(1);
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return count;
}
//================= APPLICATION STATUS COUNT =================
public int getApplicationStatusCount(String status) {

 int count = 0;

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT COUNT(*) FROM applications WHERE application_status=?"
     );

     ps.setString(1, status);

     ResultSet rs = ps.executeQuery();

     if (rs.next()) {
         count = rs.getInt(1);
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return count;
}
//================= COUNT BY STATUS =================
public int getApplicationCountByStatus(String status) {

 int count = 0;

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT COUNT(*) FROM applications WHERE application_status=?"
     );

     ps.setString(1, status);

     ResultSet rs = ps.executeQuery();

     if (rs.next()) {
         count = rs.getInt(1);
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return count;
}
//================= STUDENT TOTAL APPLICATIONS =================
public int getStudentTotalApplications(int studentId) {

 int count = 0;

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT COUNT(*) FROM applications WHERE student_id=?"
     );

     ps.setInt(1, studentId);
     ResultSet rs = ps.executeQuery();

     if (rs.next()) {
         count = rs.getInt(1);
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return count;
}
//================= STUDENT SHORTLISTED COUNT =================
public int getStudentShortlistedCount(int studentId) {

 int count = 0;

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT COUNT(*) FROM applications WHERE student_id=? AND application_status='SHORTLISTED'"
     );

     ps.setInt(1, studentId);
     ResultSet rs = ps.executeQuery();

     if (rs.next()) {
         count = rs.getInt(1);
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return count;
}
//================= STUDENT PROFILE VIEWS (DEMO) =================
public int getStudentProfileViews(int studentId) {

 int views = 0;

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT profile_views FROM students WHERE student_id=?"
     );

     ps.setInt(1, studentId);
     ResultSet rs = ps.executeQuery();

     if (rs.next()) {
         views = rs.getInt("profile_views");
     }

     con.close();

 } catch (Exception e) {
     // If column doesn't exist, just return demo value
     views = 12;
 }

 return views;
}
//================= RECOMMENDED JOBS FOR STUDENT =================
public java.util.List<String[]> getRecommendedJobs(int studentId) {

 java.util.List<String[]> list = new java.util.ArrayList<>();

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     // Simple recommendation: show latest 5 jobs student has NOT applied for
     PreparedStatement ps = con.prepareStatement(
         "SELECT j.job_id, j.job_title, c.company_name, j.salary_package, c.location " +
         "FROM jobs j " +
         "JOIN companies c ON j.company_id = c.company_id " +
         "WHERE j.job_id NOT IN (SELECT job_id FROM applications WHERE student_id=?) " +
         "ORDER BY j.job_id DESC LIMIT 5"
     );

     ps.setInt(1, studentId);

     ResultSet rs = ps.executeQuery();

     while (rs.next()) {

         String[] row = new String[5];

         row[0] = rs.getString("job_title");
         row[1] = rs.getString("company_name");
         row[2] = rs.getString("location");
         row[3] = rs.getString("salary_package");
         row[4] = rs.getString("job_id");

         list.add(row);
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return list;
}

//================= UPDATE STUDENT RESUME LINK =================
public boolean updateResumeLink(int studentId, String resumeLink) {

 boolean status = false;

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "UPDATE students SET resume_link=? WHERE student_id=?"
     );

     ps.setString(1, resumeLink);
     ps.setInt(2, studentId);

     int rows = ps.executeUpdate();
     status = (rows > 0);

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return status;
}

//================= GET RESUME LINK BY STUDENT ID =================
public String getResumeLinkByStudentId(int studentId) {

 String resumeLink = null;

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     Connection con = DriverManager.getConnection(url, username, password);

     PreparedStatement ps = con.prepareStatement(
         "SELECT resume_link FROM students WHERE student_id=?"
     );

     ps.setInt(1, studentId);
     ResultSet rs = ps.executeQuery();

     if (rs.next()) {
         resumeLink = rs.getString("resume_link");
     }

     con.close();

 } catch (Exception e) {
     e.printStackTrace();
 }

 return resumeLink;
}

}

