package bibliotek;

public class Bog {
    private String titel;
    private String forfatter;
    private String isbn;
    private boolean tilgaengelig;

    public Bog(String titel, String forfatter, String isbn) {
        this.titel = titel;
        this.forfatter = forfatter;
        this.isbn = isbn;
        this.tilgaengelig = true; // en ny bog er tilgængelig fra start
    }

    public String getTitel() {
        return titel;
    }

    public String getForfatter() {
        return forfatter;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean erTilgaengelig() {
        return tilgaengelig;
    }

    public void setTilgaengelig(boolean tilgaengelig) {
        this.tilgaengelig = tilgaengelig;
    }

    @Override
    public String toString() {
        return titel + " af " + forfatter + " (ISBN: " + isbn + ")";
    }
}
