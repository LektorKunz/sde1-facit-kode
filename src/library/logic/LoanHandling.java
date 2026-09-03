package library.logic;

import library.data.Persistence;

public class LoanHandling {
    private MaterialCatalog catalog;
    private BorrowerRegister borrowerRegister;
    private Persistence persistence;

    public LoanHandling(MaterialCatalog catalog, BorrowerRegister borrowerRegister, Persistence persistence) {
        this.catalog = catalog;
        this.borrowerRegister = borrowerRegister;
        this.persistence = persistence;
    }

    public boolean lend(String title, String borrowerId) {
        Material found = catalog.findByTitle(title);
        if (found == null || !found.isAvailable()) {
            return false;
        }
        Borrower foundBorrower = borrowerRegister.findById(borrowerId);
        if (foundBorrower == null) {
            return false;
        }
        found.borrow();
        System.out.println("LOAN: " + found.getTitle() + " -> " + foundBorrower.getName() + " (" + java.time.LocalDate.now() + ")");
        persistence.save(catalog, borrowerRegister);
        return true;
    }

    public boolean returnMaterial(String title) {
        Material found = catalog.findByTitle(title);
        if (found == null) {
            return false;
        }
        found.returnItem();
        persistence.save(catalog, borrowerRegister);
        return true;
    }

    public boolean reserveMaterial(String title, String borrowerId) {
        Material found = catalog.findByTitle(title);
        Borrower foundBorrower = borrowerRegister.findById(borrowerId);
        if (found == null || foundBorrower == null) {
            return false;
        }
        if (found instanceof Reservable reservable) {
            reservable.reserve(foundBorrower);
            persistence.save(catalog, borrowerRegister);
            return true;
        }
        System.out.println(found.getTitle() + " cannot be reserved.");
        return false;
    }
}
