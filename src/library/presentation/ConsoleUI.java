package library.presentation;

import library.logic.BorrowerRegister;
import library.logic.MaterialCatalog;
import library.logic.Material;
import library.logic.LoanHandling;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final MaterialCatalog catalog;
    private final BorrowerRegister borrowerRegister;
    private final LoanHandling loanHandling;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(MaterialCatalog catalog, BorrowerRegister borrowerRegister,
                      LoanHandling loanHandling) {
        this.catalog = catalog;
        this.borrowerRegister = borrowerRegister;
        this.loanHandling = loanHandling;
    }

    public void start() {
        boolean continueRunning = true;
        while (continueRunning) {
            showMenu();
            if (!scanner.hasNextLine()) {
                break; // input has run out (e.g. via a pipe/redirect) — exit gracefully
            }
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> listMaterials();
                case "2" -> lendMaterial();
                case "3" -> returnMaterial();
                case "4" -> reserveMaterial();
                case "0" -> continueRunning = false;
                default -> System.out.println("Unknown choice.");
            }
        }
    }

    private void showMenu() {
        System.out.println("""
                --- The Library ---
                1) List materials
                2) Borrow material
                3) Return material
                4) Reserve material
                0) Exit""");
    }

    private void listMaterials() {
        List<Material> all = catalog.getAll();
        for (Material m : all) {
            System.out.println(m.getTitle() + " - " + (m.isAvailable() ? "available" : "borrowed"));
        }
    }

    private void lendMaterial() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Borrower number: ");
        String borrowerId = scanner.nextLine();
        boolean succeeded = loanHandling.lend(title, borrowerId);
        System.out.println(succeeded ? "Borrowed." : "Could not borrow.");
    }

    private void returnMaterial() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        boolean succeeded = loanHandling.returnMaterial(title);
        System.out.println(succeeded ? "Returned." : "Could not return.");
    }

    private void reserveMaterial() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Borrower number: ");
        String borrowerId = scanner.nextLine();
        boolean succeeded = loanHandling.reserveMaterial(title, borrowerId);
        System.out.println(succeeded ? "Reserved." : "Could not reserve.");
    }
}
