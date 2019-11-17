package HBCmanage;

import com.jfoenix.controls.*;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.ResourceBundle;

public class controller_orders_edit extends class_global_vars implements Initializable {
    @FXML public JFXButton btn_save,btn_exit;
    @FXML public JFXComboBox combo_statusSelect,combo_employeeSelect,combo_pn_1,combo_pn_2,combo_pn_3,combo_pn_4,combo_pn_5,combo_pn_6,combo_pn_7,combo_pn_8,combo_pn_9,combo_pn_10,combo_pn_11,combo_pn_12,combo_pn_13,combo_pn_14,combo_pn_15;
    @FXML public JFXComboBox<String> combo_customerSelect;
    @FXML public JFXTextField text_tracking,text_price_1,text_price_2,text_price_3,text_price_4,text_price_5,text_price_6,text_price_7,text_price_8,text_price_9,text_price_10,text_price_11,text_price_12,text_price_13,text_price_14,text_price_15,text_qty_1,text_qty_2,text_qty_3,text_qty_4,text_qty_5,text_qty_6,text_qty_7,text_qty_8,text_qty_9,text_qty_10,text_qty_11,text_qty_12,text_qty_13,text_qty_14,text_qty_15;
    @FXML public JFXTextArea text_desc_1,text_desc_2,text_desc_3,text_desc_4,text_desc_5,text_desc_6,text_desc_7,text_desc_8,text_desc_9,text_desc_10,text_desc_11,text_desc_12,text_desc_13,text_desc_14,text_desc_15;
    @FXML public Label label_name,label_customerid,label_phone,label_email,label_total,label_orderid,label_inventoryid_1,label_inventoryid_2,label_inventoryid_3,label_inventoryid_4,label_inventoryid_5,label_inventoryid_6,label_inventoryid_7,label_inventoryid_8,label_inventoryid_9,label_inventoryid_10,label_inventoryid_11,label_inventoryid_12,label_inventoryid_13,label_inventoryid_14,label_inventoryid_15,label_orderitemid_1,label_orderitemid_2,label_orderitemid_3,label_orderitemid_4,label_orderitemid_5,label_orderitemid_6,label_orderitemid_7,label_orderitemid_8,label_orderitemid_9,label_orderitemid_10,label_orderitemid_11,label_orderitemid_12,label_orderitemid_13,label_orderitemid_14,label_orderitemid_15,label_extprice_1,label_extprice_2,label_extprice_3,label_extprice_4,label_extprice_5,label_extprice_6,label_extprice_7,label_extprice_8,label_extprice_9,label_extprice_10,label_extprice_11,label_extprice_12,label_extprice_13,label_extprice_14,label_extprice_15;
    @FXML public JFXDatePicker date_created;
    public int selected_id;
    public boolean ln1_used,ln2_used,ln3_used,ln4_used,ln5_used,ln6_used,ln7_used,ln8_used,ln9_used,ln10_used,ln11_used,ln12_used,ln13_used,ln14_used,ln15_used;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selected_id = orders_selected_id;

        populateCustomerList();
        populateStatusList();
        populateEmployeeList();
        populate_pn_list();

        load_form_values();
        load_orderitems();

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

