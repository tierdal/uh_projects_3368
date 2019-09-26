package employee_control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_adduser {

    @FXML public JFXButton btn_adduser_submit;
    @FXML public JFXButton btn_adduser_cancel;
    @FXML public JFXTextField field_adduser_name;
    @FXML public JFXTextField field_adduser_id;
    @FXML public JFXComboBox combo_adduser_type;
    @FXML public JFXComboBox combo_adduser_department;
    @FXML public JFXComboBox combo_adduser_accesslevel;

    @FXML public void btn_adduser_submit_action() throws IOException {
        Stage stage = (Stage) btn_adduser_submit.getScene().getWindow();
        System.out.println("Add user button click.");

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("form_mainview.fxml"));
        Parent sceneFXML = loader.load();
        Controller_mainview ctrl = (loader.getController());

        String employee_name = field_adduser_name.getText();
        String employee_id = field_adduser_id.getText();
        String employee_type = String.valueOf(combo_adduser_type.getValue());
        String employee_department = String.valueOf(combo_adduser_department.getValue());
        String employee_accesslevel = String.valueOf(combo_adduser_accesslevel.getValue());

        System.out.println(employee_id + ", " + employee_name + ", " + employee_type + ", " + employee_department + ", " + employee_accesslevel);

        if (employee_name.equals("") | employee_type == null){
            System.out.println("Please fill out all fields.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incomplete Form!");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all fields.");
            alert.showAndWait();
        } else {
            if (employee_type.equals("Staff")) {
                //faculty
                if (employee_department == null) {
                    //alert
                } else {
                    ctrl.helloWorld();
                    class_Faculty newFaculty = new class_Faculty();
                    newFaculty.id = employee_id;
                    newFaculty.name = employee_name;
                    newFaculty.hire();
                    class_variables.employeeList.add(newFaculty);
                    ctrl.updateEmployeeList();
                }
            } else if (employee_type == "Faculty") {
                //staff

            } else {
                //error
            }

        }

        /*if(employee_type == "Staff") {
            class_Staff newStaff = new class_Staff();
            newStaff.id = filler + counter;
            newStaff.name = "Staff " + counter;
            newStaff.hire();
            employeeList.add(newStaff);
        } else {
            class_Faculty newFaculty = new class_Faculty();
            newFaculty.id = filler + counter;
            newFaculty.name = "Faculty " + counter;
            newFaculty.hire();
            employeeList.add(newFaculty);
        }*/

        class_variables.employee_id_counter = class_variables.employee_id_counter + 1;
        stage.hide();
    }
    @FXML public void btn_adduser_cancel_action(){
        Stage stage = (Stage) btn_adduser_cancel.getScene().getWindow();
        System.out.println("Cancel add user button click.");
        stage.hide();
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

