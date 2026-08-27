package bibliotek.logik;

public class Bog implements Materiale, Reserverbar {
    private String titel;
    private String forfatter;
    private String isbn;
    private boolean tilgaengelig;
    private Laaner reserveretAf;

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
    public void reservér(Laaner laaner) {
        this.reserveretAf = laaner;
        System.out.println(laaner.getNavn() + " har reserveret " + titel);
    }

    // Bemærk (gang 6, uge 44): der er bevidst INGEN getter for reserveretAf i grundfacit — den
    // tilføjes kun, hvis man løser gang 6's ekstraopgave/øvelse 3, retning B (gem reservationer).

    @Override
    public String toString() {
        return titel + " af " + forfatter + " (ISBN: " + isbn + ")";
    }
}
