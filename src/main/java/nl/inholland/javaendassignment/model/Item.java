package nl.inholland.javaendassignment.model;

import javafx.beans.property.*;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;

import java.io.Serializable;

public class Item implements Serializable {
    private int itemCode;
    private boolean available;
    private String title;
    private String author;

    public Item(int itemCode, boolean available, String title, String author) {
        this.itemCode = itemCode;
        this.available = available;
        this.title = title;
        this.author = author;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}