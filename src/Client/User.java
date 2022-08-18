package Client;

public class User {
    private int id;
    private String name;
    private byte[] password;
    private String salt;

    public User(String name, byte[] password, String salt){
        this.name = name;
        this.password = password;
        this.salt = salt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public byte[] getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
