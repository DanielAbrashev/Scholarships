package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.model.Student;
import sample.model.StudentDAO;
import sample.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class StudentController {

    public TextField firstNameText;
    public TextField lastNameText;
    public TextField phoneNumberText;
    public TextField facultyNumberText;
    public ComboBox specialtyText;
    public TextField semesterText;
    @FXML
    private BorderPane rootLayout;
    @FXML
    private TextField studentIdText;
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
    private TableView studentTable;
    @FXML
    private TableColumn<Student, Integer> studentIdColumn;
    @FXML
    private TableColumn<Student, String> studentFirstNameColumn;
    @FXML
    private TableColumn<Student, String> studentLastNameColumn;
    @FXML
    private TableColumn<Student, String> studentEmailColumn;
    @FXML
    private TableColumn<Student, String> studentPhoneNumberColumn;
    @FXML
    private TableColumn<Student, String> studentFacultyNumberColumn;
    @FXML
    private TableColumn<Student, String> studentSpecialtyColumn;
    @FXML
    private TableColumn<Student, String> studentSemesterColumn;
    @FXML
    private TableColumn<Student, Double> studentScoreColumn;
    ResultSet rs;


    //For MultiThreading
    private Executor exec;

    //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.

    @FXML
    public void initialize() {
        /*
        The setCellValueFactory(...) that we set on the table columns are used to determine
        which field inside the Student objects should be used for the particular column.
        The arrow -> indicates that we're using a Java 8 feature called Lambdas.
        (Another option would be to use a PropertyValueFactory, but this is not type-safe

        We're only using StringProperty values for our table columns in this example.
        When you want to use IntegerProperty or DoubleProperty, the setCellValueFactory(...)
        must have an additional asObject():
        */

        //For multithreading: Create executor that uses daemon threads:
        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });



        studentIdColumn.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty().asObject());
        studentFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        studentLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        studentEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        studentPhoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        studentFacultyNumberColumn.setCellValueFactory(cellData -> cellData.getValue().facultyNumberProperty());
        studentSpecialtyColumn.setCellValueFactory(cellData -> cellData.getValue().specialtyProperty());
        studentSemesterColumn.setCellValueFactory(cellData -> cellData.getValue().semesterProperty());
        studentScoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());

        activateComboBox();

    }

    //Search a student
    @FXML
    private void searchStudent(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Get Student information
            Student student = StudentDAO.searchStudent(studentIdText.getText());
            //Populate Student on TableView and Display on TextArea
            populateAndShowStudent(student);
        } catch (SQLException e) {
            e.printStackTrace();
            //resultArea.setText("Error occurred while getting student information from DB.\n" + e);
            throw e;
        }
    }

    //Search all students
    @FXML
    private void searchStudents(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Get all Students information
            ObservableList<Student> studentData = StudentDAO.searchStudents();
            //Populate Students on TableView
            populateStudents(studentData);
        } catch (SQLException e) {
            System.out.println("Error occurred while getting students information from DB.\n" + e);
            throw e;
        }
    }

    //Populate Students for TableView with MultiThreading (This is for example usage)
    private void fillStudentTable(ActionEvent event) throws SQLException, ClassNotFoundException {
        Task<List<Student>> task = new Task<List<Student>>() {
            @Override
            public ObservableList<Student> call() throws Exception {
                return StudentDAO.searchStudents();
            }
        };

        task.setOnFailed(e -> task.getException().printStackTrace());
        task.setOnSucceeded(e -> studentTable.setItems((ObservableList<Student>) task.getValue()));
        exec.execute(task);
    }

    //Populate Student
    @FXML
    private void populateStudent(Student student) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<Student> studentData = FXCollections.observableArrayList();
        //Add student to the ObservableList
        studentData.add(student);
        //Set items to the studentTable
        studentTable.setItems(studentData);
    }

    //Set Student information to Text Area
    @FXML
    private void setStudentInfoToTextArea(Student student) {
        //resultArea.setText("First Name: " + student.getFirstName() + "\n" +
        //      "Last Name: " + student.getLastName());
    }

    //Populate Student for TableView and Display Student on TextArea
    @FXML
    private void populateAndShowStudent(Student student) throws ClassNotFoundException {
        if (student != null) {
            populateStudent(student);
            setStudentInfoToTextArea(student);
        } else {
            //resultArea.setText("This student does not exist!\n");
        }
    }

    //Populate Students for TableView
    @FXML
    public void populateStudents(ObservableList<Student> studentData) throws ClassNotFoundException {
        //Set items to the studentTable
        studentTable.setItems(studentData);
    }

    //Update student's email with the email which is written on newEmailText field
    @FXML
    private void updateStudentEmail(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            StudentDAO.updateStudentEmail(studentIdText.getText(), newEmailText.getText());
            //resultArea.setText("Email has been updated for, student id: " + studentIdText.getText() + "\n");
        } catch (SQLException e) {
            //resultArea.setText("Problem occurred while updating email: " + e);
        }
    }

    //Insert an student to the DB
    @FXML
    private void insertStudent(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            StudentDAO.insertStudent(firstNameText.getText(), lastNameText.getText(), emailText.getText(), phoneNumberText.getText(), facultyNumberText.getText(), specialtyText.getSelectionModel().getSelectedItem().toString(), semesterText.getText());
            //resultArea.setText("Student inserted! \n");
        } catch (SQLException e) {
            //resultArea.setText("Problem occurred while inserting student " + e);
            throw e;
        }
    }

    //Delete an student with a given student Id from DB
    @FXML
    private void deleteStudent(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            StudentDAO.deleteStudentWithId(studentIdText.getText());
            //resultArea.setText("Student deleted! Student id: " + studentIdText.getText() + "\n");
        } catch (SQLException e) {
            //resultArea.setText("Problem occurred while deleting student " + e);
            throw e;
        }
    }
    public void activateComboBox() {
        try {
            ObservableList<String> specialtyList= FXCollections.observableArrayList();
            String query = "SELECT specialty_name FROM specialty";
            rs = DBUtil.dbExecuteQuery(query);

            while ( rs.next() )
            {
                specialtyList.add(rs.getString("specialty_name"));


            }

            specialtyText.setItems(specialtyList);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
