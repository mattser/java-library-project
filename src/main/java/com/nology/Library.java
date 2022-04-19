package com.nology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Library {

    private final List<Book> books = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();
    private User user;
    private Boolean inUse;

    public Library(IBookRepository mapper) {
        this.books.addAll(mapper.mapDataToBooks());
        if (Files.exists(Paths.get("src/main/resources/book_list.json"))) {
            books.addAll(loadJsonBooks("src/main/resources/book_list.json"));
        } else loadCSVBooks();
        inUse = true;
        users.addAll(loadUsers());
        loans.addAll(loadLoans());
        logIn();
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

    public List<User> loadUsers () {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File("src/main/resources/users_list.json"), new TypeReference<List<User>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<User>();
        }
    }

    public void saveUsers (List<User> userList) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("src/main/resources/users_list.json"),userList);
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

    private List<Loan> loadLoans () {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File("src/main/resources/loan_list.json"), new TypeReference<List<Loan>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Loan>();
        }
    }

    private void saveLoanList(List<Loan> loanList) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("src/main/resources/loan_list.json"),loanList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Loan> getActiveLoans () {
        return loans.stream().filter(o -> !o.getHasBeenReturned()).collect(Collectors.toList());
    }

    private List<Book> getAvailableBooks () {
        List<Integer> loanedBookId = new ArrayList<>();
        for (Loan loan: loans) {
            if (!loan.getHasBeenReturned()) loanedBookId.add(loan.getBosokID());
        }

        return books.stream().filter(o -> !loanedBookId.contains(o.getNumber())).collect(Collectors.toList());
    }

    private void logIn() {
        Scanner s = new Scanner(System.in);
        Boolean loggedIn = false;

        while(!loggedIn) {
            System.out.println("Enter your user name:");
            String name = s.next();
            if (users.stream().noneMatch(o -> o.getName().equals(name))) {
                System.out.println(name + " does not exist. Would you like to create a new user?");
                System.out.println("1. Yes");
                System.out.println("Press Any Key For No.");
                int response = s.nextInt();
                if (response == 1) {
                    user = new User(name);
                    users.add(user);
                    saveUsers(users);
                    loggedIn = true;
                }
            } else {
                user = users.stream().filter(o -> o.getName().equals(name)).findFirst().get();
                loggedIn = true;
            }
        }
    }

    public void printLoans() {
        for (Loan loan: loans) {
            if (loan.getUserName().equals(user.getName()) || user.getAdmin()) {
                System.out.println(loan);
            }
        }
    }
    private void useLibrary() {
        Scanner s = new Scanner(System.in);
        int userInput;
        while(inUse) {
            System.out.println("You are logged in as: " + user.getName());
            System.out.println("Select From the Options Below:");
            System.out.println("1. Show All Books");
            if (!user.getAdmin()) {
                System.out.println("2. Check Your Current Loans");
                System.out.println("3. Loan a Book");
                System.out.println("4. Return a Book");
            } else {
                System.out.println("2. See Loan History");
                System.out.println("3. See Active Loans");
                System.out.println("4. See Book Popularity");
                System.out.println("5. Save Loan Reports to CSV File");
            }
            System.out.println("0. Log Off");

            userInput = s.nextInt();

            if (userInput == 1) {
                for (Book book: books) {
                    System.out.println(book);
                }
            } else if (userInput == 0) {
                System.out.println("Thank you for using this service =)");
                inUse = false;
            } else if (userInput == 2) {
                printLoans();
            } else if (userInput == 3 && !user.getAdmin()) {
                makeNewLoan();
            } else if (userInput ==3 && user.getAdmin()) {
                System.out.println(getActiveLoans());
            } else if (userInput == 4 && !user.getAdmin()) {
                returnBook();
            } else if (userInput == 4 && user.getAdmin()) {
                System.out.println("Book Name = Number of Loans");
                System.out.println(getBookPopularity());
            } else if (userInput == 5 && user.getAdmin()) {
                writeCSVReport();
            } else {
                System.out.println("Invalid Input");
            }
        }
    }

    public void makeNewLoan () {
        Scanner s = new Scanner(System.in);
        boolean finished = false;

        while(!finished) {
            System.out.println("These Books are available to Loan:");
            List<Book> availableBooks = getAvailableBooks();
            for (Book book : availableBooks) System.out.println(book);
            System.out.println("Type the Book ID you wish to Loan");
            System.out.println("Or 999 to return");
            int response = s.nextInt();
            if (response == 999) {
                break;
            }
            if (availableBooks.stream().anyMatch(o -> o.getNumber() == response)) {
                Book bookToLoan = availableBooks.stream().filter(o -> o.getNumber() == response).findFirst().get();
                System.out.println("Loaning Book:");
                System.out.println(bookToLoan);
                loans.add(new Loan(bookToLoan.getNumber(),bookToLoan.getTitle(),user.getName()));
                saveLoanList(loans);
                finished = true;
            }
        }
        s.close();
    }

    public void returnBook () {
        Scanner s = new Scanner(System.in);
        System.out.println("Your active loans are:");
        printLoans();
        System.out.println("Enter the Book Id you wish to return");
        int response = s.nextInt();
        Loan loanToEdit = loans.stream()
                .filter(o -> !o.getHasBeenReturned() && o.getBookID() == response && o.getUserName().equals(user.getName()))
                .findFirst().get();

        loans.remove(loanToEdit);
        loanToEdit.setHasBeenReturned(true);
        loans.add(loanToEdit);
        saveLoanList(loans);
        s.close();
    }

    public Map<String,Long> getBookPopularity() {
        return loans.stream().collect(Collectors.groupingBy(Loan::getBookName, Collectors.counting()));
    }

    public void writeCSVReport() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(new File("src/main/resources/report.csv")));
            writer.writeNext(new String [] {"BookID","Book Name", "User Name", "Date Taken Out", "Due Date", "Returned"});

            for (Loan loan: loans) {
                writer.writeNext(new String[] {"" + loan.getBookID(),
                        loan.getBookName(),
                        loan.getUserName(),
                        "" + loan.getOutDate(),
                        "" + loan.getDueDate(),
                        "" + loan.getHasBeenReturned()});
            }
            writer.close();
            System.out.println("Report Written");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
