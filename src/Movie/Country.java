package Movie;
import java.io.Serializable;
import java.util.Objects;

public enum Country implements Serializable {
    UNITED_KINGDOM("United Kingdom"),
    GERMANY("Germany"),
    VATICAN("Vatican"),
    ITALY("Italy"),
    NORTH_KOREA("North Korea");

    private final String name;


    Country(String name) {
        this.name = name;
    }

    public static Country getByName(String name) {
        for (Country country : Country.values()) {
            if (Objects.equals(country.name, name)) {
                return country;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
