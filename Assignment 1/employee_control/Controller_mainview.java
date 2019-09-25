package employee_control;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
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

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_mainview implements Initializable {

    @FXML private HBox root;
    @FXML private ListView<String> listView;
    @FXML private ListView<class_UHInterfaceEmployee> employeeListView;
    @FXML JFXListView<class_UHInterfaceEmployee> fancyEmployeeListView;
    @FXML Button button;

    private ObservableList<class_UHInterfaceEmployee> employeeList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ObservableList<String> items = listView.getItems();

        employeeList = FXCollections.observableArrayList();
        generateEmployees();
        employeeListView.setItems(employeeList);
        fancyEmployeeListView.setItems(employeeList);

        Label label = new Label();
        label.setText("HELLO");

        JFXSnackbar bar = new JFXSnackbar(root);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                bar.enqueue(new JFXSnackbar.SnackbarEvent(label));
            }
        });
    }

    private void generateEmployees() {
        for(int i=0;i<10;i++) {
            if(i%2 == 0) {
                class_Staff newStaff = new class_Staff();
                newStaff.id = i;
                newStaff.name = "Staff " + i;
                newStaff.hire();
                employeeList.add(newStaff);
            } else {
                class_Faculty newFaculty = new class_Faculty();
                newFaculty.id = i;
                newFaculty.name = "Faculty " + i;
                newFaculty.hire();
                employeeList.add(newFaculty);
            }
        }
    }

}


