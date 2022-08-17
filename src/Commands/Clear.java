package Commands;

import Database.Database;
import Interaction.Message;
import Movie.Movie;
import java.util.Hashtable;


public class Clear implements Command{

    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        if (Database.deleteAll(collection)) {
            collection.clear();
            return new Message(true, "Collection cleared.");
        }
        return new Message(true, "Collection wasn't cleared!");
    }
}
