package bibliotek.logik;

// GANG 4 (uge 40): LSP-eksempel. Den fulde dagsplan viser FØRST en brudt udgave, der overrider
// getLaanerId() til at kaste en exception — den er bevidst IKKE med i facit-koden her, kun den
// korrekte udgave, som eleverne selv skal aflevere. Fjernlaaner TILFØJER kun et postnummer, den
// ændrer ikke adfærd, Laaner allerede lovede.
public class Fjernlaaner extends Laaner {
    private String postnummer;

    public Fjernlaaner(String navn, String laanerId, String postnummer) {
        super(navn, laanerId);
        this.postnummer = postnummer;
    }

    public String getPostnummer() {
        return postnummer;
    }

    // Ingen overrides, der ÆNDRER adfærden af noget, Laaner allerede lovede.
    // getLaanerId() arves uændret og virker som forventet.
}
