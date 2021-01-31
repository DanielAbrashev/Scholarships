package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Admin;
import sample.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public static Admin searchAdmin (String nickname) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM admins WHERE nickname='"+nickname+"';";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsAdmin = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getAdminFromResultSet method and get admin object
            Admin admin = getAdminFromResultSet(rsAdmin);

            //Return admin object
            return admin;
        } catch (SQLException e) {
            System.out.println("While searching an admin with " + nickname + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Admin Object's attributes and return admin object.
    private static Admin getAdminFromResultSet(ResultSet rs) throws SQLException
    {
        Admin admin = null;
        if (rs.next()) {
            admin = new Admin();
            admin.setAdminId(rs.getInt("ADMIN_ID"));
            admin.setFirstName(rs.getString("FIRST_NAME"));
            admin.setLastName(rs.getString("LAST_NAME"));
            admin.setPassword(rs.getString("PASSWORD"));
            admin.setNickname(rs.getString("NICKNAME"));

        }
        return admin;
    }

    //*******************************
    //SELECT Admins
    //*******************************
    public static ObservableList<Admin> searchAdmins () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM admins";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsAdmins = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getAdminList method and get admin object
            ObservableList<Admin> adminList = getAdminList(rsAdmins);

            //Return admin object
            return adminList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from admins operation
    private static ObservableList<Admin> getAdminList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Admin objects
        ObservableList<Admin> adminList = FXCollections.observableArrayList();

        while (rs.next()) {
            Admin admin = new Admin();
            admin.setAdminId(rs.getInt("ADMIN_ID"));
            admin.setFirstName(rs.getString("FIRST_NAME"));
            admin.setLastName(rs.getString("LAST_NAME"));
            admin.setPassword(rs.getString("PASSWORD"));
            admin.setNickname(rs.getString("NICKNAME"));
            //Add admin to the ObservableList
            adminList.add(admin);
        }
        //return adminList (ObservableList of Admins)
        return adminList;
    }

    //*************************************
    //UPDATE an admin's email address
    //*************************************
    public static void updateAdminEmail (String adminId, String adminEmail) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
        String updateStmt =("UPDATE admins SET EMAIL = '" + adminEmail + "'" + "WHERE ADMIN_ID = " + adminId + ";");

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    //*************************************
    //DELETE an admin
    //*************************************
    public static void deleteAdminWithId (String adminId) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt =
                        "   DELETE FROM admins" +
                        "         WHERE admin_id ="+ adminId +";";

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    //*************************************
    //INSERT an admin
    //*************************************
    public static void insertAdmin (String name, String lastname, String nickname, String password) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        int value = 1;
        //  String updateStmt =
        //          "BEGIN\n" +
        //                  "INSERT INTO admins\n" +
        //                  "(ADMIN_ID, FIRST_NAME, LAST_NAME, EMAIL)\n" +
        //                  "VALUES\n" +
        //                  "("+value+", '"+name+"', '"+lastname+"','"+email+"');\n" +
        //                  "END;";
        String updateStmt =("INSERT INTO admins (FIRST_NAME, LAST_NAME, NICKNAME, PASSWORD) VALUES('"+name+"', '"+lastname+"','"+nickname+"','"+password+"')");
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
