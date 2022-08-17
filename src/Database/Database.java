package Database;

import Client.Client;
import Database.Services.SetUp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Database {
    private static String databaseUrl = System.getenv("DATABASE_URL");
    private static final Logger log = Logger.getLogger(Client.class.getName());
    private static Connection connection;

    private Database(){}

    public static void connectToDatabase() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        Properties properties = new Properties();
        properties.setProperty("user", System.getenv("USERNAME"));
        properties.setProperty("password", System.getenv("PASSWORD"));
        connection = DriverManager.getConnection(databaseUrl, properties);
    }

    public static void setUpDatabase() throws SQLException {
        SetUp.setUp(connection.createStatement());
    }
}
