package Movie;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {
    private String perName;
    private java.time.LocalDate birthday;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;

    public Person(String perName, java.time.LocalDate birthday, Color eyeColor, Color hairColor, Country nationality) throws Exception {
        setPerName(perName);
        setBirthday(birthday);
        setEyeColor(eyeColor);
        setHairColor(hairColor);
        setNationality(nationality);
    }

    public Person() {}

    public void setPerName(String perName) throws Exception {
        if (perName == null) {
            throw new Exception("Name can't be null!");
        } else if (perName.equals("")) {
            throw new Exception("Name can't be empty line!");
        }
        this.perName = perName;
    }

    public void setBirthday(LocalDate birthday) {
        if (birthday != null) {
            this.birthday = birthday;
        }
    }

    public void setEyeColor(Color eyeColor) throws Exception {
        if (eyeColor != null) {
            this.eyeColor = eyeColor;
        } else {
            throw new Exception("Genre doesn't exist.");
        }
    }

    public void setHairColor(Color hairColor) throws Exception {
        if (hairColor != null) {
            this.hairColor = hairColor;
        } else {
            throw new Exception("Genre doesn't exist.");
        }
    }

    public void setNationality(Country nationality) throws Exception {
        if (nationality != null) {
            this.nationality = nationality;
        } else {
            throw new Exception("Genre doesn't exist.");
        }
    }


    public String getPerName() {
        return perName;
    }
    public LocalDate getBirthday() {
        return birthday;
    }
    public Color getEyeColor() {
        return eyeColor;
    }
    public Color getHairColor() {
        return hairColor;
    }
    public Country getNationality() {
        return nationality;
    }


    @Override
    public String toString() {
        return this.perName + ", " + this.birthday + ", " + this.eyeColor.toString() + ", " + this.hairColor.toString() + ", " + this.nationality.toString();
    }
}
