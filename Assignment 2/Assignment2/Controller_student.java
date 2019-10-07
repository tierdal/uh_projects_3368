package Assignment2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class Controller_student {

    @FXML public TableColumn col_1,col_2,col_3,col_4,col_5;
    @FXML TableView student_tableview;
    @FXML public JFXComboBox combo_majors,combo_age_from,combo_age_to;
    @FXML public JFXTextField text_gpa_from,text_gpa_to;
    @FXML public JFXButton button_exit,button_validate;

    private ObservableList<Students> student_data;


    @FXML private void initialize() {

        col_1.setCellValueFactory(new PropertyValueFactory<Students,String>("id"));
        col_2.setCellValueFactory(new PropertyValueFactory<Students,String>("name"));
        col_3.setCellValueFactory(new PropertyValueFactory<Students,String>("age"));
        col_4.setCellValueFactory(new PropertyValueFactory<Students,String>("major"));
        col_5.setCellValueFactory(new PropertyValueFactory<Students,String>("gpa"));

        populateDataTable();
        populateMajorList();
        populateAgeList();

    }

    private Connection connect_db() {
        // SQLite connection string
        String url = "jdbc:sqlite:Assignment 2/Assignment2/students.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            System.out.println("Opened database successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void populateDataTable() {
        student_data = FXCollections.observableArrayList();

        Connection conn = this.connect_db();
        String sql_main = "SELECT * FROM students ORDER BY id";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql_main);
            ResultSet result_set = preparedStatement.executeQuery();
            while (result_set.next()) {
                student_data.add(new Students(result_set.getString(1),result_set.getString(2),result_set.getString(3),result_set.getString(4),result_set.getString(5)));
            }
            student_tableview.setItems(student_data);

            result_set.close();
            conn.close();
        } catch (SQLException tableQueryException) {
            System.err.println(tableQueryException.toString());
        }
    }

    private void populateMajorList() {
        Connection conn = this.connect_db();
        ObservableList<String> combo_majors_list = FXCollections.observableArrayList();
        combo_majors.getItems().clear();
        try {
            String sql = "SELECT Major FROM majors Order By Major";
            ResultSet result_set = conn.createStatement().executeQuery(sql);

            while (result_set.next()) {
                combo_majors_list.add(result_set.getString("Major"));
            }
            combo_majors.setItems(combo_majors_list);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    private void populateAgeList(){
        ObservableList<Integer> combo_age_list = FXCollections.observableArrayList();
        for(int counter=18;counter<100;counter++){
            combo_age_list.add(counter);
        }
        combo_age_from.setItems(combo_age_list);
        combo_age_to.setItems(combo_age_list);
    }

    @FXML public void updateDatatable(){
        boolean validated_age = validateAge();
        boolean validate_gpa = validateGPA();

        if (validate_gpa && validated_age) {
            student_data = null;
            student_data = FXCollections.observableArrayList();
            int age_from = Integer.parseInt(String.valueOf(combo_age_from.getValue()));
            int age_to = Integer.parseInt(String.valueOf(combo_age_to.getValue()));
            double gpa_from = Double.parseDouble(String.valueOf(text_gpa_from.getText()));
            double gpa_to = Double.parseDouble(String.valueOf(text_gpa_to.getText()));
            String major;

            if (combo_majors.getValue() == null){
                major = "";
            } else {
                major = " AND major LIKE " + String.valueOf(combo_majors.getValue());
            }

            Connection conn = this.connect_db();
            String sql_main = "SELECT * FROM students WHERE age >= " + age_from + " AND age <= " + age_to + " AND gpa >= " + gpa_from + " AND gpa <= " + gpa_to + major + " ORDER BY id";
            System.out.println(sql_main);
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql_main);
                ResultSet result_set = preparedStatement.executeQuery();
                while (result_set.next()) {
                    student_data.add(new Students(result_set.getString(1),result_set.getString(2),result_set.getString(3),result_set.getString(4),result_set.getString(5)));
                }
                student_tableview.setItems(student_data);

                result_set.close();
                conn.close();
            } catch (SQLException tableQueryException) {
                System.err.println(tableQueryException.toString());
            }
        }
    }

    private boolean validateAge(){
        if (combo_age_from.getValue() == null) {combo_age_from.setValue("18");}
        if (combo_age_to.getValue() == null) {combo_age_to.setValue("99");}
        int age_from = Integer.parseInt(String.valueOf(combo_age_from.getValue()));
        int age_to = Integer.parseInt(String.valueOf(combo_age_to.getValue()));
        if (age_to > age_from) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong Age Filter!");
            alert.setHeaderText(null);
            alert.setContentText("Make sure Age FROM is less than Age TO");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateGPA(){
        String string_gpa_from = text_gpa_from.getText();
        String string_gpa_to = text_gpa_to.getText();
        if (string_gpa_from.equals("")) {text_gpa_from.setText("0");}
        if (string_gpa_to.equals("")) {text_gpa_to.setText("4");}
        double gpa_from = Double.parseDouble(String.valueOf(text_gpa_from.getText()));
        double gpa_to = Double.parseDouble(String.valueOf(text_gpa_to.getText()));
        if (gpa_to > gpa_from) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong Age Filter!");
            alert.setHeaderText(null);
            alert.setContentText("Make sure GPA FROM is less than GPA TO");
            alert.showAndWait();
            return false;
        }
    }

    private void clearFilters(){

    }

    public class Students{
        private final StringProperty id = new SimpleStringProperty();
        private final StringProperty name = new SimpleStringProperty();
        private final StringProperty age = new SimpleStringProperty();
        private final StringProperty major = new SimpleStringProperty();
        private final StringProperty gpa = new SimpleStringProperty();

        public Students(String id_i, String name_n, String age_a, String major_m, String gpa_g) {
            id.set(id_i);
            name.set(name_n);
            age.set(age_a);
            major.set(major_m);
            gpa.set(gpa_g);
        }

        public String getStudentID() {return id.get();}
        public String getStudentName() {return name.get();}
        public String getAge() {return age.get();}
        public String getMajor() {return major.get();}
        public String getGPA() {return gpa.get();}

        public StringProperty idProperty() {
            return id;
        }
        public StringProperty nameProperty() {
            return name;
        }
        public StringProperty ageProperty() {
            return age;
        }
        public StringProperty majorProperty() {
            return major;
        }
        public StringProperty gpaProperty() {
            return gpa;
        }

        public void setStudentId(String id_i){
            id.set(id_i);
        }
        public void setStudentName(String name_n){ name.set(name_n);}
        public void setAge(String age_a){
            age.set(age_a);
        }
        public void setMajor(String major_m){
            major.set(major_m);
        }
        public void setGPA(String gpa_g){
            gpa.set(gpa_g);
        }

    }
}
