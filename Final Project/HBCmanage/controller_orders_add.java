package HBCmanage;

import com.jfoenix.controls.*;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.ResourceBundle;

public class controller_orders_add extends class_global_vars implements Initializable {
    @FXML public JFXButton btn_save,btn_exit;
    @FXML public JFXComboBox combo_statusSelect,combo_employeeSelect,combo_pn_1,combo_pn_2,combo_pn_3,combo_pn_4,combo_pn_5,combo_pn_6,combo_pn_7,combo_pn_8,combo_pn_9,combo_pn_10,combo_pn_11,combo_pn_12,combo_pn_13,combo_pn_14,combo_pn_15;
    @FXML public JFXComboBox<String> combo_customerSelect;
    @FXML public JFXTextField text_tracking,text_price_1,text_price_2,text_price_3,text_price_4,text_price_5,text_price_6,text_price_7,text_price_8,text_price_9,text_price_10,text_price_11,text_price_12,text_price_13,text_price_14,text_price_15,text_qty_1,text_qty_2,text_qty_3,text_qty_4,text_qty_5,text_qty_6,text_qty_7,text_qty_8,text_qty_9,text_qty_10,text_qty_11,text_qty_12,text_qty_13,text_qty_14,text_qty_15;
    @FXML public JFXTextArea text_desc_1,text_desc_2,text_desc_3,text_desc_4,text_desc_5,text_desc_6,text_desc_7,text_desc_8,text_desc_9,text_desc_10,text_desc_11,text_desc_12,text_desc_13,text_desc_14,text_desc_15;
    @FXML public Label label_name,label_customerid,label_phone,label_email,label_total,label_orderid,label_inventoryid_1,label_inventoryid_2,label_inventoryid_3,label_inventoryid_4,label_inventoryid_5,label_inventoryid_6,label_inventoryid_7,label_inventoryid_8,label_inventoryid_9,label_inventoryid_10,label_inventoryid_11,label_inventoryid_12,label_inventoryid_13,label_inventoryid_14,label_inventoryid_15,label_orderitemid_1,label_orderitemid_2,label_orderitemid_3,label_orderitemid_4,label_orderitemid_5,label_orderitemid_6,label_orderitemid_7,label_orderitemid_8,label_orderitemid_9,label_orderitemid_10,label_orderitemid_11,label_orderitemid_12,label_orderitemid_13,label_orderitemid_14,label_orderitemid_15,label_extprice_1,label_extprice_2,label_extprice_3,label_extprice_4,label_extprice_5,label_extprice_6,label_extprice_7,label_extprice_8,label_extprice_9,label_extprice_10,label_extprice_11,label_extprice_12,label_extprice_13,label_extprice_14,label_extprice_15;
    @FXML public JFXDatePicker date_created;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getOrderID();
        populateCustomerList();
        populateStatusList();
        populateEmployeeList();
        populate_pn_list();

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @FXML public void btn_save_action(){
        Stage stage = (Stage) btn_save.getScene().getWindow();
        save_order();
        stage.hide();
    }
    @FXML public void btn_exit_action(){
        Stage stage = (Stage) btn_exit.getScene().getWindow();
        stage.hide();
    }

