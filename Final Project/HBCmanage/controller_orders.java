package HBCmanage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class controller_orders {

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


}
