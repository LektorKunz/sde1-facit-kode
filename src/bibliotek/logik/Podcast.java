package bibliotek.logik;

// GANG 4 (uge 40), øvelse 1: elevens eget OCP-bevis — endnu en materialetype, tilføjet uden at
// ændre MaterialeKatalog eller UdlaansHaandtering.
public class Podcast implements Materiale {
    private String titel;
    private int antalEpisoder;
    private boolean tilgaengelig;

    public Podcast(String titel, int antalEpisoder) {
        this.titel = titel;
        this.antalEpisoder = antalEpisoder;
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
