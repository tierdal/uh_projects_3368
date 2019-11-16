package HBCmanage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class controller_inventory_edit extends controller_inventory{
    @FXML
    public JFXButton btn_inventory_save,btn_inventory_cancel;
    @FXML public JFXTextField text_inventory_add_pn,text_inventory_add_price,text_inventory_add_cost,text_inventory_add_qty;
    @FXML public JFXComboBox combo_inventory_add_type;
    @FXML public JFXTextArea text_inventory_add_description;


    @FXML private void initialize() {
        populateTypeList();
        populatefields();
    }

    @FXML private void populatefields() {
        TableModel_InventoryData SelectedPN = inventory_tableview.getSelectionModel().getSelectedItem().getInventory_PartNumber();
        text_inventory_add_pn.setText(SelectedPN);
    }

    @FXML public void btn_inventory_save_action(){
        Stage stage = (Stage) btn_inventory_save.getScene().getWindow();
        //submit_user();
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
        combo_inventory_add_type.setItems(inventory_type_list);
    }
}
