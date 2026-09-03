package library.logic;

public class Borrower {
    private String name;
    private String borrowerId;

    public Borrower(String name, String borrowerId) {
        this.name = name;
        this.borrowerId = borrowerId;
    }

    public String getName() {
        return name;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    @Override
    public String toString() {
        return name + " (borrower ID: " + borrowerId + ")";
    }
}
