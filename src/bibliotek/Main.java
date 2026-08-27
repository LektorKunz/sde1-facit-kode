package bibliotek;

public class Main {
    public static void main(String[] args) {
        Bibliotek bibliotek = new Bibliotek();
        bibliotek.tilfoejMateriale(new Bog("Effective Java", "Joshua Bloch", "978-0134685991"));
        bibliotek.tilfoejMateriale(new Tidsskrift("Ingeniøren", 42));
        bibliotek.tilfoejMateriale(new Film("The Matrix", 136));
        bibliotek.tilfoejMateriale(new Lydbog("Sapiens", "Morgan Freeman"));

        bibliotek.registrerLaaner(new Laaner("Anna Nielsen", "L001"));

        bibliotek.udlaan("The Matrix", "L001");           // en Film, lånt via PRÆCIS samme metode som en Bog
        bibliotek.udlaan("Ingeniøren (nr. 42)", "L001");  // et Tidsskrift, samme historie
        bibliotek.udlaan("Sapiens", "L001");               // vores egen fjerde materialetype, samme historie

        for (Materiale m : bibliotek.getMaterialer()) {
            System.out.println(m.getTitel() + " - " + (m.erTilgaengelig() ? "ledig" : "udlånt"));
        }
    }
}
