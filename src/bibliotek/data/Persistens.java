package bibliotek.data;

import bibliotek.logik.MaterialeKatalog;
import bibliotek.logik.LaanerRegister;

public interface Persistens {
    void gem(MaterialeKatalog katalog, LaanerRegister laanerRegister);
    void hent(MaterialeKatalog katalog, LaanerRegister laanerRegister);
}
