package HBCmanage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class controller_inventory_add extends class_global_vars{
    @FXML public JFXButton btn_inventory_save,btn_inventory_cancel;
    @FXML public JFXTextField text_inventory_add_pn,text_inventory_add_price,text_inventory_add_cost,text_inventory_add_qty;
    @FXML public JFXComboBox combo_inventory_add_type;
    @FXML public JFXTextArea text_inventory_add_description;


    @FXML public void btn_inventory_save_action(){
        Stage stage = (Stage) btn_inventory_save.getScene().getWindow();
        submit_item();
        stage.hide();
    }
    @FXML public void btn_inventory_cancel_action(){
        Stage stage = (Stage) btn_inventory_cancel.getScene().getWindow();
        stage.hide();
    }

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

    @FXML public void submit_item(){
        String value_pn, value_desc, value_type;
        double value_price,value_cost;
        int value_qty;
        Statement ps_conn;

        value_pn = text_inventory_add_pn.getText();
        value_desc = text_inventory_add_description.getText();
        value_type = String.valueOf(combo_inventory_add_type.getValue());
        value_cost = Double.parseDouble(text_inventory_add_cost.getText());
        value_price = Double.parseDouble(text_inventory_add_price.getText());
        value_qty = Integer.parseInt(text_inventory_add_qty.getText());

        Connection conn = this.connect_db();

        try {
            ps_conn = conn.createStatement();
            String sql = "INSERT INTO finalproject_inventory(Inventory_PartNumber,Inventory_Description,Inventory_Price,Inventory_Type,Inventory_Cost,Inventory_QtyOnHand) VALUES('"+value_pn+"','"+value_desc+"',"+value_price+",'"+value_type+"',"+value_cost+","+value_qty+")";
            ps_conn.executeUpdate(sql);
            ps_conn.close();
            conn.commit();
            conn.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText(null);
            alert.setContentText("Please refresh the Inventory table.");
            alert.showAndWait();
            btn_inventory_cancel_action();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private void initialize() {
        populateTypeList();
    }

    private void populateTypeList() {
        ObservableList<String> inventory_type_list = FXCollections.observableArrayList();
        inventory_type_list.add("");
        inventory_type_list.add("Service");
        inventory_type_list.add("Part");
        inventory_type_list.add("Product");
        inventory_type_list.add("Expense");
        inventory_type_list.add("Tax");
        combo_inventory_add_type.setItems(inventory_type_list);
    }

}
