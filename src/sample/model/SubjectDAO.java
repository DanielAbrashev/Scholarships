package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Subject;
import sample.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectDAO {
    public static Subject searchSubject (String subjectName) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM subjects WHERE subject_name='"+subjectName+"'";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsSubject = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getSubjectFromResultSet method and get subject object
            Subject subject = getSubjectFromResultSet(rsSubject);

            //Return subject object
            return subject;
        } catch (SQLException e) {
            System.out.println("While searching an subject with " + subjectName + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Subject Object's attributes and return subject object.
    private static Subject getSubjectFromResultSet(ResultSet rs) throws SQLException
    {
        Subject subject = null;
        if (rs.next()) {
            subject = new Subject();
            subject.setSubjectId(rs.getInt("SUBJECT_ID"));
            subject.setSubjectName(rs.getString("SUBJECT_NAME"));
            subject.setSpecialty(rs.getString("SPECIALTY"));
            subject.setSemester(rs.getString("SEMESTER"));
        }
        return subject;
    }

    public static ObservableList<Subject> searchSubjects () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM subjects";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsSubjects = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getSubjectList method and get subject object
            ObservableList<Subject> subjectList = getSubjectList(rsSubjects);

            //Return subject object
            return subjectList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from subjects operation
    private static ObservableList<Subject> getSubjectList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Subject objects
        ObservableList<Subject> subjectList = FXCollections.observableArrayList();

        while (rs.next()) {
            Subject subject = new Subject();
            subject.setSubjectId(rs.getInt("SUBJECT_ID"));
            subject.setSubjectName(rs.getString("SUBJECT_NAME"));
            subject.setSpecialty(rs.getString("SPECIALTY"));
            subject.setSemester(rs.getString("SEMESTER"));
            //Add subject to the ObservableList
            subjectList.add(subject);

        }
        //return subjectList (ObservableList of Subjects)
        return subjectList;
    }

    //*************************************
    //UPDATE an subject's email address
    //*************************************
    public static void updateSubjectEmail (String subjectId, String subjectEmail) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
        String updateStmt =("UPDATE subjects SET EMAIL = '" + subjectEmail + "'" + "WHERE SUBJECT_ID = " + subjectId + ";");

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    public static void deleteSubjectWithId (String subjectId) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt =
                        "   DELETE FROM subjects" +
                        "         WHERE subject_id ="+ subjectId +";";


        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    //*************************************
    //INSERT an subject
    //*************************************
    public static void insertSubject (String subjectName, String specialty, String semester) throws SQLException, ClassNotFoundException {

        String updateStmt =("INSERT INTO subjects (SUBJECT_NAME, SPECIALTY, SEMESTER) VALUES('"+subjectName+"', '"+specialty+"','"+semester+"')");
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
