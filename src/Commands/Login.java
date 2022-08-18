package Commands;

import Client.User;
import Database.Database;
import Interaction.Message;
import Interaction.Preprocessing;
import Interaction.UserInteraction;
import Movie.Movie;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Objects;

public class Login extends CommandObject implements Preprocessing {
    private final UserInteraction interaction;
    private String name;
    private String password;

    public Login(UserInteraction interaction){
        this.interaction = interaction;
    }

    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        if (isAuthorized()) return getAuthorizedMessage();
        User user = Database.getUserByLogin(name);
        if (Objects.equals(user, null)) return new Message(true, "No user with such username.");

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            return new Message(true, "Can not encode password.");
        }
        String salt = user.getSalt();
        byte[] encodedPassword = md.digest((salt + password).getBytes(StandardCharsets.UTF_8));
        if (Arrays.equals(encodedPassword, user.getPassword())) {
            return new Message(true, "Successfully logged in!", user.getId());
        }
        return new Message(true, "Wrong password.");
    }

    @Override
    public void preprocess(UserInteraction interaction) {
        while (true) {
            interaction.print(false, "Enter username: ");
            String potentialName = interaction.getData().trim();
            if (potentialName.equals("")) {
                interaction.print("Username can't be empty string.");
                continue;
            }
            this.name = potentialName;
            break;
        }

        while (true) {
            interaction.print(false, "Enter password: ");
            String potentialPassword = interaction.getData().trim();
            if (potentialPassword.equals("")) {
                interaction.print("Password can't be empty string.");
                continue;
            }
            this.password = potentialPassword;
            break;
        }
    }
}
