package Movie;

import Xml.XmlConvertable;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Movie implements XmlConvertable, Serializable {
    private int id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime movieCreationDate;
    private int oscarsCount;
    private Integer goldenPalmCount;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person screenwriter;
    private int userId;

    public Movie(int id, String name, Coordinates coordinates, int oscarsCount, Integer goldenPalmCount, MovieGenre genre, MpaaRating mpaaRating, Person screenwriter) throws Exception{
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        setMovieCreationDate();
        setOscarsCount(oscarsCount);
        setGoldenPalmCount(goldenPalmCount);
        setGenre(genre);
        setMpaaRating(mpaaRating);
        setScreenwriter(screenwriter);
    }

    public Movie() {}



    public void setId() {
        int posId = (int) (Math.random() * 10000 + 1);
        this.id = posId;

    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            setId();
        }
    }

    public void setName(String name) throws Exception{
        if (name == null) {
            throw new Exception("Name can't be null!");
        } else if (name.equals("")) {
            throw new Exception("Name can't be empty line!");
        }
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;

    }

    public void setCoordinates(Float x, double y) throws Exception {
        this.coordinates = new Coordinates(x, y);
    }

    public void setMovieCreationDate() {
        this.movieCreationDate = LocalDateTime.now();
    }

    public void setMovieCreationDate(LocalDateTime movieCreationDate) {
        this.movieCreationDate = movieCreationDate;
    }

    public void setOscarsCount(int oscarsCount) throws Exception{
        if (oscarsCount <= 0) {
            throw new Exception("Count of Oscars must be more than 0!");
        }
        this.oscarsCount = oscarsCount;
    }

    public void setGoldenPalmCount(Integer goldenPalmCount) throws Exception {
        if (goldenPalmCount <= 0) {
            throw new Exception("Count of Golden palms must be more than 0!");
        }
        this.goldenPalmCount = goldenPalmCount;
    }

    public void setGenre(MovieGenre genre) throws Exception{
        if (genre != null) {
            this.genre = genre;
        } else {
            throw new Exception("Genre doesn't exist.");
        }
    }

    public void setMpaaRating(MpaaRating mpaaRating) throws Exception {
        if (mpaaRating != null) {
            this.mpaaRating = mpaaRating;
        } else {
            throw new Exception("Genre doesn't exist.");
        }
    }

    public void setScreenwriter(Person screenwriter) {
        this.screenwriter = screenwriter;
    }


    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public LocalDateTime getMovieCreationDate() {
        return movieCreationDate;
    }
    public int getOscarsCount() {
        return oscarsCount;
    }
    public Integer getGoldenPalmCount() {
        return goldenPalmCount;
    }
    public MovieGenre getGenre() {
        return genre;
    }
    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }
    public Person getScreenwriter() {
        return screenwriter;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + coordinates.toString() +
                ", " + movieCreationDate + ", " +
                oscarsCount + ", " + goldenPalmCount + ", " + genre.toString() + ", "
                + mpaaRating.toString() + ", " + screenwriter.toString() + ", " + userId;
    }


    @Override
    public String convertToXmlString() {
        String element = "";
        element += String.format("<id>%d</id>", getId());
        element += String.format("<name>%s</name>", getName().replace(" ", "_"));
        element += String.format("<coordinateX>%s</coordinateX>", getCoordinates().getX());
        element += String.format("<coordinateY>%s</coordinateY>", getCoordinates().getY());
        element += String.format("<movieCreationDate>%s</movieCreationDate>", getMovieCreationDate());
        element += String.format("<oscarsCount>%d</oscarsCount>", getOscarsCount());
        element += String.format("<goldenPalmCount>%d</goldenPalmCount>", getGoldenPalmCount());
        element += String.format("<genre>%s</genre>", getGenre());
        element += String.format("<mpaaRating>%s</mpaaRating>", getMpaaRating());
        element += "<screenwriter>";
        element += String.format("<perName>%s</perName>", screenwriter.getPerName().replace(" ", "_"));
        element += String.format("<birthday>%s</birthday>", screenwriter.getBirthday());
        element += String.format("<eyeColor>%s</eyeColor>", screenwriter.getEyeColor());
        element += String.format("<hairColor>%s</hairColor>", screenwriter.getHairColor());
        element += String.format("<nationality>%s</nationality>", screenwriter.getNationality().toString().replace(" ", "_"));
        element += "</screenwriter>";
        element = String.format("<movie>%s</movie>", element);
        return element;
    }
}
