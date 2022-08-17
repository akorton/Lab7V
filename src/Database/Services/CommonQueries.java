package Database.Services;

import Movie.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Hashtable;

public class CommonQueries {

    private CommonQueries(){}

    public static Hashtable<String, Movie> getCollection(Statement statement) throws Exception {
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
}
