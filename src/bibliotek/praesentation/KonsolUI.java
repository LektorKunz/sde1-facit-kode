package bibliotek.praesentation;

import bibliotek.logik.LaanerRegister;
import bibliotek.logik.MaterialeKatalog;
import bibliotek.logik.Materiale;
import bibliotek.logik.UdlaansHaandtering;

import java.util.List;
import java.util.Scanner;

public class KonsolUI {
    private final MaterialeKatalog katalog;
    private final LaanerRegister laanerRegister;
    private final UdlaansHaandtering udlaansHaandtering;
    private final Scanner scanner = new Scanner(System.in);

    public KonsolUI(MaterialeKatalog katalog, LaanerRegister laanerRegister,
                     UdlaansHaandtering udlaansHaandtering) {
        this.katalog = katalog;
        this.laanerRegister = laanerRegister;
        this.udlaansHaandtering = udlaansHaandtering;
    }

    public void start() {
        boolean fortsaet = true;
        while (fortsaet) {
            visMenu();
            if (!scanner.hasNextLine()) {
                break; // input er sluppet op (fx ved en pipe/omdirigering) — afslut roligt
            }
            String valg = scanner.nextLine();
            switch (valg) {
                case "1" -> listMaterialer();
                case "2" -> udlaanMateriale();
                case "3" -> afleverMateriale();
                case "4" -> reserverMateriale();
                case "0" -> fortsaet = false;
                default -> System.out.println("Ukendt valg.");
            }
        }
    }

    private void visMenu() {
        System.out.println("""
                --- Biblioteket ---
                1) List materialer
                2) Lån materiale
                3) Aflever materiale
                4) Reservér materiale
                0) Afslut""");
    }

    private void listMaterialer() {
        List<Materiale> alle = katalog.getAlle();
        for (Materiale m : alle) {
            System.out.println(m.getTitel() + " - " + (m.erTilgaengelig() ? "ledig" : "udlånt"));
        }
    }

    private void udlaanMateriale() {
        System.out.print("Titel: ");
        String titel = scanner.nextLine();
        System.out.print("Lånernummer: ");
        String laanerId = scanner.nextLine();
        boolean lykkedes = udlaansHaandtering.udlaan(titel, laanerId);
        System.out.println(lykkedes ? "Udlånt." : "Kunne ikke udlåne.");
    }

    private void afleverMateriale() {
        System.out.print("Titel: ");
        String titel = scanner.nextLine();
        boolean lykkedes = udlaansHaandtering.aflever(titel);
        System.out.println(lykkedes ? "Afleveret." : "Kunne ikke aflevere.");
    }

    private void reserverMateriale() {
        System.out.print("Titel: ");
        String titel = scanner.nextLine();
        System.out.print("Lånernummer: ");
        String laanerId = scanner.nextLine();
        boolean lykkedes = udlaansHaandtering.reserverMateriale(titel, laanerId);
        System.out.println(lykkedes ? "Reserveret." : "Kunne ikke reservere.");
    }
}
