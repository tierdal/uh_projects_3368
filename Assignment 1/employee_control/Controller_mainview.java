package employee_control;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_mainview implements Initializable {

    @FXML private HBox root;
    @FXML private ListView<String> listView;
    @FXML private ListView<class_UHInterfaceEmployee> employeeListView;
    @FXML JFXListView<class_UHInterfaceEmployee> listview_employee;
    @FXML JFXButton button_exit;
    @FXML JFXButton button_save;
    @FXML JFXButton button_add;
    @FXML JFXButton button_delete;
    @FXML JFXTextField field_id;
    @FXML JFXTextField field_name;
    @FXML JFXCheckBox checkbox_active;

    private ObservableList<class_UHInterfaceEmployee> employeeList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ObservableList<String> items = listView.getItems();

        employeeList = FXCollections.observableArrayList();
        generateEmployees();
//        employeeListView.setItems(employeeList);
        listview_employee.setItems(employeeList);

        Label label = new Label();
        label.setText("HELLO");

        JFXSnackbar bar = new JFXSnackbar(root);

        /*button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                bar.enqueue(new JFXSnackbar.SnackbarEvent(label));
            }
        });*/
    }

    private void generateEmployees() {
        for(int counter=0;counter<20;counter++) {

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
                newStaff.hire();
                employeeList.add(newStaff);
            } else {
                class_Faculty newFaculty = new class_Faculty();
                newFaculty.id = filler + counter;
                newFaculty.name = "Faculty " + counter;
                newFaculty.hire();
                employeeList.add(newFaculty);
            }
        }
    }

    @FXML public void list_select_populate_form () {
        System.out.println("[" + listview_employee.getSelectionModel().getSelectedItem().id + "] " + listview_employee.getSelectionModel().getSelectedItem().name);
        field_id.setText(listview_employee.getSelectionModel().getSelectedItem().id);
        field_name.setText(listview_employee.getSelectionModel().getSelectedItem().name);
        checkbox_active.setSelected(listview_employee.getSelectionModel().getSelectedItem().isActive);
    }

    @FXML public void btn_save_action(){
        System.out.println("Save button click.");
        //somefunction();
    }

    @FXML public void btn_add_action(){
        System.out.println("Add button click.");
        //somefunction();
    }

    @FXML public void btn_delete_action(){
        System.out.println("Delete button click.");
        //somefunction();
    }

    @FXML public void btn_exit_action(){
        Stage stage = (Stage) button_exit.getScene().getWindow();
        System.out.println("Exit button click.");
        stage.hide();
    }
}


