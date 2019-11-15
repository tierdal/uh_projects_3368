package HBCmanage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("form_splash.fxml"));
        primaryStage.setTitle("HBC Manage");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }

/*####################################################################################

                           https://github.com/tierdal

####################################################################################*/

    public static void main(String[] args) {
        launch(args);
    }
}
