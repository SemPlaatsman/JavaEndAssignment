package nl.inholland.javaendassignment.model;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean equals(User user) {
        return (this.username.equals(user.username) && this.password.equals(user.password));
    }
}
