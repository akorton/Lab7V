package Commands;

import Interaction.Message;
import Movie.Movie;

import java.io.Serializable;
import java.util.Hashtable;

public interface Command extends Serializable {
    Message execute(Hashtable<String, Movie> collection) throws Exception;
}
