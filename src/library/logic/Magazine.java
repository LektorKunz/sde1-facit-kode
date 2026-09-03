package library.logic;

public class Magazine implements Material {
    private String title;
    private int issueNumber;
    private boolean available;

    public Magazine(String title, int issueNumber) {
        this.title = title;
        this.issueNumber = issueNumber;
        this.available = true;
    }

    @Override
    public String getTitle() {
        return title + " (no. " + issueNumber + ")";
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
}
