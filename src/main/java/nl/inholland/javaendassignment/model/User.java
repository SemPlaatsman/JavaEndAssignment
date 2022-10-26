package nl.inholland.javaendassignment.model;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;

    public String getUsername() { return username; }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
