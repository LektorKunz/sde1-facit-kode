package bibliotek.logik;

import java.util.ArrayList;
import java.util.List;

public class MaterialeKatalog {
    private List<Materiale> materialer = new ArrayList<>();

    public void tilfoej(Materiale materiale) {
        materialer.add(materiale);
    }

    public Materiale findVedTitel(String titel) {
        for (Materiale m : materialer) {
            if (m.getTitel().equals(titel)) {
                return m;
            }
        }
        return null;
    }

    public List<Materiale> getAlle() {
        return materialer;
    }
}
