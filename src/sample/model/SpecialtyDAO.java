package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Specialty;
import sample.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecialtyDAO {
    public static Specialty searchSpecialty(String specialtyName) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM specialty WHERE specialty_name='" + specialtyName + "'";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsSpecialty = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getSpecialtyFromResultSet method and get specialty object
            Specialty specialty = getSpecialtyFromResultSet(rsSpecialty);

            //Return specialty object
            return specialty;
        } catch (SQLException e) {
            System.out.println("While searching an specialty with " + specialtyName + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Specialty Object's attributes and return specialty object.
    private static Specialty getSpecialtyFromResultSet(ResultSet rs) throws SQLException {
        Specialty specialty = null;
        if (rs.next()) {
            specialty = new Specialty();
            specialty.setSpecialtyId(rs.getInt("SPECIALTY_ID"));
            specialty.setSpecialtyName(rs.getString("SPECIALTY_NAME"));
        }
        return specialty;
    }

    //*******************************
    //SELECT Specialtys
    //*******************************
    public static ObservableList<Specialty> searchSpecialtys() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM specialty";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsSpecialtys = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getSpecialtyList method and get specialty object
            ObservableList<Specialty> specialtyList = getSpecialtyList(rsSpecialtys);

            //Return specialty object
            return specialtyList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from specialtys operation
    private static ObservableList<Specialty> getSpecialtyList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Specialty objects
        ObservableList<Specialty> specialtyList = FXCollections.observableArrayList();

        while (rs.next()) {
            Specialty specialty = new Specialty();
            specialty.setSpecialtyId(rs.getInt("SPECIALTY_ID"));
            specialty.setSpecialtyName(rs.getString("SPECIALTY_NAME"));
            //Add specialty to the ObservableList
            specialtyList.add(specialty);

        }
        //return specialtyList (ObservableList of Specialtys)
        return specialtyList;
    }

    //*************************************
    //UPDATE an specialty's email address
    //*************************************
    public static void updateSpecialtyEmail(String specialtyId, String specialtyEmail) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
        String updateStmt = ("UPDATE specialtys SET EMAIL = '" + specialtyEmail + "'" + "WHERE SPECIALTY_ID = " + specialtyId + ";");

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    //*************************************
    //DELETE an specialty
    //*************************************
    public static void deleteSpecialtyWithId(String specialtyId) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt =
                "   DELETE FROM specialty" +
                        "         WHERE specialty_id =" + specialtyId + ";";


        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }


    public static void insertSpecialty(String specialtyName) throws SQLException, ClassNotFoundException {

        String updateStmt = ("INSERT INTO specialty (SPECIALTY_NAME) VALUES('" + specialtyName + "')");
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
