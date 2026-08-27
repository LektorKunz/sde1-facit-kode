package bibliotek;

// GANG 4 (uge 40), øvelse 2: en gyldig Laaner-subtype — kan substitueres overalt en almindelig
// Laaner bruges, uden at bryde LSP.
public class Underviser extends Laaner {
    private static final int UDVIDET_LAANEGRAENSE = 20;

    public Underviser(String navn, String laanerId) {
        super(navn, laanerId);
    }

    public int getLaanegraense() {
        return UDVIDET_LAANEGRAENSE;
    }

    // getNavn() og getLaanerId() arves uændret — ingen overraskelser for kode, der bruger Laaner
}
