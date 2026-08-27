package bibliotek;

import java.util.ArrayList;
import java.util.List;

// GANG 1 (uge 36): bevidst naiv "gudeklasse". GANG 3 (uge 39): refaktoreret til at arbejde mod
// Materiale-interfacet i stedet for konkret Bog — se Main for beviset på, at Film/Tidsskrift kan
// lånes/afleveres uden at Bibliotek kender dem som konkrete typer. Erstattes helt i gang 4 (uge 40,
// SRP) af MaterialeKatalog, LaanerRegister og UdlaansHaandtering.
public class Bibliotek {
    private List<Materiale> materialer = new ArrayList<>();
    private List<Laaner> laanere = new ArrayList<>();

    public void tilfoejMateriale(Materiale materiale) {
        materialer.add(materiale);
    }

    public void registrerLaaner(Laaner laaner) {
        laanere.add(laaner);
    }

    public boolean udlaan(String titel, String laanerId) {
        Materiale fundet = findMaterialeVedTitel(titel);
        if (fundet == null || !fundet.erTilgaengelig()) {
            return false;
        }
        Laaner fundetLaaner = findLaanerVedId(laanerId);
        if (fundetLaaner == null) {
            return false;
        }
        fundet.laan();
        System.out.println(fundetLaaner.getNavn() + " har lånt " + fundet.getTitel());
        return true;
    }

    public boolean aflever(String titel) {
        Materiale fundet = findMaterialeVedTitel(titel);
        if (fundet == null) {
            return false;
        }
        fundet.aflever();
        return true;
    }

    private Materiale findMaterialeVedTitel(String titel) {
        for (Materiale m : materialer) {
            if (m.getTitel().equals(titel)) {
                return m;
            }
        }
        return null;
    }

    private Laaner findLaanerVedId(String laanerId) {
        for (Laaner l : laanere) {
            if (l.getLaanerId().equals(laanerId)) {
                return l;
            }
        }
        return null;
    }

    public List<Materiale> getMaterialer() {
        return materialer;
    }
}
