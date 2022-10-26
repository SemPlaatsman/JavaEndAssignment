package nl.inholland.javaendassignment.data;

import nl.inholland.javaendassignment.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {
    private static final File DATABASE_FILE = new File("src/main/java/nl/inholland/javaendassignment/data/database.ser");
    static final long serialVersionUID = 6529685098267757990L;

    private List<User> users = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Member> members = new ArrayList<>();

    public List<User> getUsers() { return users; }
    public List<Item> getItems() { return items; }
    public List<Member> getMembers() { return members; }

    public Database() {
        deserialize();
    }

    public void loadTestData() {
        users.add(new User("sempl", "semjava"));
        users.add(new User("markie", "ahrnuld123"));

        items.add(new Item(242, true, "Java for Dummies, 13th edition", "Vries. E, de"));
        items.add(new Item(242, true, "Java for Dummies, 13th edition", "Vries. E, de"));
        items.add(new Item(242, true, "Java for Dummies, 13th edition", "Vries. E, de"));
        items.add(new Item(242, true, "Java for Dummies, 13th edition", "Vries. E, de"));

        members.add(new Member(32, "Piet", "de Vries", LocalDate.of(2000, Month.JUNE, 21)));
        members.add(new Member(32, "Piet", "de Vries", LocalDate.of(2000, Month.JUNE, 21)));
        members.add(new Member(32, "Piet", "de Vries", LocalDate.of(2000, Month.JUNE, 21)));
        members.add(new Member(32, "Piet", "de Vries", LocalDate.of(2000, Month.JUNE, 21)));
    }

    public void serialize() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(DATABASE_FILE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(this);

            fileOutputStream.close();
            objectOutputStream.close();
        }
        catch (IOException ioex) {
            throw new RuntimeException(ioex);
        }
    }

    public void deserialize() {
        try {
            if (!DATABASE_FILE.exists() || !DATABASE_FILE.canRead()) {
                loadTestData();
                return;
            }

            FileInputStream fileInputStream = new FileInputStream(DATABASE_FILE);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Database deserializedDatabase = (Database)objectInputStream.readObject();
            users = deserializedDatabase.getUsers();
            items = deserializedDatabase.getItems();
            members = deserializedDatabase.getMembers();

            fileInputStream.close();
            objectInputStream.close();
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
