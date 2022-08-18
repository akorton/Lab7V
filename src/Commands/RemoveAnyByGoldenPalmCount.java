package Commands;

import Database.Database;
import Interaction.Message;
import Interaction.UserInteraction;
import Movie.Movie;
import java.util.Hashtable;
import java.util.Set;


public class RemoveAnyByGoldenPalmCount extends CommandObject {

    private final String argument;


    public RemoveAnyByGoldenPalmCount(String[] commandArgs) {
        this.argument = commandArgs[0];
    }


    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        if (!isAuthorized()) return getUnauthorizedMessage();
        boolean count = false;

        Set<String> keys = collection.keySet();
        int goldenPalmCount;
        try {
            goldenPalmCount = Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            return new Message(true, "Count of Golden palms must be integer!");
        }
        for (String key : keys) {
            if (collection.get(key).getGoldenPalmCount() == goldenPalmCount) {
                if (Database.deleteByKey(key, userId)) collection.remove(key);
                count = true;
                break;
            }
        }
        if (count) {
            return new Message(true, "Element with such count of Golden palms was successfully removed.");
        } else {
            return new Message(true, "Movie with such count of Golden palms is not found.");
        }
    }

}
