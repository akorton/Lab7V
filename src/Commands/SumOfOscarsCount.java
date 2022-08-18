package Commands;

import Interaction.Message;
import Interaction.UserInteraction;
import Movie.Movie;
import java.util.Hashtable;


public class SumOfOscarsCount extends CommandObject {

    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        if (!collection.isEmpty()) {
            int count = 0;
            for (Movie movie : collection.values()) {
                count += movie.getOscarsCount();
            }
            return new Message(true, "Total Oscar's count: " + count + ".");
        } else {
            return new Message(true, "Collection is empty.");
        }
    }

}