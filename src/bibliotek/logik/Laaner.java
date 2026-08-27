package bibliotek.logik;

public class Laaner {
    private String navn;
    private String laanerId;

    public Laaner(String navn, String laanerId) {
        this.navn = navn;
        this.laanerId = laanerId;
    }

    public String getNavn() {
        return navn;
    }

    public String getLaanerId() {
        return laanerId;
    }

    @Override
    public String toString() {
        return navn + " (lånerID: " + laanerId + ")";
    }
}
