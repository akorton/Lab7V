package Interaction;

import java.io.Serializable;

public interface UserInteraction extends Serializable {
    void print(boolean newLine, String message);
    default void print(String message){
        print(true, message);
    }
    String getData();
}