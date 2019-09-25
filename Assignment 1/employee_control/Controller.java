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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    private HBox root;

    @FXML
    private ListView<String> listView;

    @FXML
    private ListView<UHEmployee> employeeListView;

    @FXML
    JFXListView<UHEmployee> fancyEmployeeListView;

    @FXML
    Button button;

    private ObservableList<UHEmployee> employeeList;

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
        for(int i=0;i<10;i++)
        {
            if(i%2 == 0)
            {
                Staff newStaff = new Staff();
                newStaff.id = i;
                newStaff.name = "Staff " + i;
                newStaff.hire();
                employeeList.add(newStaff);
            }
            else
            {
                Faculty newFaculty = new Faculty();
                newFaculty.id = i;
                newFaculty.name = "Faculty " + i;
                newFaculty.hire();
                employeeList.add(newFaculty);
            }


        }
    }

}


