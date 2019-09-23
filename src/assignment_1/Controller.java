package assignment_1;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    private ListView<String> listView;

    @FXML
    private ListView<UHEmployee> employeeListView;

    private ObservableList<UHEmployee> employeeList;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ObservableList<String> items = listView.getItems();

        Staff staff1 = new Staff();
        staff1.name = "Robert";
        staff1.id = 1;
        staff1.hire();
        Faculty faculty1 = new Faculty();
        faculty1.name = "Lisa";
        faculty1.id = 1;
        faculty1.hire();

        items.add(staff1.name);
        items.add(faculty1.name);

        //----second List

        employeeList = FXCollections.observableArrayList();
        generateEmployees();
        employeeListView.setItems(employeeList);

    }

    private void generateEmployees()
    {
        for(int i = 0; i < 10; i++)
        {
            if(i%2==0) //staff
            {
                Staff newStaff = new Staff();
                newStaff.id = i;
                newStaff.name = "staffName : " + i;
                newStaff.hire();
                employeeList.add(newStaff);
            }
            else //faculty
            {
                Faculty newFaculty = new Faculty();
                newFaculty.id = i;
                newFaculty.name = "facultyName : " + i;
                newFaculty.hire();
                employeeList.add(newFaculty);
            }
        }
    }
}
