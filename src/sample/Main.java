package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.controller.RootLayoutController;

import java.io.IOException;

//Main class which extends from Application Class
public class Main extends Application {

    //This is our PrimaryStage (It contains everything)
    private Stage primaryStage;

    //This is the BorderPane of RootLayout
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {
        //1) Declare a primary stage (Everything will be on this stage)
      this.primaryStage = primaryStage;
//
      //Optional: Set a title for primary stage
      this.primaryStage.setTitle("Стипендии ВУТП");
//
      //2) Initialize RootLayout
   //   initRootLayout();
     // showStudentView();
        //3) Display the StudentOperations View

      Parent root = FXMLLoader.load(getClass().getResource("view/LoginView.fxml"));
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      primaryStage.show();

    }

    //Initializes the root layout.
    public void initRootLayout() {
        try {
            //First, load root layout from RootLayout.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

          //  RootLayoutController controller = loader.getController();
          //  controller.setMain(this);

            //Second, show the scene containing the root layout.
            Scene scene = new Scene(rootLayout); //We are sending rootLayout to the Scene.
            primaryStage.setScene(scene); //Set the scene in primary stage.

            //Give the controller access to the main.

            //Third, show the primary stage
            primaryStage.show(); //Display the primary stage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Shows the student operations view inside the root layout.
   public void showStudentView() {
       try {
           //First, load StudentView from StudentView.fxml
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(Main.class.getResource("view/StudentView.fxml"));
           AnchorPane studentOperationsView = (AnchorPane) loader.load();

           // Set Student Operations view into the center of root layout.
           rootLayout.setCenter(studentOperationsView);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public void showAdminView() {
        try {
            //First, load StudentView from StudentView.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AdminView.fxml"));
            AnchorPane adminOperationsView = (AnchorPane) loader.load();

            // Set Student Operations view into the center of root layout.
            rootLayout.setRight(adminOperationsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSubjectView() {
        try {
            //First, load StudentView from StudentView.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/SubjectView.fxml"));
            AnchorPane subjectOperationsView = (AnchorPane) loader.load();

            // Set Student Operations view into the center of root layout.
            rootLayout.setCenter(subjectOperationsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddMarkView() {
        try {
            //First, load StudentView from StudentView.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AddMarkView.fxml"));
            AnchorPane addMarkOperationsView = (AnchorPane) loader.load();

            // Set Student Operations view into the center of root layout.
            rootLayout.setCenter(addMarkOperationsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSpecialtyView() {
        try {
            //First, load StudentView from StudentView.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/SpecialtyView.fxml"));
            AnchorPane specialtyOperationsView = (AnchorPane) loader.load();

            // Set Student Operations view into the center of root layout.
            rootLayout.setCenter(specialtyOperationsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showRankingView() {
        try {
            //First, load StudentView from StudentView.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RankingView.fxml"));
            AnchorPane rankingOperationsView = (AnchorPane) loader.load();

            // Set Student Operations view into the center of root layout.
            rootLayout.setCenter(rankingOperationsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
