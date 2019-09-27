package employee_control;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_mainview implements Initializable {

    @FXML JFXListView<class_UHInterfaceEmployee> listview_employee;
    @FXML JFXButton button_exit;
    @FXML JFXButton button_save;
    @FXML JFXButton button_add;
    @FXML JFXButton button_delete;
    @FXML JFXTextField field_id;
    @FXML JFXTextField field_name;
    @FXML JFXCheckBox checkbox_active;
    @FXML JFXComboBox combo_type;
    @FXML JFXComboBox combo_department;
    @FXML JFXComboBox combo_access;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Init the employee ID counter

        if (class_variables.first_run == 1) {
            class_variables.employee_id_counter = class_variables.employee_id_counter + 1;
            generateEmployees();
            listview_employee.setItems(class_variables.employeeList);
            class_variables.first_run = 0;
        }
    }

    private void generateEmployees() {
        for(int counter=0;counter<11;counter++) {

            //Generate filler for a standard 6-Digit ID number
            int length = 6 - String.valueOf(counter).length();
            String filler = "";
            for(int counter2=0;counter2<length;counter2++){
                filler = filler + "0";
            }

            //Generate staff
            if(counter%2 == 0) {
                class_Staff newStaff = new class_Staff();
                newStaff.id = filler + counter;
                newStaff.name = "Staff " + counter;
                newStaff.type = "Staff";
                newStaff.accessLevel = 1;
                newStaff.hire();
                class_variables.employeeList.add(newStaff);
            } else {
                class_Faculty newFaculty = new class_Faculty();
                newFaculty.id = filler + counter;
                newFaculty.name = "Faculty " + counter;
                newFaculty.type = "Faculty";
                newFaculty.department = "History";
                newFaculty.hire();
                class_variables.employeeList.add(newFaculty);
            }
            class_variables.employee_id_counter = counter + 1;
        }
    }

    @FXML public void list_select_populate_form () {
        if (listview_employee.getSelectionModel().getSelectedItem() == null) {
            field_id.setText("");
            field_name.setText("");
            checkbox_active.setSelected(false);
        } else {
            System.out.println("[" + listview_employee.getSelectionModel().getSelectedItem().id + "] " + listview_employee.getSelectionModel().getSelectedItem().name);
            field_id.setText(listview_employee.getSelectionModel().getSelectedItem().id);
            field_name.setText(listview_employee.getSelectionModel().getSelectedItem().name);
            checkbox_active.setSelected(listview_employee.getSelectionModel().getSelectedItem().isActive);
            combo_type.setValue(listview_employee.getSelectionModel().getSelectedItem().type);
        }
    }

    @FXML public void btn_add_action() throws IOException {
        Stage adduserStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("form_adduser.fxml"));
        adduserStage.setTitle("Add User");
        adduserStage.setScene(new Scene(root, 300, 200));
        adduserStage.show();
    }

    @FXML public void btn_delete_action(){
        System.out.println("Delete button click.");
        if (listview_employee.getSelectionModel().getSelectedItem() == null) {
            System.out.println("No user selected.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot delete user!");
            alert.setHeaderText(null);
            alert.setContentText("No user selected!");
            alert.showAndWait();
        } else {
            class_variables.employeeList.remove(listview_employee.getSelectionModel().getSelectedItem());
            listview_employee.setItems(class_variables.employeeList);
            list_select_populate_form();
            System.out.println(class_variables.employeeList);
        }
    }

    @FXML public void btn_exit_action(){
        Stage stage = (Stage) button_exit.getScene().getWindow();
        System.out.println("Exit button click.");
        stage.hide();
    }

    /*@FXML public void btn_save_action(){
        System.out.println("Save button click.");
        //this button is for editing the current selection - IS NOT YET IMPLEMENTED
        //somefunction();
    }*/

}


