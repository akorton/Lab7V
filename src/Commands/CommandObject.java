package Commands;

import Client.User;
import Interaction.Message;

public abstract class CommandObject implements Command{
    protected User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public boolean isAuthorized(){
        return user != null;
    }

    public Message getUnauthorizedMessage(){
        return new Message(true, "You are not authorized to execute this command.");
    }

    public Message getAuthorizedMessage(){
        return new Message(true, "You are already authorized.");
    }
}
