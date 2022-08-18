package Client;

public class User {
    private int id;
    private String name;
    private String password;
    private String salt;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
