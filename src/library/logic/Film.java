package library.logic;

public class Film implements Material, Reservable {
    private String title;
    private int runtimeMinutes;
    private boolean available;
    private Borrower reservedBy;

    public Film(String title, int runtimeMinutes) {
        this.title = title;
        this.runtimeMinutes = runtimeMinutes;
        this.available = true;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public int getRuntimeMinutes() {
        return runtimeMinutes;
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

    // Note (session 6, week 44): there is deliberately NO getter for reservedBy in the base solution — see
    // Book.java for the same note.
}
