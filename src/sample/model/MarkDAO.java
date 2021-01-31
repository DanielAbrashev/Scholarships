package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Mark;
import sample.util.DBUtil;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.IntStream;

public class MarkDAO {
    public static Mark searchMark(String markId) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM marks WHERE mark_id=" + markId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsMark = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getMarkFromResultSet method and get mark object
            Mark mark = getMarkFromResultSet(rsMark);

            //Return mark object
            return mark;
        } catch (SQLException e) {
            System.out.println("While searching an mark with " + markId + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Mark Object's attributes and return mark object.
    private static Mark getMarkFromResultSet(ResultSet rs) throws SQLException {
        Mark mark = null;
        if (rs.next()) {
            mark = new Mark();
            mark.setMarkId(rs.getInt("MARK_ID"));
            mark.setMarkValue(rs.getInt("MARK_VALUE"));
            mark.setSubjectName(rs.getString("SUBJECT_NAME"));
            mark.setSemester(rs.getString("SEMESTER"));
            mark.setFacultyNumber(rs.getString("FACULTY_NUMBER"));

        }
        return mark;
    }

    //*******************************
    //SELECT Marks
    //*******************************
    public static ObservableList<Mark> searchMarks() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM marks";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsMarks = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getMarkList method and get mark object
            ObservableList<Mark> markList = getMarkList(rsMarks);

            //Return mark object
            return markList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from marks operation
    private static ObservableList<Mark> getMarkList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Mark objects
        ObservableList<Mark> markList = FXCollections.observableArrayList();

        while (rs.next()) {
            Mark mark = new Mark();
            mark.setMarkId(rs.getInt("MARK_ID"));
            mark.setMarkValue(rs.getInt("MARK_VALUE"));
            mark.setSubjectName(rs.getString("SUBJECT_NAME"));
            mark.setSemester(rs.getString("SEMESTER"));
            mark.setFacultyNumber(rs.getString("FACULTY_NUMBER"));
            //Add mark to the ObservableList
            markList.add(mark);
        }
        //return markList (ObservableList of Marks)
        return markList;
    }

    public static void updateMarkEmail(String markId, String markEmail) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
        String updateStmt = ("UPDATE marks SET EMAIL = '" + markEmail + "'" + "WHERE MARK_ID = " + markId + ";");

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    //*************************************
    //DELETE an mark
    //*************************************
    public static void deleteMarkWithId(String markId) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt =
                "BEGIN\n" +
                        "   DELETE FROM marks\n" +
                        "         WHERE mark_id =" + markId + ";\n" +
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
    //INSERT an mark
    //*************************************
    public static void insertMark(int value, String subjectName, String facultyNumber) throws SQLException, ClassNotFoundException {

        String updateStmt = ("INSERT INTO marks (MARK_VALUE, SUBJECT_NAME, FACULTY_NUMBER) VALUES('" + value + "', '" + subjectName + "','" + facultyNumber + "')");
        studentScoreUpdate(facultyNumber);
        //Execute DELETE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static void studentScoreUpdate(String facultyNumber) throws SQLException, ClassNotFoundException {
        int sum = 0;
        double score = 0;
        int markNumbers = 0;
        String findStudent = "SELECT semester FROM students WHERE faculty_number='" + facultyNumber+"'";
        ResultSet rs = DBUtil.dbExecuteQuery(findStudent);
        rs.next();
        Integer studentSemester = Integer.parseInt(rs.getString("SEMESTER"));
        String findMarks ="SELECT mark_value FROM marks WHERE semester=" + studentSemester + " OR semester=" +(studentSemester-1) + " AND faculty_number = '" + facultyNumber + "'";
        ResultSet rsMarks = DBUtil.dbExecuteQuery(findMarks);
        while (rsMarks.next()) {
            int mark = rsMarks.getInt("MARK_VALUE");
            sum = sum + mark;
            markNumbers++;
        }
        score = sum/markNumbers;
        String insertScore = ("UPDATE students SET SCORE = " + score + "" + " WHERE faculty_number = '" + facultyNumber + "';");
        try {
            DBUtil.dbExecuteUpdate(insertScore);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
}
