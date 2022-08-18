package Commands;

import Interaction.Message;
import Interaction.UserInteraction;
import Movie.Movie;
import java.util.Hashtable;
import java.util.Set;


public class UpdateId extends CommandObject implements IdUsing {

    private final UserInteraction interaction;
    private final Hashtable<String, Movie> collection;
    private final String argument;


    public UpdateId(UserInteraction interaction, String[] commandArgs, Hashtable<String, Movie> collection) {
        this.interaction = interaction;
        this.argument = commandArgs[0];
        this.collection = collection;
    }


    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        search(interaction, collection, argument);
        return new Message(true,"Element with id " + argument + " was successfully removed.");
    }


    @Override
    public void remove(UserInteraction interaction, Hashtable<String, Movie> collection, String argument) throws Exception {
        Set<String> keys = collection.keySet();
        for (String key : keys) {
            if (collection.get(key).getId() == Integer.parseInt(argument)) {
                String[] commandArgs = new String[1];
                commandArgs[0] = key;
                Insert insert = new Insert(interaction, false, Integer.parseInt(argument), commandArgs);
                insert.execute(collection);
                break;
            }
        }
    }
}
