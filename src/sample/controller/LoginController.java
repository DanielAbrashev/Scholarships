package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.util.DBUtil;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    ResultSet rs;
    public AnchorPane rootPane;
    public Button loginButton;
    public Button exitButton;
    public TextField nickname;
    public PasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void login(ActionEvent actionEvent) {
        try {
            String enteredUsername = nickname.getText();
            String enteredPassword = password.getText();
            if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Моля въведете име или парола");
                alert.showAndWait();
            }
            String query = "SELECT * FROM admin Where nickname = '" + enteredUsername + "' AND password = '" + enteredPassword + "' ";
            rs = DBUtil.dbExecuteQuery(query);

            if (rs.next()) {
                Main main = new Main();
                main.initRootLayout();
                System.out.println("OK");

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Грешно име или парола");
                alert.showAndWait();
                nickname.clear();
                password.clear();
            }
        } catch (Exception e1) {
            System.out.println("Could not execute statement");
        }
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
