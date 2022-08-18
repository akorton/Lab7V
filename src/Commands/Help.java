package Commands;

import Interaction.Message;
import Movie.Movie;
import java.util.Hashtable;


public class Help extends CommandObject {

    @Override
    public Message execute(Hashtable<String, Movie> collection) {
        return new Message(true,"Command help: \n" +
                "help - displaying commands description \n" +
                "info - displaying information about the collection \n" +
                "show - displaying all elements of the collection \n" +
                "insert {key} - adding a new element by the key \n" +
                "update_id {id} - updating an element by the id \n" +
                "remove_key {key} - removing an element by the key \n" +
                "clear - clearing the collection \n" +
                "execute_script {file name} - reading and executing a script from the file \n" +
                "exit - finishing the program \n" +
                "remove_greater {key} - removing all elements greater than the given \n" +
                "remove_greater_key {key} - removing all elements, the key greater than the given \n" +
                "remove_lower_key {key} - removing all elements, the key lower than the given \n" +
                "remove_any_by_golden_palm_count {Golden Palm count} - removing an element by the number of Golden Palms \n" +
                "sum_of_oscars_count - displaying the number of Oscars \n" +
                "count_greater_than_mpaa_rating {MPAA rating} - displaying the number of elements with MPAA raiting greater than given \n" + "\n");
    }
}
