package bibliotek;

public class Film implements Materiale {
    private String titel;
    private int spilletidMinutter;
    private boolean tilgaengelig;

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
}
