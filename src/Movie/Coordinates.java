package Movie;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private float x;
    private double y;


    public Coordinates(Float x, double y) throws Exception {
        setX(x);
        setY(y);
    }



    public void setX(Float x) throws Exception {
        if (x == null) {
            throw new Exception("Coordinate x can't be null.");
        }
        else if (x > 285) {
            throw new Exception("Coordinate x can't be more than 285.");
        } else
            this.x = x;
    }


    public void setY(double y) throws Exception {
        this.y = y;
    }


    public Float getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }


    @Override
    public String toString() {
        return x + " " + y;
    }
}
