package Commands;

import Interaction.Message;
import Movie.Movie;

import java.time.LocalDateTime;
import java.util.Hashtable;

public interface Date extends Command {
    Message execute(Hashtable<String, Movie> collection, LocalDateTime initDate);
}
