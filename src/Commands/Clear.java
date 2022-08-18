package Commands;

import Database.Database;
import Interaction.Message;
import Movie.Movie;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Clear extends CommandObject{

    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        if (!isAuthorized()) return getUnauthorizedMessage();
        if (Database.deleteAll(collection, userId)) {
            List<String> keysToDelete = collection.entrySet().stream()
                    .filter((entry) -> entry.getValue().getUserId() == userId)
                    .map(Map.Entry::getKey).collect(Collectors.toList());
            keysToDelete.forEach(collection::remove);
            return new Message(true, "Collection cleared.");
        }
        return new Message(true, "Collection wasn't cleared!");
    }
}
