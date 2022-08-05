package Movie;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Collection;
import java.util.Set;


public class HashtableInfo implements Serializable {
    private static Hashtable<String, Movie> collection;
    private final LocalDateTime creationDate;

    public HashtableInfo(Hashtable<String, Movie> collection, LocalDateTime creationDate) throws Exception {
        if (collection != null && creationDate != null) {
            this.collection = collection;
            this.creationDate = creationDate;
        } else {
            throw new Exception("Arguments can't be null.");
        }
    }

    public Collection<Movie> getValues() {
        return collection.values();
    }
    public Set<String> getKeys() {
        return collection.keySet();
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public  static Hashtable<String, Movie> getCollection() {
        return collection;
    }
}
