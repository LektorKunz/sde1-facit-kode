package library.data;

import library.logic.MaterialCatalog;
import library.logic.BorrowerRegister;

public class NoPersistence implements Persistence {
    @Override
    public void save(MaterialCatalog catalog, BorrowerRegister borrowerRegister) {
        System.out.println("(NoPersistence: not saving anything yet — the real version arrives in week 44)");
    }

    @Override
    public void load(MaterialCatalog catalog, BorrowerRegister borrowerRegister) {
        System.out.println("(NoPersistence: not loading anything yet — the real version arrives in week 44)");
    }
}
