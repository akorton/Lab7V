package Interaction;

import Client.User;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

    private final String text;
    private final LocalDateTime creationDate;
    private final boolean result;
    private int userId;

    public Message(boolean result, String text) {
        this.text = text;
        this.creationDate = LocalDateTime.now();
        this.result = result;
    }

    public Message(boolean result, String text, int userId){
        this(result, text);
        this.userId = userId;
    }

    public String getText() {return text;}

    public LocalDateTime getCreationDate() {return creationDate;}

    public boolean isSuccessful() {return this.result;}

    public int getUserId() {
        return userId;
    }
}
