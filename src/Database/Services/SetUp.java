package Database.Services;

import java.sql.SQLException;
import java.sql.Statement;

public class SetUp {

    private SetUp(){}

    public static void setUp(Statement statement) throws SQLException {
        String createTableUsers = "CREATE TABLE IF not EXISTS users"+
                "(id INTEGER,"+
                "login TEXT," +
                "password bytea," +
                "salt TEXT," +
                "PRIMARY KEY(id)"+
                ")";
        statement.executeUpdate(createTableUsers);
        String createMovieTable = "CREATE TABLE IF not EXISTS movies" +
                "(id INTEGER PRIMARY KEY,"+
                "name TEXT,"+
                "x FLOAT,"+
                "y FLOAT,"+
                "movieCreationDate DATE,"+
                "oscarsCount INTEGER,"+
                "goldenPalmCount INTEGER,"+
                "genre TEXT,"+
                "mpaaRating TEXT,"+
                "personName TEXT,"+
                "birthday DATE,"+
                "eyeColor TEXT,"+
                "hairColor TEXT,"+
                "nationality TEXT)";
        statement.executeUpdate(createMovieTable);
        String createUserIdSequence = "CREATE SEQUENCE IF not EXISTS userId START WITH 1;";
        statement.executeUpdate(createUserIdSequence);
        String createMovieIdSequence = "CREATE SEQUENCE IF not EXISTS movieId START WITH 1";
        statement.executeUpdate(createMovieIdSequence);
    }
}
