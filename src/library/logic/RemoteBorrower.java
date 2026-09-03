package library.logic;

// SESSION 4 (week 40): LSP example. The full lesson plan FIRST shows a broken version that overrides
// getBorrowerId() to throw an exception — it is deliberately NOT included in this base solution, only the
// correct version, which students must hand in themselves. RemoteBorrower ONLY ADDS a postal code, it
// does not change any behavior Borrower already promised.
public class RemoteBorrower extends Borrower {
    private String postalCode;

    public RemoteBorrower(String name, String borrowerId, String postalCode) {
        super(name, borrowerId);
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    // No overrides that CHANGE the behavior of anything Borrower already promised.
    // getBorrowerId() is inherited unchanged and works as expected.
}
