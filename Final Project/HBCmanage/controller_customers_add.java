package HBCmanage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class controller_customers_add extends class_global_vars {
    @FXML
    public JFXButton btn_customers_save,btn_customers_cancel;
    @FXML
    public JFXTextField add_name_text, add_phone_text, add_email_text;


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

    @FXML public void submit_user(){
        Connection conn = this.connect_db();
        Statement ps_conn;

        String new_name, new_phone, new_email;

        new_name = add_name_text.getText();
        new_phone = add_phone_text.getText();
        new_email = add_email_text.getText();

        try {
            ps_conn = conn.createStatement();
            String sql = "INSERT INTO finalproject_customers(Customer_Name, Customer_PhoneNumber, Customer_EmailAddress) VALUES('"+new_name+"',"+new_phone+",'"+new_email+"')";
            ps_conn.executeUpdate(sql);
            ps_conn.close();
            conn.commit();
            conn.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText(null);
            alert.setContentText("Please refresh the Customer table.");
            alert.showAndWait();
            btn_cancel_action();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML public void submit_click(){
        submit_user();
        Stage stage = (Stage) btn_customers_save.getScene().getWindow();
        stage.hide();
    }

    @FXML public void btn_cancel_action(){
        Stage stage = (Stage) btn_customers_cancel.getScene().getWindow();
        stage.hide();
    }

    }



