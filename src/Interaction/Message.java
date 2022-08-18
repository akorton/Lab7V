package Interaction;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

    private final String text;
    private final LocalDateTime creationDate;
    private final boolean result;
    private boolean registered = false;

    public Message(boolean result, String text) {
        this.text = text;
        this.creationDate = LocalDateTime.now();
        this.result = result;
    }

    public Message(boolean result, String text, boolean registered){
        this(result, text);
        this.registered = registered;
    }

    public String getText() {return text;}

    public LocalDateTime getCreationDate() {return creationDate;}

    public boolean isSuccessful() {return this.result;}

    public boolean isRegistered(){
        return registered;
    }
}
