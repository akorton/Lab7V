package Database.Services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SequenceQueries {
    private static Connection connection;

    public static void setConnection(Connection connection){
        SequenceQueries.connection = connection;
    }

    public static int getNextMovieId() throws SQLException {
        Statement statement = connection.createStatement();
        String nextId = "SELECT nextval('movieId');";
        ResultSet res = statement.executeQuery(nextId);
        res.next();
        int ans = res.getInt(1);
        res.close();
        return ans;
    }

    public static int getNextUserId() throws SQLException {
        Statement statement = connection.createStatement();
        String nextId = "SELECT nextval('userId');";
        ResultSet res = statement.executeQuery(nextId);
        res.next();
        int ans = res.getInt(1);
        res.close();
        return ans;
    }

}
