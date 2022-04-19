package com.nology;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;

public class CsvRepository implements IBookRepository {

    private String url;

    public CsvMapper(String url) {
        this.url = url;
    }

    public CsvMapper() {
        this.url = "src/main/resources/books_data.csv";
    }


    @Override
    public ArrayList<Book> mapDataToBooks() {
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader("src/main/resources/books_data.csv"));
            String[] nextRecord;
            while( (nextRecord=reader.readNext()) != null) {
                if (nextRecord[0].equals("Number")) continue;
                bookList.add(new Book(nextRecord));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }
}
