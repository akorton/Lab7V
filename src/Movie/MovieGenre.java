package Movie;
import java.util.Objects;

public enum MovieGenre {
    WESTERN("western"),
    COMEDY("comedy"),
    MUSICAL("musical");

    private final String name;


    MovieGenre(String name) {
        this.name = name;
    }

    public static MovieGenre getByName(String name) {
        for (MovieGenre movieGenre : MovieGenre.values()) {
            if (Objects.equals(movieGenre.name, name)) {
                return movieGenre;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
