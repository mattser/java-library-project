package com.nology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonRepository implements IBookRepository {

    private String url;

    public JsonMapper(String url) {
        this.url = url;
    }

    public JsonMapper() {
        this.url = "src/main/resources/book_list.json";
    }

    @Override
    public ArrayList<Book> mapDataToBooks() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (ArrayList<Book>) mapper.readValue(new File(url), new TypeReference<List<Book>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Book>();
        }
    }
}
