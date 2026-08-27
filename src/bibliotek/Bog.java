package bibliotek;

public class Bog implements Materiale {
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

    @Override
    public String getTitel() {
        return titel;
    }

    public String getForfatter() {
        return forfatter;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public boolean erTilgaengelig() {
        return tilgaengelig;
    }

    @Override
    public void laan() {
        tilgaengelig = false;
    }

    @Override
    public void aflever() {
        tilgaengelig = true;
    }

    @Override
    public String toString() {
        return titel + " af " + forfatter + " (ISBN: " + isbn + ")";
    }
}
