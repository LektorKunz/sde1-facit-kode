package bibliotek.logik;

// GANG 4 (uge 40): OCP-demonstration — tilføjet UDEN at ændre en eneste linje i
// MaterialeKatalog eller UdlaansHaandtering.
public class Braetspil implements Materiale {
    private String titel;
    private int antalSpillere;
    private boolean tilgaengelig;

    public Braetspil(String titel, int antalSpillere) {
        this.titel = titel;
        this.antalSpillere = antalSpillere;
        this.tilgaengelig = true;
    }

    @Override
    public String getTitel() { return titel; }
    @Override
    public boolean erTilgaengelig() { return tilgaengelig; }
    @Override
    public void laan() { tilgaengelig = false; }
    @Override
    public void aflever() { tilgaengelig = true; }
}
