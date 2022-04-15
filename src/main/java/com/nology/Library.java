package com.nology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Library {

    private final List<Book> books = new ArrayList<>();
    private Boolean inUse;

    public Library() {
        if (Files.exists(Paths.get("src/main/resources/book_list.json"))) {
            books.addAll(loadJsonBooks("src/main/resources/book_list.json"));
        } else loadCSVBooks();
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
            saveBookList("src/main/resources/book_list.json",books);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Book> loadJsonBooks(String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(fileName), new TypeReference<List<Book>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Book>();
        }
    }

    private void saveBookList(String fileName, List<Book> bookList) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(new File(fileName),bookList);

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
