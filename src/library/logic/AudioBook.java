package library.logic;

// SESSION 3 (week 39), exercise 1: a fourth material type — proves that Library/later
// MaterialCatalog can handle it purely through the Material interface.
public class AudioBook implements Material {
    private String title;
    private String narrator;
    private boolean available;

    public AudioBook(String title, String narrator) {
        this.title = title;
        this.narrator = narrator;
        this.available = true;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getNarrator() {
        return narrator;
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
