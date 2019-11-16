package HBCmanage;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class controller_splash {

    @FXML public JFXButton start_button;

    @FXML public void setStart_button_action() throws IOException {
        Stage stage = (Stage) start_button.getScene().getWindow();
        stage.hide();
        Stage secondaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("form_mainapp.fxml"));
        secondaryStage.setTitle("HBC Manage");
        secondaryStage.setScene(new Scene(root, 600, 400));
        secondaryStage.show();
    }

    @FXML private void initialize() {
    }

}


