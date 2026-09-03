package library.data;

import library.logic.Book;
import library.logic.Borrower;
import library.logic.BorrowerRegister;
import library.logic.Material;
import library.logic.MaterialCatalog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FilePersistence implements Persistence {
    private static final String BOOKS_FILE = "books.json";
    private static final String BORROWERS_FILE = "borrowers.json";

    @Override
    public void save(MaterialCatalog catalog, BorrowerRegister borrowerRegister) {
        saveBooks(catalog);
        saveBorrowers(borrowerRegister);
    }

    private void saveBooks(MaterialCatalog catalog) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Material material : catalog.getAll()) {
                if (material instanceof Book book) {
                    writer.write(bookToJson(book));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not save books: " + e.getMessage());
        }
    }

    private String bookToJson(Book book) {
        return "{\"title\":\"" + book.getTitle() + "\",\"author\":\"" + book.getAuthor()
                + "\",\"isbn\":\"" + book.getIsbn() + "\",\"available\":" + book.isAvailable() + "}";
    }

    // Note: the reservedBy field (from week 43's Reservable interface) is NOT saved here — another
    // deliberate limitation, same principle as "only Book is persisted": a reservation does not survive
    // a program restart today. This is a natural extension in the spirit of exercise 3 (direction B),
    // not something fixed in the base solution.

    private void saveBorrowers(BorrowerRegister borrowerRegister) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BORROWERS_FILE))) {
            for (Borrower borrower : borrowerRegister.getAll()) {
                writer.write(borrowerToJson(borrower));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save borrowers: " + e.getMessage());
        }
    }

    private String borrowerToJson(Borrower borrower) {
        return "{\"name\":\"" + borrower.getName() + "\",\"borrowerId\":\"" + borrower.getBorrowerId() + "\"}";
    }

    @Override
    public void load(MaterialCatalog catalog, BorrowerRegister borrowerRegister) {
        loadBooks(catalog);
        loadBorrowers(borrowerRegister);
    }

    private void loadBooks(MaterialCatalog catalog) {
        File file = new File(BOOKS_FILE);
        if (!file.exists()) {
            System.out.println(BOOKS_FILE + " does not exist yet — starting with an empty catalog.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                try {
                    catalog.add(jsonToBook(line));
                } catch (RuntimeException e) {
                    System.out.println("Skipped invalid line " + lineNumber + " in " + BOOKS_FILE
                            + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read " + BOOKS_FILE + ": " + e.getMessage());
        }
    }

    private Book jsonToBook(String line) {
        String title = null;
        String author = null;
        String isbn = null;
        boolean available = true;

        for (String[] field : extractFields(line)) {
            switch (field[0]) {
                case "title" -> title = field[1];
                case "author" -> author = field[1];
                case "isbn" -> isbn = field[1];
                case "available" -> available = Boolean.parseBoolean(field[1]);
                default -> throw new IllegalArgumentException("unknown field \"" + field[0] + "\"");
            }
        }
        if (title == null || author == null || isbn == null) {
            throw new IllegalArgumentException("missing title, author, or isbn");
        }

        Book book = new Book(title, author, isbn);
        if (!available) {
            book.borrow();
        }
        return book;
    }

    private void loadBorrowers(BorrowerRegister borrowerRegister) {
        File file = new File(BORROWERS_FILE);
        if (!file.exists()) {
            System.out.println(BORROWERS_FILE + " does not exist yet — starting with an empty borrower register.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                try {
                    borrowerRegister.register(jsonToBorrower(line));
                } catch (RuntimeException e) {
                    System.out.println("Skipped invalid line " + lineNumber + " in " + BORROWERS_FILE
                            + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read " + BORROWERS_FILE + ": " + e.getMessage());
        }
    }

    private Borrower jsonToBorrower(String line) {
        String name = null;
        String borrowerId = null;

        for (String[] field : extractFields(line)) {
            switch (field[0]) {
                case "name" -> name = field[1];
                case "borrowerId" -> borrowerId = field[1];
                default -> throw new IllegalArgumentException("unknown field \"" + field[0] + "\"");
            }
        }
        if (name == null || borrowerId == null) {
            throw new IllegalArgumentException("missing name or borrowerId");
        }
        return new Borrower(name, borrowerId);
    }

    /**
     * Simplified JSON parsing: expects one flat object per line, e.g.
     * {"title":"Effective Java","available":true}. Splits naively on comma —
     * breaks if a value itself contains a comma (see "Common mistakes", session 6).
     */
    private String[][] extractFields(String line) {
        String content = line.trim();
        if (!content.startsWith("{") || !content.endsWith("}")) {
            throw new IllegalArgumentException("line does not start and end with { and }: " + line);
        }
        content = content.substring(1, content.length() - 1).trim();
        if (content.isEmpty()) {
            return new String[0][];
        }
        String[] pairs = content.split(",");
        String[][] fields = new String[pairs.length][2];
        for (int i = 0; i < pairs.length; i++) {
            String[] keyValue = pairs[i].split(":", 2);
            if (keyValue.length != 2) {
                throw new IllegalArgumentException("field without ':': " + pairs[i]);
            }
            fields[i][0] = stripQuotes(keyValue[0].trim());
            fields[i][1] = stripQuotes(keyValue[1].trim());
        }
        return fields;
    }

    private String stripQuotes(String s) {
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
