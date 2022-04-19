package com.nology;

import java.util.ArrayList;

public interface IBookRepository {

    Book createBook();

    ArrayList<Book> getBooks();

}
