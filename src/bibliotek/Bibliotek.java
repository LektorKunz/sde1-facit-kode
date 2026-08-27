package bibliotek;

import java.util.ArrayList;
import java.util.List;

// GANG 1 (uge 36): bevidst naiv "gudeklasse" — holder styr på bøger, lånere OG udlånslogik i én
// klasse, uden nogen adskillelse. Erstattes i gang 4 (uge 40, SRP) af MaterialeKatalog,
// LaanerRegister og UdlaansHaandtering. Klassen er bevidst bevaret i git-historikken her, men
// bruges ikke længere fra Main efter gang 4 — se README for hvornår den forsvinder fra HEAD.
public class Bibliotek {
    private List<Bog> boeger = new ArrayList<>();
    private List<Laaner> laanere = new ArrayList<>();

    public void tilfoejBog(Bog bog) {
        boeger.add(bog);
    }

    public void registrerLaaner(Laaner laaner) {
        laanere.add(laaner);
    }

    public boolean udlaanBog(String isbn, String laanerId) {
        Bog fundetBog = null;
        for (Bog b : boeger) {
            if (b.getIsbn().equals(isbn)) {
                fundetBog = b;
                break;
            }
        }
        if (fundetBog == null || !fundetBog.erTilgaengelig()) {
            return false;
        }
        Laaner fundetLaaner = null;
        for (Laaner l : laanere) {
            if (l.getLaanerId().equals(laanerId)) {
                fundetLaaner = l;
                break;
            }
        }
        if (fundetLaaner == null) {
            return false;
        }
        fundetBog.setTilgaengelig(false);
        System.out.println(fundetLaaner.getNavn() + " har lånt " + fundetBog.getTitel());
        return true;
    }

    public boolean afleverBog(String isbn) {
        for (Bog b : boeger) {
            if (b.getIsbn().equals(isbn)) {
                b.setTilgaengelig(true);
                return true;
            }
        }
        return false;
    }

    public List<Bog> getBoeger() {
        return boeger;
    }

    public List<Bog> findBoegerAfForfatter(String forfatter) {
        List<Bog> resultat = new ArrayList<>();
        for (Bog b : boeger) {
            if (b.getForfatter().equalsIgnoreCase(forfatter)) {
                resultat.add(b);
            }
        }
        return resultat;
    }

    public List<Bog> listUdlaanteBoeger() {
        List<Bog> resultat = new ArrayList<>();
        for (Bog b : boeger) {
            if (!b.erTilgaengelig()) {
                resultat.add(b);
            }
        }
        return resultat;
    }
}