    public void populateCustomerList(){
        ObservableList<String> combo_customers = FXCollections.observableArrayList();

        Connection conn = this.connect_db();
        combo_customerSelect.getItems().clear();
        combo_customers.add("");

        try {
            String sql = "SELECT Customer_Name FROM finalproject_customers Order By Customer_Name";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                combo_customers.add(result_set.getString("Customer_Name"));
            }
            combo_customerSelect.setItems(combo_customers);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void populateStatusList(){
        ObservableList<String> inventory_type_list = FXCollections.observableArrayList();
        inventory_type_list.add("New");
        inventory_type_list.add("In Progress");
        inventory_type_list.add("Stalled");
        inventory_type_list.add("Completed");
        combo_statusSelect.setItems(inventory_type_list);
    }

    public void populateEmployeeList(){
        Connection conn = this.connect_db();
        ObservableList<String> combo_employees = FXCollections.observableArrayList();
        combo_employeeSelect.getItems().clear();
        combo_employees.add("");
        try {
            String sql = "SELECT Employee_Name FROM finalproject_employees Order By Employee_Name";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                combo_employees.add(result_set.getString("Employee_Name"));
            }
            combo_employeeSelect.setItems(combo_employees);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    private void getOrderID(){
        Connection conn = this.connect_db();
        ObservableList<String> list_orderid = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Order_ID FROM finalproject_orders";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                list_orderid.add(result_set.getString("Order_ID"));
            }
            int next_id = Integer.parseInt(Collections.max(list_orderid)) + 1;
            label_orderid.setText(String.valueOf(next_id));
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    private void populate_pn_list(){
        Connection conn = this.connect_db();
        ObservableList<String> combo_pn = FXCollections.observableArrayList();
        combo_pn.add("");
        try {
            String sql = "SELECT Inventory_PartNumber FROM finalproject_inventory";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                combo_pn.add(result_set.getString("Inventory_PartNumber"));
            }
            combo_pn_1.setItems(combo_pn);
            combo_pn_2.setItems(combo_pn);
            combo_pn_3.setItems(combo_pn);
            combo_pn_4.setItems(combo_pn);
            combo_pn_5.setItems(combo_pn);
            combo_pn_6.setItems(combo_pn);
            combo_pn_7.setItems(combo_pn);
            combo_pn_8.setItems(combo_pn);
            combo_pn_9.setItems(combo_pn);
            combo_pn_10.setItems(combo_pn);
            combo_pn_11.setItems(combo_pn);
            combo_pn_12.setItems(combo_pn);
            combo_pn_13.setItems(combo_pn);
            combo_pn_14.setItems(combo_pn);
            combo_pn_15.setItems(combo_pn);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }}

    public void updateCustomerInfo(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_customers WHERE Customer_Name LIKE '" + combo_customerSelect.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_customerid.setText(result_set.getString("Customer_ID"));
                label_name.setText(result_set.getString("Customer_Name"));
                label_phone.setText(result_set.getString("Customer_PhoneNumber"));
                label_email.setText(result_set.getString("Customer_EmailAddress"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void calculateTotal(){
        double price_1,price_2,price_3,price_4,price_5,price_6,price_7,price_8,price_9,price_10,price_11,price_12,price_13,price_14,price_15,priceTotal;

        try {price_1 = Double.parseDouble(text_qty_1.getText()) * Double.parseDouble(text_price_1.getText()); label_extprice_1.setText(String.valueOf(price_1));}catch(NumberFormatException e){price_1 = 0;}
        try {price_2 = Double.parseDouble(text_qty_2.getText()) * Double.parseDouble(text_price_2.getText()); label_extprice_2.setText(String.valueOf(price_2));}catch(NumberFormatException e){price_2 = 0;}
        try {price_3 = Double.parseDouble(text_qty_3.getText()) * Double.parseDouble(text_price_3.getText()); label_extprice_3.setText(String.valueOf(price_3));}catch(NumberFormatException e){price_3 = 0;}
        try {price_4 = Double.parseDouble(text_qty_4.getText()) * Double.parseDouble(text_price_4.getText()); label_extprice_4.setText(String.valueOf(price_4));}catch(NumberFormatException e){price_4 = 0;}
        try {price_5 = Double.parseDouble(text_qty_5.getText()) * Double.parseDouble(text_price_5.getText()); label_extprice_5.setText(String.valueOf(price_5));}catch(NumberFormatException e){price_5 = 0;}
        try {price_6 = Double.parseDouble(text_qty_6.getText()) * Double.parseDouble(text_price_6.getText()); label_extprice_6.setText(String.valueOf(price_6));}catch(NumberFormatException e){price_6 = 0;}
        try {price_7 = Double.parseDouble(text_qty_7.getText()) * Double.parseDouble(text_price_7.getText()); label_extprice_7.setText(String.valueOf(price_7));}catch(NumberFormatException e){price_7 = 0;}
        try {price_8 = Double.parseDouble(text_qty_8.getText()) * Double.parseDouble(text_price_8.getText()); label_extprice_8.setText(String.valueOf(price_8));}catch(NumberFormatException e){price_8 = 0;}
        try {price_9 = Double.parseDouble(text_qty_9.getText()) * Double.parseDouble(text_price_9.getText()); label_extprice_9.setText(String.valueOf(price_9));}catch(NumberFormatException e){price_9 = 0;}
        try {price_10 = Double.parseDouble(text_qty_10.getText()) * Double.parseDouble(text_price_10.getText()); label_extprice_10.setText(String.valueOf(price_10));}catch(NumberFormatException e){price_10 = 0;}
        try {price_11 = Double.parseDouble(text_qty_11.getText()) * Double.parseDouble(text_price_11.getText()); label_extprice_11.setText(String.valueOf(price_11));}catch(NumberFormatException e){price_11 = 0;}
        try {price_12 = Double.parseDouble(text_qty_12.getText()) * Double.parseDouble(text_price_12.getText()); label_extprice_12.setText(String.valueOf(price_12));}catch(NumberFormatException e){price_12 = 0;}
        try {price_13 = Double.parseDouble(text_qty_13.getText()) * Double.parseDouble(text_price_13.getText()); label_extprice_13.setText(String.valueOf(price_13));}catch(NumberFormatException e){price_13 = 0;}
        try {price_14 = Double.parseDouble(text_qty_14.getText()) * Double.parseDouble(text_price_14.getText()); label_extprice_14.setText(String.valueOf(price_14));}catch(NumberFormatException e){price_14 = 0;}
        try {price_15 = Double.parseDouble(text_qty_15.getText()) * Double.parseDouble(text_price_15.getText()); label_extprice_15.setText(String.valueOf(price_15));}catch(NumberFormatException e){price_15 = 0;}

        priceTotal = price_1 + price_2 + price_3 + price_4 + price_5 + price_6 + price_7 + price_8 + price_9 + price_10 + price_11 + price_12 + price_13 + price_14 + price_15;
        label_total.setText(String.valueOf(priceTotal));
    }

    public void lineitemadd_action_1(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_1.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_1.setText(result_set.getString("Inventory_ID"));
                text_desc_1.setText(result_set.getString("Inventory_Description"));
                text_qty_1.setText("1");
                text_price_1.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_2(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_2.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_2.setText(result_set.getString("Inventory_ID"));
                text_desc_2.setText(result_set.getString("Inventory_Description"));
                text_qty_2.setText("1");
                text_price_2.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_3(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_3.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_3.setText(result_set.getString("Inventory_ID"));
                text_desc_3.setText(result_set.getString("Inventory_Description"));
                text_qty_3.setText("1");
                text_price_3.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_4(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_4.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_4.setText(result_set.getString("Inventory_ID"));
                text_desc_4.setText(result_set.getString("Inventory_Description"));
                text_qty_4.setText("1");
                text_price_4.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_5(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_5.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_5.setText(result_set.getString("Inventory_ID"));
                text_desc_5.setText(result_set.getString("Inventory_Description"));
                text_qty_5.setText("1");
                text_price_5.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_6(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_6.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_6.setText(result_set.getString("Inventory_ID"));
                text_desc_6.setText(result_set.getString("Inventory_Description"));
                text_qty_6.setText("1");
                text_price_6.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_7(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_7.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_7.setText(result_set.getString("Inventory_ID"));
                text_desc_7.setText(result_set.getString("Inventory_Description"));
                text_qty_7.setText("1");
                text_price_7.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_8(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_8.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_8.setText(result_set.getString("Inventory_ID"));
                text_desc_8.setText(result_set.getString("Inventory_Description"));
                text_qty_8.setText("1");
                text_price_8.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_9(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_9.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_9.setText(result_set.getString("Inventory_ID"));
                text_desc_9.setText(result_set.getString("Inventory_Description"));
                text_qty_9.setText("1");
                text_price_9.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_10(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_10.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_10.setText(result_set.getString("Inventory_ID"));
                text_desc_10.setText(result_set.getString("Inventory_Description"));
                text_qty_10.setText("1");
                text_price_10.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_11(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_11.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_11.setText(result_set.getString("Inventory_ID"));
                text_desc_11.setText(result_set.getString("Inventory_Description"));
                text_qty_11.setText("1");
                text_price_11.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_12(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_12.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_12.setText(result_set.getString("Inventory_ID"));
                text_desc_12.setText(result_set.getString("Inventory_Description"));
                text_qty_12.setText("1");
                text_price_12.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_13(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_13.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_13.setText(result_set.getString("Inventory_ID"));
                text_desc_13.setText(result_set.getString("Inventory_Description"));
                text_qty_13.setText("1");
                text_price_13.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_14(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_14.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_14.setText(result_set.getString("Inventory_ID"));
                text_desc_14.setText(result_set.getString("Inventory_Description"));
                text_qty_14.setText("1");
                text_price_14.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void lineitemadd_action_15(){
        Connection conn = this.connect_db();
        try {
            String sql = "SELECT * FROM finalproject_inventory WHERE Inventory_PartNumber LIKE '" + combo_pn_15.getValue() + "'";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                label_inventoryid_15.setText(result_set.getString("Inventory_ID"));
                text_desc_15.setText(result_set.getString("Inventory_Description"));
                text_qty_15.setText("1");
                text_price_15.setText(result_set.getString("Inventory_Price"));
            }
            conn.close();
            calculateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void save_order(){
        String value_customerName,value_tracking,value_status,value_employeeName,value_ln1_pn,value_ln2_pn,value_ln3_pn,value_ln4_pn,value_ln5_pn,value_ln6_pn,value_ln7_pn,value_ln8_pn,value_ln9_pn,value_ln10_pn,value_ln11_pn,value_ln12_pn,value_ln13_pn,value_ln14_pn,value_ln15_pn,value_ln1_desc,value_ln2_desc,value_ln3_desc,value_ln4_desc,value_ln5_desc,value_ln6_desc,value_ln7_desc,value_ln8_desc,value_ln9_desc,value_ln10_desc,value_ln11_desc,value_ln12_desc,value_ln13_desc,value_ln14_desc,value_ln15_desc;
        double value_orderTotal,value_ln1_qty,value_ln2_qty,value_ln3_qty,value_ln4_qty,value_ln5_qty,value_ln6_qty,value_ln7_qty,value_ln8_qty,value_ln9_qty,value_ln10_qty,value_ln11_qty,value_ln12_qty,value_ln13_qty,value_ln14_qty,value_ln15_qty,value_ln1_price,value_ln2_price,value_ln3_price,value_ln4_price,value_ln5_price,value_ln6_price,value_ln7_price,value_ln8_price,value_ln9_price,value_ln10_price,value_ln11_price,value_ln12_price,value_ln13_price,value_ln14_price,value_ln15_price;
        int value_customerID,value_orderID,value_ln1_inventoryID,value_ln2_inventoryID,value_ln3_inventoryID,value_ln4_inventoryID,value_ln5_inventoryID,value_ln6_inventoryID,value_ln7_inventoryID,value_ln8_inventoryID,value_ln9_inventoryID,value_ln10_inventoryID,value_ln11_inventoryID,value_ln12_inventoryID,value_ln13_inventoryID,value_ln14_inventoryID,value_ln15_inventoryID;
        String sql_ln1 = null,sql_ln2 = null,sql_ln3 = null,sql_ln4 = null,sql_ln5 = null,sql_ln6 = null,sql_ln7 = null,sql_ln8 = null,sql_ln9 = null,sql_ln10 = null,sql_ln11 = null,sql_ln12 = null,sql_ln13 = null,sql_ln14 = null,sql_ln15 = null;

        Statement ps_conn;

        value_customerID = Integer.parseInt(label_customerid.getText());
        value_customerName = String.valueOf(label_name.getText());
        value_orderTotal = Double.parseDouble(label_total.getText());
        value_tracking = String.valueOf(text_tracking.getText());
        value_status = String.valueOf(combo_statusSelect.getValue());
        value_employeeName = String.valueOf(combo_employeeSelect.getValue());
        value_orderID = Integer.parseInt(label_orderid.getText());

        LocalDate value_date = date_created.getValue();

        String sql_order = "INSERT INTO finalproject_orders(Customer_ID,Customer_Name,Order_Total,Order_TrackingNumber,Order_Status,Order_DateCreated,Employee_Name)" +
                " VALUES(" + value_customerID + ",'" + value_customerName + "'," + value_orderTotal + ",'" + value_tracking + "','" + value_status +"','" + value_date + "','" + value_employeeName + "')";

        if (!(combo_pn_1.getSelectionModel()).isEmpty()) {
            value_ln1_inventoryID = Integer.parseInt(label_inventoryid_1.getText());
            value_ln1_pn = String.valueOf(combo_pn_1.getValue());
            value_ln1_desc = String.valueOf(text_desc_1.getText());
            value_ln1_price = Double.parseDouble(text_price_1.getText());
            value_ln1_qty = Double.parseDouble(text_qty_1.getText());
            sql_ln1 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln1_inventoryID + ",'" + value_ln1_pn + "','" + value_ln1_desc + "'," + 1 + "," + value_ln1_price + "," + value_ln1_qty + ")";            
        }
        if (!(combo_pn_2.getSelectionModel()).isEmpty()) {
            value_ln2_inventoryID = Integer.parseInt(label_inventoryid_2.getText());
            value_ln2_pn = String.valueOf(combo_pn_2.getValue());
            value_ln2_desc = String.valueOf(text_desc_2.getText());
            value_ln2_price = Double.parseDouble(text_price_2.getText());
            value_ln2_qty = Double.parseDouble(text_qty_2.getText());
            sql_ln2 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln2_inventoryID + ",'" + value_ln2_pn + "','" + value_ln2_desc + "'," + 2 + "," + value_ln2_price + "," + value_ln2_qty + ")";
        }
        if (!(combo_pn_3.getSelectionModel()).isEmpty()) {
            value_ln3_inventoryID = Integer.parseInt(label_inventoryid_3.getText());
            value_ln3_pn = String.valueOf(combo_pn_3.getValue());
            value_ln3_desc = String.valueOf(text_desc_3.getText());
            value_ln3_price = Double.parseDouble(text_price_3.getText());
            value_ln3_qty = Double.parseDouble(text_qty_3.getText());
            sql_ln3 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln3_inventoryID + ",'" + value_ln3_pn + "','" + value_ln3_desc + "'," + 3 + "," + value_ln3_price + "," + value_ln3_qty + ")";
        }
        if (!(combo_pn_4.getSelectionModel()).isEmpty()) {
            value_ln4_inventoryID = Integer.parseInt(label_inventoryid_4.getText());
            value_ln4_pn = String.valueOf(combo_pn_4.getValue());
            value_ln4_desc = String.valueOf(text_desc_4.getText());
            value_ln4_price = Double.parseDouble(text_price_4.getText());
            value_ln4_qty = Double.parseDouble(text_qty_4.getText());
            sql_ln4 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln4_inventoryID + ",'" + value_ln4_pn + "','" + value_ln4_desc + "'," + 4 + "," + value_ln4_price + "," + value_ln4_qty + ")";
        }
        if (!(combo_pn_5.getSelectionModel()).isEmpty()) {
            value_ln5_inventoryID = Integer.parseInt(label_inventoryid_5.getText());
            value_ln5_pn = String.valueOf(combo_pn_5.getValue());
            value_ln5_desc = String.valueOf(text_desc_5.getText());
            value_ln5_price = Double.parseDouble(text_price_5.getText());
            value_ln5_qty = Double.parseDouble(text_qty_5.getText());
            sql_ln5 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln5_inventoryID + ",'" + value_ln5_pn + "','" + value_ln5_desc + "'," + 5 + "," + value_ln5_price + "," + value_ln5_qty + ")";
        }
        if (!(combo_pn_6.getSelectionModel()).isEmpty()) {
            value_ln6_inventoryID = Integer.parseInt(label_inventoryid_6.getText());
            value_ln6_pn = String.valueOf(combo_pn_6.getValue());
            value_ln6_desc = String.valueOf(text_desc_6.getText());
            value_ln6_price = Double.parseDouble(text_price_6.getText());
            value_ln6_qty = Double.parseDouble(text_qty_6.getText());
            sql_ln6 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln6_inventoryID + ",'" + value_ln6_pn + "','" + value_ln6_desc + "'," + 6 + "," + value_ln6_price + "," + value_ln6_qty + ")";
        }
        if (!(combo_pn_7.getSelectionModel()).isEmpty()) {
            value_ln7_inventoryID = Integer.parseInt(label_inventoryid_7.getText());
            value_ln7_pn = String.valueOf(combo_pn_7.getValue());
            value_ln7_desc = String.valueOf(text_desc_7.getText());
            value_ln7_price = Double.parseDouble(text_price_7.getText());
            value_ln7_qty = Double.parseDouble(text_qty_7.getText());
            sql_ln7 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln7_inventoryID + ",'" + value_ln7_pn + "','" + value_ln7_desc + "'," + 7 + "," + value_ln7_price + "," + value_ln7_qty + ")";
        }
        if (!(combo_pn_8.getSelectionModel()).isEmpty()) {
            value_ln8_inventoryID = Integer.parseInt(label_inventoryid_8.getText());
            value_ln8_pn = String.valueOf(combo_pn_8.getValue());
            value_ln8_desc = String.valueOf(text_desc_8.getText());
            value_ln8_price = Double.parseDouble(text_price_8.getText());
            value_ln8_qty = Double.parseDouble(text_qty_8.getText());
            sql_ln8 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln8_inventoryID + ",'" + value_ln8_pn + "','" + value_ln8_desc + "'," + 8 + "," + value_ln8_price + "," + value_ln8_qty + ")";
        }
        if (!(combo_pn_9.getSelectionModel()).isEmpty()) {
            value_ln9_inventoryID = Integer.parseInt(label_inventoryid_9.getText());
            value_ln9_pn = String.valueOf(combo_pn_9.getValue());
            value_ln9_desc = String.valueOf(text_desc_9.getText());
            value_ln9_price = Double.parseDouble(text_price_9.getText());
            value_ln9_qty = Double.parseDouble(text_qty_9.getText());
            sql_ln9 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln9_inventoryID + ",'" + value_ln9_pn + "','" + value_ln9_desc + "'," + 9 + "," + value_ln9_price + "," + value_ln9_qty + ")";
        }
        if (!(combo_pn_10.getSelectionModel()).isEmpty()) {
            value_ln10_inventoryID = Integer.parseInt(label_inventoryid_10.getText());
            value_ln10_pn = String.valueOf(combo_pn_10.getValue());
            value_ln10_desc = String.valueOf(text_desc_10.getText());
            value_ln10_price = Double.parseDouble(text_price_10.getText());
            value_ln10_qty = Double.parseDouble(text_qty_10.getText());
            sql_ln10 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln10_inventoryID + ",'" + value_ln10_pn + "','" + value_ln10_desc + "'," + 10 + "," + value_ln10_price + "," + value_ln10_qty + ")";
        }
        if (!(combo_pn_11.getSelectionModel()).isEmpty()) {
            value_ln11_inventoryID = Integer.parseInt(label_inventoryid_11.getText());
            value_ln11_pn = String.valueOf(combo_pn_11.getValue());
            value_ln11_desc = String.valueOf(text_desc_11.getText());
            value_ln11_price = Double.parseDouble(text_price_11.getText());
            value_ln11_qty = Double.parseDouble(text_qty_11.getText());
            sql_ln11 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln11_inventoryID + ",'" + value_ln11_pn + "','" + value_ln11_desc + "'," + 11 + "," + value_ln11_price + "," + value_ln11_qty + ")";
        }
        if (!(combo_pn_12.getSelectionModel()).isEmpty()) {
            value_ln12_inventoryID = Integer.parseInt(label_inventoryid_12.getText());
            value_ln12_pn = String.valueOf(combo_pn_12.getValue());
            value_ln12_desc = String.valueOf(text_desc_12.getText());
            value_ln12_price = Double.parseDouble(text_price_12.getText());
            value_ln12_qty = Double.parseDouble(text_qty_12.getText());
            sql_ln12 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln12_inventoryID + ",'" + value_ln12_pn + "','" + value_ln12_desc + "'," + 12 + "," + value_ln12_price + "," + value_ln12_qty + ")";
        }
        if (!(combo_pn_13.getSelectionModel()).isEmpty()) {
            value_ln13_inventoryID = Integer.parseInt(label_inventoryid_13.getText());
            value_ln13_pn = String.valueOf(combo_pn_13.getValue());
            value_ln13_desc = String.valueOf(text_desc_13.getText());
            value_ln13_price = Double.parseDouble(text_price_13.getText());
            value_ln13_qty = Double.parseDouble(text_qty_13.getText());
            sql_ln13 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln13_inventoryID + ",'" + value_ln13_pn + "','" + value_ln13_desc + "'," + 13 + "," + value_ln13_price + "," + value_ln13_qty + ")";
        }
        if (!(combo_pn_14.getSelectionModel()).isEmpty()) {
            value_ln14_inventoryID = Integer.parseInt(label_inventoryid_14.getText());
            value_ln14_pn = String.valueOf(combo_pn_14.getValue());
            value_ln14_desc = String.valueOf(text_desc_14.getText());
            value_ln14_price = Double.parseDouble(text_price_14.getText());
            value_ln14_qty = Double.parseDouble(text_qty_14.getText());
            sql_ln14 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln14_inventoryID + ",'" + value_ln14_pn + "','" + value_ln14_desc + "'," + 14 + "," + value_ln14_price + "," + value_ln14_qty + ")";
        }
        if (!(combo_pn_15.getSelectionModel()).isEmpty()) {
            value_ln15_inventoryID = Integer.parseInt(label_inventoryid_15.getText());
            value_ln15_pn = String.valueOf(combo_pn_15.getValue());
            value_ln15_desc = String.valueOf(text_desc_15.getText());
            value_ln15_price = Double.parseDouble(text_price_15.getText());
            value_ln15_qty = Double.parseDouble(text_qty_15.getText());
            sql_ln15 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                    " VALUES(" + value_orderID + "," + value_ln15_inventoryID + ",'" + value_ln15_pn + "','" + value_ln15_desc + "'," + 15 + "," + value_ln15_price + "," + value_ln15_qty + ")";
        }
        

        Connection conn = this.connect_db();

        try {
            ps_conn = conn.createStatement();

            ps_conn.executeUpdate(sql_order);

            if (!(sql_ln1 == null)){ ps_conn.executeUpdate(sql_ln1); }
            if (!(sql_ln2 == null)){ ps_conn.executeUpdate(sql_ln2); }
            if (!(sql_ln3 == null)){ ps_conn.executeUpdate(sql_ln3); }
            if (!(sql_ln4 == null)){ ps_conn.executeUpdate(sql_ln4); }
            if (!(sql_ln5 == null)){ ps_conn.executeUpdate(sql_ln5); }
            if (!(sql_ln6 == null)){ ps_conn.executeUpdate(sql_ln6); }
            if (!(sql_ln7 == null)){ ps_conn.executeUpdate(sql_ln7); }
            if (!(sql_ln8 == null)){ ps_conn.executeUpdate(sql_ln8); }
            if (!(sql_ln9 == null)){ ps_conn.executeUpdate(sql_ln9); }
            if (!(sql_ln10 == null)){ ps_conn.executeUpdate(sql_ln10); }
            if (!(sql_ln11 == null)){ ps_conn.executeUpdate(sql_ln11); }
            if (!(sql_ln12 == null)){ ps_conn.executeUpdate(sql_ln12); }
            if (!(sql_ln13 == null)){ ps_conn.executeUpdate(sql_ln13); }
            if (!(sql_ln14 == null)){ ps_conn.executeUpdate(sql_ln14); }
            if (!(sql_ln15 == null)){ ps_conn.executeUpdate(sql_ln15); }

            ps_conn.close();
            conn.commit();
            conn.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText(null);
            alert.setContentText("Please refresh the Inventory table.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
