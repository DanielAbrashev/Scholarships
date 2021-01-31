package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.model.SubjectDAO;
import sample.model.Subject;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SubjectController {
    public TextField semester;
    public TextField specialty;
    public Button searchSubjectBtn;
    public Button searchSubjectsBtn;
    @FXML
    private BorderPane rootLayout;
    @FXML
    private TextField subjectIdText;
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
    private TableView subjectTable;
    @FXML
    private TableColumn<Subject, Integer>  subjectIdColumn;
    @FXML
    private TableColumn<Subject, String>  subjectNameColumn;
    @FXML
    private TableColumn<Subject, String> specialtyColumn;
    @FXML
    private TableColumn<Subject, String> semesterColumn;
    @FXML
    private TableColumn<Subject, String> subjectPhoneNumberColumn;

    //For MultiThreading
    private Executor exec;

    //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.

    @FXML
    public void initialize () {
        /*
        The setCellValueFactory(...) that we set on the table columns are used to determine
        which field inside the Subject objects should be used for the particular column.
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

        subjectIdColumn.setCellValueFactory(cellData -> cellData.getValue().subjectIdProperty().asObject());
        subjectNameColumn.setCellValueFactory(cellData -> cellData.getValue().subjectNameProperty());
        specialtyColumn.setCellValueFactory(cellData -> cellData.getValue().specialtyProperty());
        semesterColumn.setCellValueFactory(cellData -> cellData.getValue().semesterProperty());


    }

    //Search a subject
    @FXML
    private void searchSubject (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Get Subject information
            Subject subject = SubjectDAO.searchSubject(subjectIdText.getText());
            //Populate Subject on TableView and Display on TextArea
            populateAndShowSubject(subject);
        } catch (SQLException e) {
            e.printStackTrace();
            //resultArea.setText("Error occurred while getting subject information from DB.\n" + e);
            throw e;
        }
    }

    //Search all subjects
    @FXML
    private void searchSubjects(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Get all Subjects information
            ObservableList<Subject> subjectData = SubjectDAO.searchSubjects();
            //Populate Subjects on TableView
            populateSubjects(subjectData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting subjects information from DB.\n" + e);
            throw e;
        }
    }

    //Populate Subjects for TableView with MultiThreading (This is for example usage)
    private void fillSubjectTable(ActionEvent event) throws SQLException, ClassNotFoundException {
        Task<List<Subject>> task = new Task<List<Subject>>(){
            @Override
            public ObservableList<Subject> call() throws Exception{
                return SubjectDAO.searchSubjects();
            }
        };

        task.setOnFailed(e-> task.getException().printStackTrace());
        task.setOnSucceeded(e-> subjectTable.setItems((ObservableList<Subject>) task.getValue()));
        exec.execute(task);
    }

    //Populate Subject
    @FXML
    private void populateSubject (Subject subject) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<Subject> subjectData = FXCollections.observableArrayList();
        //Add subject to the ObservableList
        subjectData.add(subject);
        //Set items to the subjectTable
        subjectTable.setItems(subjectData);
    }

    //Set Subject information to Text Area
    @FXML
    private void setSubjectInfoToTextArea ( Subject subject) {
//        resultArea.setText("First Name: " + subject.getSubjectName() + "\n" +
        //        "Last Name: " + subject.specialtyProperty());
    }

    //Populate Subject for TableView and Display Subject on TextArea
    @FXML
    private void populateAndShowSubject(Subject subject) throws ClassNotFoundException {
        if (subject != null) {
            populateSubject(subject);
            setSubjectInfoToTextArea(subject);
        } else {
            //resultArea.setText("This subject does not exist!\n");
        }
    }

    //Populate Subjects for TableView
    @FXML
    private void populateSubjects (ObservableList<Subject> subjectData) throws ClassNotFoundException {
        //Set items to the subjectTable
        subjectTable.setItems(subjectData);
    }

    //Update subject's email with the email which is written on newEmailText field
    @FXML
    private void updateSubjectEmail (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            SubjectDAO.updateSubjectEmail(subjectIdText.getText(),newEmailText.getText());
            //resultArea.setText("Email has been updated for, subject id: " + subjectIdText.getText() + "\n");
        } catch (SQLException e) {
            //resultArea.setText("Problem occurred while updating email: " + e);
        }
    }

    //Insert an subject to the DB
    @FXML
    private void insertSubject (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            SubjectDAO.insertSubject(nameText.getText(),specialty.getText(), semester.getText());
            nameText.clear();
            specialty.clear();
            semester.clear();
           // resultArea.setText("Subject inserted! \n");
        } catch (SQLException e) {
           // resultArea.setText("Problem occurred while inserting subject " + e);
            throw e;
        }
    }

    //Delete an subject with a given subject Id from DBa
    @FXML
    private void deleteSubject (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            SubjectDAO.deleteSubjectWithId(subjectIdText.getText());
           // resultArea.setText("Subject deleted! Subject id: " + subjectIdText.getText() + "\n");
        } catch (SQLException e) {
         //   resultArea.setText("Problem occurred while deleting subject " + e);
            throw e;
        }
    }

}
