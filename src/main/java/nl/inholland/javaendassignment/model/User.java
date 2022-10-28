package nl.inholland.javaendassignment.model;

import java.io.Serializable;

public class User implements Serializable {
    private final String username;
    private final String password;
    private final String fullName;

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    // custom equals method that compares just the username and password
    public boolean equals(User user) {
        return (this.username.equals(user.username) && this.password.equals(user.password));
    }
}
