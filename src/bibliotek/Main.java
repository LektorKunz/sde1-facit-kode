package bibliotek;

public class Main {
    public static void main(String[] args) {
        MaterialeKatalog katalog = new MaterialeKatalog();
        LaanerRegister laanerRegister = new LaanerRegister();
        Persistens persistens = new FilPersistens(); // <- den ENESTE ændrede linje ift. gang 5

        persistens.hent(katalog, laanerRegister); // indlæs evt. gemte data, FØR noget andet sker

        UdlaansHaandtering udlaansHaandtering = new UdlaansHaandtering(katalog, laanerRegister, persistens);

        if (katalog.getAlle().isEmpty()) {
            System.out.println("Tomt katalog — tilføjer eksempeldata (første køretur).");
            katalog.tilfoej(new Bog("Effective Java", "Joshua Bloch", "978-0134685991"));
            katalog.tilfoej(new Bog("Clean Code", "Robert C. Martin", "978-0132350884"));
        }
        if (laanerRegister.getAlle().isEmpty()) {
            laanerRegister.registrer(new Laaner("Anna Nielsen", "L001"));
        }

        udlaansHaandtering.udlaan("Effective Java", "L001");

        for (Materiale m : katalog.getAlle()) {
            System.out.println(m.getTitel() + " - " + (m.erTilgaengelig() ? "ledig" : "udlånt"));
        }
    }
}
