package Commands;

import Interaction.Message;
import Interaction.UserInteraction;
import Movie.Movie;
import java.util.Hashtable;
import java.util.Set;


public class RemoveAnyByGoldenPalmCount implements Command {

    private final String argument;


    public RemoveAnyByGoldenPalmCount(String[] commandArgs) {
        this.argument = commandArgs[0];
    }


    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        boolean count = false;

        Set<String> keys = collection.keySet();
        for (String key : keys) {
            try {
                if (collection.get(key).getGoldenPalmCount() == Integer.parseInt(argument)) {
                    collection.remove(key);
                    count = true;
                    break;
                }
            } catch (NumberFormatException e) {
                return new Message(true, "Count of Golden palms must be integer!");
            }
        }
        if (count) {
            return new Message(true, "Element with such count of Golden palms was successfully removed.");
        } else {
            return new Message(true, "Movie with such count of Golden palms is not found.");
        }
    }


}
