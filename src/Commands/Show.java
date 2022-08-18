package Commands;

import Interaction.Message;
import Interaction.UserInteraction;
import Movie.Movie;
import java.util.Hashtable;


public class Show extends CommandObject {

    @Override
    public Message execute(Hashtable<String, Movie> collection) {
        if (!isAuthorized()) return getUnauthorizedMessage();
        StringBuilder stringBuilder;
        if (collection.size() == 0) {
            return new Message(true, "No elements found.");
        } else {
            stringBuilder = new StringBuilder("Total: " + collection.size() +
                    "\nCollection's elements: \n");
            for (String key : collection.keySet()) {
                Movie movie = collection.get(key);
                stringBuilder.append(key + " = " + movie.toString() + "\n");
                if (stringBuilder.capacity() > 20000) {
                    stringBuilder.append("And maybe some more...\n");
                    break;
                }
            }
            return new Message(true, stringBuilder + "\n");
        }
    }
}
