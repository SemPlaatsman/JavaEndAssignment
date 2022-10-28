package nl.inholland.javaendassignment.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Item implements Serializable {
    private int itemCode;
    private boolean available;
    private String title;
    private String author;
    private LocalDate lendingDate;
    private Integer lendingMemberId;

    public Item(int itemCode, boolean available, String title, String author) {
        this.itemCode = itemCode;
        this.available = available;
        this.title = title;
        this.author = author;
        this.lendingDate = null;
        this.lendingMemberId = null;
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

    public LocalDate getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(LocalDate lendingDate) {
        this.lendingDate = lendingDate;
    }

    public void setLendingMemberId(Integer lendingMemberId) {
        this.lendingMemberId = lendingMemberId;
    }
}