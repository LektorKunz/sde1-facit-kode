package library.logic;

// SESSION 4 (week 40), exercise 2: a valid Borrower subtype — can be substituted anywhere a regular
// Borrower is used, without breaking LSP.
public class Teacher extends Borrower {
    private static final int EXTENDED_LOAN_LIMIT = 20;

    public Teacher(String name, String borrowerId) {
        super(name, borrowerId);
    }

    public int getLoanLimit() {
        return EXTENDED_LOAN_LIMIT;
    }

    // getName() and getBorrowerId() are inherited unchanged — no surprises for code using Borrower
}
