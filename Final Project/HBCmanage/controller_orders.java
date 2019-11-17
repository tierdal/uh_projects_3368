package HBCmanage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.MysqlDataSource;

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

public class controller_orders extends class_global_vars implements Initializable {
    @FXML public TableColumn col_1,col_2,col_3,col_4,col_5,col_6,col_7,col_8,col_9;
    private ObservableList<TableModel_OrderData> order_data;
    @FXML TableView order_tableview;
    @FXML JFXButton btn_orders_refresh,btn_orders_delete,btn_orders_edit,btn_orders_add,btn_orders_exit,btn_orders_clear,btn_orders_apply;
    public int selected_index;
    @FXML JFXTextField filter_orders_id,filter_orders_fname,filter_orders_lname,filter_orders_tracking;
    @FXML JFXComboBox filter_orders_status;

    //Connect to remote MySQL DB
    private Connection connect_db() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(db_user);
        dataSource.setPassword(db_pass);
        dataSource.setServerName(db_url);
        dataSource.setDatabaseName(db_database);

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
        order_data = FXCollections.observableArrayList();

        String sql_main = "SELECT * FROM finalproject_orders ORDER BY Order_ID";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql_main);
            ResultSet result_set = preparedStatement.executeQuery();
            while (result_set.next()) {
                order_data.add(new TableModel_OrderData(result_set.getInt(1),result_set.getInt(2),result_set.getString(3),result_set.getString(4),result_set.getDouble(5),result_set.getString(6),result_set.getString(7),result_set.getString(8),result_set.getInt(9),result_set.getString(10)));
            }
            order_tableview.setItems(order_data);
            result_set.close();
            conn.close();
        } catch (SQLException tableQueryException) {
            System.err.println(tableQueryException.toString());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_1.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Order_ID"));
        col_2.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Customer_ID"));
        col_3.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Customer_FirstName"));
        col_4.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Customer_LastName"));
        col_5.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Order_Total"));
        col_6.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Order_TrackingNumber"));
        col_7.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Order_Status"));
        col_7.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Order_DateCreated"));
        col_7.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Employee_ID"));
        col_7.setCellValueFactory(new PropertyValueFactory<TableModel_InventoryData,String>("Employee_Name"));

        populateDataTable();
        populateStatusList();

    }

    private void populateStatusList() {
        ObservableList<String> inventory_type_list = FXCollections.observableArrayList();
        inventory_type_list.add("New");
        inventory_type_list.add("In Progress");
        inventory_type_list.add("Stalled");
        inventory_type_list.add("Completed");
        filter_orders_status.setItems(inventory_type_list);
    }

    @FXML public void btn_orders_add_action() throws IOException {
        Stage orderStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("form_orders_add.fxml"));
        orderStage.setTitle("HBC Manage - Orders - Add");
        orderStage.setScene(new Scene(root, 300, 400));
        orderStage.show();
    }
    @FXML public void btn_orders_edit_action() throws IOException {
        fetch_RowID();
        if (order_tableview.getSelectionModel().getSelectedItem() == null) {
            System.out.println("oops");
        } else {
            Stage inventoryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("form_orders_edit_add.fxml"));
            inventoryStage.setTitle("HBC Manage - Orders - Edit");
            inventoryStage.setScene(new Scene(root, 300, 400));
            inventoryStage.show();
        }
    }

    @FXML public void btn_orders_delete_action() {
        if (order_tableview.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot delete order!");
            alert.setHeaderText(null);
            alert.setContentText("No order selected!");
            alert.showAndWait();
        } else {
            fetch_RowID();
            Connection conn = this.connect_db();
            try {
                String sql = "DELETE FROM finalproject_orders WHERE Order_ID = '" + orders_selected_id + "';";
                PreparedStatement sql_statement = conn.prepareStatement(sql);
                sql_statement.executeUpdate();
                conn.commit();
                System.out.println("Order Deleted!");
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
            }
            populateDataTable();
        }

    }

    @FXML public void btn_orders_refresh_action() {
        populateDataTable();
    }

    private void fetch_RowID(){
        selected_index = order_tableview.getSelectionModel().getSelectedIndex();
        TableModel_OrderData selected_record = (TableModel_OrderData)order_tableview.getItems().get(selected_index);
        orders_selected_id = selected_record.getOrder_ID();
    }

    @FXML private void clearFilters(){
        filter_orders_id.setText("");
        filter_orders_fname.setText("");
        filter_orders_lname.setText("");
        filter_orders_tracking.setText("");
        filter_orders_status.valueProperty().set(null);
        populateDataTable();
    }

    @FXML private void btn_orders_exit_action (){
        Stage stage = (Stage) btn_orders_exit.getScene().getWindow();
        stage.hide();
    }

    @FXML public void updateDatatable(){
        String orderid="", tracking="", fname = "", lname = "", status = "";

        order_data = null;
        order_data = FXCollections.observableArrayList();

        orderid = " Order_ID LIKE '" + filter_orders_id.getText() + "%'";
        tracking = " AND Order_TrackingNumber LIKE '" + filter_orders_tracking.getText() + "%'";
        fname = " AND Customer_FirstName LIKE '" + filter_orders_fname.getText() + "%'";
        lname = " AND Customer_LastName LIKE '" + filter_orders_lname.getText() + "%'";
        status = " AND Order_TrackingNumber LIKE '" + filter_orders_status.getValue() + "'";


        Connection conn = this.connect_db();
        String sql_main = "SELECT * FROM finalproject_orders WHERE" + orderid + tracking + fname + lname + status + " ORDER BY Order_ID";
        System.out.println(sql_main);
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql_main);
            ResultSet result_set = preparedStatement.executeQuery();
            while (result_set.next()) {
                order_data.add(new TableModel_OrderData(result_set.getInt(1),result_set.getInt(2),result_set.getString(3),result_set.getString(4),result_set.getDouble(5),result_set.getString(6),result_set.getString(7),result_set.getString(8),result_set.getInt(9),result_set.getString(10)));
            }
            order_tableview.setItems(order_data);

            result_set.close();
            conn.close();
        } catch (SQLException tableQueryException) {
            System.err.println(tableQueryException.toString());
        }
    }


}
