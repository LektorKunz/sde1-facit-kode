package bibliotek;

public class UdlaansHaandtering {
    private MaterialeKatalog katalog;
    private LaanerRegister laanerRegister;

    public UdlaansHaandtering(MaterialeKatalog katalog, LaanerRegister laanerRegister) {
        this.katalog = katalog;
        this.laanerRegister = laanerRegister;
    }

    public boolean udlaan(String titel, String laanerId) {
        Materiale fundet = katalog.findVedTitel(titel);
        if (fundet == null || !fundet.erTilgaengelig()) {
            return false;
        }
        Laaner fundetLaaner = laanerRegister.findVedId(laanerId);
        if (fundetLaaner == null) {
            return false;
        }
        fundet.laan();
        System.out.println(fundetLaaner.getNavn() + " har lånt " + fundet.getTitel());
        return true;
    }

    public boolean aflever(String titel) {
        Materiale fundet = katalog.findVedTitel(titel);
        if (fundet == null) {
            return false;
        }
        fundet.aflever();
        return true;
    }
}
