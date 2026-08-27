package bibliotek.logik;

public class Tidsskrift implements Materiale {
    private String titel;
    private int nummer;
    private boolean tilgaengelig;

    public Tidsskrift(String titel, int nummer) {
        this.titel = titel;
        this.nummer = nummer;
        this.tilgaengelig = true;
    }

    @Override
    public String getTitel() {
        return titel + " (nr. " + nummer + ")";
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
