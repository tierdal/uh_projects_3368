package HBCmanage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.MysqlDataSource;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.*;
import javafx.fxml.Initializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.ResultSet;
import javafx.scene.control.TableView;
import javafx.stage.Stage;




public class controller_inventory implements Initializable {
    @FXML public TableColumn col_1,col_2,col_3,col_4,col_5,col_6,col_7;
    private ObservableList<TableModel_InventoryData> inventory_data;
    @FXML TableView inventory_tableview;
    @FXML JFXButton btn_inventory_refresh,btn_inventory_delete,btn_inventory_edit,btn_inventory_add,btn_inventory_exit,btn_inventory_clear,btn_inventory_apply;
    public int item_id,selected_index;
    @FXML JFXComboBox filter_inventory_type;
    @FXML JFXTextField filter_inventory_pn,filter_inventory_price_from,filter_inventory_price_to,filter_inventory_cost_from,filter_inventory_cost_to;

    //Connect to remote MySQL DB
    private Connection connect_db() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("eshumeyko");
        dataSource.setPassword("Th1sGuyF@wks");
        dataSource.setServerName("db4free.net");
        dataSource.setDatabaseName("uh2336");

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void populateDataTable() {

        Connection conn = this.connect_db();
        inventory_data = FXCollections.observableArrayList();

        String sql_main = "SELECT * FROM finalproject_inventory ORDER BY Inventory_ID";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql_main);
            ResultSet result_set = preparedStatement.executeQuery();
            while (result_set.next()) {
                inventory_data.add(new TableModel_InventoryData(result_set.getInt(1),result_set.getString(2),result_set.getString(3),result_set.getDouble(4),result_set.getString(5),result_set.getDouble(6),result_set.getInt(7)));
            }
            inventory_tableview.setItems(inventory_data);
            result_set.close();
            conn.close();
        } catch (SQLException tableQueryException) {
            System.err.println(tableQueryException.toString());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_1.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Inventory_ID"));
        col_2.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Inventory_PartNumber"));
        col_3.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Inventory_Description"));
        col_4.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Inventory_Price"));
        col_5.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Inventory_Type"));
        col_6.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Inventory_Cost"));
        col_7.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Inventory_QtyOnHand"));

        populateTypeList();
        populateDataTable();

    }

    @FXML public void btn_inventory_add_action() throws IOException {
        Stage orderStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("form_inventory_add.fxml"));
        orderStage.setTitle("HBC Manage - Orders");
        orderStage.setScene(new Scene(root, 300, 400));
        orderStage.show();
    }
    @FXML public void btn_inventory_edit_action() throws IOException {
        Stage inventoryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("form_inventory_edit.fxml"));
        inventoryStage.setTitle("HBC Manage - Inventory");
        inventoryStage.setScene(new Scene(root, 300, 400));
        inventoryStage.show();
    }

    @FXML public void btn_inventory_delete_action() {
        if (inventory_tableview.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot delete item!");
            alert.setHeaderText(null);
            alert.setContentText("No item selected!");
            alert.showAndWait();
        } else {
            fetch_RowID();
            Connection conn = this.connect_db();
            try {
                String sql = "DELETE FROM finalproject_inventory WHERE Inventory_Id = '" + item_id + "';";
                PreparedStatement sql_statement = conn.prepareStatement(sql);
                sql_statement.executeUpdate();
                conn.commit();
                System.out.println("Item Deleted!");
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
            }
            populateDataTable();
        }

    }

    @FXML public void btn_inventory_refresh_action() {
        populateDataTable();
    }

    private void fetch_RowID(){
        selected_index = inventory_tableview.getSelectionModel().getSelectedIndex();
        TableModel_InventoryData selected_record = (TableModel_InventoryData)inventory_tableview.getItems().get(selected_index);
        item_id = selected_record.getInventory_ID();
    }

    private void populateTypeList() {
        ObservableList<String> inventory_type_list = FXCollections.observableArrayList();
        inventory_type_list.add("Service");
        inventory_type_list.add("Part");
        inventory_type_list.add("Product");
        inventory_type_list.add("Expense");
        inventory_type_list.add("Tax");
        filter_inventory_type.setItems(inventory_type_list);
    }

    @FXML private void clearFilters(){
        filter_inventory_type.valueProperty().set(null);
        filter_inventory_pn.setText("");
        filter_inventory_price_from.setText("");
        filter_inventory_price_to.setText("");
        filter_inventory_cost_from.setText("");
        filter_inventory_cost_to.setText("");
        populateDataTable();
    }

    @FXML private void btn_inventory_exit_action (){
        Stage stage = (Stage) btn_inventory_exit.getScene().getWindow();
        stage.hide();
    }

    @FXML public void updateDatatable(){
        boolean validate_price = validatePrice();
        boolean validate_cost = validateCost();
        double price_from,price_to,cost_from,cost_to;
        String price = "", cost = "", type, pn;

        String inventory_price_from = filter_inventory_price_from.getText();
        String inventory_price_to = filter_inventory_price_to.getText();
        String inventory_cost_from = filter_inventory_cost_from.getText();
        String inventory_cost_to = filter_inventory_cost_to.getText();

        if (validate_price && validate_cost) {
            inventory_data = null;
            inventory_data = FXCollections.observableArrayList();

            if (inventory_price_from.equals("")) {price_from = -1;} else {price_from = Double.parseDouble(String.valueOf(filter_inventory_price_from.getText()));}
            if (inventory_price_to.equals("")) {price_to = -1;} else {price_to = Double.parseDouble(String.valueOf(filter_inventory_price_to.getText()));}
            if (inventory_cost_from.equals("")) {cost_from = -1;} else {cost_from = Double.parseDouble(String.valueOf(filter_inventory_cost_from.getText()));}
            if (inventory_cost_to.equals("")) {cost_to = -1;} else {cost_to = Double.parseDouble(String.valueOf(filter_inventory_cost_to.getText()));}

            pn = " Inventory_PartNumber LIKE '" + filter_inventory_pn.getText() + "%'";
            if (filter_inventory_type.getValue() == null | filter_inventory_type.getValue() == ""){
                type = "";
            } else {
                type = " AND Inventory_Type LIKE '" + filter_inventory_type.getValue() + "'";
            }

            if (price_from == -1 && price_to == -1) {
                price = "";
            } else if (price_from != -1 && price_to == -1){
                price = " AND Inventory_Price >= " + price_from;
            } else if (price_from == -1 && price_to != -1){
                price = " AND Inventory_Price <= " + price_to;
            }else if (price_from != -1 && price_to != -1){
                price = " AND Inventory_Price >= " + price_from + " AND Inventory_Price <= " + price_to;
            }

            if (cost_from == -1 && cost_to == -1) {
                cost = "";
            } else if (cost_from != -1 && cost_to == -1){
                cost = " AND Inventory_Cost >= " + cost_from;
            } else if (cost_from == -1 && cost_to != -1){
                cost = " AND Inventory_Cost <= " + cost_to;
            }else if (cost_from != -1 && cost_to != -1){
                cost = " AND Inventory_Cost >= " + cost_from + " AND Inventory_Cost <= " + cost_to;
            }

            Connection conn = this.connect_db();
            String sql_main = "SELECT * FROM finalproject_inventory WHERE" + pn + type + price + cost + " ORDER BY Inventory_Id";
            System.out.println(sql_main);
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql_main);
                ResultSet result_set = preparedStatement.executeQuery();
                while (result_set.next()) {
                    inventory_data.add(new TableModel_InventoryData(result_set.getInt(1),result_set.getString(2),result_set.getString(3),result_set.getDouble(4),result_set.getString(5),result_set.getDouble(6),result_set.getInt(7)));
                }
                inventory_tableview.setItems(inventory_data);

                result_set.close();
                conn.close();
            } catch (SQLException tableQueryException) {
                System.err.println(tableQueryException.toString());
            }
        }
    }

    private boolean validatePrice() {
        double price_from, price_to;
        String inventory_price_from = filter_inventory_price_from.getText();
        String inventory_price_to = filter_inventory_price_to.getText();

        if (inventory_price_from.equals("")) {
            price_from = -1;
        } else {
            price_from = Double.parseDouble(String.valueOf(filter_inventory_price_from.getText()));
        }
        if (inventory_price_to.equals("")) {
            price_to = -1;
        } else {
            price_to = Double.parseDouble(String.valueOf(filter_inventory_price_to.getText()));
        }

        if (price_to == -1 && price_from >= price_to) {return true;}

        if ((price_to >= price_from) && (price_from >= 0 && price_to >= 0)) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong Price Filter!");
            alert.setHeaderText(null);
            alert.setContentText("Make sure 'Price From' is less than 'Price To' and/or is greater than '0'.");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateCost(){
        double cost_from,cost_to;
        String inventory_cost_from = filter_inventory_cost_from.getText();
        String inventory_cost_to = filter_inventory_cost_to.getText();

        if (inventory_cost_from.equals("")) {
            cost_from = -1;
        } else {
            cost_from = Double.parseDouble(String.valueOf(filter_inventory_cost_from.getText()));
        }
        if (inventory_cost_to.equals("")){
            cost_to = -1;
        } else {
            cost_to = Double.parseDouble(String.valueOf(filter_inventory_cost_to.getText()));
        }

        if (cost_to == -1 && cost_from >= cost_to) {return true;}

        if ((cost_to >= cost_from) && (cost_from >= 0 && cost_to >= 0)) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong Cost Filter!");
            alert.setHeaderText(null);
            alert.setContentText("Make sure 'Cost From' is less than 'Cost To' and/or is greater than '0'.");
            alert.showAndWait();
            return false;
        }
    }

}
