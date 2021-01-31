package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.model.MarkDAO;
import sample.model.Mark;
import sample.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddMarkController {
    public TextField semester;
    public TextField specialty;
    public Button searchMarkBtn;
    public Button searchMarksBtn;
    public TextField facultyNumberText;
    public TextField valueText;
    public ComboBox subjectText;
    @FXML
    private TextField markIdText;
    @FXML
    private TableView markTable;
    @FXML
    private TableColumn<Mark, String> facultyNumberColumn;
    @FXML
    private TableColumn<Mark, String>  subjectNameColumn;
    @FXML
    private TableColumn<Mark, Integer> valueColumn;

    ResultSet rs;

    private Executor exec;

    @FXML
    public void initialize () {

        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread (runnable);
            t.setDaemon(true);
            return t;
        });

        facultyNumberColumn.setCellValueFactory(cellData -> cellData.getValue().facultyNumberProperty());
        subjectNameColumn.setCellValueFactory(cellData -> cellData.getValue().subjectNameProperty());
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().markValueProperty().asObject());

        activateComboBox();

    }

    //Search a mark
    @FXML
    private void searchMark (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Get Mark information
            Mark mark = MarkDAO.searchMark(markIdText.getText());
            //Populate Mark on TableView and Display on TextArea
            populateAndShowMark(mark);
        } catch (SQLException e) {
            e.printStackTrace();
            //resultArea.setText("Error occurred while getting mark information from DB.\n" + e);
            throw e;
        }
    }

    //Search all marks
    @FXML
    private void searchMarks(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Get all Marks information
            ObservableList<Mark> markData = MarkDAO.searchMarks();
            //Populate Marks on TableView
            populateMarks(markData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting marks information from DB.\n" + e);
            throw e;
        }
    }

    //Populate Marks for TableView with MultiThreading (This is for example usage)
    private void fillMarkTable(ActionEvent event) throws SQLException, ClassNotFoundException {
        Task<List<Mark>> task = new Task<List<Mark>>(){
            @Override
            public ObservableList<Mark> call() throws Exception{
                return MarkDAO.searchMarks();
            }
        };

        task.setOnFailed(e-> task.getException().printStackTrace());
        task.setOnSucceeded(e-> markTable.setItems((ObservableList<Mark>) task.getValue()));
        exec.execute(task);
    }

    //Populate Mark
    @FXML
    private void populateMark (Mark mark) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<Mark> markData = FXCollections.observableArrayList();
        //Add mark to the ObservableList
        markData.add(mark);
        //Set items to the markTable
        markTable.setItems(markData);
    }

    //Set Mark information to Text Area
    @FXML
    private void setMarkInfoToTextArea ( Mark mark) {
//        resultArea.setText("First Name: " + mark.getMarkName() + "\n" +
        //        "Last Name: " + mark.specialtyProperty());
    }

    //Populate Mark for TableView and Display Mark on TextArea
    @FXML
    private void populateAndShowMark(Mark mark) throws ClassNotFoundException {
        if (mark != null) {
            populateMark(mark);
            setMarkInfoToTextArea(mark);
        } else {
            //resultArea.setText("This mark does not exist!\n");
        }
    }

    //Populate Marks for TableView
    @FXML
    private void populateMarks (ObservableList<Mark> markData) throws ClassNotFoundException {
        //Set items to the markTable
        markTable.setItems(markData);
    }

    //Update mark's email with the email which is written on newEmailText field


    //Insert an mark to the DB
    @FXML
    private void insertMark (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
           Integer valueText1 = Integer.parseInt(valueText.getText());
            MarkDAO.insertMark(valueText1, subjectText.getSelectionModel().getSelectedItem().toString(), facultyNumberText.getText());
            facultyNumberText.clear();
            // resultArea.setText("Mark inserted! \n");
        } catch (SQLException e) {
            // resultArea.setText("Problem occurred while inserting mark " + e);
            throw e;
        }
    }

    //Delete an mark with a given mark Id from DB
    @FXML
    private void deleteMark (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            MarkDAO.deleteMarkWithId(markIdText.getText());
            // resultArea.setText("Mark deleted! Mark id: " + markIdText.getText() + "\n");
        } catch (SQLException e) {
            //   resultArea.setText("Problem occurred while deleting mark " + e);
            throw e;
        }
    }
    @FXML
    public void activateComboBox() {
        try {
            ObservableList<String> subjectList= FXCollections.observableArrayList();
            String query = "SELECT subject_name FROM subjects";
            rs = DBUtil.dbExecuteQuery(query);

            while ( rs.next() )
            {
                subjectList.add(rs.getString("subject_name"));


            }

            subjectText.setItems(subjectList);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
