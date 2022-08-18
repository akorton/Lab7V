package Commands;

import Database.Database;
import Interaction.Message;
import Movie.Movie;
import java.util.Hashtable;


public class Clear extends CommandObject{

    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        if (!isAuthorized()) return getUnauthorizedMessage();
        if (Database.deleteAll(collection)) {
            collection.clear();
            return new Message(true, "Collection cleared.");
        }
        return new Message(true, "Collection wasn't cleared!");
    }
}
