package Commands;

import Interaction.Message;
import Movie.Movie;
import java.util.Hashtable;


public class Clear implements Command{

    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        collection.clear();
        return new Message(true, "Collection cleared.");
    }
}
