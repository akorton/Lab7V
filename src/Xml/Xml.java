package Xml;

import Movie.HashtableInfo;
import Movie.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public abstract class Xml {

    public static String toXml(HashtableInfo hashtableInfo) {
        StringBuilder fileString = new StringBuilder();
        for (String key : hashtableInfo.getKeys()) {
            XmlConvertable xmlConvertable = hashtableInfo.getCollection().get(key);
            fileString.append(String.format("<key>%s</key>", key));
            fileString.append(xmlConvertable.convertToXmlString());
        }

        fileString = new StringBuilder(String.format("<elements>%s</elements>", fileString));
        fileString = new StringBuilder(String.format("<creation-date>%s</creation-date>%s", hashtableInfo.getCreationDate(), fileString));
        fileString = new StringBuilder(String.format("<collection>%s</collection>", fileString));

        return fileString.toString();
    }


    private static boolean tagExists(String xmlCode, String tag) {
        int startIndex = xmlCode.indexOf(String.format("<%s>", tag));
        int endIndex = xmlCode.indexOf(String.format("</%s>", tag));
        return !(startIndex == -1 || endIndex == -1);
    }


    private static String parseTagFirst(String xmlCode, String tag) throws Exception {
        int startIndex = xmlCode.indexOf(String.format("<%s>", tag));
        int endIndex = xmlCode.indexOf(String.format("</%s>", tag));
        if (startIndex == -1 || endIndex == -1) {
            throw new Exception("Invalid file format or tag doesn't exist.");
        }
        return xmlCode.substring(startIndex + String.format("<%s>", tag).length(), endIndex);
    }


    private static String deleteTagFirst(String xmlCode, String tag) throws Exception {
        int startIndex = xmlCode.indexOf(String.format("<%s>", tag));
        int endIndex = xmlCode.indexOf(String.format("</%s>", tag));
        if (startIndex == -1 || endIndex == -1) {
            throw new Exception("Invalid file format or tag doesn't exist.");
        }
        return xmlCode.substring(endIndex + String.format("</%s>", tag).length());
    }


    public static HashtableInfo fromXml(String xmlCode) {
        xmlCode = xmlCode.replaceAll("\\s+", "");
        HashtableInfo hashtableInfo;
        try {
            LocalDateTime creationDate = LocalDateTime.parse(parseTagFirst(xmlCode, "creation-date"));
            Hashtable<String, Movie> collection = new Hashtable<>();
            String arrayCode = parseTagFirst(xmlCode, "elements");

            while (tagExists(arrayCode, "key")) {
                String key = parseTagFirst(arrayCode, "key");
                String movieCode = parseTagFirst(arrayCode, "movie");



                int id = Integer.parseInt(parseTagFirst(movieCode, "id"));
                String name = parseTagFirst(movieCode, "name");
                Float x = Float.parseFloat(parseTagFirst(movieCode, "coordinateX"));
                double y = Double.parseDouble(parseTagFirst(movieCode, "coordinateY"));
                LocalDateTime movieCreationDate = LocalDateTime.parse(parseTagFirst(movieCode, "movieCreationDate"));
                int oscarsCount = Integer.parseInt(parseTagFirst(movieCode, "oscarsCount"));
                Integer goldenPalmCount = Integer.parseInt(parseTagFirst(movieCode, "goldenPalmCount"));
                MovieGenre genre = MovieGenre.getByName(parseTagFirst(movieCode, "genre"));
                MpaaRating mpaaRating = MpaaRating.getByName(parseTagFirst(movieCode, "mpaaRating"));
                String perName = parseTagFirst(movieCode, "perName");
                java.time.LocalDate birthday = LocalDate.parse(parseTagFirst(movieCode, "birthday"));
                Color eyeColor = Color.getByName(parseTagFirst(movieCode, "eyeColor"));
                Color hairColor = Color.getByName(parseTagFirst(movieCode, "hairColor"));
                String nationality = parseTagFirst(movieCode, "nationality");
                Country country = Country.getByName(nationality.replace("_", " "));
                Person screenwriter = new Person(perName.replace("_", " "), birthday, eyeColor, hairColor, country);


                Movie movie = new Movie();

                movie.setId(id);
                movie.setName(name.replace("_", " "));
                movie.setCoordinates(x, y);
                movie.setMovieCreationDate(movieCreationDate);
                movie.setOscarsCount(oscarsCount);
                movie.setGoldenPalmCount(goldenPalmCount);
                movie.setGenre(genre);
                movie.setMpaaRating(mpaaRating);
                movie.setScreenwriter(screenwriter);

                collection.put(key, movie);
                arrayCode = deleteTagFirst(arrayCode, "key");
                arrayCode = deleteTagFirst(arrayCode, "movie");
            }
            hashtableInfo = new HashtableInfo(collection, creationDate);
        } catch (Exception e) {
            return null;
        }
        return hashtableInfo;
    }

    public static HashtableInfo fromXml(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(file));
        StringBuilder xml = new StringBuilder();

        while (scanner.hasNextLine()) {
            xml.append(scanner.nextLine());
        }
        return Xml.fromXml(xml.toString());
    }
}
