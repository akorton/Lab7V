package Commands;

import Interaction.UserInteraction;
import Movie.Movie;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Set;


public interface IdUsing extends Serializable {

    default void search(UserInteraction interaction, Hashtable<String, Movie> collection, String argument) throws Exception {
        int count = 0;

        for (Movie movie : collection.values()) {
            if (movie.getId() == Integer.parseInt(argument)) {
                count++;
            }
        }
        switch(count) {
            case 0:
                interaction.print(true,"Id: " + argument + " is not found.");
                break;
            case 1:
                remove(interaction, collection, argument);
                break;
            default:
                interaction.print(true,"This id is already used.");
                break;
        }
    }

    default void remove(UserInteraction interaction, Hashtable<String, Movie> collection, String argument) throws Exception {
        Hashtable<String, Movie> helper = new Hashtable<>();
        Movie movie;
        Set<String> keys = collection.keySet();
        for (String key : keys) {
            movie = collection.remove(key);
            if (movie.getId() != Integer.parseInt(argument)) {
                helper.put(key, movie);
            } else {
                break;
            }
        }
        for (Movie movie1 : helper.values()) {
            Set<String> keys1 = helper.keySet();
            for (String key : keys1) {
                movie1 = helper.remove(key);
                collection.put(key, movie1);
            }
        }
        interaction.print(true,"Element with id " + argument + " was successfully removed.");

    }
}
