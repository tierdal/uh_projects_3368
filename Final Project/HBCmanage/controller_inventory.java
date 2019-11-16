package HBCmanage;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import javafx.fxml.Initializable;

import java.sql.Connection;
import java.sql.SQLException;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.ResultSet;
import javafx.scene.control.TableView;


public class controller_inventory implements Initializable {
    @FXML public TableColumn col_1,col_2,col_3,col_4,col_5,col_6,col_7;
    private ObservableList<TableModel_InventoryData> inventory_data;
    @FXML TableView inventory_tableview;

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
            System.out.println("DB Connection established...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void populateDataTable() {

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

        populateDataTable();

    }
}
