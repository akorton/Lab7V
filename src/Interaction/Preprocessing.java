package Interaction;

import java.io.Serializable;

public interface Preprocessing extends Serializable {
    void preprocess(UserInteraction interaction);
}
