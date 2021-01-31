package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.model.AdminDAO;
import sample.model.Admin;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AdminController {

    public Button searchAdminBtn;
    public TextField nicknameText;
    public PasswordField passwordText;
    @FXML
    private BorderPane rootLayout;
    @FXML
    private TextField adminIdText;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField newEmailText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField surnameText;
    @FXML
    private TextField emailText;
    @FXML
    private TableView adminTable;
    @FXML
    private TableColumn<Admin, Integer> adminIdColumn;
    @FXML
    private TableColumn<Admin, String>  adminFirstNameColumn;
    @FXML
    private TableColumn<Admin, String> adminLastNameColumn;
    @FXML
    private TableColumn<Admin, String> adminNicknameColumn;

    //For MultiThreading
    private Executor exec;

    //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.

    @FXML
    public void initialize () {
        /*
        The setCellValueFactory(...) that we set on the table columns are used to determine
        which field inside the Admin objects should be used for the particular column.
        The arrow -> indicates that we're using a Java 8 feature called Lambdas.
        (Another option would be to use a PropertyValueFactory, but this is not type-safe

        We're only using StringProperty values for our table columns in this example.
        When you want to use IntegerProperty or DoubleProperty, the setCellValueFactory(...)
        must have an additional asObject():
        */

        //For multithreading: Create executor that uses daemon threads:
        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread (runnable);
            t.setDaemon(true);
            return t;
        });

        adminIdColumn.setCellValueFactory(cellData -> cellData.getValue().adminIdProperty().asObject());
        adminFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        adminLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        adminNicknameColumn.setCellValueFactory(cellData -> cellData.getValue().nicknameProperty());

    }

    //Search a admin
    @FXML
    private void searchAdmin (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Get Admin information
            Admin admin = AdminDAO.searchAdmin(adminIdText.getText());
            //Populate Admin on TableView and Display on TextArea
            populateAndShowAdmin(admin);
        } catch (SQLException e) {
            e.printStackTrace();
            //resultArea.setText("Error occurred while getting admin information from DB.\n" + e);
            throw e;
        }
    }

    //Search all admins
    @FXML
    private void searchAdmins(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Get all Admins information
            ObservableList<Admin> adminData = AdminDAO.searchAdmins();
            //Populate Admins on TableView
            populateAdmins(adminData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting admins information from DB.\n" + e);
            throw e;
        }
    }

    //Populate Admins for TableView with MultiThreading (This is for example usage)
    private void fillAdminTable(ActionEvent event) throws SQLException, ClassNotFoundException {
        Task<List<Admin>> task = new Task<List<Admin>>(){
            @Override
            public ObservableList<Admin> call() throws Exception{
                return AdminDAO.searchAdmins();
            }
        };

        task.setOnFailed(e-> task.getException().printStackTrace());
        task.setOnSucceeded(e-> adminTable.setItems((ObservableList<Admin>) task.getValue()));
        exec.execute(task);
    }

    //Populate Admin
    @FXML
    private void populateAdmin (Admin admin) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<Admin> adminData = FXCollections.observableArrayList();
        //Add admin to the ObservableList
        adminData.add(admin);
        //Set items to the adminTable
        adminTable.setItems(adminData);
    }

    //Set Admin information to Text Area
    @FXML
    private void setAdminInfoToTextArea ( Admin admin) {
        resultArea.setText("First Name: " + admin.getFirstName() + "\n" +
                "Last Name: " + admin.getLastName());
    }

    //Populate Admin for TableView and Display Admin on TextArea
    @FXML
    private void populateAndShowAdmin(Admin admin) throws ClassNotFoundException {
        if (admin != null) {
            populateAdmin(admin);
           // setAdminInfoToTextArea(admin);
        } else {
            resultArea.setText("This admin does not exist!\n");
        }
    }

    //Populate Admins for TableView
    @FXML
    private void populateAdmins (ObservableList<Admin> adminData) throws ClassNotFoundException {
        //Set items to the adminTable
        adminTable.setItems(adminData);
    }

    //Update admin's email with the email which is written on newEmailText field
    @FXML
    private void updateAdminEmail (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            AdminDAO.updateAdminEmail(adminIdText.getText(),newEmailText.getText());
            resultArea.setText("Email has been updated for, admin id: " + adminIdText.getText() + "\n");
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while updating email: " + e);
        }
    }

    //Insert an admin to the DB
    @FXML
    private void insertAdmin (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            AdminDAO.insertAdmin(nameText.getText(),surnameText.getText(),nicknameText.getText(), passwordText.getText());
            nameText.clear();
            surnameText.clear();
            nicknameText.clear();
            passwordText.clear();
           // resultArea.setText("Admin inserted! \n");
        } catch (SQLException e) {
           // resultArea.setText("Problem occurred while inserting admin " + e);
            throw e;
        }
    }

    //Delete an admin with a given admin Id from DB
    @FXML
    private void deleteAdmin (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            AdminDAO.deleteAdminWithId(adminIdText.getText());
           // resultArea.setText("Admin deleted! Admin id: " + adminIdText.getText() + "\n");
        } catch (SQLException e) {
          //  resultArea.setText("Problem occurred while deleting admin " + e);
            throw e;
        }
    }
}
