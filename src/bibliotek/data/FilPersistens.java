package bibliotek.data;

import bibliotek.logik.Bog;
import bibliotek.logik.Laaner;
import bibliotek.logik.LaanerRegister;
import bibliotek.logik.Materiale;
import bibliotek.logik.MaterialeKatalog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FilPersistens implements Persistens {
    private static final String BOEGER_FIL = "boeger.json";
    private static final String LAANERE_FIL = "laanere.json";

    @Override
    public void gem(MaterialeKatalog katalog, LaanerRegister laanerRegister) {
        gemBoeger(katalog);
        gemLaanere(laanerRegister);
    }

    private void gemBoeger(MaterialeKatalog katalog) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOEGER_FIL))) {
            for (Materiale materiale : katalog.getAlle()) {
                if (materiale instanceof Bog bog) {
                    writer.write(bogTilJson(bog));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke gemme bøger: " + e.getMessage());
        }
    }

    private String bogTilJson(Bog bog) {
        return "{\"titel\":\"" + bog.getTitel() + "\",\"forfatter\":\"" + bog.getForfatter()
                + "\",\"isbn\":\"" + bog.getIsbn() + "\",\"tilgaengelig\":" + bog.erTilgaengelig() + "}";
    }

    // Bemærk: feltet reserveretAf (fra uge 43's Reserverbar-interface) gemmes IKKE her — endnu en
    // bevidst afgrænsning, samme princip som "kun Bog persisteres": en reservation overlever ikke
    // et program-genstart i dag. Det er en naturlig udvidelse i øvelse 3's ånd (retning B), ikke
    // noget der er rettet i grundfacit.

    private void gemLaanere(LaanerRegister laanerRegister) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LAANERE_FIL))) {
            for (Laaner laaner : laanerRegister.getAlle()) {
                writer.write(laanerTilJson(laaner));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke gemme lånere: " + e.getMessage());
        }
    }

    private String laanerTilJson(Laaner laaner) {
        return "{\"navn\":\"" + laaner.getNavn() + "\",\"laanerId\":\"" + laaner.getLaanerId() + "\"}";
    }

    @Override
    public void hent(MaterialeKatalog katalog, LaanerRegister laanerRegister) {
        hentBoeger(katalog);
        hentLaanere(laanerRegister);
    }

    private void hentBoeger(MaterialeKatalog katalog) {
        File fil = new File(BOEGER_FIL);
        if (!fil.exists()) {
            System.out.println(BOEGER_FIL + " findes ikke endnu — starter med et tomt katalog.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fil))) {
            String linje;
            int linjeNummer = 0;
            while ((linje = reader.readLine()) != null) {
                linjeNummer++;
                if (linje.isBlank()) {
                    continue;
                }
                try {
                    katalog.tilfoej(jsonTilBog(linje));
                } catch (RuntimeException e) {
                    System.out.println("Sprang ugyldig linje " + linjeNummer + " over i " + BOEGER_FIL
                            + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke læse " + BOEGER_FIL + ": " + e.getMessage());
        }
    }

    private Bog jsonTilBog(String linje) {
        String titel = null;
        String forfatter = null;
        String isbn = null;
        boolean tilgaengelig = true;

        for (String[] felt : udpakFelter(linje)) {
            switch (felt[0]) {
                case "titel" -> titel = felt[1];
                case "forfatter" -> forfatter = felt[1];
                case "isbn" -> isbn = felt[1];
                case "tilgaengelig" -> tilgaengelig = Boolean.parseBoolean(felt[1]);
                default -> throw new IllegalArgumentException("ukendt felt \"" + felt[0] + "\"");
            }
        }
        if (titel == null || forfatter == null || isbn == null) {
            throw new IllegalArgumentException("mangler titel, forfatter eller isbn");
        }

        Bog bog = new Bog(titel, forfatter, isbn);
        if (!tilgaengelig) {
            bog.laan();
        }
        return bog;
    }

    private void hentLaanere(LaanerRegister laanerRegister) {
        File fil = new File(LAANERE_FIL);
        if (!fil.exists()) {
            System.out.println(LAANERE_FIL + " findes ikke endnu — starter med et tomt lånerregister.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fil))) {
            String linje;
            int linjeNummer = 0;
            while ((linje = reader.readLine()) != null) {
                linjeNummer++;
                if (linje.isBlank()) {
                    continue;
                }
                try {
                    laanerRegister.registrer(jsonTilLaaner(linje));
                } catch (RuntimeException e) {
                    System.out.println("Sprang ugyldig linje " + linjeNummer + " over i " + LAANERE_FIL
                            + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke læse " + LAANERE_FIL + ": " + e.getMessage());
        }
    }

    private Laaner jsonTilLaaner(String linje) {
        String navn = null;
        String laanerId = null;

        for (String[] felt : udpakFelter(linje)) {
            switch (felt[0]) {
                case "navn" -> navn = felt[1];
                case "laanerId" -> laanerId = felt[1];
                default -> throw new IllegalArgumentException("ukendt felt \"" + felt[0] + "\"");
            }
        }
        if (navn == null || laanerId == null) {
            throw new IllegalArgumentException("mangler navn eller laanerId");
        }
        return new Laaner(navn, laanerId);
    }

    /**
     * Forenklet JSON-oplukning: forventer ét fladt objekt pr. linje, fx
     * {"titel":"Effective Java","tilgaengelig":true}. Splitter naivt på komma —
     * går galt, hvis en værdi selv indeholder et komma (se "Typiske fejl", gang 6).
     */
    private String[][] udpakFelter(String linje) {
        String indhold = linje.trim();
        if (!indhold.startsWith("{") || !indhold.endsWith("}")) {
            throw new IllegalArgumentException("linjen starter og slutter ikke med { og }: " + linje);
        }
        indhold = indhold.substring(1, indhold.length() - 1).trim();
        if (indhold.isEmpty()) {
            return new String[0][];
        }
        String[] parDele = indhold.split(",");
        String[][] felter = new String[parDele.length][2];
        for (int i = 0; i < parDele.length; i++) {
            String[] noegleVaerdi = parDele[i].split(":", 2);
            if (noegleVaerdi.length != 2) {
                throw new IllegalArgumentException("felt uden ':': " + parDele[i]);
            }
            felter[i][0] = fjernAnfoerselstegn(noegleVaerdi[0].trim());
            felter[i][1] = fjernAnfoerselstegn(noegleVaerdi[1].trim());
        }
        return felter;
    }

    private String fjernAnfoerselstegn(String s) {
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
