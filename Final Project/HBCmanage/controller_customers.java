package HBCmanage;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import java.sql.*;


public class controller_customers {
    @FXML
    public TableColumn col_1,col_2,col_3,col_4,col_5,col_6,col_7;
    private ObservableList<CustomerDataTableModel> customer_data;
    @FXML TableView customer_list;

    @FXML private void initialize() {

        col_1.setCellValueFactory(new PropertyValueFactory<CustomerDataTableModel,String>("Customer_ID"));
        col_2.setCellValueFactory(new PropertyValueFactory<CustomerDataTableModel,String>("Customer_FirstName"));
        col_3.setCellValueFactory(new PropertyValueFactory<CustomerDataTableModel,String>("Customer_LastName"));
        col_4.setCellValueFactory(new PropertyValueFactory<CustomerDataTableModel,String>("Customer_PhoneNumber"));
        col_5.setCellValueFactory(new PropertyValueFactory<CustomerDataTableModel,String>("Customer_EmailAddress"));

        populateDataTable();

    }

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
        customer_data = FXCollections.observableArrayList();

        String sql_main = "SELECT * FROM finalproject_customers ORDER BY Customer_Id";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql_main);
            ResultSet result_set = preparedStatement.executeQuery();
            while (result_set.next()) {
                customer_data.add(new CustomerDataTableModel(result_set.getInt(1),result_set.getString(2),result_set.getString(3),result_set.getString(4),result_set.getString(5)));
            }
            customer_list.setItems(customer_data);
            result_set.close();
            conn.close();
        } catch (SQLException tableQueryException) {
            System.err.println(tableQueryException.toString());
        }
    }

}
