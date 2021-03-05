public class User {
    private String doctoreID;
    private String name;
    private String password;

    public User() {
    }

    public User(String doctoreID, String name, String password) {
        this.doctoreID = doctoreID;
        this.name = name;
        this.password = password;
    }

    public String getDoctoreID() {
        return doctoreID;
    }

    public void setDoctoreID(String doctoreID) {
        this.doctoreID = doctoreID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
