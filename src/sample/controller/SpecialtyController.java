package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.model.SpecialtyDAO;
import sample.model.Specialty;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SpecialtyController {
    public TextField semester;
    public TextField specialty;
    public Button searchSpecialtyBtn;
    public Button searchSpecialtysBtn;
    public TextField specialtyNameText;
    @FXML
    private BorderPane rootLayout;
    @FXML
    private TextField specialtyIdText;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField newEmailText;
    @FXML
    private TextField specialtyNameProperty;
    @FXML
    private TextField surnameText;
    @FXML
    private TextField emailText;
    @FXML
    private TableView specialtyTable;
    @FXML
    private TableColumn<Specialty, Integer> specialtyIdColumn;
    @FXML
    private TableColumn<Specialty, String> specialtyNameColumn;

    //For MultiThreading
    private Executor exec;

    //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.

    @FXML
    public void initialize () {
        /*
        The setCellValueFactory(...) that we set on the table columns are used to determine
        which field inside the Specialty objects should be used for the particular column.
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

        specialtyIdColumn.setCellValueFactory(cellData -> cellData.getValue().specialtyIdProperty().asObject());
        specialtyNameColumn.setCellValueFactory(cellData -> cellData.getValue().specialtyNameProperty());


    }

    //Search a specialty
    @FXML
    private void searchSpecialty (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Get Specialty information
            Specialty specialty = SpecialtyDAO.searchSpecialty(specialtyIdText.getText());
            //Populate Specialty on TableView and Display on TextArea
            populateAndShowSpecialty(specialty);
        } catch (SQLException e) {
            e.printStackTrace();
            //resultArea.setText("Error occurred while getting specialty information from DB.\n" + e);
            throw e;
        }
    }

    //Search all specialtys
    @FXML
    private void searchSpecialtys(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Get all Specialtys information
            ObservableList<Specialty> specialtyData = SpecialtyDAO.searchSpecialtys();
            //Populate Specialtys on TableView
            populateSpecialtys(specialtyData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting specialtys information from DB.\n" + e);
            throw e;
        }
    }

    //Populate Specialtys for TableView with MultiThreading (This is for example usage)
    private void fillSpecialtyTable(ActionEvent event) throws SQLException, ClassNotFoundException {
        Task<List<Specialty>> task = new Task<List<Specialty>>(){
            @Override
            public ObservableList<Specialty> call() throws Exception{
                return SpecialtyDAO.searchSpecialtys();
            }
        };

        task.setOnFailed(e-> task.getException().printStackTrace());
        task.setOnSucceeded(e-> specialtyTable.setItems((ObservableList<Specialty>) task.getValue()));
        exec.execute(task);
    }

    //Populate Specialty
    @FXML
    private void populateSpecialty (Specialty specialty) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<Specialty> specialtyData = FXCollections.observableArrayList();
        //Add specialty to the ObservableList
        specialtyData.add(specialty);
        //Set items to the specialtyTable
        specialtyTable.setItems(specialtyData);
    }

    //Set Specialty information to Text Area
    @FXML
    private void setSpecialtyInfoToTextArea ( Specialty specialty) {
//        resultArea.setText("First Name: " + specialty.getSpecialtyName() + "\n" +
        //        "Last Name: " + specialty.specialtyProperty());
    }

    //Populate Specialty for TableView and Display Specialty on TextArea
    @FXML
    private void populateAndShowSpecialty(Specialty specialty) throws ClassNotFoundException {
        if (specialty != null) {
            populateSpecialty(specialty);
            setSpecialtyInfoToTextArea(specialty);
        } else {
            //resultArea.setText("This specialty does not exist!\n");
        }
    }

    //Populate Specialtys for TableView
    @FXML
    private void populateSpecialtys (ObservableList<Specialty> specialtyData) throws ClassNotFoundException {
        //Set items to the specialtyTable
        specialtyTable.setItems(specialtyData);
    }

    //Update specialty's email with the email which is written on newEmailText field
    @FXML
    private void updateSpecialtyEmail (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            SpecialtyDAO.updateSpecialtyEmail(specialtyIdText.getText(),newEmailText.getText());
            //resultArea.setText("Email has been updated for, specialty id: " + specialtyIdText.getText() + "\n");
        } catch (SQLException e) {
            //resultArea.setText("Problem occurred while updating email: " + e);
        }
    }

    //Insert an specialty to the DB
    @FXML
    private void insertSpecialty (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            SpecialtyDAO.insertSpecialty(specialtyNameText.getText());
            specialtyNameText.clear();
            // resultArea.setText("Specialty inserted! \n");
        } catch (SQLException e) {
            // resultArea.setText("Problem occurred while inserting specialty " + e);
            throw e;
        }
    }

    //Delete an specialty with a given specialty Id from DB
    @FXML
    private void deleteSpecialty (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            SpecialtyDAO.deleteSpecialtyWithId(specialtyIdText.getText());
            // resultArea.setText("Specialty deleted! Specialty id: " + specialtyIdText.getText() + "\n");
        } catch (SQLException e) {
            //   resultArea.setText("Problem occurred while deleting specialty " + e);
            throw e;
        }
    }
}
