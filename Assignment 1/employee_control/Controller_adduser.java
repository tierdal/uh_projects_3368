package employee_control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller_adduser {

    @FXML public JFXButton btn_adduser_submit;
    @FXML public JFXButton btn_adduser_cancel;
    @FXML public JFXTextField field_adduser_name;
    @FXML public JFXTextField field_adduser_id;
    @FXML public JFXComboBox field_adduser_type;
    @FXML public JFXComboBox field_adduser_department;
    @FXML public JFXComboBox field_adduser_accesslevel;


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



        class_variables.employee_id_counter = class_variables.employee_id_counter + 1;

    }

    @FXML private void initialize() {
        //This part will set the employee ID in the format of ######
        int length = 6 - String.valueOf(class_variables.employee_id_counter+1).length();
        String filler = "";
        for(int counter=0;counter<length;counter++){
            filler = filler + "0";
        }
        System.out.println(filler + class_variables.employee_id_counter);

        field_adduser_id.setText(filler + class_variables.employee_id_counter);

    }

}

