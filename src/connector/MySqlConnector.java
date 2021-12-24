package connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnector implements DBConnector{
    private final String URL = new String("jdbc:mysql://localhost:3306/sakila");
    private final String USERNAME = new String("root");
    private final String PASSWORD = new String("1111");
    
    @Override
    public Connection makeConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
