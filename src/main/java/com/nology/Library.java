package com.nology;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.*;

public class Library {

    private final List<Book> books = new ArrayList<>();
    private Boolean inUse;

    public Library() {
        loadCSVBooks();
        inUse = true;
        useLibrary();
    }

    private void loadCSVBooks() {
        try {
            CSVReader reader = new CSVReader(new FileReader("src/main/resources/books_data.csv"));
            String[] nextRecord;

            while( (nextRecord=reader.readNext()) != null) {
                if (nextRecord[0].equals("Number")) continue;
                books.add(new Book(nextRecord));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void useLibrary() {
        Scanner s = new Scanner(System.in);
        int userInput;
        while(inUse) {
            System.out.println("Select From the Options Below:");
            System.out.println("1. Show All Books");
            System.out.println("0. Log Off");
            userInput = s.nextInt();
            if (userInput == 1) {
                System.out.println(books);
            } else if (userInput == 0) {
                System.out.println("Thank you for using this service =)");
                inUse = false;
            } else {
                System.out.println("Invalid Input");
            }
        }
    }



}
