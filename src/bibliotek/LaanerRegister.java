package bibliotek;

import java.util.ArrayList;
import java.util.List;

public class LaanerRegister {
    private List<Laaner> laanere = new ArrayList<>();

    public void registrer(Laaner laaner) {
        laanere.add(laaner);
    }

    public Laaner findVedId(String laanerId) {
        for (Laaner l : laanere) {
            if (l.getLaanerId().equals(laanerId)) {
                return l;
            }
        }
        return null;
    }

    public List<Laaner> getAlle() {
        return laanere;
    }
}
