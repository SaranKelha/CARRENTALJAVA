package carrental2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconnection {
    private  static final String url="jdbc:mysql://localhost:3306/carrental";
    private static final String userName="root";
    private static final String password="saran2003";
    public static Connection getConnection() throws SQLException {
        return  DriverManager.getConnection(url,userName,password);
    }
}
