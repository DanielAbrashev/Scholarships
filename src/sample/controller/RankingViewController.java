package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.model.Student;
import sample.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RankingViewController {
    public TextField semester;
    public TextField specialty;
    public Button searchRankingBtn;
    public Button searchRankingsBtn;
    public TextField amountOfMoney;
    public TextField numberOfStudents;
    @FXML
    private BorderPane rootLayout;
    @FXML
    private TextField rankingIdText;
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
    private TableView ranking;
    @FXML
    private TableColumn<Student, String> studentNameColumn;
    @FXML
    private TableColumn<Student, String> facultyNumberColumn;
    @FXML
    private TableColumn<Student, Double> scoreColumn;
    @FXML
    private TableColumn<Student, Double> scholarshipColumn;
    @FXML
    private TableColumn<Student, Double> scholarshipPerMonthColumn;
    @FXML
    private TableColumn semesterColumn;
    @FXML
    private TableColumn rankingPhoneNumberColumn;
    ResultSet rs;

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    //For MultiThreading
    private Executor exec;

    //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.

    @FXML
    public void initialize() {
        /*
        The setCellValueFactory(...) that we set on the table columns are used to determine
        which field inside the Ranking objects should be used for the particular column.
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

        studentNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        facultyNumberColumn.setCellValueFactory(cellData -> cellData.getValue().facultyNumberProperty());
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
        scholarshipColumn.setCellValueFactory(cellData -> cellData.getValue().scholarshipProperty().asObject());
        scholarshipPerMonthColumn.setCellValueFactory(cellData -> cellData.getValue().scholarshipPerMonthProperty().asObject());


    }


    @FXML
    private void showRanking(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        // try {

        //     Double amountOfMoneyDouble = Double.parseDouble(amountOfMoney.getText());
        //     Double numberOfStudentsDouble = Double.parseDouble(numberOfStudents.getText());
        //     String query = "SELECT score FROM students ORDER BY score DESC LIMIT "+numberOfStudentsDouble;

        //     try {
        //         DBUtil.dbExecuteUpdate(query);
        //     } catch (SQLException e) {
        //         System.out.print("Error occurred while DELETE Operation: " + e);
        //         throw e;
        //     }

        //     //Get Student information
        //     Student student = StudentDAO.searchStudent(studentIdText.getText());
        //     //Populate Student on TableView and Display on TextArea
        //     populateStudents(student);
        // } catch (SQLException e) {
        //     e.printStackTrace();
        //     //resultArea.setText("Error occurred while getting student information from DB.\n" + e);
        //     throw e;
        // }
        try {

            ObservableList<Student> studentList = FXCollections.observableArrayList();
            Double amountOfMoneyDouble = Double.parseDouble(amountOfMoney.getText());
            Integer numberOfStudentsDouble = Integer.parseInt(numberOfStudents.getText());
            Double scholarship = amountOfMoneyDouble / numberOfStudentsDouble;
            Double scholarshipPerMonth = scholarship/5;

            String query = "SELECT * FROM students ORDER BY score DESC LIMIT " + numberOfStudentsDouble;
            rs = DBUtil.dbExecuteQuery(query);

            //while ( rs.next() )
            //{
            //    specialtyList.add(rs.getString("score"));
//
//
            //}
            while (rs.next()) {

                Student student = new Student();
                if (rs.getDouble("SCORE") >= 4.50) {
                    student.setFirstName(rs.getString("FIRST_NAME"));
                    student.setFacultyNumber(rs.getString("FACULTY_NUMBER"));
                    student.setScore(rs.getDouble("SCORE"));
                    student.setScholarship(Double.parseDouble(df2.format(scholarship)));
                    student.setScholarshipPerMonth(Double.parseDouble(df2.format(scholarshipPerMonth)));
                    int studentID = rs.getInt("STUDENT_ID");
                    String updateScholarship =("UPDATE students SET SCHOLARSHIP = '" + scholarship + "'" + "WHERE STUDENT_ID = " + studentID + ";");
                    String updateScholarshipPerMonth =("UPDATE students SET SCHOLARSHIP_PER_MONTH = '" + scholarshipPerMonth + "'" + "WHERE STUDENT_ID = " + studentID + ";");
                    DBUtil.dbExecuteUpdate(updateScholarship);
                    DBUtil.dbExecuteUpdate(updateScholarshipPerMonth);
                    //Add student to the ObservableList
                    studentList.add(student);
                }

            }

            ranking.setItems(studentList);



        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
