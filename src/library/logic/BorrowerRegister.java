package library.logic;

import java.util.ArrayList;
import java.util.List;

public class BorrowerRegister {
    private List<Borrower> borrowers = new ArrayList<>();

    public void register(Borrower borrower) {
        borrowers.add(borrower);
    }

    public Borrower findById(String borrowerId) {
        for (Borrower b : borrowers) {
            if (b.getBorrowerId().equals(borrowerId)) {
                return b;
            }
        }
        return null;
    }

    public List<Borrower> getAll() {
        return borrowers;
    }
}
