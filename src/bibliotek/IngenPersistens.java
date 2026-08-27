package bibliotek;

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
