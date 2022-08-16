package Database;

import Client.Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Database {
    private static String localUrl = "";
    private static String heliosUrl = "";
    private static final Logger log = Logger.getLogger(Client.class.getName());
    private static Connection connection;

    private Database(){}

    public static void connectToDatabase() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        Properties properties = new Properties();
        properties.setProperty("user", System.getenv("USERNAME"));
        properties.setProperty("password", System.getenv("PASSWORD"));
        connection = DriverManager.getConnection(localUrl, properties);
    }
}