    public void load_form_values(){
        Connection conn = this.connect_db();

        try {
            ResultSet result_set;
            String sql_main = "SELECT * FROM finalproject_orders WHERE Order_ID=" + selected_id;

            result_set = conn.createStatement().executeQuery(sql_main);

            result_set.next();
            label_orderid.setText(result_set.getString("Order_ID"));
            combo_customerSelect.setValue(result_set.getString("Customer_Name"));
            combo_statusSelect.setValue(result_set.getString("Order_Status"));
            combo_employeeSelect.setValue(result_set.getString("Employee_Name"));
            text_tracking.setText(result_set.getString("Order_TrackingNumber"));
            date_created.setValue(result_set.getDate("Order_DateCreated").toLocalDate());

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

        updateCustomerInfo();

    }

    public void load_orderitems(){
        Connection conn = this.connect_db();
        String sql_ln1 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 1;
        String sql_ln2 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 2;
        String sql_ln3 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 3;
        String sql_ln4 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 4;
        String sql_ln5 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 5;
        String sql_ln6 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 6;
        String sql_ln7 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 7;
        String sql_ln8 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 8;
        String sql_ln9 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 9;
        String sql_ln10 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 10;
        String sql_ln11 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 11;
        String sql_ln12 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 12;
        String sql_ln13 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 13;
        String sql_ln14 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 14;
        String sql_ln15 = "SELECT * FROM finalproject_orderitems WHERE Order_ID=" + selected_id + " AND OrderItems_LineItem = " + 15;

        ResultSet result_set;

        try {
            result_set = conn.createStatement().executeQuery(sql_ln1);
            result_set.next();
            label_orderitemid_1.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_1.setText(result_set.getString("Inventory_ID"));
            combo_pn_1.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_1.setText(result_set.getString("Inventory_Description"));
            text_qty_1.setText(result_set.getString("OrderItems_Quantity"));
            text_price_1.setText(result_set.getString("OrderItems_Price"));
            ln1_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 1 - Error on Building Data");
            ln1_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln2);
            result_set.next();
            label_orderitemid_2.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_2.setText(result_set.getString("Inventory_ID"));
            combo_pn_2.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_2.setText(result_set.getString("Inventory_Description"));
            text_qty_2.setText(result_set.getString("OrderItems_Quantity"));
            text_price_2.setText(result_set.getString("OrderItems_Price"));
            ln2_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 2 - Error on Building Data");
            ln2_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln3);
            result_set.next();
            label_orderitemid_3.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_3.setText(result_set.getString("Inventory_ID"));
            combo_pn_3.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_3.setText(result_set.getString("Inventory_Description"));
            text_qty_3.setText(result_set.getString("OrderItems_Quantity"));
            text_price_3.setText(result_set.getString("OrderItems_Price"));
            ln3_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 3 - Error on Building Data");
            ln3_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln4);
            result_set.next();
            label_orderitemid_4.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_4.setText(result_set.getString("Inventory_ID"));
            combo_pn_4.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_4.setText(result_set.getString("Inventory_Description"));
            text_qty_4.setText(result_set.getString("OrderItems_Quantity"));
            text_price_4.setText(result_set.getString("OrderItems_Price"));
            ln4_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 4 - Error on Building Data");
            ln4_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln5);
            result_set.next();
            label_orderitemid_5.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_5.setText(result_set.getString("Inventory_ID"));
            combo_pn_5.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_5.setText(result_set.getString("Inventory_Description"));
            text_qty_5.setText(result_set.getString("OrderItems_Quantity"));
            text_price_5.setText(result_set.getString("OrderItems_Price"));
            ln5_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 5 - Error on Building Data");
            ln5_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln6);
            result_set.next();
            label_orderitemid_6.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_6.setText(result_set.getString("Inventory_ID"));
            combo_pn_6.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_6.setText(result_set.getString("Inventory_Description"));
            text_qty_6.setText(result_set.getString("OrderItems_Quantity"));
            text_price_6.setText(result_set.getString("OrderItems_Price"));
            ln6_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 6 - Error on Building Data");
            ln6_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln7);
            result_set.next();
            label_orderitemid_7.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_7.setText(result_set.getString("Inventory_ID"));
            combo_pn_7.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_7.setText(result_set.getString("Inventory_Description"));
            text_qty_7.setText(result_set.getString("OrderItems_Quantity"));
            text_price_7.setText(result_set.getString("OrderItems_Price"));
            ln7_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 7 - Error on Building Data");
            ln7_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln8);
            result_set.next();
            label_orderitemid_8.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_8.setText(result_set.getString("Inventory_ID"));
            combo_pn_8.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_8.setText(result_set.getString("Inventory_Description"));
            text_qty_8.setText(result_set.getString("OrderItems_Quantity"));
            text_price_8.setText(result_set.getString("OrderItems_Price"));
            ln8_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 8 - Error on Building Data");
            ln8_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln9);
            result_set.next();
            label_orderitemid_9.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_9.setText(result_set.getString("Inventory_ID"));
            combo_pn_9.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_9.setText(result_set.getString("Inventory_Description"));
            text_qty_9.setText(result_set.getString("OrderItems_Quantity"));
            text_price_9.setText(result_set.getString("OrderItems_Price"));
            ln9_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 9 - Error on Building Data");
            ln9_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln10);
            result_set.next();
            label_orderitemid_10.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_10.setText(result_set.getString("Inventory_ID"));
            combo_pn_10.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_10.setText(result_set.getString("Inventory_Description"));
            text_qty_10.setText(result_set.getString("OrderItems_Quantity"));
            text_price_10.setText(result_set.getString("OrderItems_Price"));
            ln10_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 10 - Error on Building Data");
            ln10_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln11);
            result_set.next();
            label_orderitemid_11.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_11.setText(result_set.getString("Inventory_ID"));
            combo_pn_11.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_11.setText(result_set.getString("Inventory_Description"));
            text_qty_11.setText(result_set.getString("OrderItems_Quantity"));
            text_price_11.setText(result_set.getString("OrderItems_Price"));
            ln11_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 11 - Error on Building Data");
            ln11_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln12);
            result_set.next();
            label_orderitemid_12.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_12.setText(result_set.getString("Inventory_ID"));
            combo_pn_12.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_12.setText(result_set.getString("Inventory_Description"));
            text_qty_12.setText(result_set.getString("OrderItems_Quantity"));
            text_price_12.setText(result_set.getString("OrderItems_Price"));
            ln12_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 12 - Error on Building Data");
            ln12_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln13);
            result_set.next();
            label_orderitemid_13.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_13.setText(result_set.getString("Inventory_ID"));
            combo_pn_13.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_13.setText(result_set.getString("Inventory_Description"));
            text_qty_13.setText(result_set.getString("OrderItems_Quantity"));
            text_price_13.setText(result_set.getString("OrderItems_Price"));
            ln13_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 13 - Error on Building Data");
            ln13_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln14);
            result_set.next();
            label_orderitemid_14.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_14.setText(result_set.getString("Inventory_ID"));
            combo_pn_14.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_14.setText(result_set.getString("Inventory_Description"));
            text_qty_14.setText(result_set.getString("OrderItems_Quantity"));
            text_price_14.setText(result_set.getString("OrderItems_Price"));
            ln14_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 14 - Error on Building Data");
            ln14_used = false;
        }
        try {
            result_set = conn.createStatement().executeQuery(sql_ln15);
            result_set.next();
            label_orderitemid_15.setText(result_set.getString("OrderItems_ID"));
            label_inventoryid_15.setText(result_set.getString("Inventory_ID"));
            combo_pn_15.setValue(result_set.getString("Inventory_PartNumber"));
            text_desc_15.setText(result_set.getString("Inventory_Description"));
            text_qty_15.setText(result_set.getString("OrderItems_Quantity"));
            text_price_15.setText(result_set.getString("OrderItems_Price"));
            ln15_used = true;
        } catch (SQLException e) {
            System.out.println("ITEM 15 - Error on Building Data");
            ln15_used = false;
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

        calculateTotal();

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

    private void update_inventory(int InventoryID, double Quantity, String operationType){
        double Balance=0,qtyonhand=0;
        String ItemType="";
        Statement ps_conn;
        Connection conn = this.connect_db();
        try {
            String sql_get = "SELECT * FROM finalproject_inventory WHERE Inventory_ID LIKE " + InventoryID + "";
            ResultSet result_set = conn.createStatement().executeQuery(sql_get);

            while (result_set.next()) {
                qtyonhand = Double.parseDouble(result_set.getString("Inventory_QtyOnHand"));
                ItemType = result_set.getString("Inventory_Type");
            }
            if (!ItemType.equals("Service")) {

                if (operationType.equals("Add")) {
                    Balance = qtyonhand + Quantity;
                }else if (operationType.equals("Subtract")) {
                    Balance = qtyonhand - Quantity;
                }
                String sql_set = "UPDATE finalproject_inventory SET Inventory_QtyOnHand=" + Balance + " WHERE Inventory_ID=" + InventoryID;
                ps_conn = conn.createStatement();
                ps_conn.executeUpdate(sql_set);
                ps_conn.close();
                conn.commit();
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void save_order(){
        String value_customerName,value_tracking,value_status,value_employeeName,value_ln1_pn,value_ln2_pn,value_ln3_pn,value_ln4_pn,value_ln5_pn,value_ln6_pn,value_ln7_pn,value_ln8_pn,value_ln9_pn,value_ln10_pn,value_ln11_pn,value_ln12_pn,value_ln13_pn,value_ln14_pn,value_ln15_pn,value_ln1_desc,value_ln2_desc,value_ln3_desc,value_ln4_desc,value_ln5_desc,value_ln6_desc,value_ln7_desc,value_ln8_desc,value_ln9_desc,value_ln10_desc,value_ln11_desc,value_ln12_desc,value_ln13_desc,value_ln14_desc,value_ln15_desc;
        double value_orderTotal,value_ln1_qty = 0,value_ln2_qty = 0,value_ln3_qty = 0,value_ln4_qty = 0,value_ln5_qty = 0,value_ln6_qty = 0,value_ln7_qty = 0,value_ln8_qty = 0,value_ln9_qty = 0,value_ln10_qty = 0,value_ln11_qty = 0,value_ln12_qty = 0,value_ln13_qty = 0,value_ln14_qty = 0,value_ln15_qty = 0,value_ln1_price,value_ln2_price,value_ln3_price,value_ln4_price,value_ln5_price,value_ln6_price,value_ln7_price,value_ln8_price,value_ln9_price,value_ln10_price,value_ln11_price,value_ln12_price,value_ln13_price,value_ln14_price,value_ln15_price;
        int value_customerID,value_orderID,value_ln1_inventoryID = 0,value_ln2_inventoryID = 0,value_ln3_inventoryID = 0,value_ln4_inventoryID = 0,value_ln5_inventoryID = 0,value_ln6_inventoryID = 0,value_ln7_inventoryID = 0,value_ln8_inventoryID = 0,value_ln9_inventoryID = 0,value_ln10_inventoryID = 0,value_ln11_inventoryID = 0,value_ln12_inventoryID = 0,value_ln13_inventoryID = 0,value_ln14_inventoryID = 0,value_ln15_inventoryID = 0,value_ln1_id,value_ln2_id,value_ln3_id,value_ln4_id,value_ln5_id,value_ln6_id,value_ln7_id,value_ln8_id,value_ln9_id,value_ln10_id,value_ln11_id,value_ln12_id,value_ln13_id,value_ln14_id,value_ln15_id;
        String math_type_1 = null,math_type_2 = null,math_type_3 = null,math_type_4 = null,math_type_5 = null,math_type_6 = null,math_type_7 = null,math_type_8 = null,math_type_9 = null,math_type_10 = null,math_type_11 = null,math_type_12 = null,math_type_13 = null,math_type_14 = null,math_type_15 = null;
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

        String sql_order = "UPDATE finalproject_orders SET Customer_ID="+ value_customerID + ",Customer_Name='"+ value_customerName + "',Order_Total="+value_orderTotal+
                ",Order_TrackingNumber='"+value_tracking+"',Order_Status='"+value_status+"',Order_DateCreated='"+value_date+"',Employee_Name='"+value_employeeName+
                "' WHERE Order_ID=" + selected_id;

        if (!(combo_pn_1.getSelectionModel()).isEmpty() | (combo_pn_1.getSelectionModel()).isEmpty() && !(label_orderitemid_1.getText().isEmpty())) {
            value_ln1_inventoryID = Integer.parseInt(label_inventoryid_1.getText());
            value_ln1_pn = String.valueOf(combo_pn_1.getValue());
            value_ln1_desc = String.valueOf(text_desc_1.getText());
            value_ln1_price = Double.parseDouble(text_price_1.getText());
            value_ln1_qty = Double.parseDouble(text_qty_1.getText());
            value_ln1_id = Integer.parseInt(label_orderitemid_1.getText());
            if ((combo_pn_1.getSelectionModel()).isEmpty()){math_type_1 = "Add";} else {math_type_1 = "Subtract";}
            if (ln1_used) {
                sql_ln1 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln1_inventoryID + ",Inventory_PartNumber='"+value_ln1_pn+"',Inventory_Description='"+
                        value_ln1_desc+"',OrderItems_Price="+value_ln1_price+",OrderItems_Quantity="+value_ln1_qty+
                        " WHERE OrderItems_ID="+value_ln1_id;
            } else {
                sql_ln1 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln1_inventoryID + ",'" + value_ln1_pn + "','" + value_ln1_desc + "'," + 1 + "," + value_ln1_price + "," + value_ln1_qty + ")";
            }
        }
        if (!(combo_pn_2.getSelectionModel()).isEmpty() | (combo_pn_2.getSelectionModel()).isEmpty() && !(label_orderitemid_2.getText()).isEmpty()) {
            value_ln2_inventoryID = Integer.parseInt(label_inventoryid_2.getText());
            value_ln2_pn = String.valueOf(combo_pn_2.getValue());
            value_ln2_desc = String.valueOf(text_desc_2.getText());
            value_ln2_price = Double.parseDouble(text_price_2.getText());
            value_ln2_qty = Double.parseDouble(text_qty_2.getText());
            value_ln2_id = Integer.parseInt(label_orderitemid_2.getText());
            if ((combo_pn_2.getSelectionModel()).isEmpty()){math_type_2 = "Add";} else {math_type_2 = "Subtract";}
            if (ln2_used) {
                sql_ln2 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln2_inventoryID + ",Inventory_PartNumber='"+value_ln2_pn+"',Inventory_Description='"+
                        value_ln2_desc+"',OrderItems_Price="+value_ln2_price+",OrderItems_Quantity="+value_ln2_qty+
                        " WHERE OrderItems_ID="+value_ln2_id;
            } else {
                sql_ln2 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln2_inventoryID + ",'" + value_ln2_pn + "','" + value_ln2_desc + "'," + 2 + "," + value_ln2_price + "," + value_ln2_qty + ")";
            }
        }
        if (!(combo_pn_3.getSelectionModel()).isEmpty() | (combo_pn_3.getSelectionModel()).isEmpty() && !(label_orderitemid_3.getText()).isEmpty()) {
            value_ln3_inventoryID = Integer.parseInt(label_inventoryid_3.getText());
            value_ln3_pn = String.valueOf(combo_pn_3.getValue());
            value_ln3_desc = String.valueOf(text_desc_3.getText());
            value_ln3_price = Double.parseDouble(text_price_3.getText());
            value_ln3_qty = Double.parseDouble(text_qty_3.getText());
            value_ln3_id = Integer.parseInt(label_orderitemid_3.getText());
            if ((combo_pn_3.getSelectionModel()).isEmpty()){math_type_3 = "Add";} else {math_type_3 = "Subtract";}
            if (ln3_used) {
                sql_ln3 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln3_inventoryID + ",Inventory_PartNumber='"+value_ln3_pn+"',Inventory_Description='"+
                        value_ln3_desc+"',OrderItems_Price="+value_ln3_price+",OrderItems_Quantity="+value_ln3_qty+
                        " WHERE OrderItems_ID="+value_ln3_id;
            } else {
                sql_ln3 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln3_inventoryID + ",'" + value_ln3_pn + "','" + value_ln3_desc + "'," + 3 + "," + value_ln3_price + "," + value_ln3_qty + ")";
            }
        }
        if (!(combo_pn_4.getSelectionModel()).isEmpty() | (combo_pn_4.getSelectionModel()).isEmpty() && !(label_orderitemid_4.getText()).isEmpty()) {
            value_ln4_inventoryID = Integer.parseInt(label_inventoryid_4.getText());
            value_ln4_pn = String.valueOf(combo_pn_4.getValue());
            value_ln4_desc = String.valueOf(text_desc_4.getText());
            value_ln4_price = Double.parseDouble(text_price_4.getText());
            value_ln4_qty = Double.parseDouble(text_qty_4.getText());
            value_ln4_id = Integer.parseInt(label_orderitemid_4.getText());
            if ((combo_pn_4.getSelectionModel()).isEmpty()){math_type_4 = "Add";} else {math_type_4 = "Subtract";}
            if (ln4_used) {
                sql_ln4 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln4_inventoryID + ",Inventory_PartNumber='"+value_ln4_pn+"',Inventory_Description='"+
                        value_ln4_desc+"',OrderItems_Price="+value_ln4_price+",OrderItems_Quantity="+value_ln4_qty+
                        " WHERE OrderItems_ID="+value_ln4_id;
            } else {
                sql_ln4 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln4_inventoryID + ",'" + value_ln4_pn + "','" + value_ln4_desc + "'," + 4 + "," + value_ln4_price + "," + value_ln4_qty + ")";
            }
        }
        if (!(combo_pn_5.getSelectionModel()).isEmpty() | (combo_pn_5.getSelectionModel()).isEmpty() && !(label_orderitemid_5.getText()).isEmpty()) {
            value_ln5_inventoryID = Integer.parseInt(label_inventoryid_5.getText());
            value_ln5_pn = String.valueOf(combo_pn_5.getValue());
            value_ln5_desc = String.valueOf(text_desc_5.getText());
            value_ln5_price = Double.parseDouble(text_price_5.getText());
            value_ln5_qty = Double.parseDouble(text_qty_5.getText());
            value_ln5_id = Integer.parseInt(label_orderitemid_5.getText());
            if ((combo_pn_5.getSelectionModel()).isEmpty()){math_type_5 = "Add";} else {math_type_5 = "Subtract";}
            if (ln5_used) {
                sql_ln5 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln5_inventoryID + ",Inventory_PartNumber='"+value_ln5_pn+"',Inventory_Description='"+
                        value_ln5_desc+"',OrderItems_Price="+value_ln5_price+",OrderItems_Quantity="+value_ln5_qty+
                        " WHERE OrderItems_ID="+value_ln5_id;
            } else {
                sql_ln5 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln5_inventoryID + ",'" + value_ln5_pn + "','" + value_ln5_desc + "'," + 5 + "," + value_ln5_price + "," + value_ln5_qty + ")";
            }
        }
        if (!(combo_pn_6.getSelectionModel()).isEmpty() | (combo_pn_6.getSelectionModel()).isEmpty() && !(label_orderitemid_6.getText()).isEmpty()) {
            value_ln6_inventoryID = Integer.parseInt(label_inventoryid_6.getText());
            value_ln6_pn = String.valueOf(combo_pn_6.getValue());
            value_ln6_desc = String.valueOf(text_desc_6.getText());
            value_ln6_price = Double.parseDouble(text_price_6.getText());
            value_ln6_qty = Double.parseDouble(text_qty_6.getText());
            value_ln6_id = Integer.parseInt(label_orderitemid_6.getText());
            if ((combo_pn_6.getSelectionModel()).isEmpty()){math_type_6 = "Add";} else {math_type_6 = "Subtract";}
            if (ln6_used) {
                sql_ln6 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln6_inventoryID + ",Inventory_PartNumber='"+value_ln6_pn+"',Inventory_Description='"+
                        value_ln6_desc+"',OrderItems_Price="+value_ln6_price+",OrderItems_Quantity="+value_ln6_qty+
                        " WHERE OrderItems_ID="+value_ln6_id;
            } else {
                sql_ln6 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln6_inventoryID + ",'" + value_ln6_pn + "','" + value_ln6_desc + "'," + 6 + "," + value_ln6_price + "," + value_ln6_qty + ")";
            }
        }
        if (!(combo_pn_7.getSelectionModel()).isEmpty() | (combo_pn_7.getSelectionModel()).isEmpty() && !(label_orderitemid_7.getText()).isEmpty()) {
            value_ln7_inventoryID = Integer.parseInt(label_inventoryid_7.getText());
            value_ln7_pn = String.valueOf(combo_pn_7.getValue());
            value_ln7_desc = String.valueOf(text_desc_7.getText());
            value_ln7_price = Double.parseDouble(text_price_7.getText());
            value_ln7_qty = Double.parseDouble(text_qty_7.getText());
            value_ln7_id = Integer.parseInt(label_orderitemid_7.getText());
            if ((combo_pn_7.getSelectionModel()).isEmpty()){math_type_7 = "Add";} else {math_type_7 = "Subtract";}
            if (ln7_used) {
                sql_ln7 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln7_inventoryID + ",Inventory_PartNumber='"+value_ln7_pn+"',Inventory_Description='"+
                        value_ln7_desc+"',OrderItems_Price="+value_ln7_price+",OrderItems_Quantity="+value_ln7_qty+
                        " WHERE OrderItems_ID="+value_ln7_id;
            } else {
                sql_ln7 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln7_inventoryID + ",'" + value_ln7_pn + "','" + value_ln7_desc + "'," + 7 + "," + value_ln7_price + "," + value_ln7_qty + ")";
            }
        }
        if (!(combo_pn_8.getSelectionModel()).isEmpty() | (combo_pn_8.getSelectionModel()).isEmpty() && !(label_orderitemid_8.getText()).isEmpty()) {
            value_ln8_inventoryID = Integer.parseInt(label_inventoryid_8.getText());
            value_ln8_pn = String.valueOf(combo_pn_8.getValue());
            value_ln8_desc = String.valueOf(text_desc_8.getText());
            value_ln8_price = Double.parseDouble(text_price_8.getText());
            value_ln8_qty = Double.parseDouble(text_qty_8.getText());
            value_ln8_id = Integer.parseInt(label_orderitemid_8.getText());
            if ((combo_pn_8.getSelectionModel()).isEmpty()){math_type_8 = "Add";} else {math_type_8 = "Subtract";}
            if (ln8_used) {
                sql_ln8 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln8_inventoryID + ",Inventory_PartNumber='"+value_ln8_pn+"',Inventory_Description='"+
                        value_ln8_desc+"',OrderItems_Price="+value_ln8_price+",OrderItems_Quantity="+value_ln8_qty+
                        " WHERE OrderItems_ID="+value_ln8_id;
            } else {
                sql_ln8 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln8_inventoryID + ",'" + value_ln8_pn + "','" + value_ln8_desc + "'," + 8 + "," + value_ln8_price + "," + value_ln8_qty + ")";
            }
        }
        if (!(combo_pn_9.getSelectionModel()).isEmpty() | (combo_pn_9.getSelectionModel()).isEmpty() && !(label_orderitemid_9.getText()).isEmpty()) {
            value_ln9_inventoryID = Integer.parseInt(label_inventoryid_9.getText());
            value_ln9_pn = String.valueOf(combo_pn_9.getValue());
            value_ln9_desc = String.valueOf(text_desc_9.getText());
            value_ln9_price = Double.parseDouble(text_price_9.getText());
            value_ln9_qty = Double.parseDouble(text_qty_9.getText());
            value_ln9_id = Integer.parseInt(label_orderitemid_9.getText());
            if ((combo_pn_9.getSelectionModel()).isEmpty()){math_type_9 = "Add";} else {math_type_9 = "Subtract";}
            if (ln9_used) {
                sql_ln9 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln9_inventoryID + ",Inventory_PartNumber='"+value_ln9_pn+"',Inventory_Description='"+
                        value_ln9_desc+"',OrderItems_Price="+value_ln9_price+",OrderItems_Quantity="+value_ln9_qty+
                        " WHERE OrderItems_ID="+value_ln9_id;
            } else {
                sql_ln9 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln9_inventoryID + ",'" + value_ln9_pn + "','" + value_ln9_desc + "'," + 9 + "," + value_ln9_price + "," + value_ln9_qty + ")";
            }
        }
        if (!(combo_pn_10.getSelectionModel()).isEmpty() | (combo_pn_10.getSelectionModel()).isEmpty() && !(label_orderitemid_10.getText()).isEmpty()) {
            value_ln10_inventoryID = Integer.parseInt(label_inventoryid_10.getText());
            value_ln10_pn = String.valueOf(combo_pn_10.getValue());
            value_ln10_desc = String.valueOf(text_desc_10.getText());
            value_ln10_price = Double.parseDouble(text_price_10.getText());
            value_ln10_qty = Double.parseDouble(text_qty_10.getText());
            value_ln10_id = Integer.parseInt(label_orderitemid_10.getText());
            if ((combo_pn_10.getSelectionModel()).isEmpty()){math_type_10 = "Add";} else {math_type_10 = "Subtract";}
            if (ln10_used) {
                sql_ln10 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln10_inventoryID + ",Inventory_PartNumber='"+value_ln10_pn+"',Inventory_Description='"+
                        value_ln10_desc+"',OrderItems_Price="+value_ln10_price+",OrderItems_Quantity="+value_ln10_qty+
                        " WHERE OrderItems_ID="+value_ln10_id;
            } else {
                sql_ln10 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln10_inventoryID + ",'" + value_ln10_pn + "','" + value_ln10_desc + "'," + 10 + "," + value_ln10_price + "," + value_ln10_qty + ")";
            }
        }
        if (!(combo_pn_11.getSelectionModel()).isEmpty() | (combo_pn_11.getSelectionModel()).isEmpty() && !(label_orderitemid_11.getText()).isEmpty()) {
            value_ln11_inventoryID = Integer.parseInt(label_inventoryid_11.getText());
            value_ln11_pn = String.valueOf(combo_pn_11.getValue());
            value_ln11_desc = String.valueOf(text_desc_11.getText());
            value_ln11_price = Double.parseDouble(text_price_11.getText());
            value_ln11_qty = Double.parseDouble(text_qty_11.getText());
            value_ln11_id = Integer.parseInt(label_orderitemid_11.getText());
            if ((combo_pn_11.getSelectionModel()).isEmpty()){math_type_11 = "Add";} else {math_type_11 = "Subtract";}
            if (ln11_used) {
                sql_ln11 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln11_inventoryID + ",Inventory_PartNumber='"+value_ln11_pn+"',Inventory_Description='"+
                        value_ln11_desc+"',OrderItems_Price="+value_ln11_price+",OrderItems_Quantity="+value_ln11_qty+
                        " WHERE OrderItems_ID="+value_ln11_id;
            } else {
                sql_ln11 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln11_inventoryID + ",'" + value_ln11_pn + "','" + value_ln11_desc + "'," + 11 + "," + value_ln11_price + "," + value_ln11_qty + ")";
            }
        }
        if (!(combo_pn_12.getSelectionModel()).isEmpty() | (combo_pn_12.getSelectionModel()).isEmpty() && !(label_orderitemid_12.getText()).isEmpty()) {
            value_ln12_inventoryID = Integer.parseInt(label_inventoryid_12.getText());
            value_ln12_pn = String.valueOf(combo_pn_12.getValue());
            value_ln12_desc = String.valueOf(text_desc_12.getText());
            value_ln12_price = Double.parseDouble(text_price_12.getText());
            value_ln12_qty = Double.parseDouble(text_qty_12.getText());
            value_ln12_id = Integer.parseInt(label_orderitemid_12.getText());
            if ((combo_pn_12.getSelectionModel()).isEmpty()){math_type_12 = "Add";} else {math_type_12 = "Subtract";}
            if (ln12_used) {
                sql_ln12 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln12_inventoryID + ",Inventory_PartNumber='"+value_ln12_pn+"',Inventory_Description='"+
                        value_ln12_desc+"',OrderItems_Price="+value_ln12_price+",OrderItems_Quantity="+value_ln12_qty+
                        " WHERE OrderItems_ID="+value_ln12_id;
            } else {
                sql_ln12 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln12_inventoryID + ",'" + value_ln12_pn + "','" + value_ln12_desc + "'," + 12 + "," + value_ln12_price + "," + value_ln12_qty + ")";
            }
        }
        if (!(combo_pn_13.getSelectionModel()).isEmpty() | (combo_pn_13.getSelectionModel()).isEmpty() && !(label_orderitemid_13.getText()).isEmpty()) {
            value_ln13_inventoryID = Integer.parseInt(label_inventoryid_13.getText());
            value_ln13_pn = String.valueOf(combo_pn_13.getValue());
            value_ln13_desc = String.valueOf(text_desc_13.getText());
            value_ln13_price = Double.parseDouble(text_price_13.getText());
            value_ln13_qty = Double.parseDouble(text_qty_13.getText());
            value_ln13_id = Integer.parseInt(label_orderitemid_13.getText());
            if ((combo_pn_13.getSelectionModel()).isEmpty()){math_type_13 = "Add";} else {math_type_13 = "Subtract";}
            if (ln13_used) {
                sql_ln13 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln13_inventoryID + ",Inventory_PartNumber='"+value_ln13_pn+"',Inventory_Description='"+
                        value_ln13_desc+"',OrderItems_Price="+value_ln13_price+",OrderItems_Quantity="+value_ln13_qty+
                        " WHERE OrderItems_ID="+value_ln13_id;
            } else {
                sql_ln13 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln13_inventoryID + ",'" + value_ln13_pn + "','" + value_ln13_desc + "'," + 13 + "," + value_ln13_price + "," + value_ln13_qty + ")";
            }
        }
        if (!(combo_pn_14.getSelectionModel()).isEmpty() | (combo_pn_14.getSelectionModel()).isEmpty() && !(label_orderitemid_14.getText()).isEmpty()) {
            value_ln14_inventoryID = Integer.parseInt(label_inventoryid_14.getText());
            value_ln14_pn = String.valueOf(combo_pn_14.getValue());
            value_ln14_desc = String.valueOf(text_desc_14.getText());
            value_ln14_price = Double.parseDouble(text_price_14.getText());
            value_ln14_qty = Double.parseDouble(text_qty_14.getText());
            value_ln14_id = Integer.parseInt(label_orderitemid_14.getText());
            if ((combo_pn_14.getSelectionModel()).isEmpty()){math_type_14 = "Add";} else {math_type_14 = "Subtract";}
            if (ln14_used) {
                sql_ln14 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln14_inventoryID + ",Inventory_PartNumber='"+value_ln14_pn+"',Inventory_Description='"+
                        value_ln14_desc+"',OrderItems_Price="+value_ln14_price+",OrderItems_Quantity="+value_ln14_qty+
                        " WHERE OrderItems_ID="+value_ln14_id;
            } else {
                sql_ln14 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln14_inventoryID + ",'" + value_ln14_pn + "','" + value_ln14_desc + "'," + 14 + "," + value_ln14_price + "," + value_ln14_qty + ")";
            }
        }
        if (!(combo_pn_15.getSelectionModel()).isEmpty() | (combo_pn_15.getSelectionModel()).isEmpty() && !(label_orderitemid_15.getText()).isEmpty()) {
            value_ln15_inventoryID = Integer.parseInt(label_inventoryid_15.getText());
            value_ln15_pn = String.valueOf(combo_pn_15.getValue());
            value_ln15_desc = String.valueOf(text_desc_15.getText());
            value_ln15_price = Double.parseDouble(text_price_15.getText());
            value_ln15_qty = Double.parseDouble(text_qty_15.getText());
            value_ln15_id = Integer.parseInt(label_orderitemid_15.getText());
            if ((combo_pn_15.getSelectionModel()).isEmpty()){math_type_15 = "Add";} else {math_type_15 = "Subtract";}
            if (ln15_used) {
                sql_ln15 = "UPDATE finalproject_orderitems SET Inventory_ID=" + value_ln15_inventoryID + ",Inventory_PartNumber='"+value_ln15_pn+"',Inventory_Description='"+
                        value_ln15_desc+"',OrderItems_Price="+value_ln15_price+",OrderItems_Quantity="+value_ln15_qty+
                        " WHERE OrderItems_ID="+value_ln15_id;
            } else {
                sql_ln15 = "INSERT INTO finalproject_orderitems(Order_ID,Inventory_ID,Inventory_PartNumber,Inventory_Description,OrderItems_LineItem, OrderItems_Price,OrderItems_Quantity)" +
                        " VALUES(" + value_orderID + "," + value_ln15_inventoryID + ",'" + value_ln15_pn + "','" + value_ln15_desc + "'," + 15 + "," + value_ln15_price + "," + value_ln15_qty + ")";
            }
        }

        Connection conn = this.connect_db();

        try {
            ps_conn = conn.createStatement();

            ps_conn.executeUpdate(sql_order);

            if (!(sql_ln1 == null)){ ps_conn.executeUpdate(sql_ln1); update_inventory(value_ln1_inventoryID,value_ln1_qty,math_type_1);}
            if (!(sql_ln2 == null)){ ps_conn.executeUpdate(sql_ln2); update_inventory(value_ln2_inventoryID,value_ln2_qty,math_type_2);}
            if (!(sql_ln3 == null)){ ps_conn.executeUpdate(sql_ln3); update_inventory(value_ln3_inventoryID,value_ln3_qty,math_type_3);}
            if (!(sql_ln4 == null)){ ps_conn.executeUpdate(sql_ln4); update_inventory(value_ln4_inventoryID,value_ln4_qty,math_type_4);}
            if (!(sql_ln5 == null)){ ps_conn.executeUpdate(sql_ln5); update_inventory(value_ln5_inventoryID,value_ln5_qty,math_type_5);}
            if (!(sql_ln6 == null)){ ps_conn.executeUpdate(sql_ln6); update_inventory(value_ln6_inventoryID,value_ln6_qty,math_type_6);}
            if (!(sql_ln7 == null)){ ps_conn.executeUpdate(sql_ln7); update_inventory(value_ln7_inventoryID,value_ln7_qty,math_type_7);}
            if (!(sql_ln8 == null)){ ps_conn.executeUpdate(sql_ln8); update_inventory(value_ln8_inventoryID,value_ln8_qty,math_type_8);}
            if (!(sql_ln9 == null)){ ps_conn.executeUpdate(sql_ln9); update_inventory(value_ln9_inventoryID,value_ln9_qty,math_type_9);}
            if (!(sql_ln10 == null)){ ps_conn.executeUpdate(sql_ln10); update_inventory(value_ln10_inventoryID,value_ln10_qty,math_type_10);}
            if (!(sql_ln11 == null)){ ps_conn.executeUpdate(sql_ln11); update_inventory(value_ln11_inventoryID,value_ln11_qty,math_type_11);}
            if (!(sql_ln12 == null)){ ps_conn.executeUpdate(sql_ln12); update_inventory(value_ln12_inventoryID,value_ln12_qty,math_type_12);}
            if (!(sql_ln13 == null)){ ps_conn.executeUpdate(sql_ln13); update_inventory(value_ln13_inventoryID,value_ln13_qty,math_type_13);}
            if (!(sql_ln14 == null)){ ps_conn.executeUpdate(sql_ln14); update_inventory(value_ln14_inventoryID,value_ln14_qty,math_type_14);}
            if (!(sql_ln15 == null)){ ps_conn.executeUpdate(sql_ln15); update_inventory(value_ln15_inventoryID,value_ln15_qty,math_type_15);}

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
