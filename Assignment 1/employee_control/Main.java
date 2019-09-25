package employee_control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Assignment 1/employee_control/mainview.fxml"));
        primaryStage.setTitle("Employee Control");
        primaryStage.setScene(new Scene(root, 800, 300));
        primaryStage.show();

    }

/*####################################################################################

                               https://github.com/tierdal

####################################################################################*/

    public static void main(String[] args) {
        launch(args);
    }
}

