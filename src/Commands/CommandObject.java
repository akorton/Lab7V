package Commands;

import Client.User;
import Interaction.Message;

public abstract class CommandObject implements Command{
    protected boolean registered;

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public boolean isAuthorized(){
        return registered;
    }

    public Message getUnauthorizedMessage(){
        return new Message(true, "You are not authorized to execute this command.");
    }

    public Message getAuthorizedMessage(){
        return new Message(true, "You are already authorized.");
    }
}
