package library;

import library.data.FilePersistence;
import library.data.Persistence;
import library.logic.Book;
import library.logic.Borrower;
import library.logic.BorrowerRegister;
import library.logic.MaterialCatalog;
import library.logic.LoanHandling;
import library.presentation.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        // Data layer
        Persistence persistence = new FilePersistence(); // saves to books.json/borrowers.json, cf. session 6

        // Logic layer
        MaterialCatalog catalog = new MaterialCatalog();
        BorrowerRegister borrowerRegister = new BorrowerRegister();
        LoanHandling loanHandling =
                new LoanHandling(catalog, borrowerRegister, persistence);
        persistence.load(catalog, borrowerRegister);

        if (catalog.getAll().isEmpty()) {
            catalog.add(new Book("Effective Java", "Joshua Bloch", "978-0134685991"));
            catalog.add(new Book("Clean Code", "Robert C. Martin", "978-0132350884"));
        }
        if (borrowerRegister.getAll().isEmpty()) {
            borrowerRegister.register(new Borrower("Anna Nielsen", "L001"));
        }

        // Presentation layer
        ConsoleUI ui = new ConsoleUI(catalog, borrowerRegister, loanHandling);
        ui.start();
    }
}
