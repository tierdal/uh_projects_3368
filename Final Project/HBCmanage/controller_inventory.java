package HBCmanage;

import javafx.fxml.Initializable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.net.URL;
import java.util.ResourceBundle;


public class controller_inventory implements Initializable {

    //Connect to remote MySQL DB
    private Connection connect() {
        String url = "jdbc:mysql://db4free.net/uh2336";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            System.out.println("You should have remembered this processes better :(");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Set cell values so table can properly populate

    }

}
