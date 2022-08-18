package Commands;

import Client.User;

public abstract class CommandObject implements Command{
    protected User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
