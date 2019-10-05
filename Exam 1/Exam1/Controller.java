package Exam1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class Controller {
    @FXML JFXButton button_calc;
    @FXML JFXTextField text_1;
    @FXML JFXTextField text_2;
    @FXML Label label_result;

    @FXML public void btn_calculate_action(){
        boolean is_number = validateText();
        if (is_number == true){
            double number_1 = Double.parseDouble(text_1.getText());
            double number_2 = Double.parseDouble(text_2.getText());
            label_result.setText(Double.toString(number_1 + number_2));
        }
    }

    private boolean validateText(){
        //https://stackoverflow.com/questions/18084104/accept-only-numbers-and-a-dot-in-java-textfield
        if (text_1.getText().equals("") | text_2.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Input");
            alert.setHeaderText(null);
            alert.setContentText("Please complete entering numbers on the form.");
            alert.showAndWait();
            return false;
        } else {
            try {
                Double.parseDouble(text_1.getText());
                Double.parseDouble(text_2.getText());
                return true;
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("You can only input numbers into this form. Please try again.");
                alert.showAndWait();
                return false;
            }
        }
    }
}
