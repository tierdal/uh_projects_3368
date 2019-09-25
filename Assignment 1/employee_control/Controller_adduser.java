package employee_control;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller_adduser {

    @FXML public Button btn_adduser_submit;
    @FXML public Button btn_adduser_cancel;
    @FXML public TextField field_adduser_name;


    @FXML public void btn_adduser_submit_action(){
        Stage stage = (Stage) btn_adduser_submit.getScene().getWindow();
        System.out.println("Add user button click.");
        submit_user();
        stage.hide();
    }
    @FXML public void btn_adduser_cancel_action(){
        Stage stage = (Stage) btn_adduser_cancel.getScene().getWindow();
        System.out.println("Cancel add user button click.");
        stage.hide();
    }

    @FXML public void submit_user(){

        String field_adduser_name_value = field_adduser_name.getText();

    }

    @FXML private void initialize() {
    }

}

