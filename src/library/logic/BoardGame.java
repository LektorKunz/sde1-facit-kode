package library.logic;

// SESSION 4 (week 40): OCP demonstration — added WITHOUT changing a single line in
// MaterialCatalog or LoanHandling.
public class BoardGame implements Material {
    private String title;
    private int playerCount;
    private boolean available;

    public BoardGame(String title, int playerCount) {
        this.title = title;
        this.playerCount = playerCount;
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
