package com.nology;

public class Main {

    public static void main(String[] args) {
	    // 1. Read Data from file
        IBookRepository mapper = new JsonMapper();

        // 2. Instantiate a library with that data
        Library library = new Library(mapper);

        // 3. Create a continuous scanner loop waiting for interaction



    }
}
