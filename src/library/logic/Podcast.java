package library.logic;

// SESSION 4 (week 40), exercise 1: the student's own OCP proof — another material type, added without
// changing MaterialCatalog or LoanHandling.
public class Podcast implements Material {
    private String title;
    private int episodeCount;
    private boolean available;

    public Podcast(String title, int episodeCount) {
        this.title = title;
        this.episodeCount = episodeCount;
        this.available = true;
    }

    @Override
    public String getTitle() { return title; }
    @Override
    public boolean isAvailable() { return available; }
    @Override
    public void borrow() { available = false; }
    @Override
    public void returnItem() { available = true; }
}
