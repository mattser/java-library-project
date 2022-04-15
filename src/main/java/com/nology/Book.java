package com.nology;

public class Book {

    private final int number;
    private final String title;
    private final String author;
    private final String genre;
    private final String subGenre;
    private final String publisher;

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
        return "Book [Title: " + title +
                ", Author: " + author +
                ", Genre: " + genre + ", "+ subGenre +
                ", Publisher: " + publisher + "]";
    }
}
