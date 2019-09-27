package employee_control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class class_variables {
    public static int employee_id_counter;
    public static int first_run = 1;
    public static ObservableList<class_UHInterfaceEmployee> employeeList = FXCollections.observableArrayList();

    //public ObservableList<class_UHInterfaceEmployee> getEmployeeList(){
    //    return employeeList;
        //https://stackoverflow.com/questions/32342864/applying-mvc-with-javafx
    //}
}
