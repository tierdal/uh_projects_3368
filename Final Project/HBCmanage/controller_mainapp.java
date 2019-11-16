package HBCmanage;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class controller_mainapp {
    @FXML JFXButton button_customers,button_inventory,button_salesorder;

    @FXML public void btn_salesorders_action() throws IOException {
        Stage orderStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("form_orders.fxml"));
        orderStage.setTitle("HBC Manage - Orders");
        orderStage.setScene(new Scene(root, 1200, 600));
        orderStage.show();
    }
    @FXML public void btn_inventory_action() throws IOException {
        Stage inventoryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("form_inventory.fxml"));
        inventoryStage.setTitle("HBC Manage - Inventory");
        inventoryStage.setScene(new Scene(root, 1200, 600));
        inventoryStage.show();
    }
    @FXML public void btn_customers_action() throws IOException {
        Stage customerStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("form_customers.fxml"));
        customerStage.setTitle("HBC Manage - Customers");
        customerStage.setScene(new Scene(root, 750, 400));
        customerStage.show();
    }
}
