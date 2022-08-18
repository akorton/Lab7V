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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;

public class Database {
    private static String databaseUrl = System.getenv("DATABASE_URL");
    private static Connection connection;
    private static final ReentrantLock movieLock = new ReentrantLock();
    private static final ReentrantLock userLock = new ReentrantLock();

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
        movieLock.lock();
        Exception exception = null;
        try {
            return CommonQueries.getCollection();
        } catch (Exception e){
            exception = e;
        } finally {
            movieLock.unlock();
            if (exception != null) throw exception;
        }
        return null;
    }

    public static boolean deleteByKey(String key, int id) throws SQLException{
        movieLock.lock();
        SQLException exception = null;
        try {
            return CommonQueries.deleteByKey(key, id);
        } catch (SQLException e){
            exception = e;
        } finally {
            movieLock.unlock();
            if (exception != null) throw exception;
        }
        return false;
    }

    public static boolean deleteAll(Hashtable<String, Movie> collection, int id) throws SQLException{
        movieLock.lock();
        SQLException exception = null;
        try {
            return CommonQueries.deleteAll(collection, id);
        } catch (SQLException e){
            exception = e;
        } finally {
            movieLock.unlock();
            if (exception != null) throw exception;
        }
        return false;
    }

    public static boolean insert(String key, Movie movie) throws SQLException{
        movieLock.lock();
        SQLException exception = null;
        try {
            return CommonQueries.insertMovie(key, movie);
        } catch (SQLException e){
            exception = e;
        } finally {
            movieLock.unlock();
            if (exception != null) throw exception;
        }
        return false;
    }

    public static boolean update(String key, Movie movie, int id) throws SQLException{
        movieLock.lock();
        SQLException exception = null;
        try {
            return CommonQueries.updateMovie(key, movie, id);
        } catch (SQLException e){
            exception = e;
        } finally {
            movieLock.unlock();
            if (exception != null) throw exception;
        }
        return false;
    }

    public static int getNextUserId() throws SQLException{
        userLock.lock();
        SQLException exception = null;
        try {
            return SequenceQueries.getNextUserId();
        } catch (SQLException e){
            exception = e;
        } finally {
            userLock.unlock();
            if (exception != null) throw exception;
        }
        return 0;
    }

    public static int getNextMovieId() throws SQLException{
        movieLock.lock();
        SQLException exception = null;
        try {
            return SequenceQueries.getNextMovieId();
        } catch (SQLException e){
            exception = e;
        } finally {
            movieLock.unlock();
            if (exception != null) throw exception;
        }
        return 0;
    }

    public static boolean userExistsByName(String name) throws SQLException{
        userLock.lock();
        SQLException exception = null;
        try {
            return CommonQueries.userExistsByName(name);
        } catch (SQLException e){
            exception = e;
        } finally {
            userLock.unlock();
            if (exception != null) throw exception;
        }
        return false;
    }

    public static boolean insertUser(User user) throws SQLException{
        userLock.lock();
        SQLException exception = null;
        try {
            return CommonQueries.insertUser(user);
        } catch (SQLException e){
            exception = e;
        } finally {
            userLock.unlock();
            if (exception != null) throw exception;
        }
        return false;
    }

    public static User getUserByLogin(String login) throws SQLException{
        userLock.lock();
        SQLException exception = null;
        try {
            return CommonQueries.getUserByLogin(login);
        } catch (SQLException e){
            exception = e;
        } finally {
            userLock.unlock();
            if (exception != null) throw exception;
        }
        return null;
    }
}
