package Database;

import Client.User;
import Database.Services.CommonQueries;
import Database.Services.SequenceQueries;
import Database.Services.SetUp;
import Movie.Movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

public class Database {
    private static String databaseUrl = System.getenv("DATABASE_URL");
    private static Connection connection;

    private Database(){}

    public static void connectToDatabase() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        Properties properties = new Properties();
        properties.setProperty("user", System.getenv("USERNAME"));
        properties.setProperty("password", System.getenv("PASSWORD"));
        connection = DriverManager.getConnection(databaseUrl, properties);
        CommonQueries.setConnection(connection);
        SequenceQueries.setConnection(connection);
        setUpDatabase();
    }

    public static void setUpDatabase() throws SQLException {
        SetUp.setUp(connection.createStatement());
    }

    public static Hashtable<String, Movie> getCollection() throws Exception{
        return CommonQueries.getCollection();
    }

    public static boolean deleteByKey(String key) throws SQLException{
        return CommonQueries.deleteByKey(key);
    }

    public static boolean deleteAll(Hashtable<String, Movie> collection) throws SQLException{
        return CommonQueries.deleteAll(collection);
    }

    public static boolean insert(String key, Movie movie) throws SQLException{
        return CommonQueries.insertMovie(key, movie);
    }

    public static boolean update(String key, Movie movie) throws SQLException{
        return CommonQueries.updateMovie(key, movie);
    }

    public static int getNextUserId() throws SQLException{
        return SequenceQueries.getNextUserId();
    }

    public static int getNextMovieId() throws SQLException{
        return SequenceQueries.getNextMovieId();
    }

    public static boolean userExistsByName(String name) throws SQLException{
        return CommonQueries.userExistsByName(name);
    }

    public static boolean insertUser(User user) throws SQLException{
        return CommonQueries.insertUser(user);
    }

    public static User getUserByLogin(String login) throws SQLException{
        return CommonQueries.getUserByLogin(login);
    }
}
