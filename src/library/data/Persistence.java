package library.data;

import library.logic.MaterialCatalog;
import library.logic.BorrowerRegister;

public interface Persistence {
    void save(MaterialCatalog catalog, BorrowerRegister borrowerRegister);
    void load(MaterialCatalog catalog, BorrowerRegister borrowerRegister);
}
