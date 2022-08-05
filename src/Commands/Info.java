package Commands;

import Interaction.Message;
import Movie.Movie;
import java.time.LocalDateTime;
import java.util.Hashtable;


public class Info implements Command {

    public Info() {}


    public Message execute(Hashtable<String, Movie> collection, LocalDateTime initDate) {
        return new Message(true, "Information about the collection: \n" +
                "Type: " + Movie.class.getName() + "\n" +
                "Initialization date: " + initDate + "\n" +
                "Amount of elements: " + collection.size() + "\n");
    }

    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        return new Message(false, "");
    }
}
