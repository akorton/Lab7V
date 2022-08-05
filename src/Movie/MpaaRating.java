package Movie;

import java.io.Serializable;
import java.util.Objects;

public enum MpaaRating implements Serializable {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    NC_17("NC-17");

    private final String name;


    MpaaRating(String name) {
        this.name = name;
    }

    public static MpaaRating getByName(String name) {
        for (MpaaRating mpaaRating : MpaaRating.values()) {
            if (Objects.equals(mpaaRating.name, name)) {
                return mpaaRating;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return this.name;
    }


}
