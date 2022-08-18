package Commands;

import Interaction.Message;
import Movie.Movie;
import java.util.Hashtable;


public class Exit extends CommandObject{

    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        return new Message(false,"Finishing...");
    }
}
