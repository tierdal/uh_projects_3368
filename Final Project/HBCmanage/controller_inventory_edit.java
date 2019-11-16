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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class controller_inventory_edit extends class_global_vars {
    @FXML
    public JFXButton btn_inventory_save,btn_inventory_cancel;
    @FXML public JFXTextField text_inventory_edit_pn,text_inventory_edit_price,text_inventory_edit_cost,text_inventory_edit_qty;
    @FXML public JFXComboBox combo_inventory_edit_type;
    @FXML public JFXTextArea text_inventory_edit_description;

    private int selected_id;

    @FXML private void initialize() {
        populateTypeList();
        populateFields();
        selected_id = inventory_selected_id;
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

    @FXML private void populateFields() {
        Connection conn = this.connect_db();

        try {
            ResultSet result_set;
            String sql_main = "SELECT * FROM finalproject_inventory WHERE Inventory_ID=" + selected_id;

            result_set = conn.createStatement().executeQuery(sql_main);
            result_set.next();
            text_inventory_edit_pn.setText(result_set.getString("Inventory_PartNumber"));
            text_inventory_edit_description.setText(result_set.getString("Inventory_Description"));
            text_inventory_edit_price.setText(result_set.getString("Inventory_Price"));
            text_inventory_edit_cost.setText(result_set.getString("Inventory_Cost"));
            combo_inventory_edit_type.setValue(result_set.getString("Inventory_Type"));
            text_inventory_edit_qty.setText(result_set.getString("Inventory_QtyOnHand"));
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

    }

    @FXML public void btn_inventory_save_action(){
        Stage stage = (Stage) btn_inventory_save.getScene().getWindow();
        submit_user();
        stage.hide();
    }
    @FXML public void btn_inventory_cancel_action(){
        Stage stage = (Stage) btn_inventory_cancel.getScene().getWindow();
        stage.hide();
    }

    private void populateTypeList() {
        ObservableList<String> inventory_type_list = FXCollections.observableArrayList();
        inventory_type_list.add("Service");
        inventory_type_list.add("Part");
        inventory_type_list.add("Product");
        inventory_type_list.add("Expense");
        inventory_type_list.add("Tax");
        combo_inventory_edit_type.setItems(inventory_type_list);
    }

    @FXML public void submit_user(){
        String value_pn, value_desc, value_type;
        double value_price,value_cost;
        int value_qty;
        Statement ps_conn;

        value_pn = text_inventory_edit_pn.getText();
        value_desc = text_inventory_edit_description.getText();
        value_type = String.valueOf(combo_inventory_edit_type.getValue());
        value_cost = Double.parseDouble(text_inventory_edit_cost.getText());
        value_price = Double.parseDouble(text_inventory_edit_price.getText());
        value_qty = Integer.parseInt(text_inventory_edit_qty.getText());

        Connection conn = this.connect_db();

        try {
            ps_conn = conn.createStatement();
            String sql = "UPDATE finalproject_inventory SET Inventory_PartNumber='" + value_pn + "',Inventory_Description='" + value_desc + "',Inventory_Price=" + value_price + ",Inventory_Type='" +value_type + "',Inventory_Cost=" + value_cost + ",Inventory_QtyOnHand="+ value_qty;
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
}
