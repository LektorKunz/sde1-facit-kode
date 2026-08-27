package bibliotek;

import bibliotek.data.FilPersistens;
import bibliotek.data.Persistens;
import bibliotek.logik.Bog;
import bibliotek.logik.Laaner;
import bibliotek.logik.LaanerRegister;
import bibliotek.logik.MaterialeKatalog;
import bibliotek.logik.UdlaansHaandtering;
import bibliotek.praesentation.KonsolUI;

public class Main {
    public static void main(String[] args) {
        // Datalag
        Persistens persistens = new FilPersistens(); // gemmer i boeger.json/laanere.json, jf. gang 6

        // Logiklag
        MaterialeKatalog katalog = new MaterialeKatalog();
        LaanerRegister laanerRegister = new LaanerRegister();
        UdlaansHaandtering udlaansHaandtering =
                new UdlaansHaandtering(katalog, laanerRegister, persistens);
        persistens.hent(katalog, laanerRegister);

        if (katalog.getAlle().isEmpty()) {
            katalog.tilfoej(new Bog("Effective Java", "Joshua Bloch", "978-0134685991"));
            katalog.tilfoej(new Bog("Clean Code", "Robert C. Martin", "978-0132350884"));
        }
        if (laanerRegister.getAlle().isEmpty()) {
            laanerRegister.registrer(new Laaner("Anna Nielsen", "L001"));
        }

        // Præsentationslag
        KonsolUI ui = new KonsolUI(katalog, laanerRegister, udlaansHaandtering);
        ui.start();
    }
}
