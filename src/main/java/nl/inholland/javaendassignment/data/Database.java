package nl.inholland.javaendassignment.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.inholland.javaendassignment.model.Item;
import nl.inholland.javaendassignment.model.Member;
import nl.inholland.javaendassignment.model.User;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class Database implements Serializable {
    // serial version uid to serialize the database
    static final long serialVersionUID = 6529685098267757690L;
    // file location
    private static final File DATABASE_FILE = new File("src/main/java/nl/inholland/javaendassignment/data/database.ser");
    private List<User> users = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Member> members = new ArrayList<>();

    public Database() {
        deserialize();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Member> getMembers() {
        return members;
    }

    public Item getItemByCode(int itemCode) {
        for (Item item : items) {
            if (item.getItemCode() == itemCode)
                return item;
        }
        return null;
    }

    public Member getMemberById(int memberId) {
        for (Member member : members) {
            if (member.getId() == memberId)
                return member;
        }
        return null;
    }

    public void addItem(Item item) {
        // code in try block will throw a NoSuchElementException if there is are no items in the list so there is no item to be the item with max value
        try {
            // based on code from the following stackoverflow post: https://stackoverflow.com/questions/19338686/getting-max-value-from-an-arraylist-of-objects
            item.setItemCode(items.stream().max(Comparator.comparing(Item::getItemCode)).get().getItemCode() + 1);
        } catch (NoSuchElementException nsee) {
            item.setItemCode(1);
        }
        items.add(item);
    }

    public void addMember(Member member) {
        // code in try block will throw a NoSuchElementException if there is are no members in the list so there is no members to be the member with max value
        try {
            // based on code from the following stackoverflow post: https://stackoverflow.com/questions/19338686/getting-max-value-from-an-arraylist-of-objects
            member.setId(members.stream().max(Comparator.comparing(Member::getId)).get().getId() + 1);
        } catch (NoSuchElementException nsee) {
            member.setId(1);
        }
        members.add(member);
    }

    public void editItem(Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemCode() == item.getItemCode()) {
                items.set(i, item);
            }
        }
    }

    public void editMember(Member member) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getId() == member.getId()) {
                members.set(i, member);
            }
        }
    }

    public void deleteItem(Item item) {
        items.remove(item);
    }

    public void deleteMember(Member member) {
        members.remove(member);
    }

    // test data
    public void loadTestData() {
        users.add(new User("sempl", "semjava", "Sem Plaatsman"));
        users.add(new User("markie", "ahrnuld123", "Mark de Haan"));
        users.add(new User("john", "doe87", "John Doe"));

        Item item = new Item(242, false, "Java for Dummies, 12th edition", "Vries. P, de");
        item.setLendingDate(LocalDate.of(2022, 10, 26));
        item.setLendingMemberId(35);
        items.add(item);
        items.add(new Item(243, true, "Java for Dummies, 13th edition", "Vries. E, de"));
        item = new Item(244, false, "How to find the missing semicolon (this time for real)", "Plaatsman. S,");
        item.setLendingDate(LocalDate.of(2022, 9, 24));
        item.setLendingMemberId(33);
        items.add(item);
        items.add(new Item(245, true, "Coding memes: humor based on my pain", "Plaatsman. S,"));
        items.add(new Item(246, true, "Frans Bauer: een memoire", "Bauer. F"));

        members.add(new Member(32, "Piet", "de Vries", LocalDate.of(2000, Month.JUNE, 21)));
        members.add(new Member(33, "Frans", "Bauer", LocalDate.of(1973, Month.DECEMBER, 30)));
        members.add(new Member(34, "Dries", "Roelvink", LocalDate.of(1959, Month.JANUARY, 1)));
        members.add(new Member(35, "Emanuel", "Jensen", LocalDate.of(2001, Month.APRIL, 1)));
    }

    //serialize file based on code from the following article: https://www.geeksforgeeks.org/serialization-in-java/
    public void serialize() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(DATABASE_FILE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(this);

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException ioex) {
            throw new RuntimeException(ioex);
        }
    }

    //deserialize file based on code from the following article: https://www.geeksforgeeks.org/serialization-in-java/
    public void deserialize() {
        try {
            if (!DATABASE_FILE.exists() || !DATABASE_FILE.canRead()) {
                loadTestData();
                return;
            }

            FileInputStream fileInputStream = new FileInputStream(DATABASE_FILE);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Database deserializedDatabase = (Database) objectInputStream.readObject();
            users = deserializedDatabase.getUsers();
            items = deserializedDatabase.getItems();
            members = deserializedDatabase.getMembers();

            fileInputStream.close();
            objectInputStream.close();
        } catch (InvalidClassException ice) {
            loadTestData();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
