package library.logic;

public class Book implements Material, Reservable {
    private String title;
    private String author;
    private String isbn;
    private boolean available;
    private Borrower reservedBy;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true; // a new book is available from the start
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void borrow() {
        available = false;
    }

    @Override
    public void returnItem() {
        available = true;
    }

    @Override
    public void reserve(Borrower borrower) {
        this.reservedBy = borrower;
        System.out.println(borrower.getName() + " has reserved " + title);
    }

    // Note (session 6, week 44): there is deliberately NO getter for reservedBy in the base solution — it
    // is only added if you solve session 6's bonus exercise/exercise 3, direction B (save reservations).

    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + isbn + ")";
    }
}
