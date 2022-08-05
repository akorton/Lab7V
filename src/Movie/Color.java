package Movie;
import java.io.Serializable;
import java.util.Objects;

public enum Color implements Serializable {
    RED("red"),
    BLACK("black"),
    ORANGE("orange"),
    WHITE("white"),
    BROWN("brown");

    private final String name;


    Color(String name) {
        this.name = name;
    }

    public static Color getByName(String name) {
        for (Color color : Color.values()) {
            if (Objects.equals(color.name, name)) {
                return color;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
