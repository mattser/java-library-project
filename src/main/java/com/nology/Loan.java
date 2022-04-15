package com.nology;

import java.sql.Timestamp;

public class Loan {

    private int bookID;
    private String bookName;
    private String userName;
    private Timestamp outDate;
    private Timestamp dueDate;
    private Boolean hasBeenReturned;

    public Loan () {
    }

    public Loan(int bookID, String bookName, String userName) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.userName = userName;
        this.outDate = new Timestamp(System.currentTimeMillis());
        this.dueDate = new Timestamp(System.currentTimeMillis() + 60*60*24*7);
        hasBeenReturned = false;
    }

    public Loan(int bookID, String bookName, String userName, Timestamp outDate, Timestamp dueDate, Boolean hasBeenReturned) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.userName = userName;
        this.outDate = outDate;
        this.dueDate = dueDate;
        this.hasBeenReturned = hasBeenReturned;
    }

    public int getBookID() {
        return bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getOutDate() {
        return outDate;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public Boolean getHasBeenReturned() {
        return hasBeenReturned;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setOutDate(Timestamp outDate) {
        this.outDate = outDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public void setHasBeenReturned(Boolean hasBeenReturned) {
        this.hasBeenReturned = hasBeenReturned;
    }
}
