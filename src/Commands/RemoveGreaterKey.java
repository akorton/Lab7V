package Commands;

import Database.Database;
import Interaction.Message;
import Interaction.UserInteraction;
import Movie.Movie;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;


public class RemoveGreaterKey implements Command, IdUsing{
    private final String argument;


    public RemoveGreaterKey(String[] commandArgs) {
        this.argument = commandArgs[0];
    }

    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        if (!collection.isEmpty()) {
            if (collection.containsKey(argument)) {
                Set<String> keys = collection.keySet();
                ArrayList<String> removable = new ArrayList<>();
                boolean flag = false;
                for (String key : keys) {
                    if (flag) {
                        removable.add(key);
                    }
                    if (key.equals(argument)) {
                        flag = true;
                    }
                }
                for (String key : removable) {
                    if (Database.deleteByKey(key)) collection.remove(key);
                }
                return new Message(true, "All necessary elements removed successfully.");
            } else {
                return new Message(true, "No key found.");
            }
        } else {
            return new Message(true, "Collection is empty.");
        }
    }
}
