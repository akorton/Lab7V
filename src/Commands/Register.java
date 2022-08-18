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
import java.util.Hashtable;
import java.util.Random;

public class Register extends CommandObject implements Preprocessing {
    private final UserInteraction interaction;
    private String name;
    private String password;

    public Register(UserInteraction interaction){
        this.interaction = interaction;
    }

    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        if (isAuthorized()) return getAuthorizedMessage();
        if (Database.userExistsByName(name)) return new Message(true, "User with such username already exists.");
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            return new Message(true, "Can not encode password.");
        }
        String salt = getSalt();
        byte[] encodedPassword = md.digest((salt + password).getBytes(StandardCharsets.UTF_8));
        User user = new User(name, encodedPassword, salt);
        user.setId(Database.getNextUserId());
        if (!Database.insertUser(user)) return new Message(true, "User was not registered.");
        return new Message(true, "Successfully registered!");
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

    private String getSalt(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
