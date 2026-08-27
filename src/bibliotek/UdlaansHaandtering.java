package bibliotek;

public class UdlaansHaandtering {
    private MaterialeKatalog katalog;
    private LaanerRegister laanerRegister;
    private Persistens persistens;

    public UdlaansHaandtering(MaterialeKatalog katalog, LaanerRegister laanerRegister, Persistens persistens) {
        this.katalog = katalog;
        this.laanerRegister = laanerRegister;
        this.persistens = persistens;
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
        System.out.println("UDLÅN: " + fundet.getTitel() + " -> " + fundetLaaner.getNavn() + " (" + java.time.LocalDate.now() + ")");
        persistens.gem(katalog, laanerRegister);
        return true;
    }

    public boolean aflever(String titel) {
        Materiale fundet = katalog.findVedTitel(titel);
        if (fundet == null) {
            return false;
        }
        fundet.aflever();
        persistens.gem(katalog, laanerRegister);
        return true;
    }

    public boolean reserverMateriale(String titel, String laanerId) {
        Materiale fundet = katalog.findVedTitel(titel);
        Laaner fundetLaaner = laanerRegister.findVedId(laanerId);
        if (fundet == null || fundetLaaner == null) {
            return false;
        }
        if (fundet instanceof Reserverbar reserverbart) {
            reserverbart.reservér(fundetLaaner);
            persistens.gem(katalog, laanerRegister);
            return true;
        }
        System.out.println(fundet.getTitel() + " kan ikke reserveres.");
        return false;
    }
}
