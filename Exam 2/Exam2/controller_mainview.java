package Exam2;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class controller_mainview implements Initializable {

    @FXML JFXListView<class_car> listview_car;
    @FXML JFXButton button_drive;
    @FXML JFXButton button_add;
    @FXML JFXButton button_delete;
    @FXML Label label_display;

    public int car_counter = 0;
    public int first_run = 1;
    public ObservableList<class_car> carList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (first_run == 1) {
            car_counter = car_counter + 1;
            generateCars();
            listview_car.setItems(carList);
            first_run = 0;
        }
    }

    private void generateCars() {
        for(int counter=0;counter<4;counter++) {
            class_car newCar = new class_car();
            newCar.name = "Car " + car_counter;
            carList.add(newCar);
            car_counter = car_counter + 1;
        }
    }

    @FXML public void btn_add_action() throws IOException {
        class_car newCar = new class_car();
        newCar.name = "Car " + car_counter;
        carList.add(newCar);
        car_counter = car_counter + 1;
    }

    @FXML public void btn_delete_action(){
        if (listview_car.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot delete car!");
            alert.setHeaderText(null);
            alert.setContentText("No car selected!");
            alert.showAndWait();
        } else {
            carList.remove(listview_car.getSelectionModel().getSelectedItem());
            listview_car.setItems(carList);
        }
    }

    @FXML public void btn_drive_action(){
        int random = (int)(Math.random()*100);

        if (listview_car.getSelectionModel().getSelectedItem() == null) {
        } else {
            label_display.setText(listview_car.getSelectionModel().getSelectedItem().drive(random));
        }
    }

    @FXML public void list_selection_change(){
        label_display.setText("");
    }

}
