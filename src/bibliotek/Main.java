package bibliotek;

public class Main {
    public static void main(String[] args) {
        MaterialeKatalog katalog = new MaterialeKatalog();
        LaanerRegister laanerRegister = new LaanerRegister();
        Persistens persistens = new IngenPersistens();
        UdlaansHaandtering udlaansHaandtering = new UdlaansHaandtering(katalog, laanerRegister, persistens);

        katalog.tilfoej(new Bog("Effective Java", "Joshua Bloch", "978-0134685991"));
        katalog.tilfoej(new Tidsskrift("Ingeniøren", 42));
        katalog.tilfoej(new Film("The Matrix", 136));
        katalog.tilfoej(new Lydbog("Sapiens", "Morgan Freeman"));
        katalog.tilfoej(new Braetspil("Catan", 4));
        katalog.tilfoej(new Podcast("Klog på sprog", 87));

        laanerRegister.registrer(new Laaner("Anna Nielsen", "L001"));
        laanerRegister.registrer(new Fjernlaaner("Cecilie Berg", "L002", "8000"));
        laanerRegister.registrer(new Underviser("David Poulsen", "L003"));

        udlaansHaandtering.udlaan("Effective Java", "L001");
        udlaansHaandtering.udlaan("Catan", "L002");
        udlaansHaandtering.udlaan("Klog på sprog", "L003");

        udlaansHaandtering.reserverMateriale("The Matrix", "L003");        // virker — Film
        udlaansHaandtering.reserverMateriale("Ingeniøren (nr. 42)", "L003"); // "kan ikke reserveres"

        for (Materiale m : katalog.getAlle()) {
            System.out.println(m.getTitel() + " - " + (m.erTilgaengelig() ? "ledig" : "udlånt"));
        }
    }
}
