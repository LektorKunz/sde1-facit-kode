package bibliotek;

public interface Persistens {
    void gem(MaterialeKatalog katalog, LaanerRegister laanerRegister);
    void hent(MaterialeKatalog katalog, LaanerRegister laanerRegister);
}
