package Assignment2;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;

import java.sql.*;

public class Controller_student {

    @FXML public TableColumn col_1,col_2,col_3,col_4,col_5;
    @FXML TableView student_tableview;

    private ObservableList<Students> student_data;


    @FXML private void initialize() {

        populateDataTable();

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
