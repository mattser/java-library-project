package com.nology;

public class Book {

    private int number;
    private String title;
    private String author;
    private String genre;
    private String subGenre;
    private String publisher;

    public Book () {}

    public Book(int number, String title, String author, String genre, String subGenre, String publisher) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.subGenre = subGenre;
        this.publisher = publisher;
    }

    public Book (String[] data) {
        this.number = Integer.parseInt(data[0]);
        this.title = data[1];
        this.author = data[2];
        this.genre = data[3];
        this.subGenre = data[4];
        this.publisher = data[5];
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setSubGenre(String subGenre) {
        this.subGenre = subGenre;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getSubGenre() {
        return subGenre;
    }

    public String getPublisher() {
        return publisher;
    }



    @Override
    public String toString() {
        return "[ID: " + number +
                " | Title: " + title +
                " | Author: " + author +
                " | Genre: " + genre + ", "+ subGenre +
                " | Publisher: " + publisher + "]";
    }
}
