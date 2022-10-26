package nl.inholland.javaendassignment.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Member implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public Member(int id, String firstName, String lastName, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
