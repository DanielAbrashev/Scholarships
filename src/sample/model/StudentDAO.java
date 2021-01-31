package sample.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import sample.model.Student;
import sample.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {


    public static Student searchStudent (String studentId) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM students WHERE student_id="+studentId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsStudent = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getStudentFromResultSet method and get student object
            Student student = getStudentFromResultSet(rsStudent);

            //Return student object
            return student;
        } catch (SQLException e) {
            System.out.println("While searching an student with " + studentId + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Student Object's attributes and return student object.
    private static Student getStudentFromResultSet(ResultSet rs) throws SQLException
    {
        Student student = null;
        if (rs.next()) {
            student = new Student();
            student.setStudentId(rs.getInt("STUDENT_ID"));
            student.setFirstName(rs.getString("FIRST_NAME"));
            student.setLastName(rs.getString("LAST_NAME"));
            student.setEmail(rs.getString("EMAIL"));
            student.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            student.setFacultyNumber(rs.getString("FACULTY_NUMBER"));
            student.setSpecialty(rs.getString("SPECIALTY"));
            student.setSemester(rs.getString("SEMESTER"));
            student.setScore(rs.getDouble("SCORE"));
            student.setScholarship(rs.getDouble("SCHOLARSHIP"));
            student.setScholarshipPerMonth(rs.getDouble("SCHOLARSHIP_PER_MONTH"));

        }
        return student;
    }

    public static ObservableList<Student> searchStudents () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM students";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsStudents = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getStudentList method and get student object
            ObservableList<Student> studentList = getStudentList(rsStudents);

            //Return student object
            return studentList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }



    //Select * from students operation
    private static ObservableList<Student> getStudentList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Student objects
        ObservableList<Student> studentList = FXCollections.observableArrayList();

        while (rs.next()) {
            Student student = new Student();
            student.setStudentId(rs.getInt("STUDENT_ID"));
            student.setFirstName(rs.getString("FIRST_NAME"));
            student.setLastName(rs.getString("LAST_NAME"));
            student.setEmail(rs.getString("EMAIL"));
            student.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            student.setFacultyNumber(rs.getString("FACULTY_NUMBER"));
            student.setSpecialty(rs.getString("SPECIALTY"));
            student.setSemester(rs.getString("SEMESTER"));
            student.setScore(rs.getDouble("SCORE"));
            student.setScholarship(rs.getDouble("SCHOLARSHIP"));
            student.setScholarshipPerMonth(rs.getDouble("SCHOLARSHIP_PER_MONTH"));
            //Add student to the ObservableList
            studentList.add(student);
        }
        //return studentList (ObservableList of Students)
        return studentList;
    }


    public static void updateStudentEmail (String studentId, String studentEmail) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
        String updateStmt =("UPDATE students SET EMAIL = '" + studentEmail + "'" + "WHERE STUDENT_ID = " + studentId + ";");

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    //*************************************
    //DELETE an student
    //*************************************
    public static void deleteStudentWithId (String studentId) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt =
                "BEGIN\n" +
                        "   DELETE FROM students\n" +
                        "         WHERE student_id ="+ studentId +";\n" +
                        "   COMMIT;\n" +
                        "END;";

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    //*************************************
    //INSERT an student
    //*************************************
    public static void insertStudent (String name, String lastname, String email, String phoneNumber, String facultyNumber, String specialty, String semester) throws SQLException, ClassNotFoundException {

        String updateStmt =("INSERT INTO students (FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, FACULTY_NUMBER, SPECIALTY, SEMESTER) VALUES('"+name+"', '"+lastname+"','"+email+"','"+phoneNumber+"', '"+facultyNumber+"','"+specialty+"','"+semester+"')");
//
        //Execute DELETE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
}
