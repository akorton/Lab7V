package Database.Services;

import Movie.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Hashtable;

public class CommonQueries {
    private static Connection connection;
    private static Statement statement;

    private CommonQueries(){}

    public static Hashtable<String, Movie> getCollection() throws Exception {
        String query = "SELECT * FROM movies";
        ResultSet result = statement.executeQuery(query);
        Hashtable<String, Movie> collection = new Hashtable<String, Movie>();
        while (result.next()){
            String key = result.getString("key");
            int id  = result.getInt("id");
            String name = result.getString("name");
            float x = result.getFloat("x");
            double y = result.getDouble("y");
            Coordinates coordinates = new Coordinates(x, y);
            LocalDateTime movieCreationDate = LocalDateTime.parse(result.getString("movieCreationDate"));
            int oscarCount = result.getInt("oscarCount");
            Integer goldenPalmCount = result.getInt("goldenPalmCount");
            MovieGenre movieGenre = MovieGenre.getByName(result.getString("movieGenre"));
            MpaaRating mpaaRating = MpaaRating.getByName(result.getString("mpaaRating"));
            String personName = result.getString("personName");
            LocalDate birthday = LocalDate.parse(result.getString("birthday"));
            Color eyeColor = Color.getByName(result.getString("eyeColor"));
            Color hairColor = Color.getByName(result.getString("hairColor"));
            Country nationality = Country.getByName(result.getString("nationality"));
            Person screenwriter = new Person(personName, birthday, eyeColor, hairColor, nationality);
            Movie movie = new Movie(id, name, coordinates, oscarCount,
                    goldenPalmCount, movieGenre, mpaaRating, screenwriter);
            movie.setMovieCreationDate(movieCreationDate);
            collection.put(key, movie);
        }
        return collection;
    }

    public static boolean deleteByKey(String key) throws SQLException{
        String query = "DELETE from movies WHERE key=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, key);
        preparedStatement.executeUpdate();
        return true;
    }

    public static boolean deleteAll(Hashtable<String, Movie> collection) throws SQLException{
        for (String key: collection.keySet()){
            deleteByKey(key);
        }
        return true;
    }

    public static boolean insertMovie(String key, Movie movie) throws SQLException{
        String query = "INSERT INTO movies VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, key);
        preparedStatement.setString(2, movie.getName());
        preparedStatement.setFloat(3, movie.getCoordinates().getX());
        preparedStatement.setDouble(4, movie.getCoordinates().getY());
        preparedStatement.setDate(5, Date.valueOf(movie.getMovieCreationDate().toLocalDate()));
        preparedStatement.setInt(6, movie.getOscarsCount());
        preparedStatement.setInt(7, movie.getGoldenPalmCount());
        preparedStatement.setString(8, movie.getGenre().toString());
        preparedStatement.setString(9, movie.getMpaaRating().toString());
        preparedStatement.setString(10, movie.getScreenwriter().getPerName());
        preparedStatement.setDate(11, Date.valueOf(movie.getScreenwriter().getBirthday()));
        preparedStatement.setString(12, movie.getScreenwriter().getEyeColor().toString());
        preparedStatement.setString(13, movie.getScreenwriter().getHairColor().toString());
        preparedStatement.setString(14, movie.getScreenwriter().getNationality().toString());
        preparedStatement.executeUpdate();
        return true;
    }

    public static void setConnection(Connection connection) throws SQLException {
        CommonQueries.connection = connection;
        CommonQueries.statement = connection.createStatement();
    }
}
