package HBCmanage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.sql.*;


public class controller_customers extends class_global_vars{
    @FXML public TableColumn col_1,col_2,col_3,col_4;
    private ObservableList<TableModel_CustomerData> customer_data;
    @FXML TableView customer_list;
    @FXML JFXTextField filter_name,filter_phone,filter_email;
    @FXML JFXButton btn_customers_add, btn_customers_edit,btn_customers_delete,btn_customers_refresh,btn_customers_apply,btn_customers_clear,btn_customers_exit;

    @FXML private void initialize() {

        col_1.setCellValueFactory(new PropertyValueFactory<TableModel_CustomerData,String>("Customer_ID"));
        col_2.setCellValueFactory(new PropertyValueFactory<TableModel_CustomerData,String>("Customer_Name"));
        col_3.setCellValueFactory(new PropertyValueFactory<TableModel_CustomerData,String>("Customer_PhoneNumber"));
        col_4.setCellValueFactory(new PropertyValueFactory<TableModel_CustomerData,String>("Customer_EmailAddress"));

        populateDataTable();

    }

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
            System.out.println("DB Connection established...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void populateDataTable() {

        Connection conn = this.connect_db();
        customer_data = FXCollections.observableArrayList();

        String sql_main = "SELECT * FROM finalproject_customers ORDER BY Customer_Id";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql_main);
            ResultSet result_set = preparedStatement.executeQuery();
            while (result_set.next()) {
                customer_data.add(new TableModel_CustomerData(result_set.getInt(1),result_set.getString(2),result_set.getString(3),result_set.getString(4)));
            }
            customer_list.setItems(customer_data);
            result_set.close();
            conn.close();
        } catch (SQLException tableQueryException) {
            System.err.println(tableQueryException.toString());
        }
    }
    @FXML public void btn_customers_add_action() throws IOException {
        Stage inventoryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("form_customers_add.fxml"));
        inventoryStage.setTitle("HBC Manage - Customers");
        inventoryStage.setScene(new Scene(root, 300, 220));
        inventoryStage.show();
    }


    private void fetch_RowID(){
        int selected_index;
        selected_index = customer_list.getSelectionModel().getSelectedIndex();
        TableModel_CustomerData selected_record = (TableModel_CustomerData)customer_list.getItems().get(selected_index);
        customer_selected_id = selected_record.getCustomer_ID();
    }

    @FXML private void btn_customers_edit_action () throws IOException {
        fetch_RowID();
        if (customer_list.getSelectionModel().getSelectedItem() == null) {
            System.out.println("oops");
        } else {
            Stage customerStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("form_customers_edit.fxml"));
            customerStage.setTitle("HBC Manage - Customers - Edit");
            customerStage.setScene(new Scene(root, 300, 220));
            customerStage.show();
            }
    }
  
    @FXML private void btn_customers_delete_action (){

        if (customer_list.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot delete item!");
            alert.setHeaderText(null);
            alert.setContentText("No item selected!");
            alert.showAndWait();
        } else {
            fetch_RowID();
            Connection conn = this.connect_db();
            try {
                String sql = "DELETE FROM finalproject_customers WHERE Customer_ID_Id = '" + customer_selected_id + "';";
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
  
    @FXML private void btn_customers_refresh_action (){
        populateDataTable();
      
    }
    @FXML private void btn_customers_exit_action (){
        Stage stage = (Stage) btn_customers_exit.getScene().getWindow();
        stage.hide();
    }

    @FXML public void updateDatatable(){
        String name = "", phone="", email="";

        customer_data = null;
        customer_data = FXCollections.observableArrayList();

        name = " Customer_Name LIKE '%" + filter_name.getText() + "%'";
        phone = " AND Customer_PhoneNumber LIKE '" + filter_phone.getText() + "%'";
        email = " AND Customer_EmailAddress LIKE '" + filter_email.getText() + "%'";


        Connection conn = this.connect_db();
        String sql_main = "SELECT * FROM finalproject_customers WHERE" + name + phone + email + " ORDER BY Customer_ID";
        System.out.println(sql_main);
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql_main);
            ResultSet result_set = preparedStatement.executeQuery();
            while (result_set.next()) {
                customer_data.add(new TableModel_CustomerData(result_set.getInt(1),result_set.getString(2),result_set.getString(3),result_set.getString(4)));
            }
            customer_list.setItems(customer_data);

            result_set.close();
            conn.close();
        } catch (SQLException tableQueryException) {
            System.err.println(tableQueryException.toString());
        }
    }

    @FXML private void clearFilters(){
        filter_name.setText("");
        filter_phone.setText("");
        filter_email.setText("");
        populateDataTable();
    }
}
