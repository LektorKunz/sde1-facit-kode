package library.data;

import library.logic.MaterialCatalog;
import library.logic.BorrowerRegister;

// SESSION 5 (week 43), exercise 2: another Persistence stand-in (not a real file), proving that
// LoanHandling does not need to change to use a different implementation (DIP).
public class CountPersistence implements Persistence {
    private int saveCallCount = 0;

    @Override
    public void save(MaterialCatalog catalog, BorrowerRegister borrowerRegister) {
        saveCallCount++;
        System.out.println("(CountPersistence: save() has now been called " + saveCallCount + " time(s))");
    }

    @Override
    public void load(MaterialCatalog catalog, BorrowerRegister borrowerRegister) {
        System.out.println("(CountPersistence: not loading real data yet)");
    }
}
