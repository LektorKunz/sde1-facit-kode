package bibliotek.logik;

// GANG 3 (uge 39), øvelse 1: en fjerde materialetype — beviser at Bibliotek/senere
// MaterialeKatalog kan håndtere den udelukkende via Materiale-interfacet.
public class Lydbog implements Materiale {
    private String titel;
    private String opleser;
    private boolean tilgaengelig;

    public Lydbog(String titel, String opleser) {
        this.titel = titel;
        this.opleser = opleser;
        this.tilgaengelig = true;
    }

    @Override
    public String getTitel() {
        return titel;
    }

    public String getOpleser() {
        return opleser;
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
