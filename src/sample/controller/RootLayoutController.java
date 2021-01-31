package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootLayoutController implements Initializable {

    private BorderPane rootLayout;
    private Main main;
    private StudentController studentController;


    /*//Reference to the main application
    private Main main;

    //Is called by the main application to give a reference back to itself.
    public void setMain (Main main) {
        this.main = main;
    }*/

    //Exit the program
    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }
    public void showAdmins(ActionEvent actionEvent) {
        System.exit(0);
    }
    @FXML
    public void showStudents(ActionEvent actionEvent) {

    try {
        //First, load StudentView from StudentView.fxml
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/StudentView.fxml"));
//
        AnchorPane studentOperationsView = (AnchorPane) loader.load();
//
        // Set Student Operations view into the center of root layout.
        rootLayout.setCenter(studentOperationsView);
    } catch (IOException e) {
        e.printStackTrace();
    }

    }
    public void showSubjects(ActionEvent actionEvent) {
        System.exit(0);
    }

    //Help Menu button behavior
    public void handleHelp(ActionEvent actionEvent) {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Program Information");
        alert.setHeaderText("This is a sample JAVAFX application for SWTESTACADEMY!");
        alert.setContentText("You can search, delete, update, insert a new student with this program.");
        alert.show();
    }
    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
