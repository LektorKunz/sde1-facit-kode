package bibliotek;

public class Film implements Materiale, Reserverbar {
    private String titel;
    private int spilletidMinutter;
    private boolean tilgaengelig;
    private Laaner reserveretAf;

    public Film(String titel, int spilletidMinutter) {
        this.titel = titel;
        this.spilletidMinutter = spilletidMinutter;
        this.tilgaengelig = true;
    }

    @Override
    public String getTitel() {
        return titel;
    }

    public int getSpilletidMinutter() {
        return spilletidMinutter;
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

    // Bemærk (gang 6, uge 44): der er bevidst INGEN getter for reserveretAf i grundfacit — se
    // Bog.java for samme bemærkning.
}
