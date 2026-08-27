package bibliotek.data;

import bibliotek.logik.MaterialeKatalog;
import bibliotek.logik.LaanerRegister;

public class IngenPersistens implements Persistens {
    @Override
    public void gem(MaterialeKatalog katalog, LaanerRegister laanerRegister) {
        System.out.println("(IngenPersistens: gemmer ikke noget endnu — den rigtige udgave kommer i uge 44)");
    }

    @Override
    public void hent(MaterialeKatalog katalog, LaanerRegister laanerRegister) {
        System.out.println("(IngenPersistens: henter ikke noget endnu — den rigtige udgave kommer i uge 44)");
    }
}
