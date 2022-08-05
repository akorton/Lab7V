package Commands;

import Interaction.Message;
import Interaction.UserInteraction;
import Movie.*;
import java.util.Hashtable;


public class CountGreaterThanMpaaRating implements Command{
    private final String argument;

    public CountGreaterThanMpaaRating(String[] commandArgs) {
        this.argument = commandArgs[0];
    }

    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        int count = 0;
        try {
            for (Movie movie : collection.values()) {
                if (movie.getMpaaRating().ordinal() > MpaaRating.getByName(argument).ordinal()) {
                    count++;
                }
            }
            return new Message(true, "Count of movies with greater MPAA rating is: " + count + ".");
        } catch (NullPointerException e) {
            return new Message(true, "No such MPAA rating.");
        }
    }
}
